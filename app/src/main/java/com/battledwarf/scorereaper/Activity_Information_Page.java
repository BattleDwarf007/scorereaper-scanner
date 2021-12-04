package com.battledwarf.scorereaper;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class Activity_Information_Page extends AppCompatActivity {
    private Disposable networkDisposable;
private TextView txtConectivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TextView txtServerStatus;
        TextView txtVersion;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_menu_information);

        txtConectivity = findViewById(R.id.textView_connectivity);
//        txtInternetStatus = findViewById(R.id.textView_internet_status);
        txtVersion = findViewById(R.id.textView_version);

//        txtVersion.setText(String.format("Version: %s", BuildConfig.VERSION_NAME));
        txtVersion.setText("Version: 123");




    }

    @Override protected void onResume() {
        super.onResume();

        networkDisposable = ReactiveNetwork.observeNetworkConnectivity(getApplicationContext())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(connectivity -> {
                    Log.d(TAG, connectivity.toString());
                    final NetworkInfo.State state = connectivity.state();
                    final String name = connectivity.typeName();
                    txtConectivity.setText(String.format("State: %s, Connection Type: %s", state, name));
                });

    }

    @Override protected void onPause() {
        super.onPause();
        safelyDispose(networkDisposable);
    }

    private void safelyDispose(Disposable... disposables) {
        for (Disposable subscription : disposables) {
            if (subscription != null && !subscription.isDisposed()) {
                subscription.dispose();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);
    }
}
