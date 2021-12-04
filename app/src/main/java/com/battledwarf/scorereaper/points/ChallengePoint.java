package com.battledwarf.scorereaper.points;

import static com.battledwarf.scorereaper.R.id;
import static com.battledwarf.scorereaper.R.layout;
import static com.battledwarf.scorereaper.util.StringUtils.getDateTime;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.battledwarf.scorereaper.R;
import com.battledwarf.scorereaper.data.DatabaseHelperPoints;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ChallengePoint extends AppCompatActivity {
    String inputScore = null;
    String username, activity;
    Dialog dialog;
    TextView contentTxt;
    Button yesBtn, noBtn;

    boolean Btn1Enabled;
    boolean Btn2Enabled;
    boolean Btn3Enabled;
    boolean Btn4Enabled;
    boolean Btn5Enabled;
    private TextInputLayout score;

    private Button btnScanner;

    private DatabaseHelperPoints db;
    private List<points> pointsScans;
    private PointsScansAdapter pointsAdapter;
    private ListView listViewNames;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.app_point_scoring_layout);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        createDialog();


        username = prefs.getString("prefUsername", null);
        activity = prefs.getString("prefActivityName", "default");

        //initializing views and objects
        db = new DatabaseHelperPoints(this);
        pointsScans = new ArrayList<>();
        listViewNames = findViewById(id.listViewNamesShort);

        //Setting scan button default state to disabled
        btnScanner = findViewById(id.butScanner);
        btnScanner.setEnabled(false);

        score = findViewById(id.score);
        Objects.requireNonNull(score.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String scoreinput = score.getEditText().getText().toString().trim();
                btnScanner.setEnabled(!scoreinput.isEmpty());

            }

            @Override
            public void afterTextChanged(Editable s) {
                inputScore = s.toString();
                showToast();
            }
        });

        // Button1
        Button btn1 = findViewById(id.btnScore1);
        Btn1Enabled = prefs.getBoolean("swState1", false);
        if (!Btn1Enabled) {
            btn1.setVisibility(View.GONE);
        }

        btn1.setText(prefs.getString("btnValue1", null));
        btn1.setOnClickListener(v -> {
            inputScore = prefs.getString("btnValue1", null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                btnScanner.setEnabled(!Objects.requireNonNull(inputScore).isEmpty());
            }
            showToast();
        });


        Button btn2 = findViewById(id.btnScore2);
        Btn2Enabled = prefs.getBoolean("swState2", false);
        if (!Btn2Enabled) {
            btn2.setVisibility(View.GONE);
        }
        btn2.setText(prefs.getString("btnValue2", null));
        btn2.setOnClickListener(v -> {
            inputScore = prefs.getString("btnValue2", null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                btnScanner.setEnabled(!Objects.requireNonNull(inputScore).isEmpty());
            }
            showToast();
        });

        Button btn3 = findViewById(id.btnScore3);
        Btn3Enabled = prefs.getBoolean("swState3", false);
        if (!Btn3Enabled) {
            btn3.setVisibility(View.GONE);
        }
        btn3.setText(prefs.getString("btnValue3", null));
        btn3.setOnClickListener(v -> {
            inputScore = prefs.getString("btnValue3", null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                btnScanner.setEnabled(!Objects.requireNonNull(inputScore).isEmpty());
            }
            showToast();
        });

        Button btn4 = findViewById(id.btnScore4);
        Btn4Enabled = prefs.getBoolean("swState4", false);
        if (!Btn4Enabled) {
            btn4.setVisibility(View.GONE);
        }
        btn4.setText(prefs.getString("btnValue4", null));
        btn4.setOnClickListener(v -> {
            inputScore = prefs.getString("btnValue4", null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                btnScanner.setEnabled(!Objects.requireNonNull(inputScore).isEmpty());
            }
            showToast();
        });

        Button btn5 = findViewById(id.btnScore5);
        Btn5Enabled = prefs.getBoolean("swState5", false);
        if (!Btn5Enabled) {
            btn5.setVisibility(View.GONE);
        }
        btn5.setText(prefs.getString("btnValue5", null));
        btn5.setOnClickListener(v -> {
            inputScore = prefs.getString("btnValue5", null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                btnScanner.setEnabled(!Objects.requireNonNull(inputScore).isEmpty());
            }
            showToast();
        });


        btnScanner.setOnClickListener(view -> doTheScanning());


        //calling the method to load all the stored names
        loadNames();


        FloatingActionButton fab = findViewById(id.fab_more);
        fab.setOnClickListener(view -> {
            startActivity(new Intent(ChallengePoint.this, all_points_scan_data.class));
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
                Toast.makeText(ChallengePoint.this, "Not Scanned!", Toast.LENGTH_SHORT).show();
            } else {
                if (db.CheckIfRecordExist(result.getContents())) {
                    contentTxt.setText(String.format("A record for this car (%s) already exist. do you want to replace it?", result.getContents()));
                    dialog.show();
                    yesBtn.setOnClickListener(v -> {
                        dialog.dismiss();
                        updateScanInLocalStorage(result.getContents(), activity, username, inputScore, getDateTime());

                        Toast.makeText(ChallengePoint.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    });
                    //No
                    noBtn.setOnClickListener(v -> {
                        dialog.dismiss();
                        Toast.makeText(ChallengePoint.this, "Not updated!", Toast.LENGTH_SHORT).show();
                    });

                } else {
                    saveScanToLocalStorage(result.getContents(), activity, username, inputScore, getDateTime());
                    Toast.makeText(ChallengePoint.this, "Stored Successfully!", Toast.LENGTH_SHORT).show();
                }
                Objects.requireNonNull(score.getEditText()).setText("");
                btnScanner.setEnabled(false);

                //add last scaned item to list view on scan view
                loadNames();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void showToast() {
        Toast.makeText(this, inputScore, Toast.LENGTH_SHORT).show();
    }


    private void refreshList() {
        pointsAdapter.notifyDataSetChanged();
    }

    private void loadNames() {
        pointsScans.clear();
        Cursor cursor = db.getLastScan();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") points name = new points(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperPoints.COLUMN_CAR)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperPoints.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperPoints.COLUMN_POINTS)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelperPoints.COLUMN_STATUS))
                );
                pointsScans.add(name);
            } while (cursor.moveToNext());
        }

        pointsAdapter = new PointsScansAdapter(this, layout.lv_points, pointsScans);
        listViewNames.setAdapter(pointsAdapter);
    }

    private void saveScanToLocalStorage(String car, String activity, String user, String points, String timestamp) {
        db.addScan(car, activity, user, points, timestamp, 0);
        points n = new points(car, timestamp, points, 0);
        pointsScans.add(n);
        refreshList();
    }

    private void updateScanInLocalStorage(String car, String activity, String user, String points, String timestamp) {
        db.updateScan(car, activity, user, points, timestamp, 0);
        points n = new points(car, timestamp, points, 0);
        pointsScans.add(n);
        refreshList();
    }

    private void createDialog() {
        dialog = new Dialog(this);
        //SET TITLE
        dialog.setTitle("Confirm");

        //set content
        dialog.setContentView(R.layout.duplicate_scan_dialog_layout);

        yesBtn = dialog.findViewById(R.id.btnYes);
        noBtn = dialog.findViewById(R.id.btnNo);
        contentTxt = dialog.findViewById(R.id.contentTxt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}


