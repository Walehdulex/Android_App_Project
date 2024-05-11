package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateProductActivity extends AppCompatActivity {

    private TextView editName;
    private TextView editQuantity;
    private Spinner editUnitSpinner;
    private int productId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);
        Intent intent = getIntent();
        editName = findViewById(R.id.edit_text_product_name);
        editQuantity = findViewById(R.id.edit_text_product_quantity);
        editUnitSpinner = findViewById(R.id.edit_text_product_unit);
        Button plusButton = findViewById(R.id.button_increment_quantity);
        Button minusButton = findViewById(R.id.button_decrement_quantity);
        productId = intent.getIntExtra("productId", -1);
        String currentName = intent.getStringExtra("productName");
        String currentQuantity = intent.getStringExtra("productQuantity");
        String currentUnit = intent.getStringExtra("productUnit");
        editName.setText(currentName);
        editQuantity.setText(currentQuantity);
        int spinnerIndex;
        switch (currentUnit) {
            case "Kg":
                spinnerIndex = 1;
                break;
            case "Liter":
                spinnerIndex = 2;
                break;
            default:
                spinnerIndex = 0;
        }
        editUnitSpinner.setSelection(spinnerIndex);
        plusButton.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(editQuantity.getText().toString());
            int newValue = currentValue + 1;
            editQuantity.setText(String.valueOf(newValue));
        });
        minusButton.setOnClickListener(v -> {
            int currentValue = Integer.parseInt(editQuantity.getText().toString());
            int newValue = currentValue - 1;
            if (newValue >= 0) {
                editQuantity.setText(String.valueOf(newValue));
            }
        });
    }
    public void OnProductUpdate(View view) {
        Intent replyIntent = new Intent(this, ShoppingListActivity.class);
        String productName = editName.getText().toString();
        String productQuantity = editQuantity.getText().toString();
        String productUnit = editUnitSpinner.getSelectedItem().toString();
        if (productName.length() <= 0) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
        } else {
            replyIntent.putExtra("productId", productId);
            replyIntent.putExtra("productName", productName);
            replyIntent.putExtra("productQuantity", productQuantity);
            replyIntent.putExtra("productUnit", productUnit);
            setResult(RESULT_OK, replyIntent);
            finish();
        }
    }

}