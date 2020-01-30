package com.dea.anaconda.demo.data;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "meubles")
public class MeubleEntity implements Serializable {

    @Id
    @GeneratedValue
    private long id;  //for db

    @Column(nullable = false, unique = true)
    private String meubleId;
    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @ElementCollection
    private List<String> fotos;

    @Column(nullable = false, length = 50)
    private String lastPrice;

    @Column(nullable = false, length = 50)
    private String newPrice;
    @NotNull
    private boolean isPromo;

    @NotNull
    private String category;


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMeubleId() {
        return meubleId;
    }

    public void setMeubleId(String meubleId) {
        this.meubleId = meubleId;
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

    public String getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(String lastPrice) {
        this.lastPrice = lastPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public void setPromo(boolean promo) {
        isPromo = promo;
    }
}
