package com.battledwarf.scorereaper.preferences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.battledwarf.scorereaper.R;

import java.util.Objects;

public class app_settings_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // below line is to change
        // the title of our action bar.
        Objects.requireNonNull(getSupportActionBar()).setTitle("Settings");

        // below line is used to check if
        // frame layout is empty or not.
        if (findViewById(R.id.idFrameLayout) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // below line is to inflate our fragment.
            getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new app_settings_fragment()).commit();
        }
    }
}