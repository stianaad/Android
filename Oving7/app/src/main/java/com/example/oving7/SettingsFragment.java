package com.example.oving7;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}