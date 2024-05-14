package com.example.technology_app.models.EditProfile;

public class Metadata {
    private User user;

    public Metadata(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
