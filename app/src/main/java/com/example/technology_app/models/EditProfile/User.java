package com.example.technology_app.models.EditProfile;

public class User {
    private String _id;
    private String name;
    private String avatar;
    private String dateOfBirth;
    private String address;
    private String mobile;
    private String gender;

    public User(String _id, String name, String avatar, String dateOfBirth, String address, String mobile, String gender) {
        this._id = _id;
        this.name = name;
        this.avatar = avatar;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.mobile = mobile;
        this.gender = gender;
    }

    public String get_id() {
        return _id;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
