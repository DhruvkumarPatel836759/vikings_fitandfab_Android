package com.example.vikings_fitandfab_android.Class;

import java.util.ArrayList;
import java.util.HashMap;

public class OrderModel {

    String orderId,userId,status;
    ArrayList<HashMap<String, Object>> products = new ArrayList<>();

    double payment;

    UserModel userModel;

    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    public OrderModel() {
    }

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public ArrayList<HashMap<String, Object>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HashMap<String, Object>> products) {
        this.products = products;
    }
}
