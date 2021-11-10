package com.example.trashcollector.main.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityDriverHomeBinding;
import com.example.trashcollector.databinding.ActivityLoginBinding;
import com.example.trashcollector.main.LoginActivity;
import com.example.trashcollector.main.Utility;

public class DriverHomeActivity extends AppCompatActivity {

    private ActivityDriverHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profile.setOnClickListener(v->{
            startActivity(new Intent(this, LoginActivity.class));
            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.IS_LOGIN, "no");

        });
        binding.seeMyRequest.setOnClickListener(v->{
            startActivity(new Intent(this, MyRequest.class));
        });

        binding.vehicleIssueCard.setOnClickListener(v->{
            startActivity(new Intent(this, VehicleIssue.class));

        });

    }
}