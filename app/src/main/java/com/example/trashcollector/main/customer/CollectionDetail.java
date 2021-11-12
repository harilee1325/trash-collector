package com.example.trashcollector.main.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.trashcollector.databinding.ActivityCollectionDetailBinding;
import com.example.trashcollector.main.Utility;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CollectionDetail extends AppCompatActivity {

    private ActivityCollectionDetailBinding binding;
    private CommentAdapter adapterDriver, adapterOfficer;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCollectionDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String id = getIntent().getStringExtra("id");
        db = FirebaseFirestore.getInstance();
        getDetails(id);
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
}