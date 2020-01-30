package com.cmr.dea.wareapi.demo.data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "wares") //you re to set whish table data should be persisted
public class WareEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;  //for db

    @Column(nullable = false, unique = true)
    private String wareId;

    @Column(nullable = false)
    private String wareSellerId;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 200)
    private String description;

    @Lob
    @ElementCollection
    private List<String> fotos;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private boolean cart;


    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private int count;

    @Column(nullable = false)
    private boolean rightAgree;


    private String marketPosition;
    private String subCategory;
    private String phone;
    private String date;
    private String marketPlace;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getWareSellerId() {
        return wareSellerId;
    }

    public void setWareSellerId(String wareSellerId) {
        this.wareSellerId = wareSellerId;
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
