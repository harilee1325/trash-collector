package com.example.trashcollector.main.officer;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trashcollector.databinding.DriverAssignDialogBinding;
import com.example.trashcollector.main.Utility;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class AssignDriverActivity extends AppCompatActivity implements DriverAdapter.OnItemClickListener {

    private DriverAssignDialogBinding binding;
    private DriverAdapter adapter;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private String id;
    private List<DriverModel> driverModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DriverAssignDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        id = getIntent().getStringExtra("id");
        getDrivers();
    }

    private void getDrivers() {

        progressBar = ProgressDialog.show(this, "Fetching Requests", "Loading...");
        db.collection("users")
                .whereEqualTo(Utility.TYPE, "driver")
                .get()
                .addOnCompleteListener(task -> {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            DriverModel model = new DriverModel();
                            model.setId(document.getId());
                            model.setEmail(document.getString(Utility.EMAIL));
                            model.setName(document.getString(Utility.NAME));
                            model.setLocation(document.getString(Utility.LOCATION));
                            model.setLat(document.getString(Utility.LAT));
                            model.setLng(document.getString(Utility.LNG));
                            model.setPhone(document.getString(Utility.PHONE));
                            model.setType(document.getString(Utility.TYPE));
                            driverModels.add(model);
                        }
                        setUpAdapter(driverModels);
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

    private void setUpAdapter(List<DriverModel> driverModels) {
        adapter = new DriverAdapter(driverModels);
        adapter.setOnItemClickListener(this);
        binding.driverListRv.setAdapter(adapter);
    }

    @Override
    public void onClickListener(String id, String name) {
        progressBar = ProgressDialog.show(this, "Assigning driver", "Loading...");
        DocumentReference ref1 = db.collection("requests").document(this.id);
        ref1
                .update(Utility.DRIVER_NAME, name)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
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

        DocumentReference ref2 = db.collection("requests").document(this.id);
        ref2
                .update(Utility.DRIVER_ID, id)
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
