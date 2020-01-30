package com.cmr.project.mymarket.ResponseModel;

import java.io.Serializable;
import java.util.List;

public class WareResponse implements Serializable {
    private String wareSellerId;
    private String wareSellerName;
    private String wareId;
    private String name;
    private String description;
    private List<String> fotos;
    private String price;
    private boolean cart;
    private String category;
    private int count;
    //date recuperation
    private String date;
    //for recuperation
    private String phone;
    private String marketPlace;
    //
    private String marketPosition;
    private String subCategory;


    public String getWareSellerName() {
        return wareSellerName;
    }

    public void setWareSellerName(String wareSellerName) {
        this.wareSellerName = wareSellerName;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getMarketPosition() {
        return marketPosition;
    }

    public void setMarketPosition(String marketPosition) {
        this.marketPosition = marketPosition;
    }

    public String getMarketPlace() {
        return marketPlace;
    }

    public void setMarketPlace(String marketPlace) {
        this.marketPlace = marketPlace;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public boolean isCart() {
        return cart;
    }

    public void setCart(boolean cart) {
        this.cart = cart;
    }

    public String getWareSellerId() {
        return wareSellerId;
    }

    public void setWareSellerId(String wareSellerId) {
        this.wareSellerId = wareSellerId;
    }

    public String getWareId() {
        return wareId;
    }

    public void setWareId(String wareId) {
        this.wareId = wareId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
