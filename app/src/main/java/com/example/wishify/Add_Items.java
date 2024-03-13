package com.example.wishify;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wishify.databinding.AddItemsBinding;

public class Add_Items extends AppCompatActivity {
    AddItemsBinding binding;
    DatabaseHelper items_dbHelper;
    private Uri imageUri;
    //intent method for click and jump to anther activity
    public static Intent getIntent(Context context) {
        return new Intent(context, Add_Items.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items_dbHelper = new DatabaseHelper(this);
        imageUri = Uri.EMPTY;
        binding.itemImg.setOnClickListener(this::pickImage);
        binding.addItem.setOnClickListener(this::saveItem);

    }


    //Add Item Method and form validation also
    private void saveItem(View view) {
        //Item Event
        String event = binding.itemEvent.getText().toString().trim();
        if (event.isEmpty()) {
            binding.itemEvent.setError("Event field is empty");
            binding.itemEvent.requestFocus();
        }
        //Item Name
        String name = binding.itemName.getText().toString().trim();
        if (name.isEmpty()) {
            binding.itemName.setError("Name is empty ");
            binding.itemName.requestFocus();
        }

        if (items_dbHelper.insertItem(event , name, imageUri.toString()) ){
            Toast.makeText(this, "Save Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    //Pick image from emulator internal storage and camera
    private void pickImage(View view) {
        ImagePickUtility.pickImage(view, Add_Items.this);
    }


    //set image uri
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            imageUri = data.getData();
            binding.itemImg.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}