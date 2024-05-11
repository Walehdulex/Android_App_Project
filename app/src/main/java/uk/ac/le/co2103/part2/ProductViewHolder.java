package uk.ac.le.co2103.part2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ProductViewHolder  extends RecyclerView.ViewHolder {
    private final TextView productName;
    private final TextView productQuantity;
    private final TextView productUnit;


    private ProductViewHolder(View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.editTextName);
        productQuantity = itemView.findViewById(R.id.editTextQuantity);
        productUnit= itemView.findViewById(R.id.editTextQuantity);
    }
    public void bind(String name, String quantity, String unit) {
        productName.setText(name);
        productQuantity.setText(quantity);
        productUnit.setText(unit);
    }
    static ProductViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_update_product, parent, false);
        return new ProductViewHolder(view);
    }
}
