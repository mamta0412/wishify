package com.example.wishify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishify.databinding.ResetPasswordBinding;

public class ResetPassword extends AppCompatActivity {
    ResetPasswordBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseHelper=new DatabaseHelper(this);

        Intent intent=getIntent();
        String email = intent.getStringExtra("email");
        binding.email.setText(intent.getStringExtra("email"));
        binding.confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String email=binding.email.getText().toString();
                String password=binding.newPassword.getText().toString().trim();
                String rePassword=binding.rePassword.getText().toString().trim();

                if (password.equals(rePassword)){
                    Boolean checkPasswordUpdate=databaseHelper.updatePassword(email, password);
                    if (checkPasswordUpdate==true){
                        Intent updatePasswordIntent=new Intent(getApplicationContext(),Login_Page.class);
                        startActivity(updatePasswordIntent);
                        Toast.makeText(ResetPassword.this, "Password Updated", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(ResetPassword.this, "Password not update ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}