package com.example.trashcollector.main.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityDriverCollectionDetailBinding;
import com.example.trashcollector.databinding.ActivityDriverHomeBinding;
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
import java.util.Locale;

public class DriverCollectionDetail extends AppCompatActivity  {

    private ActivityDriverCollectionDetailBinding binding;
    private CommentAdapter adapterDriver, adapterOfficer;
    private List<String> comment = new ArrayList<>();
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private String id;
    private RequestModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverCollectionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        id = getIntent().getStringExtra("id");
        getDetails(id);
        binding.driverComment.setOnClickListener(v->{
            showBottomSheetDialog();
        });
        binding.pickedUpBt.setOnClickListener(v->{
            updateRequestStatus();
        });
        binding.rejectBt.setOnClickListener(v->{
            rejectRequestStatus();
        });
        binding.driverLocation.setOnClickListener(v->{
            redirectToGoogleMaps();
        });
    }

    private void redirectToGoogleMaps() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + model.getLat() + ">,<" + model.getLng() + ">?q=<" + model.getLat() + ">,<" + model.getLng()+ ">(" + "Name" + ")"));
        startActivity(intent);
    }

    private void rejectRequestStatus() {
        progressBar = ProgressDialog.show(this, "Rejecting request", "Loading...");
        DocumentReference ref = db.collection("requests").document(id);
        ref
                .update(Utility.DRIVER_ID, "")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
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
        DocumentReference ref2 = db.collection("requests").document(id);
        ref2
                .update(Utility.DRIVER_NAME, "")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
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
        DocumentReference ref3 = db.collection("requests").document(id);
        ref3
                .update(Utility.COMMENT_DRIVER, FieldValue.arrayUnion(Utility
                        .getUtilityInstance().getPreference(getApplicationContext(), Utility.NAME)+" Rejected this Request"))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
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

    private void updateRequestStatus() {
        progressBar = ProgressDialog.show(this, "Updating status of request", "Loading...");
        DocumentReference ref = db.collection("requests").document(id);
        ref
                .update(Utility.STATUS, "done")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
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

    private void getDetails(String id) {

        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");

        DocumentReference docRef = db.collection("requests").document(id);
        docRef.get().addOnSuccessListener(document -> {
            if (progressBar.isShowing()) {
                progressBar.dismiss();
            }
            model = new RequestModel();
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
                progressBar = ProgressDialog.show(this, "Adding driver comment", "Loading...");
                DocumentReference ref = db.collection("requests").document(id);
                ref
                        .update(Utility.COMMENT_DRIVER, FieldValue.arrayUnion(comment.getText().toString()))
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