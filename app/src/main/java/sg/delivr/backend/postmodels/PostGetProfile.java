package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostGetProfile {
    @Expose
    @SerializedName("Id")
    private String id;

    @Expose @SerializedName("APIKEY")
    private String APIKEY;
    @Expose @SerializedName("Signature")
    private String Signature;
    public PostGetProfile(String id,
                        String APIKEY, String Signature) {
        this.id = id;
        this.APIKEY = APIKEY;
        this.Signature = Signature;
    }
}
