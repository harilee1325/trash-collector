package com.example.trashcollector.main.customer;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityCompletedRequestBinding;
import com.example.trashcollector.main.Utility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CompletedRequestActivity extends AppCompatActivity implements RequestAdapter.OnItemClickListener, View.OnClickListener {

    private ActivityCompletedRequestBinding binding;
    private RequestAdapter adapter;
    ColorStateList def;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private ArrayList<RequestModel> requestModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCompletedRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        def = binding.customTab.select.getTextColors();
        binding.customTab.item1.setOnClickListener(this);
        binding.customTab.item2.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        getCompletedRequests();
    }

    private void getCompletedRequests() {

        requestModels.clear();
        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");
        db.collection("requests")
                .whereEqualTo(Utility.STATUS, "done")
                .get()
                .addOnCompleteListener(task -> {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

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
                            requestModels.add(model);
                        }
                        setUpAdapter(requestModels);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                    }
                }).addOnFailureListener(fail -> {
                    Log.d(TAG, "Error getting documents: " + fail.getLocalizedMessage());
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
        });
    }

    private void getInCompletedRequest() {

        requestModels.clear();
        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");
        db.collection("requests")
                .whereEqualTo(Utility.STATUS, "open")
                .get()
                .addOnCompleteListener(task -> {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

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

                            requestModels.add(model);
                        }
                        setUpAdapter(requestModels);
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                    }
                })
                .addOnFailureListener(fail -> {
                    Log.d(TAG, "Error getting documents: " + fail.getLocalizedMessage());
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                });
    }

    private void setUpAdapter(ArrayList<RequestModel> requestModels) {

        adapter = new RequestAdapter(requestModels);
        adapter.setOnItemClickListener(this);
        binding.collectionRv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item1) {
            binding.customTab.select.animate().x(0).setDuration(100);
            binding.customTab.item1.setTextColor(Color.WHITE);
            binding.customTab.item2.setTextColor(def);
            getCompletedRequests();
        } else if (view.getId() == R.id.item2) {
            getInCompletedRequest();
            binding.customTab.item1.setTextColor(def);
            binding.customTab.item2.setTextColor(Color.WHITE);
            int size = binding.customTab.item2.getWidth();
            binding.customTab.select.animate().x(size).setDuration(100);
        }
    }

    @Override
    public void onClickListener(String id) {
        Intent i = new Intent(this, CollectionDetail.class);
        i.putExtra("id", id);
        startActivity(i);
    }
}