package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoOrderHistory {

    @Expose
    @SerializedName("MerchantId")
    private String MerchantId;
    @Expose
    @SerializedName("OrderWBUId")
    private String OrderWBUId;
    @Expose
    @SerializedName("OrderNo")
    private String OrderNo;
    @Expose
    @SerializedName("UserRole")
    private String UserRole;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;
    @Expose
    @SerializedName("RowFrom")
    private String RowFrom;
    @Expose
    @SerializedName("RowTo")
    private String RowTo;

    public PostDoOrderHistory(String merchantId, String orderWBUId, String orderNo, String userRole,
                              String APIKEY, String signature, String rowFrom, String rowTo) {
        MerchantId = merchantId;
        OrderWBUId = orderWBUId;
        OrderNo = orderNo;
        UserRole = userRole;
        this.APIKEY = APIKEY;
        Signature = signature;
        RowFrom = rowFrom;
        RowTo = rowTo;
    }

}
