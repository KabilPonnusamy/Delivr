package com.delivr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.delivr.Common.PermissionManager;
import com.delivr.ui.login.LoginActivity;

public class SplashActivity extends Activity {

    final int welcomeScreenDisplay = 3000;
    private static final int REQUEST_LOCATION = 10;

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
            )
            {
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
                    launchApp();
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
                    /*Toast.makeText(getApplicationContext(), "This App required some missing permissions." +
                                    "Please enable from app settings.",
                            Toast.LENGTH_SHORT).show();
                    finish();*/
                    launchApp();
                }
        }
    }

    private void launchApp() {

        checkForLocationPermission();


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
                //... Permission has already been granted, obtain the UUID
                startApp();
            }

        } else {
            //... No need to request permission, obtain the UUID
            startApp();
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
                    /**
                     * Called after splash times up. Do some action after splash
                     * times up. Here we moved to another main activity class
                     */
                    /*Log.i("SPLASH","Log USeerid"+new PreferenceHelper(SplashActivity.this).getUserId());
                    if (!TextUtils.isEmpty(new PreferenceHelper(SplashActivity.this).getUserId())) {
                        Log.e("SPLASH","User id avialble");
                        Intent myIntent = new Intent(SplashActivity.this,FacilityHomeActivity.class);
                        startActivityForResult(myIntent, 0);
                        finish();

                    }else{
                        Intent myIntent = new Intent(SplashActivity.this,MainActivity.class);
                        startActivityForResult(myIntent, 0);
                        finish();
                    }*/
                    Intent myIntent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(myIntent);

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
}
