package uk.ac.le.co2103.part2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView shoppingListTextView;
    private final ImageView shoppingListImageView;

    private ViewHolder(View itemView) {
        super(itemView);
        shoppingListTextView = itemView.findViewById(R.id.textView);
        shoppingListImageView = itemView.findViewById(R.id.imageview);
    }

    static ViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    public void bind(String text, byte[] imageByteArray) {
        shoppingListTextView.setText(text);
        if (imageByteArray == null) {
            shoppingListImageView.setVisibility(View.GONE);
        } else {
            Bitmap imageBitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            shoppingListImageView.setImageBitmap(imageBitmap);
            shoppingListImageView.setVisibility(View.VISIBLE);
        }
    }
}
