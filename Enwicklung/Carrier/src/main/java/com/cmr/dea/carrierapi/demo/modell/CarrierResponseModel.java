package com.cmr.dea.carrierapi.demo.modell;

public class CarrierResponseModel {

    private String carrierId;
    private String foto;
    private String lastName;
    private String firstName;
    private String age;
    private String phoneNbr;
    private String ownDescription;
    private String marketPosition;
    private String marketName;
    private String appreciation;
    private String function;
    private boolean rightInfos;
    private String price;

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

    public String getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(String appreciation) {
        this.appreciation = appreciation;
    }

    public String getCarrierId() {
        return carrierId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setCarrierId(String carrierId) {
        this.carrierId = carrierId;
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
