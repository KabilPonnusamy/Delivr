package sg.delivr.backend;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataEnvelope<T> {
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("data")
    private T Data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }


    public T getData() {
        return Data;
    }
}
