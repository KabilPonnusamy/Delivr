package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponsePaymentIntentClientSecret extends BaseResponse {
        @Expose
        @SerializedName("id")
        String id;
        @Expose
        @SerializedName("object")
        String object;
        @Expose
        @SerializedName("amount")
        String amount;
        @Expose
        @SerializedName("client_secret")
        String client_secret;

    public String getId() {
        return id;
    }

    public String getObject() {
        return object;
    }

    public String getAmount() {
        return amount;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public String getCurrency() {
        return currency;
    }

    @Expose
        @SerializedName("currency")
        String currency;


}
