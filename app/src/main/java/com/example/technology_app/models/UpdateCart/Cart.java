package com.example.technology_app.models.UpdateCart;

import java.util.List;

public class Cart {
    private String _id;
    private String user;
    private int __v;
    private String createdAt;
    List<Item> items;
    private String updatedAt;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Cart(String _id, String user, int __v, String createdAt, List<Item> items, String updatedAt) {
        this._id = _id;
        this.user = user;
        this.__v = __v;
        this.createdAt = createdAt;
        this.items = items;
        this.updatedAt = updatedAt;
    }
}
