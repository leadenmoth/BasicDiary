package me.astashenkov.basicdiary;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;


public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String s) {

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
