package com.cmr.dea.clientapi.demo.data;

import javax.persistence.*;
import java.io.Serializable;
//damit informations really persisted in my db
//to transform

@Entity
@Table(name = "clients") //you re to set whish table data should be persisted
public class ClientEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;  //for db

    @Column(nullable = false, unique = true)
    private String clientId;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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



}
