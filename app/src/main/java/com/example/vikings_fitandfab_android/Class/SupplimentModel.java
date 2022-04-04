package com.example.vikings_fitandfab_android.Class;

import java.io.Serializable;

public class SupplimentModel implements Serializable {

    String discription,image,name,type,sId;
    int quantity;
    double price;

    int remainQuantity;
    int selectQunatity;


    public SupplimentModel() {
    }

    public String getsId() {
        return sId;
    }

    public int getSelectQunatity() {
        return selectQunatity;
    }

    public void setSelectQunatity(int selectQunatity) {
        this.selectQunatity = selectQunatity;
    }

    public int getRemainQuantity() {
        return remainQuantity;
    }

    public void setRemainQuantity(int remainQuantity) {
        this.remainQuantity = remainQuantity;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
