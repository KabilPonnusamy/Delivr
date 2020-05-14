package com.delivr.ui.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.delivr.R;
import com.delivr.backend.APIService;
import com.delivr.backend.DataEnvelope;
import com.delivr.backend.RetrofitClient;
import com.delivr.backend.postmodels.PostDoLogin;
import com.delivr.backend.responsemodels.ResponseUserLogin;
import com.delivr.ui.activity.Dashboard;
import com.delivr.utils.CheckNetwork;
import com.delivr.utils.Constants;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    TextView loginbtn, forgot_pwdbtn, signupbtn;
    EditText ext_username, ext_password;
    String str_useremail, str_user_password;
    ProgressDialog progressDialog;
    ImageView buttonTogglePasswordVisibility;

    private APIService apiService;
    // ArrayList<User_Information> user_informations;
    private Call<DataEnvelope<ResponseUserLogin>> callLogin;
    private boolean isShowingPassword;
    String user_memberid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initiateView();
        if (!CheckNetwork.isInternetAvailable(LoginActivity.this))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network), ext_username);
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

    }

    private void initiateView() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        loginbtn = findViewById(R.id.loginsubmit);
        forgot_pwdbtn = findViewById(R.id.forgot_pwd);
        //  signupbtn = findViewById(R.id.signup);
        ext_username = findViewById(R.id.edt_loginemail);
        ext_password = findViewById(R.id.edt_loginpwd);
        buttonTogglePasswordVisibility = findViewById(R.id.buttonTogglePasswordVisibility);
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

              //  insertData_to_Server(str_useremail, str_user_password);
                Intent loginintent = new Intent(LoginActivity.this, Dashboard.class);
                startActivity(loginintent);
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
        }
    }

    //    LOGINMETHOD
    public void insertData_to_Server(final String str_usernames, final String str_passwords) {
        progressDialog.show();
        callLogin = RetrofitClient.getInstance().getApiInterface().checkLogin(
                new PostDoLogin(
                        str_useremail,
                        str_user_password,
                        Constants.DEVICE_TYPE));

        callLogin.enqueue(new Callback<DataEnvelope<ResponseUserLogin>>() {
            @Override
            public void onResponse(Call<DataEnvelope<ResponseUserLogin>> call,
                                   final Response<DataEnvelope<ResponseUserLogin>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        user_memberid = response.body().getData().getMember_id();
                       /* Prefs.setMemberId(user_memberid);
                        MaterialDialog materialDialog = new MaterialDialog.Builder(LoginActivity.this)
                                .title("Success")
                                .content(response.body().getMessage())
                                .positiveText(R.string.btn_ok)
                                .onPositive(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        Intent intent = new Intent(LoginActivity.this, LoginOTPVerificationActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();*/

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
            public void onFailure(Call<DataEnvelope<ResponseUserLogin>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(LoginActivity.this);
            }
        });
    }




}
