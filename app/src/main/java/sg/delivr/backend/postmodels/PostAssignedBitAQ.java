package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAssignedBitAQ {
    @Expose
    @SerializedName("RiderId")
    private String RiderId;
    @Expose
    @SerializedName("OrderNo")
    private String OrderNo;
    @Expose
    @SerializedName("Action")
    private String Action;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostAssignedBitAQ(String id,String OrderNo, String Action,
                          String APIKEY, String Signature) {
        this.RiderId = id;
        this.OrderNo = OrderNo;
        this.Action = Action;
        this.APIKEY = APIKEY;
        this.Signature = Signature;
    }


}
