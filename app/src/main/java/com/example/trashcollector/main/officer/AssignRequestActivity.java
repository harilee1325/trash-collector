package com.example.trashcollector.main.officer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityAssignRequestBinding;
import com.example.trashcollector.databinding.ActivityDriverCollectionDetailBinding;
import com.example.trashcollector.main.customer.CommentAdapter;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class AssignRequestActivity extends AppCompatActivity {

    private ActivityAssignRequestBinding binding;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setUpAdapter();
        binding.officerComment.setOnClickListener(v->{
            showBottomSheetDialog();
        });

        binding.assignDriver.setOnClickListener(v->{
            startActivity(new Intent(this, AssignDriverActivity.class  ));
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