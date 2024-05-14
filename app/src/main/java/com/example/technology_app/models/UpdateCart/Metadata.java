package com.example.technology_app.models.UpdateCart;

public class Metadata {
    private Cart cart;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Metadata(Cart cart) {
        this.cart = cart;
    }
}
