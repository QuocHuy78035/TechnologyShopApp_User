package com.example.technology_app.models.EditProfile;

public class UserModel {
    public int status;
    public String message;
    public  Metadata metadata;

    public UserModel(int status, String message, Metadata metadata) {
        this.status = status;
        this.message = message;
        this.metadata = metadata;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
}
