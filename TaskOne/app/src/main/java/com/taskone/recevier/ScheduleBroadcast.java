package com.taskone.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;

import com.taskone.model.FinalLocation;
import com.taskone.service.TimeService;


public class ScheduleBroadcast extends BroadcastReceiver {
    /* renamed from: c */
    Context context;

    public void onReceive(Context context, Intent intent) {
        try {
            this.context = context;
            String locationKey = "location";
            if (intent.hasExtra(locationKey)) {
                FinalLocation.getInstance().setLocation((Location) intent.getExtras().get(locationKey));
                context.startService(new Intent(context, TimeService.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
