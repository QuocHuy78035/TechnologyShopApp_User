package com.example.technology_app.models.Order;

import java.util.List;

public class Order {
    private String user;
    private  Checkout checkout;
    private  String shippingAddress;
    private  Payment payment;
    private  String trackingNumber;
    private int coin;
    private String voucher;
    private List<Product> product;
    private String status;
    private String phone;
    private String deliveredDate;
    private String _id;
    private String createdAt;
    private String updatedAt;
    private int __v;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Checkout getCheckout() {
        return checkout;
    }

    public void setCheckout(Checkout checkout) {
        this.checkout = checkout;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public List<Product> getProduct() {
        return product;
    }

    public void setProduct(List<Product> product) {
        this.product = product;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(String deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public Order(String user, Checkout checkout, String shippingAddress, Payment payment, String trackingNumber, int coin, String voucher, List<Product> product, String status, String phone, String deliveredDate, String _id, String createdAt, String updatedAt, int __v) {
        this.user = user;
        this.checkout = checkout;
        this.shippingAddress = shippingAddress;
        this.payment = payment;
        this.trackingNumber = trackingNumber;
        this.coin = coin;
        this.voucher = voucher;
        this.product = product;
        this.status = status;
        this.phone = phone;
        this.deliveredDate = deliveredDate;
        this._id = _id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }
}
