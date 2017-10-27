package com.example.locationdemo.data.model;

/**
 * Created by fanlat on 27/10/17.
 */

public class GeolocationError {

    private GeolocationErrorTypes error;

    public GeolocationError(GeolocationErrorTypes error) {
        this.error = error;
    }

    public GeolocationErrorTypes getError() {
        return error;
    }

    public void setError(GeolocationErrorTypes error) {
        this.error = error;
    }
}
