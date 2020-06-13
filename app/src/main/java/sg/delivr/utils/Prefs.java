package sg.delivr.utils;

import android.content.Context;
import android.content.SharedPreferences;

import sg.delivr.Application;


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

    public static String getUserImage() {
        return getPrefs().getString("userimage", "");
    }

    public static void setUserImage(String userimage) {
        getPrefs().edit().putString("userimage", userimage).apply();
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

    public static String getSenderName() {
        return getPrefs().getString("user_sendername", "");
    }

    public static void setSenderName(String user_sendername) {
        getPrefs().edit().putString("user_sendername", user_sendername).apply();
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

    public static String getUserCompanyName() {
        return getPrefs().getString("user_companyname", "");
    }

    public static void setUserCompanyName(String user_companyname) {
        getPrefs().edit().putString("user_companyname", user_companyname).apply();
    }

    public static String getUserUnitNo() {
        return getPrefs().getString("user_unitno", "");
    }

    public static void setUserUnitNo(String user_unitno) {
        getPrefs().edit().putString("user_unitno", user_unitno).apply();
    }

    public static String getUserPostalCode() {
        return getPrefs().getString("user_postalcode", "");
    }

    public static void setUserPostalCode(String user_postalcode) {
        getPrefs().edit().putString("user_postalcode", user_postalcode).apply();
    }

    public static String getMerchAuthTimeFrom() {
        return getPrefs().getString("merch_timefrom", "");
    }

    public static void setMerchAuthTimeFrom(String merch_timefrom) {
        getPrefs().edit().putString("merch_timefrom", merch_timefrom).apply();
    }

    public static String getMerchAuthTimeTo() {
        return getPrefs().getString("merch_timeto", "");
    }

    public static void setMerchAuthTimeTo(String merch_timeto) {
        getPrefs().edit().putString("merch_timeto", merch_timeto).apply();
    }

    public static String getMerchAuthTimeSensitive() {
        return getPrefs().getString("merch_timesensitive", "");
    }

    public static void setMerchAuthTimeSensitive(String merch_timesensitive) {
        getPrefs().edit().putString("merch_timesensitive", merch_timesensitive).apply();
    }

    public static String getMerchAuthPriceType() {
        return getPrefs().getString("merch_pricetype", "");
    }

    public static void setMerchAuthPriceType(String merch_pricetype) {
        getPrefs().edit().putString("merch_pricetype", merch_pricetype).apply();
    }

    public static String getMerchAuthPrice() {
        return getPrefs().getString("merch_price", "");
    }

    public static void setMerchAuthPrice(String merch_price) {
        getPrefs().edit().putString("merch_price", merch_price).apply();
    }

    public static String getMerchAuthConsignmentType() {
        return getPrefs().getString("merch_consignmenttype", "");
    }

    public static void setMerchAuthConsignmentType(String merch_consignmenttype) {
        getPrefs().edit().putString("merch_consignmenttype", merch_consignmenttype).apply();
    }

    public static String getMerchAuthServiceType() {
        return getPrefs().getString("merch_servicetype", "");
    }

    public static void setMerchAuthServiceType(String merch_servicetype) {
        getPrefs().edit().putString("merch_servicetype", merch_servicetype).apply();
    }

    public static String getMerchAuthExceptZones() {
        return getPrefs().getString("merch_exceptzones", "");
    }

    public static void setMerchAuthExceptZones(String merch_exceptzones) {
        getPrefs().edit().putString("merch_exceptzones", merch_exceptzones).apply();
    }

    public static String getUserDeliveryAddressLine() {
        return getPrefs().getString("user_deliveryaddr", "");
    }

    public static void setUserDeliveryAddressLine(String user_deliveryaddr2) {
        getPrefs().edit().putString("user_deliveryaddr", user_deliveryaddr2).apply();
    }

    public static String getUserDeliveryPostalCode() {
        return getPrefs().getString("user_deliverypostalcode", "");
    }

    public static void setUserDeliveryPostalCode(String user_deliverypostalcode) {
        getPrefs().edit().putString("user_deliverypostalcode", user_deliverypostalcode).apply();
    }

    public static String getUserAddress() {
        return getPrefs().getString("user_addr", "");
    }

    public static void setUserAddress(String user_addr) {
        getPrefs().edit().putString("user_addr", user_addr).apply();
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
