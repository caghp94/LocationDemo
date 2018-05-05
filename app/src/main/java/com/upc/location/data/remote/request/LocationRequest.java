package com.upc.location.data.remote.request;


import android.content.Context;

public class LocationRequest {

    private double latitude;
    private double longitude;
    private Context context;


    public LocationRequest(double latitude, double longitude, Context context) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.context = context;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public Context getContext() { return context; }

    public void setContext(Context context) { this.context = context; }
}
