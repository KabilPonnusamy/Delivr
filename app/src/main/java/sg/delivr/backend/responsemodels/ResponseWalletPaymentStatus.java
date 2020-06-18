package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseWalletPaymentStatus extends BaseResponse {
    @Expose
    @SerializedName("CreditAmt")
    String CreditAmt;
    @Expose
    @SerializedName("Status")
    String Status;


    public String getCreditAmt() {
        return CreditAmt;
    }

    public String getStatus() {
        return Status;
    }

}
