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
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public String getRiderId() {
        return RiderId;
    }

    public void setRiderId(String riderId) {
        RiderId = riderId;
    }

    public String getOrderWBUid() {
        return OrderWBUid;
    }

    public void setOrderWBUid(String orderWBUid) {
        OrderWBUid = orderWBUid;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getURL1() {
        return URL1;
    }

    public void setURL1(String URL1) {
        this.URL1 = URL1;
    }

    public String getURL2() {
        return URL2;
    }

    public void setURL2(String URL2) {
        this.URL2 = URL2;
    }

    public String getComments() {
        return Comments;
    }

    public void setComments(String comments) {
        Comments = comments;
    }

    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String APIKEY) {
        this.APIKEY = APIKEY;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

}
