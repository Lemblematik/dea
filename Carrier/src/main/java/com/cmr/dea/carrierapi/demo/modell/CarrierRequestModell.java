package com.cmr.dea.carrierapi.demo.modell;

import javax.validation.constraints.NotNull;

public class CarrierRequestModell {


    @NotNull(message = "foto should be inserted")
    private String foto;
    @NotNull(message = "lastName cannot be null")
    private String lastName;
    @NotNull(message = "firstname cannot be nulll")
    private String firstName;

    @NotNull(message = "firstname cannot be nulll")
    private String age;

    @NotNull(message = "phone Number cannot be nulll")
    private String phoneNbr;

    @NotNull(message = "description cannot be nulll")
    private String ownDescription;

    @NotNull(message = "market position cannot be nulll")
    private String  marketPosition;

    @NotNull(message = "market name cannot be nulll")
    private String marketName;

    @NotNull(message = "function cannot be null")
    private String function;

    @NotNull(message = "should be given")
    private boolean rightInfos;

    private String price;
    private String carrierId;

    public String getCarrierId() {
        return carrierId;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isRightInfos() {
        return rightInfos;
    }

    public void setRightInfos(boolean rightInfos) {
        this.rightInfos = rightInfos;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }
    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
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

    public String getMarketPosition() {
        return marketPosition;
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
}
