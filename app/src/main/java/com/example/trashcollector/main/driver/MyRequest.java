package com.example.trashcollector.main.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityCompletedRequestBinding;
import com.example.trashcollector.databinding.ActivityMyRequestBinding;
import com.example.trashcollector.main.customer.CollectionDetail;
import com.example.trashcollector.main.customer.RequestAdapter;
import com.example.trashcollector.main.customer.RequestModel;

import java.util.ArrayList;

public class MyRequest extends AppCompatActivity implements RequestAdapter.OnItemClickListener, View.OnClickListener  {
    private ArrayList<RequestModel> requestModels = new ArrayList<>();

    private ActivityMyRequestBinding binding;
    private RequestAdapter adapter;
    ColorStateList def;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        def = binding.customTab.select.getTextColors();
        binding.customTab.item1.setOnClickListener(this);
        binding.customTab.item2.setOnClickListener(this);

        setUpAdapter();
    }
    private void setUpAdapter() {

        adapter = new RequestAdapter(requestModels);
        adapter.setOnItemClickListener(this);
        binding.collectionRv.setAdapter(adapter);

    }

    @Override
    public void onClickListener(String id) {
        startActivity(new Intent(this, DriverCollectionDetail.class));
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