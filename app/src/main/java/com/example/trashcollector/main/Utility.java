package com.example.trashcollector.main;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class Utility {
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
