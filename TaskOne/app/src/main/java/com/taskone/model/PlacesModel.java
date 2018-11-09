package com.taskone.model;


import android.graphics.Bitmap;

public class PlacesModel {
    private Bitmap icon;
    private String lat;
    private String lng;
    private String name;
    private String operating_time;
    private Bitmap photo_reference;
    private String photo_reference_url;
    private float rating;
    private String reference;
    private String vicinity;
    private boolean status;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Bitmap getIcon() {
        return icon;
    }

    public void setIcon(Bitmap icon) {
        this.icon = icon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperating_time() {
        return operating_time;
    }

    public void setOperating_time(String operating_time) {
        this.operating_time = operating_time;
    }

    public Bitmap getPhoto_reference() {
        return photo_reference;
    }

    public void setPhoto_reference(Bitmap photo_reference) {
        this.photo_reference = photo_reference;
    }

    public String getPhoto_reference_url() {
        return photo_reference_url;
    }

    public void setPhoto_reference_url(String photo_reference_url) {
        this.photo_reference_url = photo_reference_url;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }
}
