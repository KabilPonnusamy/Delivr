package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostMerchantOrderEntry {
    @Expose
    @SerializedName("MerchantId")
    private String MerchantId;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostMerchantOrderEntry(String MerchantId, String APIKEY, String signature) {
        this.MerchantId = MerchantId;
        this.APIKEY = APIKEY;
        this.Signature = signature;
    }

}
