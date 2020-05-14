package com.delivr.Common;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


/**
 * Zco Engineering Dept.
 */
public class PermissionManager {
    public static final int ALL_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_WRITE_PERMISSION_REQUEST_CODE = 2;
    public static final int EXTERNAL_STORAGE_READ_PERMISSION_REQUEST_CODE = 3;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 5;
    private static final String TAG = "Permission";


    /**
     * Check Permission
     *
     * @return
     */
    public static boolean checkIsGreaterThanM() {
        return Build.VERSION.SDK_INT >= 23;
    }

    public static boolean checkPermissionForWriteExternalStorage(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionForReadExternalStorage(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionForReadPhoneState(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionForCamara(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean checkPermissionForCall(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    public static boolean checkPermissionForLocation(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean checkPermissionForCoarseLocation(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    public static boolean checkPermissionForFineLocation(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request Permission
     */

    public static boolean checkPermissionForRecord_audio(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermissionForWriteExternalStorage(Fragment fragment) {
        fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                EXTERNAL_STORAGE_WRITE_PERMISSION_REQUEST_CODE);
    }

    public static void requestPermissionForReadExternalStorage(Fragment fragment) {
        fragment.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_READ_PERMISSION_REQUEST_CODE);
    }
    public static void requestPermissionForLocation(Fragment fragment) {
        fragment.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
    }
    public static void requestPermissionForAll(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
//                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
//                Manifest.permission.CAMERA,
                Manifest.permission.CALL_PHONE/*,
                Manifest.permission.RECORD_AUDIO*/
                ,}, ALL_PERMISSION_REQUEST_CODE);
    }
    /**
     * Is permission rationale
     * Used to Check If the permission is not in the application and user decline the popup previously.
     */

    public boolean isExternalStorageWritePermissionRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    public static boolean isExternalStorageReadPermissionRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    public static boolean isLocationPermissionRationale(Activity activity) {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION);
    }
}
