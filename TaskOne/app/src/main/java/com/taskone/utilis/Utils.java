package com.taskone.utilis;

import android.content.Context;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.taskone.app.App;
import com.taskone.R;

public class Utils {
    private static Utils instance;

    private Utils() {
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    public int getSearchRadiusSP() {
        Context c = App.getAppContext();
        try {
            return Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(c).getString(c.getResources().getString(R.string.setRadiusKey), c.getResources().getString(R.string.radiusDefault)));
        } catch (Exception e) {
            return 5;
        }
    }

    public String getSearchSortSP() {
        Context c = App.getAppContext();
        String i = PreferenceManager.getDefaultSharedPreferences(c).getString(c.getResources().getString(R.string.setSortKey), c.getResources().getString(R.string.sortDefault));
        Toast.makeText(c, i, Toast.LENGTH_SHORT).show();
        return i;
    }
}
