package com.yatty.sevenatenine.client;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

public class PreferenceFragment extends android.preference.PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    // TODO: fixme
    public static volatile boolean musicEnabled;

    public static final String TAG = "PreferenceFragment";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Log.i(TAG, "Music settings changed. SharedPreferences key: " + key);
        musicEnabled = sharedPreferences.getBoolean(key, true);
        if (musicEnabled) {
            Log.i(TAG, "Music enabled");
            BackgroundMusicService.getInstance().start();
        } else {
            Log.i(TAG, "Music disabled");
            BackgroundMusicService.getInstance().pause();
        }
    }
}