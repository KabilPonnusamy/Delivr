package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseWalletHistory extends BaseResponse {

    @Expose
    @SerializedName("CreditDate")
    String CreditDate;
    @Expose
    @SerializedName("WalletDetlId")
    String WalletDetlId;
    @Expose
    @SerializedName("Merchwalletid")
    String Merchwalletid;
    @Expose
    @SerializedName("OrderWBUId")
    String OrderWBUId;
    @Expose
    @SerializedName("MerchId")
    String MerchId;
    @Expose
    @SerializedName("CreditAmt")
    String CreditAmt;
    @Expose
    @SerializedName("Transfertype")
    String Transfertype;
    @Expose
    @SerializedName("Actiontype")
    String Actiontype;
    @Expose
    @SerializedName("Balance")
    String Balance;
    public String getCreditDate() {
        return CreditDate;
    }

    public String getWalletDetlId() {
        return WalletDetlId;
    }

    public String getMerchwalletid() {
        return Merchwalletid;
    }

    public String getOrderWBUId() {
        return OrderWBUId;
    }

    public String getMerchId() {
        return MerchId;
    }

    public String getCreditAmt() {
        return CreditAmt;
    }

    public String getTransfertype() {
        return Transfertype;
    }

    public String getActiontype() {
        return Actiontype;
    }

    public String getBalance() {
        return Balance;
    }
}