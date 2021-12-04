package com.battledwarf.scorereaper.stopwatch;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.battledwarf.scorereaper.R;
import com.battledwarf.scorereaper.data.DatabaseHelperStopwatch;
import com.battledwarf.scorereaper.util.Constants;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class all_laps_scan_data extends Activity {

    private DatabaseHelperStopwatch db;
    private ListView listViewNames;
    private List<laps> allLaps;
    private String server_url, server_user, server_password;
    Dialog dialog;
    TextView contentTxt;
    Button yesBtn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_all_laps_scan_data);

        createDialog();

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        server_url = prefs.getString("prefServerAddress", null);
        server_user = prefs.getString("prefServerUser", null);
        server_password = prefs.getString("prefServerPassword", null);

        //initializing views and objects
        db = new DatabaseHelperStopwatch(this);
        allLaps = new ArrayList<>();

        listViewNames = findViewById(R.id.listViewNames);

        //calling the method to load all the stored names
        loadNames();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(view -> {
            saveScansToServer();
            Toast.makeText(all_laps_scan_data.this, "Upload Complete", Toast.LENGTH_LONG).show();

        });
    }

    /*
     * this method will
     * load the names from the database
     * with updated sync status
     * */
    private void loadNames() {
        allLaps.clear();
        Cursor cursor = db.getScans();
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") laps name = new laps(
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_CAR)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_LAP_TIME)),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_STATUS))
                );
                allLaps.add(name);
            } while (cursor.moveToNext());
        }

        LapsScansAdapter nameAdapter = new LapsScansAdapter(this, R.layout.lv_laps, allLaps);
        listViewNames.setAdapter(nameAdapter);
    }

    @SuppressLint("Range")
    private void saveScansToServer() {

        db = new DatabaseHelperStopwatch(this);

        //getting all the unsynced names
        Cursor cursor = db.getUnsyncedScans();
        if (cursor.moveToFirst()) {
            do {
                //calling the method to save the unsynced name to MySQL
                sendPost(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_CAR)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_USER)),
                        cursor.getLong(cursor.getColumnIndex(DatabaseHelperStopwatch.COLUMN_LAP_TIME))
                );
            } while (cursor.moveToNext());

        }
    }

    public void sendPost(final int id, String car, final String location,final String user, final Long lap_time) {
        if (server_password != null && server_user != null && server_url != null) {
        Thread thread = new Thread(() -> {
            try {
                URL url = new URL(server_url + "/stopwatch");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                String authString = "Basic " + Base64.encodeToString((server_user + ":" + server_password).getBytes(), Base64.NO_WRAP);
                conn.setRequestProperty("Authorization", authString);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject params = new JSONObject();
                params.put("car", car);
                params.put("location", location);
                params.put("user", user);
                params.put("lap_time", lap_time);

                Log.i("JSON", params.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                os.writeBytes(params.toString());

                os.flush();
                os.close();

                int status = conn.getResponseCode();
                if (status == 202) {
                    db.updateSyncStatus(id, Constants.SYNCED_WITH_SERVER);
                } else {
                    db.updateSyncStatus(id, Constants.NOT_SYNCED);
                }

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        } else {
            contentTxt.setText(R.string.Server_settings_error);
            dialog.show();
            yesBtn.setOnClickListener(v -> dialog.dismiss());
        }
    }

    private void createDialog() {
        dialog = new Dialog(this);
        //SET TITLE
        dialog.setTitle("Error");

        //set content
        dialog.setContentView(R.layout.generic_error_dialog_layout);

        yesBtn = dialog.findViewById(R.id.btnYes);
        contentTxt = dialog.findViewById(R.id.contentTxt);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

}
