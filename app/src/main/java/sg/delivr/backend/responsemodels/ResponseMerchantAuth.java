package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseMerchantAuth extends BaseResponse {

        @Expose
        @SerializedName("TimeFrom")
        String TimeFrom;
        @Expose
        @SerializedName("TimeTo")
        String TimeTo;
        @Expose
        @SerializedName("TimeSensitive")
        String TimeSensitive;
        @Expose
        @SerializedName("PriceType")
        String PriceType;
        @Expose
        @SerializedName("Price")
        String Price;
        @Expose
        @SerializedName("ConsignmentType")
        String ConsignmentType;
        @Expose
        @SerializedName("ServiceType")
        String ServiceType;
        @Expose
        @SerializedName("ExceptZones")
        String ExceptZones;

    public String getTimeFrom() {
        return TimeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        TimeFrom = timeFrom;
    }

    public String getTimeTo() {
        return TimeTo;
    }

    public void setTimeTo(String timeTo) {
        TimeTo = timeTo;
    }

    public String getTimeSensitive() {
        return TimeSensitive;
    }

    public void setTimeSensitive(String timeSensitive) {
        TimeSensitive = timeSensitive;
    }

    public String getPriceType() {
        return PriceType;
    }

    public void setPriceType(String priceType) {
        PriceType = priceType;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getConsignmentType() {
        return ConsignmentType;
    }

    public void setConsignmentType(String consignmentType) {
        ConsignmentType = consignmentType;
    }

    public String getServiceType() {
        return ServiceType;
    }

    public void setServiceType(String serviceType) {
        ServiceType = serviceType;
    }

    public String getExceptZones() {
        return ExceptZones;
    }

    public void setExceptZones(String exceptZones) {
        ExceptZones = exceptZones;
    }
}
