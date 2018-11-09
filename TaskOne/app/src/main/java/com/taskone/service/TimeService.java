package com.taskone.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TimeService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
//        sendBroadcast(new Intent(CityGuideActivity.ACTION_REFRESH_LOCATION));
        stopSelf();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
