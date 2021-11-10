package com.example.trashcollector.main.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityCollectionDetailBinding;
import com.example.trashcollector.databinding.ActivityCustomerHomeBinding;

public class CollectionDetail extends AppCompatActivity {

    private ActivityCollectionDetailBinding binding;
    private CommentAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCollectionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setUpAdapter();
    }

    private void setUpAdapter() {
        adapter = new CommentAdapter();
        binding.commentDriverRv.setAdapter(adapter);
        binding.commentOfficerRv.setAdapter(adapter);

    }
}