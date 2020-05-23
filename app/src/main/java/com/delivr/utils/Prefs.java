package com.delivr.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.delivr.Application;


public class Prefs {

    public static SharedPreferences getPrefs() {
        return Application.getInstance().getSharedPreferences(
                "com.delivr.app_preferences", Context.MODE_PRIVATE);
    }

    public static String getUserId() {
        return getPrefs().getString("userid", "");
    }

    public static void setUserId(String userid) {
        getPrefs().edit().putString("userid", userid).apply();
    }

    public static String getUserRole() {
        return getPrefs().getString("userrole", "");
    }

    public static void setUserRole(String userrole) {
        getPrefs().edit().putString("userrole", userrole).apply();
    }

    public static String getUserFullname() {
        return getPrefs().getString("fullname", "");
    }

    public static void setUserFullname(String fullname) {
        getPrefs().edit().putString("fullname", fullname).apply();
    }

    public static String getLoginVerified() {
        return getPrefs().getString("loginsuccess", "");
    }

    public static void setLoginVerified(String emailPendingActivation) {
        getPrefs().edit().putString("loginsuccess", emailPendingActivation).apply();
    }

    public static String getUserName() {
        return getPrefs().getString("user_firstname", "");
    }

    public static void setUserName(String user_firstname) {
        getPrefs().edit().putString("user_firstname", user_firstname).apply();
    }

    public static String getUserEmail() {
        return getPrefs().getString("user_email", "");
    }

    public static void setUserEmail(String user_email) {
        getPrefs().edit().putString("user_email", user_email).apply();
    }

    public static String getUserMobileNo() {
        return getPrefs().getString("user_mobileno", "");
    }

    public static void setUserMobileNo(String user_mobileno) {
        getPrefs().edit().putString("user_mobileno", user_mobileno).apply();
    }

    public static String getUserAddressLine1() {
        return getPrefs().getString("user_addr1", "");
    }

    public static void setUserAddressLine1(String user_addr1) {
        getPrefs().edit().putString("user_addr1", user_addr1).apply();
    }

    public static String getUserAddressLine2() {
        return getPrefs().getString("user_addr2", "");
    }

    public static void setUserAddressLine2(String user_addr2) {
        getPrefs().edit().putString("user_addr2", user_addr2).apply();
    }

    public static String getUserPostalCode() {
        return getPrefs().getString("user_postalcode", "");
    }

    public static void setUserPostalCode(String user_postalcode) {
        getPrefs().edit().putString("user_postalcode", user_postalcode).apply();
    }

    public static String getUserDeliveryAddressLine1() {
        return getPrefs().getString("user_deliveryaddr1", "");
    }

    public static void setUserDeliveryAddressLine1(String user_deliveryaddr1) {
        getPrefs().edit().putString("user_deliveryaddr1", user_deliveryaddr1).apply();
    }

    public static String getUserDeliveryAddressLine2() {
        return getPrefs().getString("user_deliveryaddr2", "");
    }

    public static void setUserDeliveryAddressLine2(String user_deliveryaddr2) {
        getPrefs().edit().putString("user_deliveryaddr2", user_deliveryaddr2).apply();
    }

    public static String getUserDeliveryPostalCode() {
        return getPrefs().getString("user_deliverypostalcode", "");
    }

    public static void setUserDeliveryPostalCode(String user_deliverypostalcode) {
        getPrefs().edit().putString("user_deliverypostalcode", user_deliverypostalcode).apply();
    }

    public static String getUserEnteredDeliveryAddress() {
        return getPrefs().getString("user_enterdeliveryaddr", "");
    }

    public static void setUserEnteredDeliveryAddress(String user_enterdeliveryaddr) {
        getPrefs().edit().putString("user_enterdeliveryaddr", user_enterdeliveryaddr).apply();
    }

    public static String getUserAddress() {
        return getPrefs().getString("user_addr", "");
    }

    public static void setUserAddress(String user_addr) {
        getPrefs().edit().putString("user_addr", user_addr).apply();
    }

    public static String getUserPoints() {
        return getPrefs().getString("userpoints", "");
    }

    public static void setUserPoints(String userpoints) {
        getPrefs().edit().putString("userpoints", userpoints).apply();
    }

    public static String getPlanId() {
        return getPrefs().getString("user_planid", "");
    }

    public static void setPlanId(String user_planid) {
        getPrefs().edit().putString("user_planid", user_planid).apply();
    }

    public static String getMobileVerification_BackClicked() {
        return getPrefs().getString("mobile_verify", "false");
    }

    public static void setMobileVerification_BackClicked(String mobile_verify) {
        getPrefs().edit().putString("mobile_verify", mobile_verify).apply();
    }

    public static String getIsVerifiedOTP() {
        return getPrefs().getString("is_verifiedotp", "false");
    }

    public static void setIsVerifiedOTP(String is_verifiedotp) {
        getPrefs().edit().putString("is_verifiedotp", is_verifiedotp).apply();
    }

    public static String getIsVerifiedValidPlan() {
        return getPrefs().getString("is_validplan", "false");
    }

    public static void setIsVerifiedValidPlan(String is_validplan) {
        getPrefs().edit().putString("is_validplan", is_validplan).apply();
    }

    public static String getDeviceToken() {
        return getPrefs().getString("device_tokenid", "false");
    }

    public static void setDeviceToken(String device_tokenid) {
        getPrefs().edit().putString("device_tokenid", device_tokenid).apply();
    }

    public static int getNotificationCount() {
        return getPrefs().getInt("notification_count", 0);
    }

    public static void setNotificationCount(int notification_count) {
        getPrefs().edit().putInt("notification_count", notification_count).apply();
    }
}
