package com.example.oving7;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class SettingsActivity extends AppCompatActivity{
    public static final String ALLOW_MULTIPLE_USERS = "allowMultipleUsers";
    public static final String ENVIRONMENT_ID = "envId";
    public static final String ACCOUNT = "account";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content,
                        new SettingsFragment()).commit();

        SharedPreferences appPrefs = getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = appPrefs.edit();
        boolean isEnabled = appPrefs.getBoolean("white", true);
        Log.i("hehehe", String.valueOf(isEnabled));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
