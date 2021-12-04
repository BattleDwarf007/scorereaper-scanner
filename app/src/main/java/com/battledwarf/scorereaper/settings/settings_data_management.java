package com.battledwarf.scorereaper.settings;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.battledwarf.scorereaper.R;
import com.battledwarf.scorereaper.data.DatabaseHelperPoints;
import com.battledwarf.scorereaper.data.DatabaseHelperStopwatch;

public class settings_data_management extends AppCompatActivity {

    Dialog dialog;
    TextView showBtn, cancelBtn;
    EditText pwd;
    private DatabaseHelperPoints dbPoints;
    private DatabaseHelperStopwatch dbLaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu_data);

        createDialog();

        dbPoints = new DatabaseHelperPoints(this);
        dbLaps = new DatabaseHelperStopwatch(this);

        Button btnDataDelPoints = findViewById(R.id.buttonDeleteDataPoints);
        Button btnDataDelLaps = findViewById(R.id.buttonDeleteDataLaps);

        btnDataDelPoints.setOnClickListener(v -> {
            dialog.show();
            //YES
            showBtn.setOnClickListener(view -> {
                if (pwd.getText().toString().equals(getString(R.string.magic_word))) {
                    dbPoints.deleteAll();
                    dialog.dismiss();
                } else {
                    Toast.makeText(settings_data_management.this, "Wrong password", Toast.LENGTH_LONG).show();
                }

            });
            //CANCEL
            cancelBtn.setOnClickListener(view -> dialog.dismiss());
        });

        btnDataDelLaps.setOnClickListener(v -> {
            dialog.show();
            //YES
            showBtn.setOnClickListener(view -> {
                if (pwd.getText().toString().equals(getString(R.string.magic_word))) {
                    dbLaps.deleteAll();
                    dialog.dismiss();
                } else {
                    Toast.makeText(settings_data_management.this, "Wrong password", Toast.LENGTH_LONG).show();
                }

            });
            //CANCEL
            cancelBtn.setOnClickListener(view -> dialog.dismiss());
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }

    private void createDialog() {
        dialog = new Dialog(this);

        //SET TITLE
        dialog.setTitle("Confirm");

        //set content
        dialog.setContentView(R.layout.dialog_layout);

        showBtn = dialog.findViewById(R.id.showTxt);
        cancelBtn = dialog.findViewById(R.id.cancelTxt);
        pwd = dialog.findViewById(R.id.editTextTextPassword);
    }


}
