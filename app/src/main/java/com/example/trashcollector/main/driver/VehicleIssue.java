package com.example.trashcollector.main.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityDriverCollectionDetailBinding;
import com.example.trashcollector.databinding.ActivityVehicleIssueBinding;
import com.example.trashcollector.main.customer.CommentAdapter;
import com.example.trashcollector.main.customer.RequestAdapter;

public class VehicleIssue extends AppCompatActivity implements View.OnClickListener {

    private ActivityVehicleIssueBinding binding;
    private VehicleIssueAdapter adapter;
    ColorStateList def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVehicleIssueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        def = binding.customTab.select.getTextColors();
        binding.customTab.item1.setOnClickListener(this);
        binding.customTab.item2.setOnClickListener(this);
        setUpAdapter();

    }
    private void setUpAdapter() {
        adapter = new VehicleIssueAdapter();
        binding.issuesRv.setAdapter(adapter);
        binding.issuesRv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item1){
            binding.customTab.select.animate().x(0).setDuration(100);
            binding.customTab.item1.setTextColor(Color.WHITE);
            binding.customTab.item2.setTextColor(def);
        } else if (view.getId() == R.id.item2){
            binding.customTab.item1.setTextColor(def);
            binding.customTab.item2.setTextColor(Color.WHITE);
            int size =  binding.customTab.item2.getWidth();
            binding.customTab.select.animate().x(size).setDuration(100);
        }
    }
}