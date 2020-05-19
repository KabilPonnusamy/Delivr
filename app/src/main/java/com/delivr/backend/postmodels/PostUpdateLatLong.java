package com.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostUpdateLatLong {
    @Expose
    @SerializedName("Riderid")
    private String Riderid;
    @Expose
    @SerializedName("Latitude")
    private String Latitude;
    @Expose
    @SerializedName("Longitude")
    private String Longitude;
    @Expose @SerializedName("APIKEY")
    private String APIKEY;
    @Expose @SerializedName("Signature")
    private String Signature;
    public PostUpdateLatLong(String Riderid,String latitude, String longitude,
                             String APIKEY, String Signature) {
        this.Riderid = Riderid;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.APIKEY = APIKEY;
        this.Signature = Signature;
    }
}
