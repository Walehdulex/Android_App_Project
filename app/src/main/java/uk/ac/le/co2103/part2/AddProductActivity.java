package uk.ac.le.co2103.part2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddProductActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextQuantity;
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        editTextName = findViewById(R.id.editTextName);
        editTextQuantity = findViewById(R.id.editTextQuantity);
        spinner = findViewById(R.id.spinner);
    }
    public void returnAddProduct(View view) {
        Intent replyIntent = new Intent(this, ShoppingListActivity.class);
        String productName = editTextName.getText().toString();
        String productQuantity = editTextQuantity.getText().toString();
        String productUnit = spinner.getSelectedItem().toString();
        if (productName.length() < 1) {
            Toast.makeText(this, "productName", Toast.LENGTH_SHORT).show();
        } else if (productQuantity.length() < 1) {
            Toast.makeText(this, "productQuantity", Toast.LENGTH_SHORT).show();
        } else {
            replyIntent.putExtra("productName", productName);
            replyIntent.putExtra("productQuantity", productQuantity);
            replyIntent.putExtra("productUnit", productUnit);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }
}