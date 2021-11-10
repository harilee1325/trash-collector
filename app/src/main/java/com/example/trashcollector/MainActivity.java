package com.example.trashcollector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.trashcollector.main.LoginActivity;
import com.example.trashcollector.main.Utility;
import com.example.trashcollector.main.customer.CustomerHomeActivity;
import com.example.trashcollector.main.driver.DriverHomeActivity;
import com.example.trashcollector.main.officer.OfficerHomeActivity;
import com.google.firebase.installations.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String status = Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.IS_LOGIN);
                if (!status.equalsIgnoreCase("yes")){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else{
                    String type = Utility.getUtilityInstance().getPreference(getApplicationContext(), Utility.TYPE);
                    if (type.equalsIgnoreCase("user"))
                        startActivity(new Intent(MainActivity.this, CustomerHomeActivity.class));
                    else if (type.equalsIgnoreCase("driver")) {
                        startActivity(new Intent(MainActivity.this, DriverHomeActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, OfficerHomeActivity.class));
                    }
                }

            }
        }, 2000);
    }
}