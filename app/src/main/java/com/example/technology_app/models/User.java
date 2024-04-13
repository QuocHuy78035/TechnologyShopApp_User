package com.example.technology_app.models;

public class User {
    public User() {}

    public String get_id() {
        return _id;
    }

    public User(String _id, String name, String email) {
        this._id = _id;
        this.name = name;
        this.email = email;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String _id;
    public String name;
    public String email;
}
