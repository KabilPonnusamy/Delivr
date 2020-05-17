package com.delivr.backend.responsemodels;

import com.delivr.backend.BaseResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseGetRiders extends BaseResponse {

    @Expose
    @SerializedName("Riders")
    private List<RiderssList> ridersList;

    public static class RiderssList {

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
        @Expose
        @SerializedName("Zipcode")
        String Zipcode;
        @Expose
        @SerializedName("Message")
        String Message;

        public String getUserId() {
            return UserId;
        }

        public void setUserId(String userId) {
            UserId = userId;
        }

        public String getLogin() {
            return Login;
        }

        public void setLogin(String login) {
            Login = login;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            Password = password;
        }

        public String getFname() {
            return Fname;
        }

        public void setFname(String fname) {
            Fname = fname;
        }

        public String getFullname() {
            return Fullname;
        }

        public void setFullname(String fullname) {
            Fullname = fullname;
        }

        public String getRole() {
            return Role;
        }

        public void setRole(String role) {
            Role = role;
        }

        public String getEmail() {
            return Email;
        }

        public void setEmail(String email) {
            Email = email;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String address) {
            Address = address;
        }

        public String getMobileno() {
            return Mobileno;
        }

        public void setMobileno(String mobileno) {
            Mobileno = mobileno;
        }

        public String getPhoneno() {
            return Phoneno;
        }

        public void setPhoneno(String phoneno) {
            Phoneno = phoneno;
        }

        public String getDOJ() {
            return DOJ;
        }

        public void setDOJ(String DOJ) {
            this.DOJ = DOJ;
        }

        public String getFAX() {
            return FAX;
        }

        public void setFAX(String FAX) {
            this.FAX = FAX;
        }

        public String getCompanyName() {
            return CompanyName;
        }

        public void setCompanyName(String companyName) {
            CompanyName = companyName;
        }

        public String getNRICFIN_ACRA() {
            return NRICFIN_ACRA;
        }

        public void setNRICFIN_ACRA(String NRICFIN_ACRA) {
            this.NRICFIN_ACRA = NRICFIN_ACRA;
        }

        public String getRiderType() {
            return RiderType;
        }

        public void setRiderType(String riderType) {
            RiderType = riderType;
        }

        public String getCommission() {
            return Commission;
        }

        public void setCommission(String commission) {
            Commission = commission;
        }

        public String getCommissionDate() {
            return CommissionDate;
        }

        public void setCommissionDate(String commissionDate) {
            CommissionDate = commissionDate;
        }

        public String getZipcode() {
            return Zipcode;
        }

        public void setZipcode(String zipcode) {
            Zipcode = zipcode;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            Message = message;
        }

    }

}
