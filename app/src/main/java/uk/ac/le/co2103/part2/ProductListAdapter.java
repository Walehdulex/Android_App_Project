package uk.ac.le.co2103.part2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class ProductListAdapter extends ListAdapter<Product, ProductViewHolder> {
    private final ProductSelectListener listener;

    public ProductListAdapter(@NonNull DiffUtil.ItemCallback<Product> diffCallback, ProductSelectListener listener) {
        super(diffCallback);
        this.listener = listener;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ProductViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Product current = getItem(position);
        holder.bind(current.getName(), current.getQuantity(), current.getUnit());
        holder.itemView.setOnClickListener(view -> listener.onProductClicked(current));
    }

    static class ProductDiff extends DiffUtil.ItemCallback<Product> {
        @Override
        public boolean areItemsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Product oldItem, @NonNull Product newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }

}

