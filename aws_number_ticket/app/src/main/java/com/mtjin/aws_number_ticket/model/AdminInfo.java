package com.mtjin.aws_number_ticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdminInfo {
    @SerializedName("result")
    @Expose
    private List<Apply> result;

    public List<Apply> getResult() {
        return result;
    }

    public void setResult(List<Apply> result) {
        this.result = result;
    }
}
