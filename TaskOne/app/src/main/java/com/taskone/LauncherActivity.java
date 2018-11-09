package com.taskone;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.taskone.service.PlayLocation;
import com.taskone.utilis.AppIntegers;
import com.taskone.utilis.AppMethods;

public class LauncherActivity extends AppCompatActivity {
    LocationManager locationManager;
    int close_data = AppIntegers.splash.splash_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        showGpsAlert();
    }

    public void showGpsAlert() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AppMethods.alertMessageNoGps(LauncherActivity.this);
        } else {
            locationFetch();
        }
    }

    public void locationFetch() {
        if (AppMethods.checkinglocationPermissions(LauncherActivity.this)) {
            AppMethods.permissionLocation(LauncherActivity.this);
        } else {
            if (!AppMethods.isLocationServiceRunning(this)) {
                startLocationService();
            }
            startApp();
        }
    }

    public void startLocationService() {
        startService(new Intent(LauncherActivity.this, PlayLocation.class));
    }

    public void startApp() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (close_data != AppIntegers.splash.splash_close_data) {
                    redirectToWelcomeActivity();

                }
            }
        }, AppIntegers.splash.splash_duration);
    }

    private void redirectToWelcomeActivity() {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case AppIntegers.permission.Location:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationFetch();
                }
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        showGpsAlert();
    }

}
