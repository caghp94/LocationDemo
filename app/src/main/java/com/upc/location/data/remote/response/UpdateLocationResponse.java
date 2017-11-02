package com.upc.location.data.remote.response;

public class UpdateLocationResponse {
    private boolean success = true;

    public UpdateLocationResponse() {
        success = true;
    }

    public UpdateLocationResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
