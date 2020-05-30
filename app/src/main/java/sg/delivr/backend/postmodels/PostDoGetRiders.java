package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoGetRiders {
    @Expose
    @SerializedName("RiderType")
    private String RiderType;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostDoGetRiders(String riderType, String APIKEY, String signature) {
        RiderType = riderType;
        this.APIKEY = APIKEY;
        Signature = signature;
    }

}
