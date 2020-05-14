package com.delivr.backend.responsemodels;


import com.delivr.backend.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseUserLogin extends BaseResponse {

    @Expose
    @SerializedName("member_id")
    String member_id;


    public String getMember_id() {
        return member_id;
    }
}