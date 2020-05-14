package com.delivr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.delivr.ui.login.LoginActivity;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final int welcomeScreenDisplay = 3000;
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
