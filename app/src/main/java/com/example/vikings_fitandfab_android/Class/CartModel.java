package com.example.vikings_fitandfab_android.Class;

public class CartModel {

   String productId,cId;
   boolean selection =true;

    public CartModel() {
    }

    public boolean isSelection() {
        return selection;
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    SupplimentModel supplimentModel;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public SupplimentModel getSupplimentModel() {
        return supplimentModel;
    }

    public void setSupplimentModel(SupplimentModel supplimentModel) {
        this.supplimentModel = supplimentModel;
    }
}
