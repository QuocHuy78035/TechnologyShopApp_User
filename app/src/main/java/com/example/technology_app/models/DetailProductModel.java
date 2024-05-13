package com.example.technology_app.models;

import java.util.ArrayList;

public class DetailProductModel{
    public String message;
    public int status;

    public DetailProductModel(String message, int status, Metadata metadata) {
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

    public Metadata metadata;

    public static class Category{
        public String _id;
        public String image;
        public String name;
        public String createdAt;
        public String updatedAt;
        public int __v;
    }

    public static class Metadata{
        public ProductDetail getProduct() {
            return product;
        }

        public void setProduct(ProductDetail product) {
            this.product = product;
        }

        public ProductDetail product;
    }

    public static class ProductDetail{
        public String _id;
        public String name;
        public ArrayList<String> images;
        public String linkytb;
        public String sale_price;
        public ArrayList<String> extraInfo;
        public String type;
        public Description description;
        public String category;
        public int sold;
        public int left;
        public int views;
        public int rating;
        public String thumb;
        public ArrayList<String> variants;

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

        public String getLinkytb() {
            return linkytb;
        }

        public void setLinkytb(String linkytb) {
            this.linkytb = linkytb;
        }

        public String getSale_price() {
            return sale_price;
        }

        public void setSale_price(String sale_price) {
            this.sale_price = sale_price;
        }

        public ArrayList<String> getExtraInfo() {
            return extraInfo;
        }

        public void setExtraInfo(ArrayList<String> extraInfo) {
            this.extraInfo = extraInfo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Description getDescription() {
            return description;
        }

        public void setDescription(Description description) {
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

        public int getLeft() {
            return left;
        }

        public void setLeft(int left) {
            this.left = left;
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

        public ArrayList<String> getVariants() {
            return variants;
        }

        public void setVariants(ArrayList<String> variants) {
            this.variants = variants;
        }

        public ProductDetail(String _id, String name, ArrayList<String> images, String linkytb, String sale_price, ArrayList<String> extraInfo, String type, Description description, String category, int sold, int left, int views, int rating, String thumb, ArrayList<String> variants) {
            this._id = _id;
            this.name = name;
            this.images = images;
            this.linkytb = linkytb;
            this.sale_price = sale_price;
            this.extraInfo = extraInfo;
            this.type = type;
            this.description = description;
            this.category = category;
            this.sold = sold;
            this.left = left;
            this.views = views;
            this.rating = rating;
            this.thumb = thumb;
            this.variants = variants;
        }
    }

    public static class Description{
        public String screen;
        public String operating_system;
        public String rear_camera;
        public String front_camera;
        public String chip;
        public String ram;
        public String storage;
        public String sim;
        public String battery;
        public String brand;

        public String getScreen() {
            return screen;
        }

        public void setScreen(String screen) {
            this.screen = screen;
        }

        public String getOperating_system() {
            return operating_system;
        }

        public void setOperating_system(String operating_system) {
            this.operating_system = operating_system;
        }

        public String getRear_camera() {
            return rear_camera;
        }

        public void setRear_camera(String rear_camera) {
            this.rear_camera = rear_camera;
        }

        public String getFront_camera() {
            return front_camera;
        }

        public void setFront_camera(String front_camera) {
            this.front_camera = front_camera;
        }

        public String getChip() {
            return chip;
        }

        public void setChip(String chip) {
            this.chip = chip;
        }

        public String getRam() {
            return ram;
        }

        public void setRam(String ram) {
            this.ram = ram;
        }

        public String getStorage() {
            return storage;
        }

        public void setStorage(String storage) {
            this.storage = storage;
        }

        public String getSim() {
            return sim;
        }

        public void setSim(String sim) {
            this.sim = sim;
        }

        public String getBattery() {
            return battery;
        }

        public void setBattery(String battery) {
            this.battery = battery;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public Description(String screen, String operating_system, String rear_camera, String front_camera, String chip, String ram, String storage, String sim, String battery, String brand) {
            this.screen = screen;
            this.operating_system = operating_system;
            this.rear_camera = rear_camera;
            this.front_camera = front_camera;
            this.chip = chip;
            this.ram = ram;
            this.storage = storage;
            this.sim = sim;
            this.battery = battery;
            this.brand = brand;
        }
    }
}
