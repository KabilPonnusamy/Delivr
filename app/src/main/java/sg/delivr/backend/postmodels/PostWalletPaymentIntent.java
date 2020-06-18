package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostWalletPaymentIntent {
    @Expose
    @SerializedName("Amount")
    private int Amount;
    @Expose
    @SerializedName("MerchantId")
    private String MerchantId;
    @Expose
    @SerializedName("Description")
    private String Description;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostWalletPaymentIntent(String MerchantId, String Description, String APIKEY, String signature, int Amount) {
        this.MerchantId = MerchantId;
        this.Description = Description;
        this.APIKEY = APIKEY;
        this.Signature = signature;
        this.Amount = Amount;
    }
}
