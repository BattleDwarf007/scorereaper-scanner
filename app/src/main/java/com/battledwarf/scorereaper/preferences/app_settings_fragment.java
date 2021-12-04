package com.battledwarf.scorereaper.preferences;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragment;

import com.battledwarf.scorereaper.R;

@SuppressWarnings("ALL")
public class app_settings_fragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // below line is used to add preference
        // fragment from our xml folder.
        addPreferencesFromResource(R.xml.settings);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    }
}