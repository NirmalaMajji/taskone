package com.taskone.model;

import android.location.Location;

import java.io.Serializable;

public class FinalLocation implements Serializable {
    public static FinalLocation fLocation = null;
    private static final long serialVersionUID = 1;
    private Location location;

    private FinalLocation() {
    }

    public static FinalLocation getInstance() {
        if (fLocation != null) {
            return fLocation;
        }
        fLocation = new FinalLocation();
        return fLocation;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
