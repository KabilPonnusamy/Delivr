package com.delivr.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import com.delivr.R;
import com.delivr.backend.BaseResponse;
import com.delivr.backend.RetrofitClient;
import com.delivr.backend.postmodels.PostGetProfile;
import com.delivr.backend.postmodels.PostUpdateLatLong;
import com.delivr.backend.responsemodels.ResponseUserLogin;
import com.delivr.backend.responsemodels.ResponseUserProfile;
import com.delivr.ui.login.LoginActivity;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;



import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by prem on 6/2/2015.
 */
public class AlarmService extends Service {
    private Call<BaseResponse> callUpdatelatlong;
    private static String convertToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);

        java.util.Formatter fmt = new java.util.Formatter(sb);
        for (byte b : data) {
            fmt.format("%02x", b);
        }

        return sb.toString().toUpperCase();
    }

    public static String SHA1(String text) {
        byte[] sha1hash = new byte[40];
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
        } catch (Exception e) {
        }
        return convertToHex(sha1hash);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        //Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
     //   Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG).show();
        Log.e("DelivrApp", "Last Geo Location:" +  LoginActivity.LastGeoloc);
        if (Prefs.getUserId().length() > 0 && LoginActivity.LastGeoloc != null) {
            String strRiderid, strLat, strLong;
            strRiderid = Prefs.getUserId();
            strLat = Double.toString(LoginActivity.LastGeoloc.getLatitude());
            strLong = Double.toString(LoginActivity.LastGeoloc.getLongitude());
            //Toast.makeText(this, "I'm running"+strLat + strLong, Toast.LENGTH_SHORT).show();
            if (strLat != "0.0" && strLong != "0.0") {
                UpdateLatLong(strRiderid, strLat, strLong);
            }

        }
    }

    private void UpdateLatLong(String strRiderid, String strLat, String strLong) {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = strRiderid + Strapikey + Strapicode;
        String StrSignature = SHA1(sign);
        callUpdatelatlong = RetrofitClient.getInstance().getApiInterface().updateLatLong(
                new PostUpdateLatLong(
                        strRiderid ,
                        strLat,strLong,Strapikey, StrSignature));

        callUpdatelatlong.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call,
                                   Response<BaseResponse> response) {


                if (response.body() != null) {
                    /*try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(2000);
                        r.play();
                        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock lock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                        lock.acquire();
                        Toast.makeText(AlarmService.this, "You have " + strresult + " jobs assigned.", Toast.LENGTH_LONG).show();
                        lock.release();
                    } catch (Exception e) {
                        //e.printStackTrace();
                        Toast.makeText(AlarmService.this, "You have " + strresult + " jobs assigned.", Toast.LENGTH_LONG).show();
                    }*/
                } else {
                    Log.e("DelivrApp", "");
                   /* Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            response.body().getMessage());*/
                }
            }



            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

                t.printStackTrace();


            }
        });
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
        return super.onUnbind(intent);
    }



    /*private class SchAsyncCaller extends AsyncTask<String, Void, Void> {
        String strerror = "", strresult = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... params) {
            String strRiderid, strLat, strLong;
            strRiderid = params[0].toString();
            strLat = params[1].toString();
            strLong = params[2].toString();

            HttpClient httpclient = new DefaultHttpClient();
            String Strapiurl = getString(R.string.apiurl);
            String Strapimethod = "Schedule/UpdateLatLong";
            HttpPost httppost = new HttpPost(Strapiurl + "/" + Strapimethod);

            try {

                String Strapikey = getString(R.string.apikey);
                ;
                String Strapicode = getString(R.string.apicode);
                String sign = strRiderid + Strapikey + Strapicode;
                String StrSignature = SHA1(sign);

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("Riderid", strRiderid));
                nameValuePairs.add(new BasicNameValuePair("Latitude", strLat));
                nameValuePairs.add(new BasicNameValuePair("Longitude", strLong));
                nameValuePairs.add(new BasicNameValuePair("APIKEY", Strapikey));
                nameValuePairs.add(new BasicNameValuePair("Signature", StrSignature));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = httpclient.execute(httppost);

                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    strresult = EntityUtils.toString(response.getEntity());
                } else {
                    strerror = "Error while update location";
                }

            } catch (ClientProtocolException e) {
                //str = e.getMessage();
                strerror = "Unable to process, Please try later";
            } catch (IOException e) {
                //str = e.getMessage();
                strerror = "Unable to process, Please try later";
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (strerror.toString().length() > 0) {
                //Toast.makeText(getApplicationContext(), strerror, Toast.LENGTH_LONG).show();
                //
            } else {
                if (strresult != "" && Integer.parseInt(strresult) > 0) {
                    try {
                        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        v.vibrate(2000);
                        r.play();
                        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock lock = ((PowerManager) getSystemService(Context.POWER_SERVICE)).newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP, "TAG");
                        lock.acquire();
                        Toast.makeText(AlarmService.this, "You have " + strresult + " jobs assigned.", Toast.LENGTH_LONG).show();
                        lock.release();
                    } catch (Exception e) {
                        //e.printStackTrace();
                        Toast.makeText(AlarmService.this, "You have " + strresult + " jobs assigned.", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }

    }*/
}
