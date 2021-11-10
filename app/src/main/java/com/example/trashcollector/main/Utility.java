package com.example.trashcollector.main;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Utility {
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String LOCATION = "location";
    public static final String PHONE = "phone";
    public static final String ID = "id";

    public static final String REQUEST_DATE = "request-date";
    public static final String USERNAME = "user-name";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String STATUS = "status";
    public static final String DRIVER_ID = "driver-id";
    public static final String DRIVER_NAME = "driver-name";
    public static final String USERID = "user-id";
    public static final String IS_LOGIN = "is_login";
    public static final String TYPE = "type";


    private static Utility utilityInstance;

    private Utility() {
    }

    public static synchronized Utility getUtilityInstance() {
        if (null == utilityInstance) {
            utilityInstance = new Utility();
        }
        return utilityInstance;

    }

    public void setPreference(Context context, String key, String value) {

        SharedPreferences.Editor editor = context.getSharedPreferences("trash", Context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();

    }

    public String getPreference(Context context, String key) {

        SharedPreferences prefs = context.getSharedPreferences("trash", Context.MODE_PRIVATE);
        String result = prefs.getString(key, "");
        return result;
    }


}
