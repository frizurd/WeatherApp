package com.frizkykramer.weatherapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AccountModel {

    @SerializedName("phone")
    @Expose
    private int phone;

    @SerializedName("forUpdate")
    @Expose
    private String update;

    private String name;


    public int getPhone() {
        return phone;
    }
    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate() {
        return update;
    }
    public void setUpdate(String update) {
        this.update = update;
    }
}
