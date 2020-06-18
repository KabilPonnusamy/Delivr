package sg.delivr.ui.login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import sg.delivr.Common.GPSTracker;
import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.APIService;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.models.CAdddress;
import sg.delivr.backend.postmodels.PostDoLogin;
import sg.delivr.backend.postmodels.PostGetProfile;
import sg.delivr.backend.postmodels.PostMerchantAuth;
import sg.delivr.backend.postmodels.PostMerchantOrderEntry;
import sg.delivr.backend.responsemodels.ResponseMerchantAuth;
import sg.delivr.backend.responsemodels.ResponseMerchantOrderEntry;
import sg.delivr.backend.responsemodels.ResponseUserLogin;
import sg.delivr.backend.responsemodels.ResponseUserProfile;
import sg.delivr.service.AlarmService;
import sg.delivr.ui.LocalDB.DbContract;
import sg.delivr.ui.LocalDB.DbHelper;
import sg.delivr.ui.activity.Activity_HelpSupport;
import sg.delivr.ui.activity.Dashboard_Merchant;
import sg.delivr.ui.activity.Dashboard_Rider;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.DataBaseHelper;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener,
        Intent_Constants {

    TextView loginbtn, forgot_pwdbtn, signupbtn, helpsupport;
    EditText ext_username, ext_password;
    String str_useremail, str_user_password;
    String strerror = "", strresult = "";
    ProgressDialog progressDialog;
    ImageView buttonTogglePasswordVisibility;
    public static String UserId, UserFullName, UserRole, UserStatusMsg;
    private APIService apiService;
    // ArrayList<User_Information> user_informations;
    private Call<ResponseUserLogin> callLogin;
    private Call<ResponseUserProfile> callGetProfile;
    private Call<ArrayList<ResponseMerchantOrderEntry>> callMerchantOrderEntry;
    private Call<ArrayList<ResponseMerchantAuth>> callMerchantAuth;
    private boolean isShowingPassword;
    public static Location LastGeoloc;
    public static PendingIntent pendingIntent;
    public static AlarmManager manager;
    public static PendingIntent pendingFBIntent;
    String user_memberid;
    public static double CLat, CLong;
    String strLat, strLon;
    public static String CSenderName, CContactNo, CCompanyName, CUnitno, CAddress, CPostalCode, CRoadNo, CRoadName,
    CBuilding, CZipcode, CEmailAddress, CSenderId, CZoneCode, CTimeFrom, CTimeTo,CTimeSensitive, CPriceType, CPrice,
    CConsignmentType, CServiceType, CExceptZones;
    //Location
    GPSTracker gpsTracker;
    public static Double lat, lon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initiateView();
        getLocations();
        if (!CheckNetwork.isInternetAvailable(LoginActivity.this))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network), ext_username);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

    }

    private void getLocations() {
        gpsTracker = new GPSTracker(this);
        lat = gpsTracker.getLatitude();
        lon = gpsTracker.getLongitude();
        strLat = String.valueOf(lat);
        strLon = String.valueOf(lon);
        Log.e("delivrApp" ,"Latitude: " + strLat);
        Log.e("delivrApp" ,"Longitude: " + strLon);
    }

    private void initiateView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginbtn = findViewById(R.id.loginsubmit);
        forgot_pwdbtn = findViewById(R.id.forgot_pwd);
        //  signupbtn = findViewById(R.id.signup);
        ext_username = findViewById(R.id.edt_loginemail);
        ext_password = findViewById(R.id.edt_loginpwd);
        buttonTogglePasswordVisibility = findViewById(R.id.buttonTogglePasswordVisibility);
        helpsupport = findViewById(R.id.helpsupport);
        helpsupport.setOnClickListener(this);
        loginbtn.setOnClickListener(this);
        forgot_pwdbtn.setOnClickListener(this);
