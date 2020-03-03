package com.wyj.androidx;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPreferenceFragment extends PreferenceFragmentCompat
        implements Preference.OnPreferenceChangeListener {
    private final String TAG = "MyPreferenceFragment";

    public static final String FRAGMENT_TAG = "my_preference_fragment";
    public MyPreferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(TAG, "onPreferenceChange: wyj " + newValue);
        return false;
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        Log.d(TAG, "onPreferenceTreeClick: wyj  " + preference.getKey());
        return super.onPreferenceTreeClick(preference);
    }
}
