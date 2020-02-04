package com.cmr.dea.clientapi.demo.modell;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ClientRequestModel {

    @NotNull(message = "lastName cannot be null")
    private String lastName;
    @NotNull(message = "firstname cannot be nulll")
    private String firstName;

    @NotNull(message = "phone cannot be nulll")
    private String phone;

    @NotNull(message = "adress cannot be nulll")
    private String adress;



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

}
