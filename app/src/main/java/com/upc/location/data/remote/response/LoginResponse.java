package com.upc.location.data.remote.response;

public class LoginResponse {

    private boolean success;
    private String message;

    public LoginResponse() {
        success = false;
        message = "Ocurrió un error inesperado";
    }

    public LoginResponse(boolean success, String message){
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}