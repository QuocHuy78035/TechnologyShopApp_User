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
        public String brand;
        public ArrayList<String> images;
        public String linkYtb;
        public String price;
        public String type;
        public String description;
        public String category;
        public int sold;
        public int views;
        public int rating;
        public String thumb;
        public ArrayList<Object> extraInfo;
        public ArrayList<Object> variants;
        public int left;

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

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public Object getLinkYtb() {
            return linkYtb;
        }

        public void setLinkYtb(String linkYtb) {
            this.linkYtb = linkYtb;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public int getViews() {
            return views;
        }

        public void setViews(int views) {
            this.views = views;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public ArrayList<Object> getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(ArrayList<Object> extraInfo) {
            this.extraInfo = extraInfo;
        }

        public ArrayList<Object> getVariants() {
            return variants;
        }

        public void setVariants(ArrayList<Object> variants) {
            this.variants = variants;
        }

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
        }

        public Product(String _id, String name, String brand, ArrayList<String> images, String linkYtb, String price, String type, String description, String category, int sold, int views, int rating, String thumb, ArrayList<Object> extraInfo, ArrayList<Object> variants, int left) {
            this._id = _id;
            this.name = name;
            this.brand = brand;
            this.images = images;
            this.linkYtb = linkYtb;
            this.price = price;
            this.type = type;
            this.description = description;
            this.category = category;
            this.sold = sold;
            this.views = views;
            this.rating = rating;
            this.thumb = thumb;
            this.extraInfo = extraInfo;
            this.variants = variants;
            this.left = left;
        }
    }
}
