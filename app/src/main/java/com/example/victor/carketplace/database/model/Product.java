package com.example.victor.carketplace.database.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class Product {
    @SerializedName("id")
    @PrimaryKey
    private long id;

    @SerializedName("nome")
    @ColumnInfo(name = "name")
    private String name;

    @SerializedName("descricao")
    @ColumnInfo(name = "description")
    private String description;

    @SerializedName("marca")
    @ColumnInfo(name = "model")
    private String model;

    @SerializedName("quantidade")
    @ColumnInfo(name = "amount")
    private String amount;

    @SerializedName("preco")
    @ColumnInfo(name = "price")
    private String price;

    @SerializedName("imagem")
    @ColumnInfo(name = "image")
    private String image;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
