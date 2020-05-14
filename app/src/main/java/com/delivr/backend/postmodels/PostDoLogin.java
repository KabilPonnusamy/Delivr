package com.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoLogin {
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose @SerializedName("device_type") private String device_type;

    public PostDoLogin(String email,
                       String password, String device_type) {
        this.email = email;
        this.password = password;
        this.device_type = device_type;
    }
}
