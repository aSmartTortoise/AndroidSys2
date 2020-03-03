package com.wyj.androidx;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

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
        PreferenceCategory category = findPreference("category");
        PreferenceScreen preferenceScreen = findPreference("key_root");

//        category.removePreference(findPreference("key_3"));

        ListPreference listPreference = new ListPreference(getContext());
        listPreference.setKey("key_6");
        listPreference.setTitle("第二个listPreferenceDialog");
        listPreference.setSummary("来来来，喝完这杯，还有三杯");
        listPreference.setEntries(new String[]{"A", "B", "C", "D"});
        listPreference.setEntryValues(new String[]{"A", "B", "C", "D"});
        category.addPreference(listPreference);

        Preference preference1 = findPreference("key_1");
        preference1.setTitle("i am a switchButton");

        EditTextPreference editTextPreference = new EditTextPreference(getContext());
        editTextPreference.setKey("key_7");
        editTextPreference.setTitle("i am a edittext");
        editTextPreference.setSummary("scorpions");
        editTextPreference.setDialogTitle("请输入一首歌的名字，最好是英文歌曲");
        editTextPreference.setDialogMessage("摇滚乐最佳");
        editTextPreference.setText("摇滚不死，青春永驻");
        category.addPreference(editTextPreference);

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
