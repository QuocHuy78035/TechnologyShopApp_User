package com.example.technology_app.models;



public class UserModel{
    public int statusCode;
    public String message;
    public Metadata metadata;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public UserModel(int statusCode, String message, Metadata metadata) {
        this.statusCode = statusCode;
        this.message = message;
        this.metadata = metadata;
    }

    public static class Metadata{
        public User user;
        public Tokens tokens;
    }

    public static class Tokens{
        public String accessToken;
        public String refreshToken;
    }
}

