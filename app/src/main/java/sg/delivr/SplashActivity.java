package sg.delivr;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import sg.delivr.Common.GPSTracker;
import sg.delivr.Common.PermissionManager;
import sg.delivr.Common.StoredDatas;

import sg.delivr.R;

import sg.delivr.ui.activity.Dashboard_Rider;
import sg.delivr.ui.login.LoginActivity;
import sg.delivr.utils.Prefs;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class SplashActivity extends Activity {

    final int welcomeScreenDisplay = 3000;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private static final int REQUEST_LOCATION = 10;
    String strLat, strLon, strIMEI, strMake, strModel, strOSVersion;
    //Location
    GPSTracker gps;
    public static Double lat, lon;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        checkFolderPermission();
//        launchApp();

    }

    private void checkFolderPermission() {
        if (PermissionManager.checkIsGreaterThanM()) {
            if (!PermissionManager.checkPermissionForReadExternalStorage(SplashActivity.this) ||
                    !PermissionManager.checkPermissionForWriteExternalStorage(SplashActivity.this) ||
//                    !PermissionManager.checkPermissionForReadPhoneState(SplashActivity.this) ||
                    !PermissionManager.checkPermissionForFineLocation(SplashActivity.this) ||
//                    !PermissionManager.checkPermissionForCamara(SplashActivity.this) ||
                    !PermissionManager.checkPermissionForCall(SplashActivity.this) /*||
                    !PermissionManager.checkPermissionForRecord_audio(SplashActivity.this)*/
            ) {
                PermissionManager.requestPermissionForAll(SplashActivity.this);
            } else {

                launchApp();
            }

        } else {
            launchApp();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // .. Can now obtain the UUID
//                    launchApp();
                    checkLocationEnabled();
                } else {
                    Toast.makeText(SplashActivity.this, "Unable to continue without granting permission", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;

            case PermissionManager.ALL_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                    launchApp();
                } else {
                    // Case for all the permissions are disabled
                    /*Toast.makeText(getApplicationContext(), "This App required some missing permissions." +
                                    "Please enable from app settings.",
                            Toast.LENGTH_SHORT).show();
                    finish();*/
                    launchApp();
                }
                break;

            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && cameraAccepted) {
                        Log.e("delivrApp", "Both_Accepted");
                        getLatLang();
//            Snackbar.make(view, "Permission Granted, Now you can access location data and camera.", Snackbar.LENGTH_LONG).show();
                    } else {
//            Snackbar.make(view, "Permission Denied, You cannot access location data and camera.", Snackbar.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                                showMessageOKCancel(getResources().getString(R.string.need_to_allow),
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Log.e("delivrApp", "Again_asking_permission");
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{ACCESS_FINE_LOCATION, CAMERA},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        } else {
                            Log.e("delivrApp", "Lower_version");
                            getLatLang();
                        }
                    }
                }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(SplashActivity.this)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        if (!checkPermission()) {
                            requestPermission();
                        } else {
                            getLatLang();
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loc_required_text), Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                })
                .create()
                .show();
    }

    private void launchApp() {
        checkForLocationPermission();
//        checkLocationEnabled();

    }

    private void checkLocationEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            Log.e("delivrApp", "GPS_ERROR");
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
            Log.e("delivrApp", "NETWORK_ERROR");
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(this);
            dialog.setMessage(getResources().getString(R.string.gps_network_not_enabled));
            dialog.setCancelable(false);
            dialog.setPositiveButton(getResources().getString(R.string.open_location_settings), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.dismiss();
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 901);
                }
            });
            dialog.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    paramDialogInterface.dismiss();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loc_required_text), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
            dialog.show();
        } else {
            startApp();
        }
    }

    private void checkForLocationPermission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(SplashActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    showPermissionMessage();

                } else {

                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(SplashActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION);
                }
            } else {
                checkLocationEnabled();
                //... Permission has already been granted, obtain the UUID
//                startApp();
            }

        } else {
            checkLocationEnabled();
            //... No need to request permission, obtain the UUID
//            startApp();
        }

    }

    private void startApp() {
        /** create a thread to show splash up to splash time */
        Thread welcomeThread = new Thread() {
            int wait = 0;

            @Override
            public void run() {
                try {
                    super.run();
                    /**
                     * use while to get the splash time. Use sleep() to increase
                     * the wait variable for every 100L.
                     */
                    while (wait < welcomeScreenDisplay) {
                        sleep(100);
                        wait += 100;
                    }
                } catch (Exception e) {
                    System.out.println("EXc=" + e);
                } finally {
                    userId = Prefs.getUserId();
                    if(!userId.equalsIgnoreCase("")) {
                        StoredDatas.getInstance().setScreenValidation("Splash");
                        Intent i = new Intent(getApplicationContext(), Dashboard_Rider.class);
                        startActivity(i);
                        finish();
                    } else {
                        Intent myIntent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(myIntent);
                        finish();
                    }
                }
            }
        };
        welcomeThread.start();
    }

    private void showPermissionMessage() {
        new AlertDialog.Builder(this)
                .setTitle("Alert!")
                .setMessage("This app requires the permission to gather user location.")
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(SplashActivity.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                REQUEST_LOCATION);
                    }
                }).create().show();
    }

    private boolean checkPermission() {
        Log.e("delivrApp", "check_permission");
        int result = ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION);
//        int result1 = ContextCompat.checkSelfPermission(this, CAMERA);
//        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        Log.e("delivrApp", "request_permission");
        ActivityCompat.requestPermissions(SplashActivity.this, new String[]{ACCESS_FINE_LOCATION},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 901) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
                Log.e("delivrApp", "GPS_ERROR");
            }
            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
                Log.e("delivrApp", "NETWORK_ERROR");
            }

            if (!gps_enabled && !network_enabled) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loc_required_text), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                if (!checkPermission()) {
                    requestPermission();
                } else {
                    getLatLang();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void getLatLang() {
        gps = new GPSTracker(SplashActivity.this);
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            strLat = String.valueOf(latitude);
            strLon = String.valueOf(longitude);
            Log.e("delivrApp", "Splash_Latitude: " + strLat);
            Log.e("delivrApp", "Splash_Longitude: " + strLon);
            Toast.makeText(SplashActivity.this, "Current Latitude:" +strLat + "Longitude:" + strLon, Toast.LENGTH_LONG).show();
            startApp();
        } else {
            gps.showSettingsAlert();
        }
//        call_handler();
    }
}
