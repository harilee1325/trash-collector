package com.example.trashcollector.main.officer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityAssignRequestBinding;
import com.example.trashcollector.databinding.ActivityDriverCollectionDetailBinding;
import com.example.trashcollector.main.Utility;
import com.example.trashcollector.main.customer.CommentAdapter;
import com.example.trashcollector.main.customer.RequestModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AssignRequestActivity extends AppCompatActivity {

    private ActivityAssignRequestBinding binding;
    private CommentAdapter adapter;
    private CommentAdapter adapterDriver, adapterOfficer;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAssignRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        id = getIntent().getStringExtra("id");
        getDetails(id);
        binding.officerComment.setOnClickListener(v->{
            showBottomSheetDialog();
        });

        binding.assignDriver.setOnClickListener(v->{
            Intent i = new Intent(this, AssignDriverActivity.class);
            i.putExtra("id", id);
            startActivity(i);
            //startActivity(new Intent(this, AssignDriverActivity.class  ));
        });
    }

    private void getDetails(String id) {

        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");

        DocumentReference docRef = db.collection("requests").document(id);
        docRef.get().addOnSuccessListener(document -> {
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
            RequestModel model = new RequestModel();
            model.setId(document.getId());
            model.setDate(document.getString(Utility.REQUEST_DATE));
            model.setDriverId(document.getString(Utility.DRIVER_ID));
            model.setDriverName(document.getString(Utility.DRIVER_NAME));
            model.setLat(document.getString(Utility.LAT));
            model.setLng(document.getString(Utility.LNG));
            model.setLocationName(document.getString(Utility.LOCATION));
            model.setStatus(document.getString(Utility.STATUS));
            model.setUserName(document.getString(Utility.USERNAME));
            model.setUserId(document.getString(Utility.USERID));
            model.setCommentDriver((List<String>) document.get(Utility.COMMENT_DRIVER));
            model.setCommentOfficer((List<String>) document.get(Utility.COMMENT_OFFICER));

            setUpView(model);
        }).addOnFailureListener(failure->{
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
            Toast.makeText(getApplicationContext(), failure.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        });

    }

    private void setUpView(RequestModel model) {

        binding.driverName.setText("Driver: "+model.getDriverName());
        binding.location.setText("Location: "+model.getLocationName());
        binding.requestTitle.setText(model.getDate());
        binding.status.setText("Status: "+model.getStatus());
        if (binding.status.getText().toString().contains("open")){
            binding.status.setTextColor(Color.RED);
        }else{
            binding.status.setTextColor(Color.GREEN);

        }
        setUpAdapter(model.getCommentDriver(), model.getCommentOfficer());
    }

    private void setUpAdapter(List<String> commentDriver, List<String> commentOfficer) {
        adapterDriver = new CommentAdapter(commentDriver);
        adapterOfficer = new CommentAdapter(commentOfficer);

        binding.commentDriverRv.setAdapter(adapterDriver);
        binding.commentOfficerRv.setAdapter(adapterOfficer);

    }
    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.enter_comment_layout);
        bottomSheetDialog.show();

        TextView comment = bottomSheetDialog.findViewById(R.id.comment);
        Button submitComment = bottomSheetDialog.findViewById(R.id.comment_bt);

        submitComment.setOnClickListener(v->{
            if (comment.getText().toString().isEmpty()){
                comment.setError("Please enter comment");
            }else{
                progressBar = ProgressDialog.show(this, "Adding officer comment", "Loading...");
                DocumentReference washingtonRef = db.collection("requests").document(id);
                washingtonRef
                        .update(Utility.COMMENT_OFFICER, FieldValue.arrayUnion(comment.getText().toString()))
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if (progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                                bottomSheetDialog.cancel();
                                getDetails(id);
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error updating document", e);
                                if (progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                                Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }


}