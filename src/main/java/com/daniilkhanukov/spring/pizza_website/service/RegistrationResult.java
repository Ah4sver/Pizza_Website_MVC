package com.daniilkhanukov.spring.pizza_website.service;

public class RegistrationResult {
    private boolean success;
    private String message;

    public RegistrationResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccessStatus() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
