package com.example.wishify;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.wishify.databinding.EditItemsBinding;

public class EditItem extends AppCompatActivity {
    EditItemsBinding binding;
    public static final String ID="id";
    public static final String EVENT="event";
    public static final String NAME="name";
    public static final String DESCRIPTION="description";
    public static final String IMAGE="image";
    public static final String IS_PURCHASED="purchased";

    private DatabaseHelper items_dbHelper;
    private Uri imageUri;
    private int id;
    private boolean isPurchased;


    //put date from model class
    public static Intent getIntent(Context context,ItemsModel itemsModel){
        Intent intent=new Intent(context,EditItem.class);
        intent.putExtra(ID ,itemsModel.getId());
        intent.putExtra(EVENT,itemsModel.getName());
        intent.putExtra(NAME,itemsModel.getName());
        intent.putExtra(DESCRIPTION,itemsModel.getDescription());
        intent.putExtra(IMAGE,itemsModel.getImage().toString());
        intent.putExtra(IS_PURCHASED,itemsModel.isPurchased());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=EditItemsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        items_dbHelper=new DatabaseHelper(this);
        Bundle bundle=getIntent().getExtras();
        id=bundle.getInt(Items_Details_Page.ID);
        isPurchased=bundle.getBoolean(Items_Details_Page.IS_PURCHASED);
        String name=bundle.getString(Items_Details_Page.NAME);
        String price=bundle.getString(Items_Details_Page.PRICE);
        String description=bundle.getString(Items_Details_Page.DESCRIPTION);
        imageUri=Uri.EMPTY;

        try {
            imageUri=Uri.parse(bundle.getString(Items_Details_Page.IMAGE));
        }catch (NullPointerException e){
            Toast.makeText(this, "Error occurred in identifying Image ", Toast.LENGTH_SHORT).show();
        }
        binding.editItemName.setText(name);
        binding.editItemPrice.setText(price);
        binding.editItemDescription.setText(description);
        binding.editItemImage.setImageURI(imageUri);

        binding.editItemImage.setOnClickListener(this::pickImage);
        binding.btnEdit.setOnClickListener(this::saveItem);
    }
    //pick image from internal storage
    private void pickImage(View view){
        ImagePickUtility.pickImage(view,EditItem.this);
    }
    //create and save new updated detail
    private void saveItem(View view){
        String event=binding.editItemName.getText().toString().trim();
        if (event.isEmpty()){
            binding.editItemName.setError("Event field is empty");
            binding.editItemName.requestFocus();
        }
        String name=binding.editItemName.getText().toString().trim();
        if (name.isEmpty()){
            binding.editItemName.setError("Name field is empty");
            binding.editItemName.requestFocus();
        }
        String description= binding.editItemDescription.getText().toString().trim();
        if (description.isEmpty()){
            binding.editItemDescription.setError("Description is empty ");
            binding.editItemDescription.requestFocus();
        }
        Log.d("EditItem","saving : {"+ "id:"+id+",name:"+name+",name:"+name+"" +
                ",description:"+description+",imageUri:"+imageUri.toString()+"" +
                ",isPurchased:"+isPurchased+"}");

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data!=null){
            imageUri=data.getData();
            binding.editItemImage.setImageURI(imageUri);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void finish(){
        super.finish();
    }
}