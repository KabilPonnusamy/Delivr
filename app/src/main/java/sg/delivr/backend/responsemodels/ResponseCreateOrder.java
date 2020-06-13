package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseCreateOrder extends BaseResponse {
        @Expose
        @SerializedName("OrderWBUid")
        String OrderWBUid;
        @Expose
        @SerializedName("OrdersNo")
        String OrdersNo;
        @Expose
        @SerializedName("Status")
        String Status;
        @Expose
        @SerializedName("StatusMessage")
        String StatusMessage;

    public String getOrdersNo() {
        return OrdersNo;
    }

    public String getStatus() {
        return Status;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

    public String getOrderWBUid() {
            return OrderWBUid;
        }
}
