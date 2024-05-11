package uk.ac.le.co2103.part2;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CustomListAdapter extends ListAdapter<ShoppingList, ViewHolder> {
    private final ClickListener listener;

    public CustomListAdapter(@NonNull DiffUtil.ItemCallback<ShoppingList> diffCallback, ClickListener listener) {
        super(diffCallback);
        this.listener = listener;
    }



    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ShoppingList current = getItem(position);
        holder.bind(current.getName(), current.getImage());
        holder.itemView.setOnClickListener(view -> listener.onListItemClicked(current));
        holder.itemView.setOnLongClickListener(view -> {
            listener.onItemLongClickList(current);
            return true;
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ViewHolder.create(parent);
    }

    static class CustomListDiff extends DiffUtil.ItemCallback<ShoppingList> {
        @Override
        public boolean areItemsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem.getListId() == newItem.getListId();
        }
    }
}
