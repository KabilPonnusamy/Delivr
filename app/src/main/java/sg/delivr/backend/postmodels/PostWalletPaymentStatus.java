package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostWalletPaymentStatus {
    @Expose
    @SerializedName("MerchantId")
    private String MerchantId;
    @Expose
    @SerializedName("Status")
    private String Status;
    @Expose
    @SerializedName("RawJObject")
    private String RawJObject;
    @Expose
    @SerializedName("BalanceTransactionId")
    private String BalanceTransactionId;
    @Expose
    @SerializedName("Amount")
    private int Amount;
    @Expose
    @SerializedName("Address")
    private String Address;
    @Expose
    @SerializedName("Description")
    private String Description;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostWalletPaymentStatus(String MerchantId, String Status, String RawJObject, String BalanceTransactionId,
                                   int Amount, String Address, String Description, String APIKEY, String signature) {
        this.MerchantId = MerchantId;
        this.Status = Status;
        this.RawJObject = RawJObject;
        this.BalanceTransactionId = BalanceTransactionId;
        this.Amount = Amount;
        this.Address = Address;
        this.Description = Description;
        this.APIKEY = APIKEY;
        this.Signature = signature;
    }
}
