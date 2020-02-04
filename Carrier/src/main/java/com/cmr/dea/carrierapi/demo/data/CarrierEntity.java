package com.cmr.dea.carrierapi.demo.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "carriers") //you re to set whish table data should be persisted
public class CarrierEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;  //for db

    @Column(nullable = false, unique = true)
    private String carrierId;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Lob
    private String foto;

    @Column(nullable = false)
    private String age;
    @Column(nullable = false, length = 50)
    private String phoneNbr;

    @Column(nullable = false, length = 200)
    private String ownDescription;

    @Column(nullable = false)
    private String marketName;

    @Column(nullable = false)
    private String function;

    @Column(nullable = false)
    private String marketPosition;
    @Column(nullable = false)
    private boolean rightInfos;

    private String appreciation;
    private String price;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getFunction() {
        return function;
    }

    public boolean isRightInfos() {
        return rightInfos;
    }

    public void setRightInfos(boolean rightInfos) {
        this.rightInfos = rightInfos;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneNbr() {
        return phoneNbr;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public String getOwnDescription() {
        return ownDescription;
    }

    public void setOwnDescription(String ownDescription) {
        this.ownDescription = ownDescription;
    }



    public void setMarketPosition(String marketPosition) {
        this.marketPosition = marketPosition;
    }

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketPosition() {
        return marketPosition;
    }

}
