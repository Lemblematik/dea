package com.cmr.project.mymarket.RequestModell;


import java.util.List;

public class WareRequest {

    private String wareSellerId;
    private String name;
    private String description;
    private List<String> fotos;
    private String price;
    private String category;
    private boolean rightAgree;
    private String marketPlace;
    public String marketPosition;
    private String subCategory;

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

    public boolean isRightAgree() {
        return rightAgree;
    }

    public void setRightAgree(boolean rightAgree) {
        this.rightAgree = rightAgree;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getWareSellerId() {
        return wareSellerId;
    }

    public void setWareSellerId(String wareSellerId) {
        this.wareSellerId = wareSellerId;
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
