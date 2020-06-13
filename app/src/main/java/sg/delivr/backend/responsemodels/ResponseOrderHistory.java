package sg.delivr.backend.responsemodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import sg.delivr.backend.BaseResponse;

public class ResponseOrderHistory extends BaseResponse {

    @Expose
    @SerializedName("OrderWBUId")
    String OrderWBUId;
    @Expose
    @SerializedName("OrderNo")
    String OrderNo;
    @Expose
    @SerializedName("SenderId")
    String SenderId;
    @Expose
    @SerializedName("PickupSameas")
    String PickupSameas;
    @Expose
    @SerializedName("PickupZipCode")
    String PickupZipCode;
    @Expose
    @SerializedName("PickupUnitNo")
    String PickupUnitNo;
    @Expose
    @SerializedName("PickupContactName")
    String PickupContactName;
    @Expose
    @SerializedName("PickupContactNo")
    String PickupContactNo;
    @Expose
    @SerializedName("PickupCompName")
    String PickupCompName;
    @Expose
    @SerializedName("DeliverySameas")
    String DeliverySameas;
    @Expose
    @SerializedName("DeliveryZipCode")
    String DeliveryZipCode;
    @Expose
    @SerializedName("DeliveryUnitNo")
    String DeliveryUnitNo;
    @Expose
    @SerializedName("DeliveryContactName")
    String DeliveryContactName;
    @Expose
    @SerializedName("DeliveryCompName")
    String DeliveryCompName;
    @Expose
    @SerializedName("DeliveryContactNo")
    String DeliveryContactNo;
    @Expose
    @SerializedName("ConsignmentType")
    String ConsignmentType;
    @Expose
    @SerializedName("ServiceType")
    String ServiceType;
    @Expose
    @SerializedName("PickupDateTime")
    String PickupDateTime;
    @Expose
    @SerializedName("Description")
    String Description;
    @Expose
    @SerializedName("SpInst")
    String SpInst;
    @Expose
    @SerializedName("Delivery_CO")
    String Delivery_CO;
    @Expose
    @SerializedName("Rate")
    String Rate;
    @Expose
    @SerializedName("SenderName")
    String SenderName;
    @Expose
    @SerializedName("Status")
    String Status;
    @Expose
    @SerializedName("credit")
    String credit;
    @Expose
    @SerializedName("RiderType")
    String RiderType;
    @Expose
    @SerializedName("surcharge")
    String surcharge;
    @Expose
    @SerializedName("Total")
    String Total;
    @Expose
    @SerializedName("RiderId")
    String RiderId;
    @Expose
    @SerializedName("RiderName")
    String RiderName;
    @Expose
    @SerializedName("WBNo")
    String WBNo;
    @Expose
    @SerializedName("CompanyName")
    String CompanyName;
    @Expose
    @SerializedName("RoadNO")
    String RoadNO;
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
    @SerializedName("PRoadNO")
    String PRoadNO;
    @Expose
    @SerializedName("PRoadName")
    String PRoadName;
    @Expose
    @SerializedName("PBuilding")
    String PBuilding;
    @Expose
    @SerializedName("PLatitude")
    String PLatitude;
    @Expose
    @SerializedName("PLongitude")
    String PLongitude;
    @Expose
    @SerializedName("DRoadNO")
    String DRoadNO;
    @Expose
    @SerializedName("DRoadName")
    String DRoadName;
    @Expose
    @SerializedName("DBuilding")
    String DBuilding;
    @Expose
    @SerializedName("DLatitude")
    String DLatitude;
    @Expose
    @SerializedName("DLongitude")
    String DLongitude;
    @Expose
    @SerializedName("EmbMOM")
    String EmbMOM;

    public String getOrderWBUId() {
        return OrderWBUId;
    }

