package com.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoActionAQ {
    @Expose
    @SerializedName("RiderId")
    private String RiderId;
    @Expose
    @SerializedName("OrderWBUid")
    private String OrderWBUid;
    @Expose
    @SerializedName("Action")
    private String Action;
    @Expose
    @SerializedName("URL1")
    private String URL1;
    @Expose
    @SerializedName("URL2")
    private String URL2;
    @Expose
    @SerializedName("Comments")
    private String Comments;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;

    public PostDoActionAQ(String riderId, String orderWBUid, String action, String URL1, String URL2,
                          String comments, String APIKEY, String signature) {
        RiderId = riderId;
        OrderWBUid = orderWBUid;
        Action = action;
        this.URL1 = URL1;
        this.URL2 = URL2;
        Comments = comments;
        this.APIKEY = APIKEY;
        Signature = signature;
    }

    @Expose
    @SerializedName("Signature")
    private String Signature;



}
