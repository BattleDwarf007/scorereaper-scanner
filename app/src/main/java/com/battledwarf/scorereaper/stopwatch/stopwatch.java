package com.battledwarf.scorereaper.stopwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.battledwarf.scorereaper.R;
import com.battledwarf.scorereaper.data.DatabaseHelperStopwatch;
import com.battledwarf.scorereaper.util.Constants;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.battledwarf.scorereaper.util.StringUtils.getStopWatchFormattedTime;
import static com.battledwarf.scorereaper.util.StringUtils.isEmpty;


public class stopwatch extends AppCompatActivity {

    boolean isStart;
    String codeId;
    Date startTime;
    Date lapTime;
    String username, activity;
    Button butStartStop;
    private Chronometer stopwatch;
    private DatabaseHelperStopwatch db;
    private List<laps> lapsscans;
    private LapsScansAdapter scansAdapter;
    private ListView listViewNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_laps);
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //initializing views and objects
        db = new DatabaseHelperStopwatch(this);
        lapsscans = new ArrayList<>();
        listViewNames = findViewById(R.id.listViewNamesShortlaps);

        isStart = false;

        username = prefs.getString("prefUsername", null);
        activity = prefs.getString("prefActivityName", "default");

        stopwatch = findViewById(R.id.stopwatch);

        Button butScanner = findViewById(R.id.butScanner);
        butScanner.setOnClickListener(view -> doTheScanning());



        butStartStop = findViewById(R.id.butStartStop);
        butStartStop.setEnabled(false);
        butStartStop.setOnClickListener(view -> {

            if (isEmpty(codeId)) {
                if (!isStart) {
                    startTime = new Date();
                    saveScanToLocalStorage(codeId, activity, username, (long) 0);
                    stopwatch.setBase(SystemClock.elapsedRealtime());
                    stopwatch.start();
                    butStartStop.setText(R.string.stop);
                    isStart = true;

                } else {
                    lapTime = new Date();
                    long diff = lapTime.getTime() - startTime.getTime();
                    saveScanToLocalStorage(codeId, activity, username, diff);
                    stopwatch.stop();
                    isStart = false;
                    butStartStop.setText(R.string.start);
                }
            }

        });

        Button butLap = findViewById(R.id.butLaps);
        butLap.setOnClickListener(v -> {
            if (isEmpty(codeId)) {
                lapTime = new Date();
                long diff = lapTime.getTime() - startTime.getTime();
                saveScanToLocalStorage(codeId, activity, username, diff);
            }

        });

        stopwatch.setOnChronometerTickListener(chronometer -> stopwatch.setText(getStopWatchFormattedTime((SystemClock.elapsedRealtime() - chronometer.getBase()))));

        loadNames();


        FloatingActionButton fab = findViewById(R.id.fab_more);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(stopwatch.this, all_laps_scan_data.class));
            overridePendingTransition(0, 0);
        });

    }

    public void doTheScanning() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(stopwatch.this, "Not Scanned!", Toast.LENGTH_SHORT).show();
            } else {
                codeId = result.getContents();
                stopwatch.stop();
                stopwatch.setText(R.string.stopwats_begin);
                isStart = false;
                butStartStop.setEnabled(true);
                //Scanned successfully
            }
        } else super.onActivityResult(requestCode, resultCode, data);
    }

    private void refreshList() {
        scansAdapter.notifyDataSetChanged();
    }


    private void loadNames() {
        lapsscans.clear();
        Cursor cursor = db.getLastScan();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") laps name = new laps(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_CAR)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_LAP_TIME)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_STATUS))
                );
                lapsscans.add(name);
            } while (cursor.moveToNext());
        }

        scansAdapter = new LapsScansAdapter(this, R.layout.lv_laps, lapsscans);
        listViewNames.setAdapter(scansAdapter);
    }

    private void saveScanToLocalStorage(String car, String location, String user, Long lapTime) {
        db.addLapStartScan(car, location, user, lapTime, Constants.NOT_SYNCED);
        laps n = new laps(car, lapTime);
        lapsscans.add(n);
        refreshList();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
