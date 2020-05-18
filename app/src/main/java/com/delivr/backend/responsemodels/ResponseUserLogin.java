package com.delivr.backend.responsemodels;

import com.delivr.backend.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserLogin extends BaseResponse {

    public String getFullname() {
        return Fullname;
    }

    public String getUserId() {
        return UserId;
    }

    public String getRole() {
        return Role;
    }

    @Expose
    @SerializedName("Fullname")
    String Fullname;
    @Expose
    @SerializedName("UserId")
    String UserId;

    @Expose
    @SerializedName("Role")
    String Role;

}