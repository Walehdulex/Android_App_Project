package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickListener {
    static final int NEW_LIST_CODE = 1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private ShoppingListViewModel shoppingListViewModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view_shopping_lists);
        final CustomListAdapter adapter = new CustomListAdapter(new CustomListAdapter.CustomListDiff(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        shoppingListViewModel = new ViewModelProvider(this).get(ShoppingListViewModel.class);
        shoppingListViewModel.getAllShoppingLists().observe(this, adapter::submitList);
        final FloatingActionButton button = findViewById(R.id.fab);
        button.setOnClickListener(view -> {
            Log.d(TAG, "Floating action button clicked.");
            openCreateListActivity(view);
        });
    }

    public void openCreateListActivity(View view) {
        Intent intent = new Intent(this, CreateListActivity.class);
        startActivityForResult(intent, NEW_LIST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_LIST_CODE && resultCode == RESULT_OK) {
            String listNameReply = data.getStringExtra("shoppingListName");
            Uri listImageUri = data.getParcelableExtra("shoppingListImageUri");
            if (listImageUri == null) {
                ShoppingList shoppingList = new ShoppingList(listNameReply, null);
                shoppingListViewModel.insert(shoppingList);
            } else {
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), listImageUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ByteArrayOutputStream bs = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bs);
                byte[] listImageByteArray = bs.toByteArray();
                ShoppingList shoppingList = new ShoppingList(listNameReply, listImageByteArray);
                shoppingListViewModel.insert(shoppingList);
            }
        }
    }

    @Override
    public void onListItemClicked(ShoppingList shoppingList) {
        Intent intent = new Intent(this, ShoppingListActivity.class);
        intent.putExtra("shoppingListId", shoppingList.getListId());
        startActivity(intent);
    }

    @Override
    public void onItemLongClickList(ShoppingList shoppingList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + shoppingList.getName() + "?").setPositiveButton("Delete", (dialog, which) -> {
            shoppingListViewModel.delete(shoppingList);
            Toast.makeText(MainActivity.this, "Shopping list deleted", Toast.LENGTH_SHORT).show();
        }).setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss()).show();
    }
}