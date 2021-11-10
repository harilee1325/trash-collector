package com.example.trashcollector.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityLoginBinding;
import com.example.trashcollector.main.customer.CustomerHomeActivity;
import com.example.trashcollector.main.driver.DriverHomeActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.login.setOnClickListener(v -> {
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();

            if (username.isEmpty()) {
                binding.username.setError("Please enter email");
            } else if (password.isEmpty()) {
                binding.password.setError("Please enter password");
            } else {
                loginUse(username, password);
            }
        });
    }

    private void loginUse(String username, String password) {

        if (username.equalsIgnoreCase("hari"))
            startActivity(new Intent(this, CustomerHomeActivity.class));
        else{
            startActivity(new Intent(this, DriverHomeActivity.class));

        }
    }
}