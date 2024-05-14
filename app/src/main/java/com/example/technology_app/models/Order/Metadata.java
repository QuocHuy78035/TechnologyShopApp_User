package com.example.technology_app.models.Order;

public class Metadata {
    private Order order;

    public Metadata(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
