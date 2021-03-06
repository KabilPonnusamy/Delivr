package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoCompletedJobs {

    @Expose
    @SerializedName("RiderId")
    private String RiderId;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostDoCompletedJobs(String riderId, String APIKEY, String signature) {
        RiderId = riderId;
        this.APIKEY = APIKEY;
        Signature = signature;
    }

}
