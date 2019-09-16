package com.mtjin.aws_number_ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @Expose
    @SerializedName("id") private int id;
    @Expose
    @SerializedName("userId") private String userId;
    @Expose
    @SerializedName("userPassword") private String userPassword;
    @Expose
    @SerializedName("userRestaurant") private String userRestaurant;
    @Expose
    @SerializedName("restaurantLocation") private String restaurantLocation;
    @Expose
    @SerializedName("restaurantTel") private String restaurantTel;
    @Expose
    @SerializedName("result") private Boolean result;
    @Expose
    @SerializedName("msg") private String msg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRestaurant() {
        return userRestaurant;
    }

    public void setUserRestaurant(String userRestaurant) {
        this.userRestaurant = userRestaurant;
    }

    public String getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(String restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getRestaurantTel() {
        return restaurantTel;
    }

    public void setRestaurantTel(String restaurantTel) {
        this.restaurantTel = restaurantTel;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
