package com.mtjin.aws_number_ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Apply {
    @Expose
    @SerializedName("id") private int id;
    @Expose
    @SerializedName("restaurant_id") private int restaurant_id;
    @Expose
    @SerializedName("restaurant_name") private String restaurant_name;
    @Expose
    @SerializedName("apply_id") private String apply_id;
    @Expose
    @SerializedName("apply_date") private String apply_date;
    @Expose
    @SerializedName("reserve_date") private String reserve_date;
    @Expose
    @SerializedName("user_tel") private String user_tel;
    @Expose
    @SerializedName("user_pw") private String user_pw;
    @Expose
    @SerializedName("accept") private String accept;
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

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getApply_id() {
        return apply_id;
    }

    public void setApply_id(String apply_id) {
        this.apply_id = apply_id;
    }

    public String getApply_date() {
        return apply_date;
    }

    public void setApply_date(String apply_date) {
        this.apply_date = apply_date;
    }

    public String getReserve_date() {
        return reserve_date;
    }

    public void setReserve_date(String reserve_date) {
        this.reserve_date = reserve_date;
    }

    public String getUser_tel() {
        return user_tel;
    }

    public void setUser_tel(String user_tel) {
        this.user_tel = user_tel;
    }

    public String getUser_pw() {
        return user_pw;
    }

    public void setUser_pw(String user_pw) {
        this.user_pw = user_pw;
    }

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
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
