package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoCreateOrder {
    @Expose
    @SerializedName("RiderType")
    private String RiderType;
    @Expose
    @SerializedName("OrderUid ")
    private String OrderUid ;
    @Expose
    @SerializedName("OrderNo")
    private String OrderNo;
    @Expose
    @SerializedName("SenderId")
    private String SenderId;
    @Expose
    @SerializedName("PickupSameas")
    private String PickupSameas;
    @Expose
    @SerializedName("PickupZipCode")
    private String PickupZipCode;
    @Expose
    @SerializedName("PickupUnitNo")
    private String PickupUnitNo;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostDoCreateOrder(String riderType, String APIKEY, String signature) {
        RiderType = riderType;
        this.APIKEY = APIKEY;
        Signature = signature;
    }

}
