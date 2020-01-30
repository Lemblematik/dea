package com.cmr.dea.sellerapi.demo.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "sellers")
public class SellerEntity implements Serializable {
    @Id
    @GeneratedValue
    private long id;  //for db

    @Column(nullable = false, unique = true)
    private String sellerId;

    @Column(nullable = false, length = 50)
    private String lastName;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(nullable = false, length = 50)
    private String foto;

    @Column(nullable = false, length = 50)
    private String passwort;

    @Column(nullable = false)
    private String age;
    @Column(nullable = false, length = 50)
    private String phoneNbr;

    @Column(nullable = false, length = 200)
    private String ownDescription;

    @Column(nullable = false)
    private String marketName;


    @Column(nullable = false)
    private String marketPosition;




    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
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

    public String getMarketName() {
        return marketName;
    }

    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    public String getMarketPosition() {
        return marketPosition;
    }

    public void setMarketPosition(String marketPosition) {
        this.marketPosition = marketPosition;
    }


}
