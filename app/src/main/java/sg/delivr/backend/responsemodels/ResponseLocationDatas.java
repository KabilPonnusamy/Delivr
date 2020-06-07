package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import sg.delivr.backend.BaseResponse;

public class ResponseLocationDatas extends BaseResponse {

    @Expose
    @SerializedName("p")
    String P;
    @Expose
    @SerializedName("d")
    String D;
    @Expose
    @SerializedName("v")
    String V;

    public String getP() {
        return P;
    }

    public void setP(String p) {
        P = p;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }

    public String getV() {
        return V;
    }

    public void setV(String v) {
        V = v;
    }

}
