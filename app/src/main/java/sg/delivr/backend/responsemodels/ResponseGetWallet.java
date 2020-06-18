package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseGetWallet extends BaseResponse {
    public String getCreditAmt() {
        return CreditAmt;
    }

        @Expose
        @SerializedName("CreditAmt")
        String CreditAmt;


}
