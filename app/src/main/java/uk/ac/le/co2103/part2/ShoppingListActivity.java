package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoppingListActivity extends AppCompatActivity implements ProductSelectListener {
    static final int NEW_PRODUCT_CODE = 3;
    static final int UPDATE_PRODUCT_CODE = 5;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseReadExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private ProductViewModel productViewModel;
    private int shoppingListId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        RecyclerView recyclerView = findViewById(R.id.View);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final ProductListAdapter adapter = new ProductListAdapter(new ProductListAdapter.ProductDiff(), this);
        recyclerView.setAdapter(adapter);

        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        Intent intent = getIntent();
        shoppingListId = intent.getIntExtra("shoppingListId", -1);
        productViewModel.getProductsByShoppingListId(shoppingListId).observe(this, adapter::submitList);
    }

    public void openAddProductActivity(View view) {
        Intent intent = new Intent(this, AddProductActivity.class);
        startActivityForResult(intent, NEW_PRODUCT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_PRODUCT_CODE && resultCode == RESULT_OK) {
            handleNewProductData(data);
        }
        if (requestCode == UPDATE_PRODUCT_CODE && resultCode == RESULT_OK) {
            handleUpdateProductData(data);
        }
    }

    private void handleNewProductData(Intent data) {
        String productName = data.getStringExtra("productName");
        String productQuantity = data.getStringExtra("productQuantity");
        String productUnit = data.getStringExtra("productUnit");

        databaseReadExecutor.execute(() -> {
            boolean exists = productViewModel.productExistsInList(productName, shoppingListId);
            if (!exists) {
                Product product = new Product(productName, productQuantity, productUnit, shoppingListId);
                productViewModel.insert(product);
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Product already exists", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void handleUpdateProductData(Intent data) {
        int productId = data.getIntExtra("productId", -1);
        String productName = data.getStringExtra("productName");
        String productQuantity = data.getStringExtra("productQuantity");
        String productUnit = data.getStringExtra("productUnit");

        databaseReadExecutor.execute(() -> {
            boolean existsInList = productViewModel.productExistsInList(productName, shoppingListId);
            boolean existsInListWithSameId = productViewModel.productExistsInListAndIdSame(productName, shoppingListId, productId);
            if (!existsInList || existsInListWithSameId) {
                productViewModel.updateProductGivenId(productId, productName, productQuantity, productUnit);
            } else {
                runOnUiThread(() -> Toast.makeText(this, "Product with same name exists", Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public void onProductClicked(Product product) {
        Toast.makeText(this, product.getName() + "\nQuantity: " + product.getQuantity() + ", Unit: " + product.getUnit(), Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(product.getName());
        builder.setMessage("Select an option")
                .setPositiveButton("Delete", (dialog, which) -> {
                    productViewModel.delete(product);
                    Toast.makeText(ShoppingListActivity.this, "Product deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Edit", (dialog, which) -> {
                    Intent intent = new Intent(ShoppingListActivity.this, UpdateProductActivity.class);
                    intent.putExtra("productId", product.getProductId());
                    intent.putExtra("productName", product.getName());
                    intent.putExtra("productQuantity", product.getQuantity());
                    intent.putExtra("productUnit", product.getUnit());
                    startActivityForResult(intent, UPDATE_PRODUCT_CODE);
                })
                .setNeutralButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }
}
