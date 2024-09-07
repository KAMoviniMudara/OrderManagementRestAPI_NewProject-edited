package com.example.ordermanagementrestapi.Response;

public class LoginResponse {
    private String message;
    private boolean status;
    private String role;

    public LoginResponse(String message, boolean status, String role) {
        this.message = message;
        this.status = status;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