//        signupbtn.setOnClickListener(this);
        buttonTogglePasswordVisibility.setOnClickListener(this);
    }

    public void AlertBox(final String head, final String meg, final EditText ext_field) {
        LayoutInflater in = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vv = in.inflate(R.layout.alertbox, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(vv);
        dialog.setCancelable(false);
        TextView content = (TextView) vv.findViewById(R.id.content);
        TextView header = (TextView) vv.findViewById(R.id.header);
        header.setText(head);
        TextView no = (TextView) vv.findViewById(R.id.no);
        TextView yes = (TextView) vv.findViewById(R.id.yes);
        LinearLayout cancel = (LinearLayout) vv.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        LinearLayout ok = (LinearLayout) vv.findViewById(R.id.ok);
        content.setText(meg);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (head.equalsIgnoreCase("Error")) {
                    finish();
                } else if (head.equalsIgnoreCase("Alert")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                    ext_field.requestFocus();
                }
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginsubmit:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(ext_username.getWindowToken(), 0);

                str_useremail = ext_username.getText().toString();
                str_user_password = ext_password.getText().toString();

                if (str_useremail == null || str_useremail.equals("")) {
                    AlertBox(getResources().getString(R.string.alert), getResources().getString(R.string.enterusername), ext_username);
                    return;
                }
                String str_emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,4})$";
                if (!str_useremail.trim().matches(str_emailPattern)) {
                    AlertBox(getResources().getString(R.string.alert), getResources().getString(R.string.entervalidemailid), ext_username);
                    return;
                }
                if (str_user_password == null || str_user_password.equals("")) {
                    AlertBox(getResources().getString(R.string.alert), getResources().getString(R.string.enterpassword), ext_password);
                    return;
                }

                insertData_to_Server(str_useremail, str_user_password);
               /* Intent loginintent = new Intent(LoginActivity.this, Dashboard_Rider.class);
                startActivity(loginintent);*/
                break;
            case R.id.forgot_pwd:
               /* Intent forgotpwdIntent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(forgotpwdIntent);*/
                break;
           /* case R.id.signup:
                Intent regIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(regIntent);
                break;*/
            case R.id.buttonTogglePasswordVisibility:
                if (isShowingPassword) {
                    buttonTogglePasswordVisibility.setImageDrawable(
                            ContextCompat.getDrawable(this, R.drawable.ic_password_off));
                    ext_password.setTransformationMethod(new PasswordTransformationMethod());
                    isShowingPassword = false;
                } else {
                    buttonTogglePasswordVisibility.setImageDrawable(
                            ContextCompat.getDrawable(this, R.drawable.ic_password_show));
                    ext_password.setTransformationMethod(null);
                    isShowingPassword = true;
                }
                break;
            case R.id.helpsupport:
                Intent help_support = new Intent(LoginActivity.this, Activity_HelpSupport.class);
                startActivity(help_support);
                break;
        }
    }
    //    LOGINMETHOD
    public void insertData_to_Server(final String str_usernames, final String str_passwords) {
        progressDialog.show();
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = str_usernames + str_passwords + Strapikey + Strapicode;
//        String sign = "1007" + "05e24a10-1e3b-41c8-8079-16efadce3c23" + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);

        Log.e("delivrApp", "Sign: " + StrSignature);
        Log.e("delivrApp", "StrapiKey: " + Strapikey);

        callLogin = RetrofitClient.getInstance().getApiInterface().checkLogin(
                new PostDoLogin(
                        str_useremail,
                        str_user_password,
                        Strapikey, StrSignature));

        callLogin.enqueue(new Callback<ResponseUserLogin>() {
            @Override
            public void onResponse(Call<ResponseUserLogin> call,
                                   final Response<ResponseUserLogin> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {
                        UserFullName = response.body().getFullname();

                        UserId = response.body().getUserId();
                        UserRole = response.body().getRole();
                        UserStatusMsg = response.body().getMessage();
                        if (UserStatusMsg.equalsIgnoreCase("Success")) {
                            Toast.makeText(LoginActivity.this, "User Role"+UserRole, Toast.LENGTH_LONG).show();
                            Prefs.setUserId(UserId);
                            Prefs.setUserRole(UserRole);
                            Prefs.setUserFullname(UserFullName);
                            if (UserRole.equals("Client")) {
                                getprofile();

                            }
                        } else {
                            strerror = UserStatusMsg;
                        }
                        if (strerror.toString().length() > 0) {
                            Toast.makeText(getApplicationContext(), strerror, Toast.LENGTH_LONG).show();
                            /*lblerrmessage = (TextView) findViewById(R.id.lblerrmessage);
                            lblerrmessage.setText(strerror);
                            lblerrmessage.setVisibility(View.VISIBLE);*/
                            Utils.showMessageDialog(LoginActivity.this,
                                    getString(R.string.dialog_title_sorry),
                                    response.body().getMessage());
                        } else {
                            if (UserRole.equals("Rider")) {

                                // Retrieve a PendingIntent that will perform a broadcast
                                Intent alarmIntent = new Intent(LoginActivity.this, AlarmService.class);
                                LoginActivity.pendingIntent = PendingIntent.getService(LoginActivity.this, 0, alarmIntent, 0);

                                LoginActivity.manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                int interval = 180000;
                                LoginActivity.manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, LoginActivity.pendingIntent);

                                checkUserData(UserId);


                            } else if (UserRole.equals("Client")) {

                                /*Intent alarmFBIntent = new Intent(MainActivity.this, FeedbackReceiver.class);
                                pendingFBIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmFBIntent, 0);

                                AlarmManager FBmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                int interval = 1800000;
                                FBmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingFBIntent);

                                Intent i = new Intent(getApplicationContext(), CustomerTile.class);
                                startActivity(i)*/;
                                StoredDatas.getInstance().setScreenValidation("Login");
                                Intent i = new Intent(getApplicationContext(), Dashboard_Rider.class);
                                startActivityForResult(i, LOGIN_to_RIDER_DASH);
                                finish();
                            } else if (UserRole.equals("Merchant")) {

                                /*Intent alarmFBIntent = new Intent(MainActivity.this, FeedbackReceiver.class);
                                pendingFBIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmFBIntent, 0);

                                AlarmManager FBmanager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                                int interval = 1800000;
                                FBmanager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingFBIntent);

                                Intent i = new Intent(getApplicationContext(), CustomerTile.class);
                                startActivity(i)*/;
                                progressDialog = new ProgressDialog(LoginActivity.this);
                                progressDialog.setMessage("Please wait..");
                                progressDialog.setCancelable(false);
                                getMerchantOrderEntry();
                                getMerchantAuth();
                            } else {
                                /*lblerrmessage = (TextView) findViewById(R.id.lblerrmessage);
                                lblerrmessage.setText(strerror);
                                lblerrmessage.setVisibility(View.VISIBLE);*/
                                Utils.showMessageDialog(LoginActivity.this,
                                        getString(R.string.dialog_title_sorry),
                                        response.body().getMessage());
                            }
                        }

                    } else {
                        Utils.showMessageDialog(LoginActivity.this,
                                getString(R.string.dialog_title_sorry),
                                response.body().getMessage());
                    }
                } else {
                    Utils.showMessageDialog(LoginActivity.this,
                            getString(R.string.dialog_title_sorry),
                            response.body().getMessage());
                }
            }


            @Override
            public void onFailure(Call<ResponseUserLogin> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(LoginActivity.this);
            }
        });
    }

    private void checkUserData(String userId) {
        String userimage = "";
        DbHelper dbHelper = new DbHelper(LoginActivity.this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = dbHelper.readProfile(database, userId);
        int count = 0;
        while(cursor.moveToNext()) {
            userimage = cursor.getString(cursor.getColumnIndex(DbContract.P_IMAGE_PATH));
            count++;
        }

        if(count > 0) {
            Prefs.setUserImage(userimage);
        } else {
            database = dbHelper.getWritableDatabase();
            dbHelper.saveProfile(database, userId, "", "");
        }

        /* Intent i = new Intent(getApplicationContext(), MenuTile.class);
        startActivity(i);*/
        StoredDatas.getInstance().setScreenValidation("Login");
        Intent i = new Intent(getApplicationContext(), Dashboard_Rider.class);
        startActivityForResult(i, LOGIN_to_RIDER_DASH);
        finish();

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

    private static String convertToHex(byte[] data) {
        StringBuilder sb = new StringBuilder(data.length * 2);

        java.util.Formatter fmt = new java.util.Formatter(sb);
        for (byte b : data) {
            fmt.format("%02x", b);
        }

        return sb.toString().toUpperCase();
    }

    public void getprofile() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = SHA1(sign);
        callGetProfile = RetrofitClient.getInstance().getApiInterface().getProfile(
                new PostGetProfile(
                        UserId ,
                        Strapikey, StrSignature));

        callGetProfile.enqueue(new Callback<ResponseUserProfile>() {
            @Override
            public void onResponse(Call<ResponseUserProfile> call,
                                   final Response<ResponseUserProfile> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getMessage().equalsIgnoreCase("success")) {

                        CSenderName = response.body().getFullname();
                        CContactNo = response.body().getMobileno();
                        CCompanyName = response.body().getCompanyName();
                        CUnitno = "-";
                        CPostalCode = response.body().getZipcode();

                        DataBaseHelper myDbHelper = new DataBaseHelper(LoginActivity.this);
                        try {
                            myDbHelper.createDataBase();
                        } catch (IOException ioe) {
                            throw new Error("Unable to create database");
                        }
                        try {
                            myDbHelper.openDataBase();
                        } catch (SQLException sqle) {
                            throw sqle;
                        }
                        CAdddress cadddress = myDbHelper.getAddress(CPostalCode);

                        CAddress = cadddress.toString();

                        CLat = cadddress.Lat();
                        CLong = cadddress.Long();


                    } else {
                        Utils.showMessageDialog(LoginActivity.this,
                                getString(R.string.dialog_title_sorry),
                                response.body().getMessage());
                    }
                } else {
                    Utils.showMessageDialog(LoginActivity.this,
                            getString(R.string.dialog_title_sorry),
                            response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseUserProfile> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(LoginActivity.this);
            }
        });
    }

    private void getMerchantOrderEntry() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign =  UserId + Strapikey + Strapicode;
        String StrSignature = SHAInterface.SHA1(sign);
        Log.e("delivrApp", "Signature: " + StrSignature);
        Log.e("delivrApp", "Sign: " + sign);
        Log.e("delivrApp", "StraipKey: " + Strapikey);
        Log.e("delivrApp", "StraipCode: " + Strapicode);
        progressDialog.show();
        callMerchantOrderEntry = RetrofitClient.getInstance().getApiInterface().getMerchantOrderEntry(
                new PostMerchantOrderEntry(UserId, Strapikey, StrSignature));
        callMerchantOrderEntry.enqueue(new Callback<ArrayList<ResponseMerchantOrderEntry>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseMerchantOrderEntry>> call,
                                   final Response<ArrayList<ResponseMerchantOrderEntry>> response) {
                if (response.body() != null) {
                    CSenderName = response.body().get(0).getSenderName();
                    CCompanyName = response.body().get(0).getCompanyName();
                    CRoadNo = response.body().get(0).getRoadNo();
                    CRoadName = response.body().get(0).getRoadName();
                    CBuilding = response.body().get(0).getBuilding();
                    CUnitno = response.body().get(0).getUnitNo();
                    CZipcode = response.body().get(0).getZipcode();


                    CContactNo = response.body().get(0).getContactNo();
                    CEmailAddress = response.body().get(0).getEmailAddress();
                    CSenderId = response.body().get(0).getSenderId();
                    CZoneCode = response.body().get(0).getZoneCode();
                    CAddress = "";
                    if (CRoadNo != null && !CRoadNo.isEmpty()) {
                        CAddress = CRoadNo;
                    }
                    if (CRoadName != null && !CRoadName.isEmpty()) {
                        CAddress = CAddress + " " + CRoadName;
                    }
                    if (CBuilding != null && !CBuilding.isEmpty()) {
                        if (!CAddress.isEmpty()) {
                            CAddress = CAddress + " " + CBuilding;
                        } else {
                            CAddress = CBuilding;
                        }
                    }
                    if (CZipcode != null && !CZipcode.isEmpty()) {
                        CAddress = CAddress + " " + CZipcode;
                    }
                    Log.e("delivrApp", "CAddress" + CAddress);
                    Prefs.setUserName(CSenderName);
                    Prefs.setUserMobileNo(CContactNo);
                    Prefs.setUserCompanyName(CCompanyName);
                    Prefs.setUserUnitNo(CUnitno);
                    Prefs.setUserPostalCode(CZipcode);
                    Prefs.setUserAddress(CAddress);
                } else {

                    Utils.showMessageDialog(LoginActivity.this,
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }

                    @Override
                    public void onFailure(Call<ArrayList<ResponseMerchantOrderEntry>> call, Throwable t) {
                        t.printStackTrace();
                        Prefs.setLoginVerified("failure");
                        Utils.showGenericErrorDialog(LoginActivity.this);
                    }
                });

    }
    private void getMerchantAuth() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign =  UserId + Strapikey + Strapicode;
        String StrSignature = SHAInterface.SHA1(sign);
        Log.e("delivrApp", "Signature: " + StrSignature);
        Log.e("delivrApp", "Sign: " + sign);
        Log.e("delivrApp", "StraipKey: " + Strapikey);
        Log.e("delivrApp", "StraipCode: " + Strapicode);


        callMerchantAuth = RetrofitClient.getInstance().getApiInterface().getMerchantAuth(
                new PostMerchantAuth(UserId, Strapikey, StrSignature));

        callMerchantAuth.enqueue(new Callback<ArrayList<ResponseMerchantAuth>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseMerchantAuth>> call,
                                   final Response<ArrayList<ResponseMerchantAuth>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    CTimeFrom = response.body().get(0).getTimeFrom();
                    CTimeTo = response.body().get(0).getTimeTo();
                    CTimeSensitive = response.body().get(0).getTimeSensitive();
                    CPriceType = response.body().get(0).getPriceType();
                    CPrice = response.body().get(0).getPrice();
                    CConsignmentType = response.body().get(0).getConsignmentType();
                    CServiceType = response.body().get(0).getServiceType();
                    CExceptZones = response.body().get(0).getExceptZones();

                    Prefs.setMerchAuthTimeFrom(CTimeFrom);
                    Prefs.setMerchAuthTimeTo(CTimeTo);
                    Prefs.setMerchAuthTimeSensitive(CTimeSensitive);
                    Prefs.setMerchAuthPriceType(CPriceType);
                    Prefs.setMerchAuthPrice(CPrice);
                    Prefs.setMerchAuthConsignmentType(CConsignmentType);
                    Prefs.setMerchAuthServiceType(CServiceType);
                    Prefs.setMerchAuthExceptZones(CExceptZones);
                    StoredDatas.getInstance().setScreenValidation("Login");
                    Intent i = new Intent(getApplicationContext(), Dashboard_Merchant.class);
                    startActivityForResult(i, LOGIN_to_MERCHANT_DASH);
                    finish();
                } else {
                    progressDialog.dismiss();
                    Utils.showMessageDialog(LoginActivity.this,
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseMerchantAuth>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Utils.showGenericErrorDialog(LoginActivity.this);
            }
        });

    }


}
