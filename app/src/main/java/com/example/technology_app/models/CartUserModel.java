package com.example.technology_app.models;

import java.util.ArrayList;

public class CartUserModel {
    public String message;
    public int status;
    public Metadata metadata;

    public CartUserModel(String message, int status, Metadata metadata) {
        this.message = message;
        this.status = status;
        this.metadata = metadata;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public static class Metadata{
        public Cart cart;

        public Cart getCart() {
            return cart;
        }

        public void setCart(Cart cart) {
            this.cart = cart;
        }

        public static class Cart{
            public String _id;
            public String user;
            public ArrayList<Item> items;

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

            public ArrayList<Item> getItems() {
                return items;
            }

            public void setItems(ArrayList<Item> items) {
                this.items = items;
            }

            public static class Item{
                public DetailProductModel.Product product;
                public int quantity;
                public String _id;

                public DetailProductModel.Product getProduct() {
                    return product;
                }

                public void setProduct(DetailProductModel.Product product) {
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
            }

        }
    }
}
