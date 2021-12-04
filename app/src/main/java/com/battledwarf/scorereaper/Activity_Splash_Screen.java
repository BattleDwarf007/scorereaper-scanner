package com.battledwarf.scorereaper;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Activity_Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen_activity);

        new Handler().postDelayed(() -> {
            // This method will be executed once the timer is over
            Intent i = new Intent(Activity_Splash_Screen.this, Activity_Home_Page.class);
            startActivity(i);
            overridePendingTransition(0, 0);
            finish();
        }, 10000);
    }
}