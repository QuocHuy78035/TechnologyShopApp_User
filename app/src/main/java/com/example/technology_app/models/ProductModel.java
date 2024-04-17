package com.example.technology_app.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductModel {
    public int status;
    public String message;
    public Metadata metadata;

    public ProductModel(int status, String message, Metadata metadata) {
        this.status = status;
        this.message = message;
        this.metadata = metadata;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public static class Metadata{
        public List<Product> products;

        public Metadata(List<Product> product) {
            this.products = product;
        }

        public List<Product> getProduct() {
            return products;
        }

        public void setProduct(List<Product> products) {
            this.products = products;
        }
    }

    public static class Product implements Serializable {
        public String _id;
        public String name;
        public ArrayList<String> images;
        public int price;
        public String description;
        public String category;
        public int sold;

        public Product(String _id, String name, ArrayList<String> images, int price, String description, String category, int sold, String createdAt, String updatedAt, int __v) {
            this._id = _id;
            this.name = name;
            this.images = images;
            this.price = price;
            this.description = description;
            this.category = category;
            this.sold = sold;
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

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getSold() {
            return sold;
        }

        public void setSold(int sold) {
            this.sold = sold;
        }
    }
}
