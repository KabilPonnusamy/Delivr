package com.delivr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.delivr.Common.PermissionManager;
import com.delivr.ui.login.LoginActivity;

public class SplashActivity extends Activity {

    final int welcomeScreenDisplay = 3000;

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
            case PermissionManager.ALL_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        && grantResults[2] == PackageManager.PERMISSION_GRANTED
                        && grantResults[3] == PackageManager.PERMISSION_GRANTED
                        && grantResults[4] == PackageManager.PERMISSION_GRANTED) {
                    launchApp();
                } else {
                    Toast.makeText(getApplicationContext(), "This App required some missing permissions." +
                                    "Please enable from app settings.",
                            Toast.LENGTH_SHORT).show();
                    finish();
//                    launchApp();
                }
        }
    }

    private void launchApp() {
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
}
