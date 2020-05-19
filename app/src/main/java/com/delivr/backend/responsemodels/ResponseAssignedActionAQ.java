package com.delivr.backend.responsemodels;

import com.delivr.backend.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAssignedActionAQ extends BaseResponse {
    @Expose
    @SerializedName("StatusCode")
    String StatusCode;

    public String getStatusCode() {
        return StatusCode;
    }

    public String getStatus() {
        return Status;
    }

    public String getStatusmsg() {
        return Statusmsg;
    }

    @Expose
    @SerializedName("Status")
    String Status;

    @Expose
    @SerializedName("Statusmsg")
    String Statusmsg;

}
