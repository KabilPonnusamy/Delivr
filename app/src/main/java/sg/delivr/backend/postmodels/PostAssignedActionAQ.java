package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostAssignedActionAQ {
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
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostAssignedActionAQ(String id,String OrderWBUid, String Action,
                             String APIKEY, String Signature) {
        this.RiderId = id;
        this.OrderWBUid = OrderWBUid;
        this.Action = Action;
        this.APIKEY = APIKEY;
        this.Signature = Signature;
    }

}
