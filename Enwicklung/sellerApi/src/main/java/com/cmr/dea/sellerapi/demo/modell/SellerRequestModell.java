package com.cmr.dea.sellerapi.demo.modell;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SellerRequestModell {
    @NotNull(message = "foto should be inserted")
    private String foto;
    @NotNull(message = "lastName cannot be null")
    private String lastName;
    @NotNull(message = "firstname cannot be nulll")
    private String firstName;
    @Size(min = 4, max = 9, message = "passwort beetween 4 and 9")
    private String passwort;

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

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
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
