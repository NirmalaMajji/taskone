package com.taskone.model;

import android.graphics.Bitmap;

import org.w3c.dom.NodeList;

import java.util.List;

public class PlaceDetailsModel {
    String address;
    String googlePlusPage;
    Bitmap image;
    String imageUrl;
    double lat;
    double lng;
    String name;
    String open;
    String phone;
    NodeList photoList;
    String photoReference;
    float priceLevel;
    float rating;
//    List<ReviewPojo> review;
    String website;

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public float getPriceLevel() {
        return this.priceLevel;
    }

    public void setPriceLevel(float priceLevel) {
        this.priceLevel = priceLevel;
    }

    public NodeList getPhotoList() {
        return this.photoList;
    }

    public void setPhotoList(NodeList photoList) {
        this.photoList = photoList;
    }

    public String getPhotoReference() {
        return this.photoReference;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference = photoReference;
    }

    public String getGooglePlusPage() {
        return this.googlePlusPage;
    }

    public void setGooglePlusPage(String googlePlusPage) {
        this.googlePlusPage = googlePlusPage;
    }

    public String getOpen() {
        return this.open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

//    public List<ReviewPojo> getReview() {
//        return this.review;
//    }
//
//    public void setReview(List<ReviewPojo> review) {
//        this.review = review;
//    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return this.website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public double getLat() {
        return this.lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return this.lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