    public void setOrderWBUId(String orderWBUId) {
        OrderWBUId = orderWBUId;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getSenderId() {
        return SenderId;
    }

    public void setSenderId(String senderId) {
        SenderId = senderId;
    }

    public String getPickupSameas() {
        return PickupSameas;
    }

    public void setPickupSameas(String pickupSameas) {
        PickupSameas = pickupSameas;
    }

    public String getPickupZipCode() {
        return PickupZipCode;
    }

    public void setPickupZipCode(String pickupZipCode) {
        PickupZipCode = pickupZipCode;
    }

    public String getPickupUnitNo() {
        return PickupUnitNo;
    }

    public void setPickupUnitNo(String pickupUnitNo) {
        PickupUnitNo = pickupUnitNo;
    }

    public String getPickupContactName() {
        return PickupContactName;
    }

    public void setPickupContactName(String pickupContactName) {
        PickupContactName = pickupContactName;
    }

    public String getPickupContactNo() {
        return PickupContactNo;
    }

    public void setPickupContactNo(String pickupContactNo) {
        PickupContactNo = pickupContactNo;
    }

    public String getPickupCompName() {
        return PickupCompName;
    }

    public void setPickupCompName(String pickupCompName) {
        PickupCompName = pickupCompName;
    }

    public String getDeliverySameas() {
        return DeliverySameas;
    }

    public void setDeliverySameas(String deliverySameas) {
        DeliverySameas = deliverySameas;
    }

    public String getDeliveryZipCode() {
        return DeliveryZipCode;
    }

    public void setDeliveryZipCode(String deliveryZipCode) {
        DeliveryZipCode = deliveryZipCode;
    }

    public String getDeliveryUnitNo() {
        return DeliveryUnitNo;
    }

    public void setDeliveryUnitNo(String deliveryUnitNo) {
        DeliveryUnitNo = deliveryUnitNo;
    }

    public String getDeliveryContactName() {
        return DeliveryContactName;
    }

    public void setDeliveryContactName(String deliveryContactName) {
        DeliveryContactName = deliveryContactName;
    }

    public String getDeliveryCompName() {
        return DeliveryCompName;
    }

    public void setDeliveryCompName(String deliveryCompName) {
        DeliveryCompName = deliveryCompName;
    }

    public String getDeliveryContactNo() {
        return DeliveryContactNo;
    }

    public void setDeliveryContactNo(String deliveryContactNo) {
        DeliveryContactNo = deliveryContactNo;
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

    public String getPickupDateTime() {
        return PickupDateTime;
    }

    public void setPickupDateTime(String pickupDateTime) {
        PickupDateTime = pickupDateTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getSpInst() {
        return SpInst;
    }

    public void setSpInst(String spInst) {
        SpInst = spInst;
    }

    public String getDelivery_CO() {
        return Delivery_CO;
    }

    public void setDelivery_CO(String delivery_CO) {
        Delivery_CO = delivery_CO;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getRiderType() {
        return RiderType;
    }

    public void setRiderType(String riderType) {
        RiderType = riderType;
    }

    public String getSurcharge() {
        return surcharge;
    }

    public void setSurcharge(String surcharge) {
        this.surcharge = surcharge;
    }

    public String getTotal() {
        return Total;
    }

    public void setTotal(String total) {
        Total = total;
    }

    public String getRiderId() {
        return RiderId;
    }

    public void setRiderId(String riderId) {
        RiderId = riderId;
    }

    public String getRiderName() {
        return RiderName;
    }

    public void setRiderName(String riderName) {
        RiderName = riderName;
    }

    public String getWBNo() {
        return WBNo;
    }

    public void setWBNo(String WBNo) {
        this.WBNo = WBNo;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getRoadNO() {
        return RoadNO;
    }

    public void setRoadNO(String roadNO) {
        RoadNO = roadNO;
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

    public String getPRoadNO() {
        return PRoadNO;
    }

    public void setPRoadNO(String PRoadNO) {
        this.PRoadNO = PRoadNO;
    }

    public String getPRoadName() {
        return PRoadName;
    }

    public void setPRoadName(String PRoadName) {
        this.PRoadName = PRoadName;
    }

    public String getPBuilding() {
        return PBuilding;
    }

    public void setPBuilding(String PBuilding) {
        this.PBuilding = PBuilding;
    }

    public String getPLatitude() {
        return PLatitude;
    }

    public void setPLatitude(String PLatitude) {
        this.PLatitude = PLatitude;
    }

    public String getPLongitude() {
        return PLongitude;
    }

    public void setPLongitude(String PLongitude) {
        this.PLongitude = PLongitude;
    }

    public String getDRoadNO() {
        return DRoadNO;
    }

    public void setDRoadNO(String DRoadNO) {
        this.DRoadNO = DRoadNO;
    }

    public String getDRoadName() {
        return DRoadName;
    }

    public void setDRoadName(String DRoadName) {
        this.DRoadName = DRoadName;
    }

    public String getDBuilding() {
        return DBuilding;
    }

    public void setDBuilding(String DBuilding) {
        this.DBuilding = DBuilding;
    }

    public String getDLatitude() {
        return DLatitude;
    }

    public void setDLatitude(String DLatitude) {
        this.DLatitude = DLatitude;
    }

    public String getDLongitude() {
        return DLongitude;
    }

    public void setDLongitude(String DLongitude) {
        this.DLongitude = DLongitude;
    }

    public String getEmbMOM() {
        return EmbMOM;
    }

    public void setEmbMOM(String embMOM) {
        EmbMOM = embMOM;
    }

}
