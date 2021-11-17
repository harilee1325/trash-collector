package com.example.trashcollector.main.officer;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityCreateDriverBinding;
import com.example.trashcollector.databinding.ActivitySignupBinding;
import com.example.trashcollector.main.LoginActivity;
import com.example.trashcollector.main.SignupActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateDriverActivity extends AppCompatActivity {

    private ActivityCreateDriverBinding binding;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private FusedLocationProviderClient fusedLocationClient;
    private double lat = 0.0, lng = 0.0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        ActivityResultLauncher<String[]> locationPermissionRequest =
                registerForActivityResult(new ActivityResultContracts
                                .RequestMultiplePermissions(), result -> {
                            Boolean fineLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_FINE_LOCATION, false);
                            Boolean coarseLocationGranted = result.getOrDefault(
                                    Manifest.permission.ACCESS_COARSE_LOCATION, false);
                            if (fineLocationGranted != null && fineLocationGranted) {
                                // Precise location access granted.
                            } else if (coarseLocationGranted != null && coarseLocationGranted) {
                                // Only approximate location access granted.
                            } else {
                                Toast.makeText(getApplicationContext(), "Please give location access from settings to continue", Toast.LENGTH_SHORT).show();
                                // No location access granted.
                            }
                        }
                );
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                });
        binding.signup.setOnClickListener(v -> {
            createUser();
        });
    }

    private void createUser() {

        if (binding.firstname.getText().toString().isEmpty()){
            binding.firstname.setError("Please enter name");
        }else if (binding.email.getText().toString().isEmpty()){
            binding.email.setError("Please enter email");
        }else if (binding.phone.getText().toString().isEmpty()){
            binding.email.setError("Please enter phone number");
        }else if (binding.locationName.getText().toString().isEmpty()){
            binding.locationName.setError("Please enter location name");
        }else if (binding.password.getText().toString().isEmpty()){
            binding.password.setError("Please enter password");
        }else{
            createUserInDB();
        }

    }

    private void createUserInDB() {


        // Create a new user with a first, middle, and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", binding.firstname.getText().toString());
        user.put("email", binding.email.getText().toString());
        user.put("phone", binding.phone.getText().toString());
        user.put("location", binding.locationName.getText().toString());
        user.put("password", binding.password.getText().toString());
        user.put("lat",  String.valueOf(lat));
        user.put("lng",  String.valueOf(lng));
        user.put("type", "driver");
        progressBar = ProgressDialog.show(this, "Creating user", "Creating...");

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        if (progressBar.isShowing()) {
                            progressBar.dismiss();
                        }
                        startActivity(new Intent(CreateDriverActivity.this, LoginActivity.class));
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
}