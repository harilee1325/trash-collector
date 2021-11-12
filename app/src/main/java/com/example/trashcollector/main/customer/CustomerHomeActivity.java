package com.example.trashcollector.main.customer;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityCustomerHomeBinding;
import com.example.trashcollector.main.LoginActivity;
import com.example.trashcollector.main.SignupActivity;
import com.example.trashcollector.main.Utility;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerHomeActivity extends AppCompatActivity {

    private ActivityCustomerHomeBinding binding;
    private FusedLocationProviderClient fusedLocationClient;
    private double lat = 0.0, lng = 0.0;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCustomerHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        db = FirebaseFirestore.getInstance();

        String name = Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.NAME);
        binding.username.setText("Welcome " + name);
        binding.profile.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.IS_LOGIN, "no");

        });

        binding.createRequestCard.setOnClickListener(v -> {
            showBottomSheetDialog();
        });

        binding.seeRequestCard.setOnClickListener(v -> {
            startActivity(new Intent(this, CompletedRequestActivity.class));
        });
    }

    private void showBottomSheetDialog() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.activity_create_request);
        bottomSheetDialog.show();
        Button createRequest = bottomSheetDialog.findViewById(R.id.create_request);
        TextView dateTv = bottomSheetDialog.findViewById(R.id.request_date);
        TextView locationTv = bottomSheetDialog.findViewById(R.id.request_location);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        System.out.println(formatter.format(date));
        dateTv.setText("New Request for "+formatter.format(date));
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
                        Log.e(TAG, "onSuccess: "+location );
                        if (location != null) {
                            // Logic to handle location object
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                            locationTv.setText("Latitude : "+lat +"\n"+"Longitude : "+lng);

                        }
                    }
                });


        createRequest.setOnClickListener(v -> {


            List<String> commentDriver = new ArrayList<>();
            List<String> commentOfficer = new ArrayList<>();

            Map<String, Object> user = new HashMap<>();
            user.put(Utility.REQUEST_DATE, "Collection for " + formatter.format(date));
            user.put(Utility.USERNAME, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.NAME));
            user.put(Utility.LOCATION, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.LOCATION));
            user.put(Utility.LAT, String.valueOf(lat));
            user.put(Utility.LNG,  String.valueOf(lng));
            user.put(Utility.STATUS, "open");
            user.put(Utility.DRIVER_ID, "not assigned");
            user.put(Utility.DRIVER_NAME, "not assigned");
            user.put(Utility.COMMENT_DRIVER, commentDriver);
            user.put(Utility.COMMENT_OFFICER, commentOfficer);
            user.put(Utility.DRIVER_NAME, "not assigned");

            user.put(Utility.USERID, Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.ID));
            progressBar = ProgressDialog.show(this, "Creating user", "Creating...");

            // Add a new document with a generated ID
            db.collection("requests")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            if (progressBar.isShowing()) {
                                progressBar.dismiss();
                            }
                            bottomSheetDialog.cancel();
                            Toast.makeText(getApplicationContext(), "Raised Trash Collection Request.", Toast.LENGTH_SHORT).show();
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

        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}