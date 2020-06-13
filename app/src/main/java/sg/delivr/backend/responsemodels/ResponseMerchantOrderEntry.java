package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseMerchantOrderEntry extends BaseResponse {

        @Expose
        @SerializedName("SenderName")
        String SenderName;
        @Expose
        @SerializedName("CompanyName")
        String CompanyName;
        @Expose
        @SerializedName("RoadNo")
        String RoadNo;
        @Expose
        @SerializedName("RoadName")
        String RoadName;
        @Expose
        @SerializedName("Building")
        String Building;
        @Expose
        @SerializedName("UnitNo")
        String UnitNo;
        @Expose
        @SerializedName("Zipcode")
        String Zipcode;
        @Expose
        @SerializedName("ContactNo")
        String ContactNo;
        @Expose
        @SerializedName("EmailAddress")
        String EmailAddress;
        @Expose
        @SerializedName("SenderId")
        String SenderId;

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getRoadNo() {
        return RoadNo;
    }

    public void setRoadNo(String roadNo) {
        RoadNo = roadNo;
    }

    public String getRoadName() {
        return RoadName;
    }

    public void setRoadName(String roadName) {
        RoadName = roadName;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getUnitNo() {
        return UnitNo;
    }

    public void setUnitNo(String unitNo) {
        UnitNo = unitNo;
    }

    public String getZipcode() {
        return Zipcode;
    }

    public void setZipcode(String zipcode) {
        Zipcode = zipcode;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getZoneCode() {
        return ZoneCode;
    }

    public void setZoneCode(String zoneCode) {
        ZoneCode = zoneCode;
    }

    @Expose
        @SerializedName("ZoneCode")
        String ZoneCode;

}
