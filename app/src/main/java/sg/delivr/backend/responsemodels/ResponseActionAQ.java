package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseActionAQ {

    @Expose
    @SerializedName("StatusCode")
    String StatusCode;
    @Expose
    @SerializedName("Status")
    String Status;
    @Expose
    @SerializedName("Statusmsg")
    String Statusmsg;

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getStatusmsg() {
        return Statusmsg;
    }

    public void setStatusmsg(String statusmsg) {
        Statusmsg = statusmsg;
    }

}
