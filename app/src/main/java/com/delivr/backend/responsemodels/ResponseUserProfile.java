package com.delivr.backend.responsemodels;

import com.delivr.backend.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseUserProfile extends BaseResponse {
    @Expose
    @SerializedName("UserId")
    String UserId;
    @Expose
    @SerializedName("Login")
    String Login;
    @Expose
    @SerializedName("Password")
    String Password;
    @Expose
    @SerializedName("Fname")
    String Fname;
    @Expose
    @SerializedName("Fullname")
    String Fullname;
    @Expose
    @SerializedName("Role")
    String Role;
    @Expose
    @SerializedName("Email")
    String Email;
    @Expose
    @SerializedName("Address")
    String Address;
    @Expose
    @SerializedName("Mobileno")
    String Mobileno;
    @Expose
    @SerializedName("Phoneno")
    String Phoneno;
    @Expose
    @SerializedName("DOJ")
    String DOJ;
    @Expose
    @SerializedName("FAX")
    String FAX;
    @Expose
    @SerializedName("CompanyName")
    String CompanyName;
    @Expose
    @SerializedName("NRICFIN_ACRA")
    String NRICFIN_ACRA;
    @Expose
    @SerializedName("RiderType")
    String RiderType;
    @Expose
    @SerializedName("Commission")
    String Commission;
    @Expose
    @SerializedName("CommissionDate")
    String CommissionDate;

    public String getUserId() {
        return UserId;
    }

    public String getLogin() {
        return Login;
    }

    public String getPassword() {
        return Password;
    }

    public String getFname() {
        return Fname;
    }

    public String getFullname() {
        return Fullname;
    }

    public String getRole() {
        return Role;
    }

    public String getEmail() {
        return Email;
    }

    public String getAddress() {
        return Address;
    }

    public String getMobileno() {
        return Mobileno;
    }

    public String getPhoneno() {
        return Phoneno;
    }

    public String getDOJ() {
        return DOJ;
    }

    public String getFAX() {
        return FAX;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public String getNRICFIN_ACRA() {
        return NRICFIN_ACRA;
    }

    public String getRiderType() {
        return RiderType;
    }

    public String getCommission() {
        return Commission;
    }

    public String getCommissionDate() {
        return CommissionDate;
    }

    public String getZipcode() {
        return Zipcode;
    }



    @Expose
    @SerializedName("Zipcode")
    String Zipcode;

}
