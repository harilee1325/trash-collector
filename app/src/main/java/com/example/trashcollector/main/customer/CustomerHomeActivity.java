package com.example.trashcollector.main.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityCustomerHomeBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class CustomerHomeActivity extends AppCompatActivity {

    private ActivityCustomerHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.profile.setOnClickListener(v->{
            startActivity(new Intent(this, ProfileActivity.class));

        });

        binding.createRequestCard.setOnClickListener(v->{
            showBottomSheetDialog();
        });
        
        binding.seeRequestCard.setOnClickListener(v->{
            startActivity(new Intent(this, CompletedRequestActivity.class));
        });
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.activity_create_request);
        bottomSheetDialog.show();
    }
}