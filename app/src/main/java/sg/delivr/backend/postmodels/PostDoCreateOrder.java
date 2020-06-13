package sg.delivr.backend.postmodels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostDoCreateOrder {
    @Expose
    @SerializedName("OrderUid")
    private String OrderUid;
    @Expose
    @SerializedName("OrderNo")
    private String OrderNo;
    @Expose
    @SerializedName("SenderId")
    private String SenderId;
    @Expose
    @SerializedName("PickupSameas")
    private String PickupSameas;
    @Expose
    @SerializedName("PickupZipCode")
    private String PickupZipCode;
    @Expose
    @SerializedName("PickupUnitNo")
    private String PickupUnitNo;
    @Expose
    @SerializedName("PickupContactName")
    private String PickupContactName;
    @Expose
    @SerializedName("PickupCompanyName")
    private String PickupCompanyName;
    @Expose
    @SerializedName("PickupContactNo")
    private String PickupContactNo;
    @Expose
    @SerializedName("DeliverySameas")
    private String DeliverySameas;
    @Expose
    @SerializedName("DeliveryZipCode")
    private String DeliveryZipCode;
    @Expose
    @SerializedName("DeliveryUnitNo")
    private String DeliveryUnitNo;
    @Expose
    @SerializedName("DeliveryContactName")
    private String DeliveryContactName;
    @Expose
    @SerializedName("DeliveryCompanyName")
    private String DeliveryCompanyName;
    @Expose
    @SerializedName("DeliveryContactNo")
    private String DeliveryContactNo;
    @Expose
    @SerializedName("ConsignmentType")
    private String ConsignmentType;
    @Expose
    @SerializedName("ServiceType")
    private String ServiceType;
    @Expose
    @SerializedName("PickupDateTime")
    private String PickupDateTime;
    @Expose
    @SerializedName("Description")
    private String Description;
    @Expose
    @SerializedName("SpInst")
    private String SpInst;
    @Expose
    @SerializedName("Delivery_CO")
    private String Delivery_CO;
    @Expose
    @SerializedName("Status")
    private String Status;
    @Expose
    @SerializedName("RiderType")
    private String RiderType;
    @Expose
    @SerializedName("Rate")
    private String Rate;
    @Expose
    @SerializedName("Credit")
    private String Credit;
    @Expose
    @SerializedName("surcharge")
    private String surcharge;
    @Expose
    @SerializedName("Total")
    private String Total;
    @Expose
    @SerializedName("InvoiceAmount")
    private String InvoiceAmount;
    @Expose
    @SerializedName("ID")
    private String ID;
    @Expose
    @SerializedName("APIKEY")
    private String APIKEY;
    @Expose
    @SerializedName("Signature")
    private String Signature;

    public PostDoCreateOrder(String OrderUid, String OrderNo, String SenderId, String PickupSameas,
                             String PickupZipCode, String PickupUnitNo, String PickupContactName, String PickupCompanyName,
                             String PickupContactNo, String DeliverySameas, String DeliveryZipCode, String DeliveryUnitNo,
                             String DeliveryContactName, String DeliveryCompanyName, String DeliveryContactNo, String ConsignmentType,
                             String ServiceType, String PickupDateTime, String Description, String SpInst, String Delivery_CO,
                             String Status, String RiderType, String Rate, String Credit, String surcharge, String Total,
                             String InvoiceAmount, String ID, String APIKEY, String signature) {
        this.OrderUid = OrderUid;
        this.OrderNo = OrderNo;
        this.SenderId = SenderId;
        this.PickupSameas = PickupSameas;
        this.PickupZipCode = PickupZipCode;
        this.PickupUnitNo = PickupUnitNo;
        this.PickupContactName = PickupContactName;
        this.PickupCompanyName = PickupCompanyName;
        this.PickupContactNo = PickupContactNo;
        this.DeliverySameas = DeliverySameas;
        this.DeliveryZipCode = DeliveryZipCode;
        this.DeliveryUnitNo = DeliveryUnitNo;
        this.DeliveryContactName = DeliveryContactName;
        this.DeliveryCompanyName = DeliveryCompanyName;
        this.DeliveryContactNo = DeliveryContactNo;
        this.ConsignmentType = ConsignmentType;
        this.ServiceType = ServiceType;
        this.PickupDateTime = PickupDateTime;
        this.Description = Description;
        this.SpInst = SpInst;
        this.Delivery_CO = Delivery_CO;
        this.Status = Status;
        this.RiderType = RiderType;
        this.Rate = Rate;
        this.Credit = Credit;
        this.surcharge = surcharge;
        this.Total = Total;
        this.InvoiceAmount = InvoiceAmount;
        this.ID = ID;
        this.APIKEY = APIKEY;
        Signature = signature;
    }

}
