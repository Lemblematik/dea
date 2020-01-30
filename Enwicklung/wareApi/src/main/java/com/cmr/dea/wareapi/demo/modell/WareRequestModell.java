package com.cmr.dea.wareapi.demo.modell;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class WareRequestModell {

    @NotNull
    private String wareSellerId;

    @NotNull(message = "name of ware cannot be null")
    private String name;

    @NotNull(message = "description cannot be null")
    private String description;

    @NotEmpty
    private List<String> fotos;

    @NotNull(message = "price cannot be null")
    private String price;

    @NotNull(message = "category cannot be null")
    private String category;

    @NotNull(message = "should accept the condition")
    private boolean rightAgree;

    private String phone;
    String marketPlace;
    private String marketPosition;
    private String subCategory;

    public String getMarketPosition() {
        return marketPosition;
    }

    public void setMarketPosition(String marketPosition) {
        this.marketPosition = marketPosition;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public boolean isRightAgree() {
        return rightAgree;
    }

    public void setRightAgree(boolean rightAgree) {
        this.rightAgree = rightAgree;
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
