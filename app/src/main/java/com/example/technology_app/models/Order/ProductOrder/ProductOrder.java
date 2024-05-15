package com.example.technology_app.models.Order.ProductOrder;

import java.util.List;

public class ProductOrder {
    private final List<Item> products;

    public ProductOrder(List<Item> products) {
        this.products = products;
    }

    public List<Item> getProducts() {
        return products;
    }

    public static class Item {
        private String product;
        private int quantity;

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

        public Item(String product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }
    }
}
