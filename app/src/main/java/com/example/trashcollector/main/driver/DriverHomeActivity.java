package com.example.trashcollector.main.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityDriverHomeBinding;
import com.example.trashcollector.databinding.ActivityLoginBinding;

public class DriverHomeActivity extends AppCompatActivity {

    private ActivityDriverHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.seeMyRequest.setOnClickListener(v->{
            startActivity(new Intent(this, MyRequest.class));
        });

        binding.vehicleIssueCard.setOnClickListener(v->{
            startActivity(new Intent(this, VehicleIssue.class));

        });

    }
}