package com.wyj.androidx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import android.os.Bundle;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

/**
 * 学习参考链接
 * https://www.jianshu.com/p/4a922fa49f43
 * https://github.com/madlymad/PreferenceApp
 */
public class MainActivity extends AppCompatActivity
        implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(MyPreferenceFragment.FRAGMENT_TAG);
        if (null == fragment) {
            fragment = new MyPreferenceFragment();
        }
        fragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, MyPreferenceFragment.FRAGMENT_TAG)
                .commit()
        ;
    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat caller, PreferenceScreen pref) {

        return false;
    }


}
