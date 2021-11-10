package com.example.trashcollector.main.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityDriverCollectionDetailBinding;
import com.example.trashcollector.databinding.ActivityDriverHomeBinding;
import com.example.trashcollector.main.customer.CommentAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DriverCollectionDetail extends AppCompatActivity  {

    private ActivityDriverCollectionDetailBinding binding;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverCollectionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpAdapter();

        binding.driverComment.setOnClickListener(v->{
            showBottomSheetDialog();
        });
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.enter_comment_layout);
        bottomSheetDialog.show();
    }

    private void setUpAdapter() {
        adapter = new CommentAdapter();
        binding.commentDriverRv.setAdapter(adapter);
        binding.commentOfficerRv.setAdapter(adapter);

    }


}