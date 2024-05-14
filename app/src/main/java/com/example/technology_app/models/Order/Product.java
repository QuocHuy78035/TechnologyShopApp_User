package com.example.technology_app.models.Order;

public class Product {
    private String product;
    private int quantity;
    private String _id;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Product(String product, int quantity, String _id) {
        this.product = product;
        this.quantity = quantity;
        this._id = _id;
    }
}
