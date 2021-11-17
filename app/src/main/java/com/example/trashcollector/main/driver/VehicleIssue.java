package com.example.trashcollector.main.driver;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityVehicleIssueBinding;
import com.example.trashcollector.main.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleIssue extends AppCompatActivity implements View.OnClickListener , VehicleIssueAdapter.OnItemClickListener {

    private ActivityVehicleIssueBinding binding;
    private VehicleIssueAdapter adapter;
    ColorStateList def;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private List<IssueModel> issueModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVehicleIssueBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        def = binding.customTab.select.getTextColors();
        binding.customTab.item1.setOnClickListener(this);
        binding.customTab.item2.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        getVehicleIssuesDone();

        if (Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.TYPE).equalsIgnoreCase("officer")){
            binding.raiseVehicleIssue.setVisibility(View.GONE);
        }
        binding.raiseVehicleIssue.setOnClickListener(v->{
            raiseVehicleIssue();
        });
        setUpAdapter(issueModel);

    }

    private void getVehicleIssuesOpen() {
        issueModel.clear();
        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");
        if (Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.TYPE).equalsIgnoreCase("driver")) {

            db.collection("vehicle_issues")
                    .whereEqualTo(Utility.STATUS, "open")
                    .whereEqualTo(Utility.DRIVER_ID, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.ID))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                IssueModel model = new IssueModel();
                                model.setId(document.getId());
                                model.setIssue(document.getString(Utility.ISSUES));
                                model.setStatus(document.getString(Utility.STATUS));
                                model.setDriverName(document.getString(Utility.DRIVER_NAME));
                                issueModel.add(model);
                            }
                            setUpAdapter(issueModel);
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
        }else{
            db.collection("vehicle_issues")
                    .whereEqualTo(Utility.STATUS, "open")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                IssueModel model = new IssueModel();
                                model.setId(document.getId());
                                model.setIssue(document.getString(Utility.ISSUES));
                                model.setStatus(document.getString(Utility.STATUS));
                                model.setDriverName(document.getString(Utility.DRIVER_NAME));
                                issueModel.add(model);
                            }
                            setUpAdapter(issueModel);
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
    }

    private void getVehicleIssuesDone() {
        issueModel.clear();
        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");
        if (Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.TYPE).equalsIgnoreCase("driver")) {

            db.collection("vehicle_issues")
                    .whereEqualTo(Utility.STATUS, "done")
                    .whereEqualTo(Utility.DRIVER_ID, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.ID))
                    .get()
                    .addOnCompleteListener(task -> {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                IssueModel model = new IssueModel();
                                model.setId(document.getId());
                                model.setIssue(document.getString(Utility.ISSUES));
                                model.setStatus(document.getString(Utility.STATUS));
                                model.setDriverName(document.getString(Utility.DRIVER_NAME));
                                issueModel.add(model);
                            }
                            setUpAdapter(issueModel);
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
        }else{
            db.collection("vehicle_issues")
                    .whereEqualTo(Utility.STATUS, "done")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                IssueModel model = new IssueModel();
                                model.setId(document.getId());
                                model.setIssue(document.getString(Utility.ISSUES));
                                model.setStatus(document.getString(Utility.STATUS));
                                model.setDriverName(document.getString(Utility.DRIVER_NAME));
                                issueModel.add(model);
                            }
                            setUpAdapter(issueModel);
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
    }

    private void raiseVehicleIssue() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.enter_comment_layout);
        bottomSheetDialog.show();

        TextView comment = bottomSheetDialog.findViewById(R.id.comment);
        Button submitComment = bottomSheetDialog.findViewById(R.id.comment_bt);

        submitComment.setOnClickListener(v-> {
            if (comment.getText().toString().isEmpty()) {
                comment.setError("Please enter comment");
            } else {
                Map<String, Object> user = new HashMap<>();
                user.put(Utility.DRIVER_ID, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.ID));
                user.put(Utility.ISSUES, comment.getText().toString());
                user.put(Utility.STATUS, "open");
                user.put(Utility.DRIVER_NAME, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.NAME));

                progressBar = ProgressDialog.show(this, "Creating vehicle issue", "Creating...");

                // Add a new document with a generated ID
                db.collection("vehicle_issues")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                if (progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                            onBackPressed();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error adding document", e);
                                if (progressBar.isShowing()) {
                                    progressBar.dismiss();
                                }
                            }
                        });
            }
        });
    }




    private void setUpAdapter(List<IssueModel> issueModel) {
        adapter = new VehicleIssueAdapter(issueModel);
        adapter.setOnItemClickListener(this);
        binding.issuesRv.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.item1){
            binding.customTab.select.animate().x(0).setDuration(100);
            binding.customTab.item1.setTextColor(Color.WHITE);
            binding.customTab.item2.setTextColor(def);
            getVehicleIssuesDone();
        } else if (view.getId() == R.id.item2){
            getVehicleIssuesOpen();
            binding.customTab.item1.setTextColor(def);
            binding.customTab.item2.setTextColor(Color.WHITE);
            int size =  binding.customTab.item2.getWidth();
            binding.customTab.select.animate().x(size).setDuration(100);
        }
    }

    @Override
    public void onClickListener(String id) {
        progressBar = ProgressDialog.show(this, "Assigning driver", "Loading...");
        DocumentReference ref1 = db.collection("vehicle_issues").document(id);
        ref1
                .update(Utility.STATUS, "done")
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                      onBackPressed();
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
}