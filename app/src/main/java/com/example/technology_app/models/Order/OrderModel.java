package com.example.technology_app.models.Order;

public class OrderModel {
    private  String message;
    private String status;
    private Metadata metadata;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public OrderModel(String message, String status, Metadata metadata) {
        this.message = message;
        this.status = status;
        this.metadata = metadata;
    }
}
