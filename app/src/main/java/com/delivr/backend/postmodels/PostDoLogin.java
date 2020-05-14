package com.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoLogin {
    @Expose
    @SerializedName("Login")
    private String email;
    @Expose
    @SerializedName("Password")
    private String password;
    @Expose @SerializedName("APIKEY")
    private String APIKEY;
    @Expose @SerializedName("Signature")
    private String Signature;
    public PostDoLogin(String email,
                       String password, String APIKEY, String Signature) {
        this.email = email;
        this.password = password;
        this.APIKEY = APIKEY;
        this.Signature = Signature;
    }
}
