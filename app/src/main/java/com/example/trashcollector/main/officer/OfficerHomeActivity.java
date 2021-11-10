package com.example.trashcollector.main.officer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityDriverHomeBinding;
import com.example.trashcollector.databinding.ActivityOfficerHomeBinding;
import com.example.trashcollector.main.driver.VehicleIssue;

public class OfficerHomeActivity extends AppCompatActivity {

    private ActivityOfficerHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOfficerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.vehicleIssueCard.setOnClickListener(v->{
            startActivity(new Intent(this, VehicleIssue.class));

        });

        binding.newRequestCard.setOnClickListener(v->{
            startActivity(new Intent(this, NewRequestActivity.class));

        });
    }
}