package com.cmr.project.mymarket.Actuel_User;

import com.cmr.project.mymarket.ResponseModel.ClientResponseModel;

public class User {
    private String clientId;
    private String phone;
    private String lastName;
    private String firstName;
    private String adress;

    public static User actuelUser;



    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public static User getActuelUser(ClientResponseModel clientResponseModel){
        actuelUser = new User();
        actuelUser.setAdress(clientResponseModel.getAdress());
        actuelUser.setClientId(clientResponseModel.getClientId());
        actuelUser.setFirstName(clientResponseModel.getFirstName());
        actuelUser.setPhone(clientResponseModel.getPhone());
        actuelUser.setLastName(clientResponseModel.getLastName());
        return actuelUser;

    }
}
