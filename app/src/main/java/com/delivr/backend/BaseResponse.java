package com.delivr.backend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse {


    @Expose
    @SerializedName("Message")
    private String message;



    public String getMessage() {
        return message;
    }

}
