package com.battledwarf.scorereaper;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.battledwarf.scorereaper.stopwatch.stopwatch;
import com.battledwarf.scorereaper.points.ChallengePoint;
import com.battledwarf.scorereaper.preferences.app_settings_Activity;
import com.battledwarf.scorereaper.settings.settings_data_management;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class Activity_Home_Page extends AppCompatActivity {

    Button butCheckpoint;
    Button butLaps;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);

        butCheckpoint = findViewById(R.id.butCheckpoint);
        butCheckpoint.setOnClickListener(v -> {
            Intent i = new Intent(Activity_Home_Page.this, ChallengePoint.class);
            startActivity(i);
            overridePendingTransition(0, 0);
        });

        butLaps = findViewById(R.id.butLaps);
        butLaps.setOnClickListener(v -> {
            Intent i = new Intent(Activity_Home_Page.this, stopwatch.class);
            startActivity(i);
            overridePendingTransition(0, 0);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_settings) {
            // launch settings activity
            startActivity(new Intent(Activity_Home_Page.this, app_settings_Activity.class));
            return true;
        } else if (id == R.id.menu_data_settings) {
            // launch settings activity
            startActivity(new Intent(Activity_Home_Page.this, settings_data_management.class));
            return true;
        } else if (id == R.id.menu_about) {
            // launch settings activity
            startActivity(new Intent(Activity_Home_Page.this, Activity_Information_Page.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
