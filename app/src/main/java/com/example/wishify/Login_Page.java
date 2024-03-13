package com.example.wishify;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.wishify.databinding.LoginPageBinding;

public class Login_Page extends AppCompatActivity {
   LoginPageBinding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=LoginPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        databaseHelper= new DatabaseHelper(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=binding.email.getText().toString().trim();
                String password=binding.password.getText().toString().trim();

                if(email.equals("") || password.equals("")){
                    Toast.makeText(Login_Page.this, "All field are mandatory", Toast.LENGTH_SHORT).show();
                }else {
                    Boolean checkCredential=databaseHelper.checkEmailPassword(email,password);
                    if (checkCredential==true){
                        Toast.makeText(Login_Page.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(Login_Page.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        binding.txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ForgotPassword_Page.class);
                startActivity(intent);
            }
        });
        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Signup_Page.class);
                startActivity(intent);
            }
        });

    }
}