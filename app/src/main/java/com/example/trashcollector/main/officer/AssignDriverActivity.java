package com.example.trashcollector.main.officer;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trashcollector.databinding.ActivityAssignRequestBinding;
import com.example.trashcollector.databinding.DriverAssignDialogBinding;

public class AssignDriverActivity extends AppCompatActivity {

    private DriverAssignDialogBinding binding;
    private DriverAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DriverAssignDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpAdapter();
    }

    private void setUpAdapter() {
    }
}
