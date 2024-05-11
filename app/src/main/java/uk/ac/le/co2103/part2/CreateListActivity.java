package uk.ac.le.co2103.part2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateListActivity extends AppCompatActivity {
    static final int SELECT_IMAGE_CODE = 2;
    private static final String TAG = CreateListActivity.class.getSimpleName();
    private EditText replyEditText;
    private ImageView replyImageView;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        replyEditText = findViewById(R.id.editText);
        replyImageView = findViewById(R.id.imageImageview);
        Button selectImageButton = findViewById(R.id.chooseImageButton);
        imageUri = null;
        replyImageView.setImageURI(null);
        selectImageButton.setOnClickListener(view -> {
            Log.d(TAG, "selectImageButton clicked");
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, SELECT_IMAGE_CODE);
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            replyImageView.setImageURI(imageUri);
        }
    }
    public void returnReply(View view) {
        Intent replyIntent = new Intent(this, MainActivity.class);
        String listName = replyEditText.getText().toString();
        if (listName.length() > 0) {
            replyIntent.putExtra("shoppingListName", listName);
            replyIntent.putExtra("shoppingListImageUri", imageUri);
            setResult(RESULT_OK, replyIntent);
            finish();
        } else {
            Toast.makeText(this, "List name", Toast.LENGTH_SHORT).show();
        }
    }

}