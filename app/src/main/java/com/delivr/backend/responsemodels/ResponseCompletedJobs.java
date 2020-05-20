package com.delivr.backend.responsemodels;

import com.delivr.backend.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseCompletedJobs extends BaseResponse {

        @Expose
        @SerializedName("OrderWBUid")
        String OrderWBUid;
        @Expose
        @SerializedName("WBno")
        String WBno;
        @Expose
        @SerializedName("ServiceType")
        String ServiceType;
        @Expose
        @SerializedName("pickupdatetime")
        String pickupdatetime;
        @Expose
        @SerializedName("ConsignmentType")
        String ConsignmentType;
        @Expose
        @SerializedName("PickupRoadno")
        String PickupRoadno;
        @Expose
        @SerializedName("PickupRoadName")
        String PickupRoadName;
        @Expose
        @SerializedName("PickupBuilding")
        String PickupBuilding;
        @Expose
        @SerializedName("PickupBuildingType")
        String PickupBuildingType;
        @Expose
        @SerializedName("PickupZipcode")
        String PickupZipcode;
        @Expose
        @SerializedName("DlvyRoadno")
        String DlvyRoadno;
        @Expose
        @SerializedName("DlvyRoadName")
        String DlvyRoadName;
        @Expose
        @SerializedName("DlvyBuilding")
        String DlvyBuilding;
        @Expose
        @SerializedName("DlvyBuildingType")
        String DlvyBuildingType;
        @Expose
        @SerializedName("DlvyZipcode")
        String DlvyZipcode;
        @Expose
        @SerializedName("Status")
        String Status;
        @Expose
        @SerializedName("StatusCode")
        String StatusCode;
        @Expose
        @SerializedName("Amount")
        String Amount;
        @Expose
        @SerializedName("Delivery_CO")
        String Delivery_CO;
        @Expose
        @SerializedName("Credit")
        String Credit;
        @Expose
        @SerializedName("LastStatusCode")
        String LastStatusCode;
        @Expose
        @SerializedName("JobType")
        String JobType;
        @Expose
        @SerializedName("SenderCompName")
        String SenderCompName;
        @Expose
        @SerializedName("PickupSender")
        String PickupSender;
        @Expose
        @SerializedName("PickupContactno")
        String PickupContactno;
        @Expose
        @SerializedName("PickupUnitNo")
        String PickupUnitNo;
        @Expose
        @SerializedName("DlvyUnitNo")
        String DlvyUnitNo;
        @Expose
        @SerializedName("SpInst")
        String SpInst;
        @Expose
        @SerializedName("OrderType")
        String OrderType;
        @Expose
        @SerializedName("PassType")
        String PassType;

        public String getOrderWBUid() {
            return OrderWBUid;
        }

        public void setOrderWBUid(String orderWBUid) {
            OrderWBUid = orderWBUid;
        }

        public String getWBno() {
            return WBno;
        }

        public void setWBno(String WBno) {
            this.WBno = WBno;
        }

        public String getServiceType() {
            return ServiceType;
        }

        public void setServiceType(String serviceType) {
            ServiceType = serviceType;
        }

        public String getPickupdatetime() {
            return pickupdatetime;
        }

        public void setPickupdatetime(String pickupdatetime) {
            this.pickupdatetime = pickupdatetime;
        }

        public String getConsignmentType() {
            return ConsignmentType;
        }

        public void setConsignmentType(String consignmentType) {
            ConsignmentType = consignmentType;
        }

        public String getPickupRoadno() {
            return PickupRoadno;
        }

        public void setPickupRoadno(String pickupRoadno) {
            PickupRoadno = pickupRoadno;
        }

        public String getPickupRoadName() {
            return PickupRoadName;
        }

        public void setPickupRoadName(String pickupRoadName) {
            PickupRoadName = pickupRoadName;
        }

        public String getPickupBuilding() {
            return PickupBuilding;
        }

        public void setPickupBuilding(String pickupBuilding) {
            PickupBuilding = pickupBuilding;
        }

        public String getPickupBuildingType() {
            return PickupBuildingType;
        }

        public void setPickupBuildingType(String pickupBuildingType) {
            PickupBuildingType = pickupBuildingType;
        }

        public String getPickupZipcode() {
            return PickupZipcode;
        }

        public void setPickupZipcode(String pickupZipcode) {
            PickupZipcode = pickupZipcode;
        }

        public String getDlvyRoadno() {
            return DlvyRoadno;
        }

        public void setDlvyRoadno(String dlvyRoadno) {
            DlvyRoadno = dlvyRoadno;
        }

        public String getDlvyRoadName() {
            return DlvyRoadName;
        }

        public void setDlvyRoadName(String dlvyRoadName) {
            DlvyRoadName = dlvyRoadName;
        }

        public String getDlvyBuilding() {
            return DlvyBuilding;
        }

        public void setDlvyBuilding(String dlvyBuilding) {
            DlvyBuilding = dlvyBuilding;
        }

        public String getDlvyBuildingType() {
            return DlvyBuildingType;
        }

        public void setDlvyBuildingType(String dlvyBuildingType) {
            DlvyBuildingType = dlvyBuildingType;
        }

        public String getDlvyZipcode() {
            return DlvyZipcode;
        }

        public void setDlvyZipcode(String dlvyZipcode) {
            DlvyZipcode = dlvyZipcode;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getStatusCode() {
            return StatusCode;
        }

        public void setStatusCode(String statusCode) {
            StatusCode = statusCode;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String amount) {
            Amount = amount;
        }

        public String getDelivery_CO() {
            return Delivery_CO;
        }

        public void setDelivery_CO(String delivery_CO) {
            Delivery_CO = delivery_CO;
        }

        public String getCredit() {
            return Credit;
        }

        public void setCredit(String credit) {
            Credit = credit;
        }

        public String getLastStatusCode() {
            return LastStatusCode;
        }

        public void setLastStatusCode(String lastStatusCode) {
            LastStatusCode = lastStatusCode;
        }

        public String getJobType() {
            return JobType;
        }

        public void setJobType(String jobType) {
            JobType = jobType;
        }

        public String getSenderCompName() {
            return SenderCompName;
        }

        public void setSenderCompName(String senderCompName) {
            SenderCompName = senderCompName;
        }

        public String getPickupSender() {
            return PickupSender;
        }

        public void setPickupSender(String pickupSender) {
            PickupSender = pickupSender;
        }

        public String getPickupContactno() {
            return PickupContactno;
        }

        public void setPickupContactno(String pickupContactno) {
            PickupContactno = pickupContactno;
        }

        public String getPickupUnitNo() {
            return PickupUnitNo;
        }

        public void setPickupUnitNo(String pickupUnitNo) {
            PickupUnitNo = pickupUnitNo;
        }

        public String getDlvyUnitNo() {
            return DlvyUnitNo;
        }

        public void setDlvyUnitNo(String dlvyUnitNo) {
            DlvyUnitNo = dlvyUnitNo;
        }

        public String getSpInst() {
            return SpInst;
        }

        public void setSpInst(String spInst) {
            SpInst = spInst;
        }

        public String getOrderType() {
            return OrderType;
        }

        public void setOrderType(String orderType) {
            OrderType = orderType;
        }

        public String getPassType() {
            return PassType;
        }

        public void setPassType(String passType) {
            PassType = passType;
        }
}
