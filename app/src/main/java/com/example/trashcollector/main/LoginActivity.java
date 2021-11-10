package com.example.trashcollector.main;

import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.trashcollector.R;
import com.example.trashcollector.databinding.ActivityLoginBinding;
import com.example.trashcollector.main.customer.CustomerHomeActivity;
import com.example.trashcollector.main.driver.DriverHomeActivity;
import com.example.trashcollector.main.officer.OfficerHomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseFirestore db;
    private ProgressDialog progressBar;
    private final int LOCATION_REQUEST_CODE = 1001;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseFirestore.getInstance();
        askLocationPermission();
        binding.login.setOnClickListener(v -> {
            String username = binding.username.getText().toString();
            String password = binding.password.getText().toString();

            if (username.isEmpty()) {
                binding.username.setError("Please enter email");
            } else if (password.isEmpty()) {
                binding.password.setError("Please enter password");
            } else {
                loginUse(username, password);
            }
        });

        binding.createUser.setOnClickListener(v -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
    }

    private void askLocationPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);

            }


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("TAG", "onRequestPermissionsResult: got location");
            } else {
                System.out.println("location permission not granted");
            }
        }
    }

    private void loginUse(String username, String password) {

        progressBar = ProgressDialog.show(this, "Validating user", "Loading...");
        db.collection("users")
                .whereEqualTo("email", username)
                .whereEqualTo("password", password)
                .get()
                .addOnCompleteListener(task -> {
                    if (progressBar.isShowing()) {
                        progressBar.dismiss();
                    }
                    if (task.isSuccessful()) {

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());

                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.TYPE, document.getString("type"));
                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.EMAIL, document.getString("email"));
                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.NAME, document.getString("name"));
                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.LOCATION, document.getString("location"));
                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.PHONE, document.getString("phone"));
                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.ID, document.getId());
                            Utility.getUtilityInstance().setPreference(getApplicationContext(), Utility.IS_LOGIN, document.getString("yes"));

                            String type = document.getString("type");
                            if (type.equalsIgnoreCase("user"))
                                startActivity(new Intent(LoginActivity.this, CustomerHomeActivity.class));
                            else if (type.equalsIgnoreCase("driver")) {
                                startActivity(new Intent(LoginActivity.this, DriverHomeActivity.class));
                            } else {
                                startActivity(new Intent(LoginActivity.this, OfficerHomeActivity.class));
                            }
                        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}