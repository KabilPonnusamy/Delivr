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
