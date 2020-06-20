package sg.delivr.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;



import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;

import sg.delivr.backend.postmodels.PostWalletPaymentIntent;
import sg.delivr.backend.postmodels.PostWalletPaymentStatus;
import sg.delivr.backend.responsemodels.ResponsePaymentIntentClientSecret;

import sg.delivr.backend.responsemodels.ResponseWalletPaymentStatus;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;

import static sg.delivr.ui.interfaces.Intent_Constants.MYWALLETCHECKOUT_success;

public class CheckOutActivity extends AppCompatActivity {
    private static final String BACKEND_URL = "https://delivr.sg/API/";

   // private OkHttpClient httpClient = new OkHttpClient();
    private String paymentIntentClientSecret;
    private Stripe stripe;
    ProgressDialog progressDialog;
    static ProgressDialog progressDialog_payment;
    public String stripePublishableKey;
    public String userId;
    public String entered_amount;
    public String Description, creditAmount;
    public static String Balancetransactionid, amount_response, payment_status, billing_address;
    public static String RawJObject;
    int stripe_chargeamount;
    LinearLayout submit_checkoutpay;
    CardInputWidget cardInputWidget;
    ImageView jbcheckout_back;
    private Call<ResponsePaymentIntentClientSecret> CallpaymentIntentClientSecret;
    private Call<ResponseWalletPaymentStatus> CallpaymentStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        initView();
        startCheckout();
    }

    private void initView() {
        userId = Prefs.getUserId();
        submit_checkoutpay = findViewById(R.id.submit_checkoutpay);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        jbcheckout_back = findViewById(R.id.jbcheckout_back);
        progressDialog_payment = new ProgressDialog(CheckOutActivity.this);
        progressDialog_payment.setMessage("Processing Payment, Please wait..");
        progressDialog_payment.setCancelable(false);
        jbcheckout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("PaymentStatus", "backclick");
                setResult(MYWALLETCHECKOUT_success, resultIntent);
                finish();
            }
        });
        submit_checkoutpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog_payment.show();
                disablepayButton();
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    stripe.confirmPayment(CheckOutActivity.this, confirmParams);
                }
            }
        });
    }

    private void enablepayButton() {
        submit_checkoutpay.setEnabled(true);
        submit_checkoutpay.setBackgroundResource(R.drawable.box_layout);
    }

    private void disablepayButton() {
        submit_checkoutpay.setEnabled(false);
        submit_checkoutpay.setBackgroundResource(R.drawable.empty_border_solid);
    }

    private void startCheckout() {
        // Create a PaymentIntent by calling the sample server's /create-payment-intent endpoint.
        progressDialog = new ProgressDialog(CheckOutActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);
        getPaymentIntentSecret(userId);
       /* MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        String json = "{"
                + "\"currency\":\"sgd\","
                + "\"amount\":\"54\","
                *//*+ "\"items\":["
                + "{\"id\":\"photo_subscription\"}"
                + "]"*//*
                + "}";
        RequestBody body = RequestBody.create(mediaType, json);
        Request request = new Request.Builder()
                .url(BACKEND_URL + "Wallet/Charges")
                .post(body)
                .build();
        httpClient.newCall(request)
                .enqueue(new PayCallback(this));
*/
        // Hook up the pay button to the card widget and stripe instance
        //Button payButton = findViewById(R.id.payButton);

        /*submit_checkoutpay.setOnClickListener((View view) -> {


        });*/
    }

    private void getPaymentIntentSecret(String UserId) {
        Log.e("delivrApp", "Member_Id: " + Prefs.getUserId());
        progressDialog.show();
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);
        entered_amount = StoredDatas.getInstance().getCheckOutAmount();
        stripe_chargeamount = Integer.parseInt(entered_amount) *100;
        Log.e("delivrApp", "Stripe Charge Amount" + stripe_chargeamount);
        Description = "Topup done from android app for merchant" + userId + "and transaction amount is" + entered_amount;
        CallpaymentIntentClientSecret = RetrofitClient.getInstance().getApiInterface().getPaymentIntentClientSecret(
                new PostWalletPaymentIntent(userId,Description,Strapikey,StrSignature,stripe_chargeamount));

        CallpaymentIntentClientSecret.enqueue(new Callback<ResponsePaymentIntentClientSecret>() {
            @Override
            public void onResponse(Call<ResponsePaymentIntentClientSecret> call,
                                   Response<ResponsePaymentIntentClientSecret> response) {
                progressDialog.dismiss();

                if (response.body() != null) {
                    if (response.body().getObject().equalsIgnoreCase("payment_intent")) {
                        stripePublishableKey = "pk_test_UUneYjetyYPeNMjd664l4wl300TQYrMirc";
                        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
                        paymentIntentClientSecret = response.body().getClient_secret();
                        stripe = new Stripe(
                                getApplicationContext(),
                                Objects.requireNonNull(stripePublishableKey)
                        );


                    } else {
                        progressDialog.dismiss();
                        Utils.showGenericErrorDialog(CheckOutActivity.this);
                    }
                } else {
                    progressDialog.dismiss();
                    Utils.showGenericErrorDialog(CheckOutActivity.this);
                }
            }
            @Override
            public void onFailure(Call<ResponsePaymentIntentClientSecret> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Utils.showGenericErrorDialog(CheckOutActivity.this);
            }
        });
    }

    private void displayAlert(@NonNull String title,
                              @Nullable String message,
                              boolean restartDemo) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(false)
                .setMessage(message);
        if (restartDemo) {
            builder.setPositiveButton("Ok",
                    (DialogInterface dialog, int index) -> {
                        CardInputWidget cardInputWidget = findViewById(R.id.cardInputWidget);
                        cardInputWidget.clear();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("PaymentStatus", "success");
                        setResult(MYWALLETCHECKOUT_success, resultIntent);
                        finish();
                    });
        } else {
            builder.setPositiveButton("Ok", null);
        }
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Handle the result of stripe.confirmPayment
        stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
    }

   /* private void onPaymentSuccess(@NonNull final Response response) throws IOException {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> responseMap = gson.fromJson(
                Objects.requireNonNull(response.body()).string(),
                type
        );

        // The response from the server includes the Stripe publishable key and
        // PaymentIntent details.
        // For added security, our sample app gets the publishable key from the server
        //String stripePublishableKey = responseMap.get("publishableKey");
        String stripePublishableKey = "pk_test_UUneYjetyYPeNMjd664l4wl300TQYrMirc";
        //paymentIntentClientSecret = responseMap.get("clientSecret");
        paymentIntentClientSecret = "sk_test_Shv9eBMJcmkjGIM3vCG8Xqvv00hGxKt3ty";

        // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
        stripe = new Stripe(
                getApplicationContext(),
                Objects.requireNonNull(stripePublishableKey)
        );
    }*/

    /*private static final class PayCallback implements Callback {
        @NonNull private final WeakReference<CheckOutActivity> activityRef;

        PayCallback(@NonNull CheckOutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onFailure(@NonNull Call call, @NonNull IOException e) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            activity.runOnUiThread(() ->
                    Toast.makeText(
                            activity, "Error: " + e.toString(), Toast.LENGTH_LONG
                    ).show()
            );
        }

        @Override
        public void onResponse(@NonNull Call call, @NonNull final Response response)
                throws IOException {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            if (!response.isSuccessful()) {
                activity.runOnUiThread(() ->
                        Toast.makeText(
                                activity, "Error: " + response.toString(), Toast.LENGTH_LONG
                        ).show()
                );
            } else {
                activity.onPaymentSuccess(response);
            }
        }
    }*/

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull private final WeakReference<CheckOutActivity> activityRef;

        PaymentResultCallback(@NonNull CheckOutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }

            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Gson gson2 = new GsonBuilder().create();
               /* activity.displayAlert(
                        "Payment completed",
                        gson.toJson(paymentIntent),
                        true
                );*/
                activity.getPaymentResponse(gson.toJson(paymentIntent),gson2.toJson(paymentIntent) );

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                progressDialog_payment.dismiss();
                activity.enablepayButton();
                activity.displayAlert(
                        "Payment failed",
                        Objects.requireNonNull(paymentIntent.getLastPaymentError()).message,
                        false
                );
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final CheckOutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            progressDialog_payment.dismiss();
            activity.enablepayButton();
            // Payment request failed – allow retrying using the same payment method
            activity.displayAlert("Error", e.toString(), false);
        }
    }

    private void getPaymentResponse(String rawjdata, String rjdata) {
        try {
            JSONObject jsonObject = new JSONObject(rawjdata);
            RawJObject = rjdata;
            Balancetransactionid = jsonObject.getString("mId");
            amount_response = StoredDatas.getInstance().getCheckOutAmount();
            payment_status = jsonObject.getString("mStatus");
            sendPaymentStatus(userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendPaymentStatus(String UserId) {
        Log.e("delivrApp", "Member_Id: " + Prefs.getUserId());
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);
        entered_amount = StoredDatas.getInstance().getCheckOutAmount();
        Description = "Topup done from android app for merchant" + userId + "and transaction amount is" + entered_amount;
        payment_status = "success";
        CallpaymentStatus = RetrofitClient.getInstance().getApiInterface().sendPaymentStatus(
                new PostWalletPaymentStatus(userId,payment_status,RawJObject, Balancetransactionid, Integer.parseInt(entered_amount),
                        "",Description,Strapikey,StrSignature));

        CallpaymentStatus.enqueue(new Callback<ResponseWalletPaymentStatus>() {
            @Override
            public void onResponse(Call<ResponseWalletPaymentStatus> call,
                                   Response<ResponseWalletPaymentStatus> response) {
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {

                        progressDialog_payment.dismiss();
                        enablepayButton();
                        creditAmount = response.body().getCreditAmt();
                        Prefs.setWalletTotalCreditAmount(creditAmount);
                        displayAlert(
                                "Payment completed",
                                response.body().getMessage(),
                                true
                        );
                    } else {
                        progressDialog_payment.dismiss();
                        Utils.showGenericErrorDialog(CheckOutActivity.this);
                    }
                } else {
                    progressDialog_payment.dismiss();
                    Utils.showGenericErrorDialog(CheckOutActivity.this);
                }
            }
            @Override
            public void onFailure(Call<ResponseWalletPaymentStatus> call, Throwable t) {
                progressDialog_payment.dismiss();
                t.printStackTrace();
                Utils.showGenericErrorDialog(CheckOutActivity.this);
            }
        });
    }


}
