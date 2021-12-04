package com.battledwarf.scorereaper.settings;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import com.battledwarf.scorereaper.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


@SuppressLint("UseSwitchCompatOrMaterialCode")
public class settings_score_btn_management extends AppCompatActivity {

    private Switch sw1, sw2, sw3, sw4, sw5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu_score_settings);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        TextInputLayout btnValue1 = findViewById(R.id.btnValue1);
        Objects.requireNonNull(btnValue1.getEditText()).setText(prefs.getString("btnValue1", null));
        btnValue1.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("btnValue1", s.toString()).apply();
            }
        });

        TextInputLayout btnValue2 = findViewById(R.id.btnValue2);
        Objects.requireNonNull(btnValue2.getEditText()).setText(prefs.getString("btnValue2", null));
        btnValue2.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("btnValue2", s.toString()).apply();
            }
        });

        TextInputLayout btnValue3 = findViewById(R.id.btnValue3);
        Objects.requireNonNull(btnValue3.getEditText()).setText(prefs.getString("btnValue3", null));
        btnValue3.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("btnValue3", s.toString()).apply();
            }
        });

        TextInputLayout btnValue4 = findViewById(R.id.btnValue4);
        Objects.requireNonNull(btnValue4.getEditText()).setText(prefs.getString("btnValue4", null));
        btnValue4.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("btnValue4", s.toString()).apply();
            }
        });

        TextInputLayout btnValue5 = findViewById(R.id.btnValue5);
        Objects.requireNonNull(btnValue5.getEditText()).setText(prefs.getString("btnValue5", null));
        btnValue5.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                prefs.edit().putString("btnValue5", s.toString()).apply();
            }
        });


        // switch stuffs

        sw1 = findViewById(R.id.switch1);
        sw1.setChecked(prefs.getBoolean("swState1", false));
        sw1.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("swState1", sw1.isChecked()).apply());


        sw2 = findViewById(R.id.switch2);
        sw2.setChecked(prefs.getBoolean("swState2", false));
        sw2.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("swState2", sw2.isChecked()).apply());


        sw3 = findViewById(R.id.switch3);
        sw3.setChecked(prefs.getBoolean("swState3", false));
        sw3.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("swState3", sw3.isChecked()).apply());


        sw4 = findViewById(R.id.switch4);
        sw4.setChecked(prefs.getBoolean("swState4", false));
        sw4.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("swState4", sw4.isChecked()).apply());


        sw5 = findViewById(R.id.switch5);
        sw5.setChecked(prefs.getBoolean("swState5", false));
        sw5.setOnCheckedChangeListener((buttonView, isChecked) -> prefs.edit().putBoolean("swState5", sw5.isChecked()).apply());


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
