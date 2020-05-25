package com.delivr.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.delivr.Common.StoredDatas;
import com.delivr.R;
import com.delivr.backend.RetrofitClient;
import com.delivr.backend.postmodels.PostAssignedActionAQ;
import com.delivr.backend.postmodels.PostAssignedBitAQ;
import com.delivr.backend.postmodels.PostDoActionAQ;
import com.delivr.backend.responsemodels.ResponseActionAQ;
import com.delivr.backend.responsemodels.ResponseAssignedActionAQ;
import com.delivr.backend.responsemodels.ResponseAssignedQueue;
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.ui.interfaces.Intent_Constants;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobsQueue_Details extends AppCompatActivity implements View.OnClickListener,
        Intent_Constants {

    ProgressDialog progressDialog;
    ImageView jbd_back;
    Button btn_accept, btn_reject;
    TextView wb_no, cpny_name, pickup_address, delivery_address, sender_name, contact_num,
            consig_value, service_type, type_value, job_type_value, spt_inst, status_value, pickupdtime_value;
    RelativeLayout cpny_layout;
    private Call<ResponseAssignedActionAQ> callassignedActionAQ;
   ResponseAssignedQueue riderQueues;
    int riderPos = 0;
    String chkstatus = "";
    String userId;
    String newstatus = "", strUserId = "";
    private Call<ResponseActionAQ> callRiderAQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjobsqueue_details);

        initView();
    }

    private void initView() {
        btn_accept = findViewById(R.id.btn_accept_det);
        btn_reject = findViewById(R.id.btn_reject_det);
        wb_no = findViewById(R.id.wb_no);
        cpny_name = findViewById(R.id.cpny_name);
        pickup_address = findViewById(R.id.pickup_address);
        delivery_address = findViewById(R.id.delivery_address);
        sender_name = findViewById(R.id.sender_name);
        contact_num = findViewById(R.id.contact_num);
        consig_value = findViewById(R.id.consig_value);
        service_type = findViewById(R.id.service_type);
        type_value = findViewById(R.id.type_value);
        job_type_value = findViewById(R.id.job_type_value);
        spt_inst = findViewById(R.id.spt_inst);
        status_value = findViewById(R.id.status_value);
        pickupdtime_value = findViewById(R.id.pickupdtime_value);
        cpny_layout = findViewById(R.id.cpny_layout);

        riderQueues = StoredDatas.getInstance().getAssignedjobQueues();
        riderPos = StoredDatas.getInstance().getrQuePos();

        jbd_back = findViewById(R.id.jbd_back);
        strUserId = Prefs.getUserId();
        Log.e("delivrApp", "UserId: " + strUserId);
        setValues();
        setListeners();

    }

    private void setValues() {
        sender_name.setText(riderQueues.getPickupSender());
        contact_num.setText(riderQueues.getPickupContactno());
        wb_no.setText(riderQueues.getWBno());
        if (riderQueues.getSenderCompName() != null) {
            if (riderQueues.getSenderCompName().equalsIgnoreCase("")) {
                cpny_layout.setVisibility(View.GONE);
            } else {
                cpny_name.setText(riderQueues.getSenderCompName());
            }
        }
        String pickupaddr = "";
        if (riderQueues.getPickupRoadno() != null && !riderQueues.getPickupRoadno().isEmpty())  {
            pickupaddr = riderQueues.getPickupRoadno();
        }
        if (riderQueues.getPickupRoadName() != null && !riderQueues.getPickupRoadName().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.getPickupRoadName();
        }
        if (riderQueues.getPickupBuilding() != null && !riderQueues.getPickupBuilding().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.getPickupBuilding();
        }
        if (riderQueues.getPickupBuildingType() != null &&  !riderQueues.getPickupBuildingType().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.getPickupBuildingType();
        }
        if (riderQueues.getPickupZipcode() != null && !riderQueues.getPickupZipcode().isEmpty())  {
            if (!pickupaddr.isEmpty()) {
                pickupaddr = pickupaddr + "," + riderQueues.getPickupZipcode();
            } else {
                pickupaddr = riderQueues.getPickupZipcode();
            }
        }

        pickup_address.setText(pickupaddr);
        String deliveryaddr = "";
        if (riderQueues.getDlvyRoadno() != null && !riderQueues.getDlvyRoadno().isEmpty())  {
            deliveryaddr = riderQueues.getDlvyRoadno();
        }
        if (riderQueues.getDlvyRoadName() != null && !riderQueues.getDlvyRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.getDlvyRoadName();
        }
        if (riderQueues.getDlvyBuilding() != null && !riderQueues.getDlvyBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.getDlvyBuilding();
        }
        if (riderQueues.getDlvyBuildingType() != null && !riderQueues.getDlvyBuildingType().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.getDlvyBuildingType();
        }
        if (riderQueues.getDlvyZipcode() != null && !riderQueues.getDlvyZipcode().isEmpty())  {
            if (!deliveryaddr.isEmpty()) {
                deliveryaddr = deliveryaddr + "," + riderQueues.getDlvyZipcode();
            } else {
                deliveryaddr = riderQueues.getDlvyZipcode();
            }
        }
        delivery_address.setText(deliveryaddr);

        consig_value.setText(riderQueues.getConsignmentType());
        service_type.setText(riderQueues.getServiceType());

        job_type_value.setText(riderQueues.getJobType());
        pickupdtime_value.setText(riderQueues.getPickupdatetime());
        spt_inst.setText(riderQueues.getSpInst());



    }

    private void setListeners() {
        jbd_back.setOnClickListener(this);
        btn_accept.setOnClickListener(this);
        btn_reject.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jbd_back:
                onBackPressed();
                break;
            case R.id.btn_accept_det:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait..");
                progressDialog.setCancelable(false);
                Log.e("DelivrApp", "Selected Job Position after accept:"+ riderPos);

                if (riderQueues.getPassType() == null || riderQueues.getPassType().equals("")) {
                    // check times senstive and use order bidding
                    if (riderQueues.getOrderType().equals("TS")) {

                        SendRidersAction(strUserId,riderQueues.getWBno(),"RidAcc", "TS");
                        // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("WBno"), "RidAcc", "TS"});
                    } else {
                        SendRidersAction(strUserId,riderQueues.getOrderWBUid(),"RidAcc", "normal");
                        // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "RidAcc", "normal"});
                    }
                } else {
                    SendRidersAction(strUserId,riderQueues.getOrderWBUid(),"PassAcc", "normal");
                    // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "PassAcc", "normal"});
                }
                break;
            case R.id.btn_reject_det:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait..");
                progressDialog.setCancelable(false);
                Log.e("DelivrApp", "Selected Job Position after reject:" + riderPos);
                if (riderQueues.getPassType() == null || riderQueues.getPassType().equals("")) {
                    if (riderQueues.getOrderType().equals("TS")) {
                        SendRidersAction(strUserId,riderQueues.getWBno(),"RidRej", "TS");
                        // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("WBno"), "RidRej", "TS"});
                    } else {
                        SendRidersAction(strUserId,riderQueues.getOrderWBUid(),"RidRej", "normal");
                        //   ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "RidRej", "normal"});
                    }
                } else {
                    SendRidersAction(strUserId,riderQueues.getOrderWBUid(),"PassRej", "normal");
                    // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "PassRej", "normal"});
                }
                break;
        }
    }

    private void SendRidersAction(String  strUserid, String strOrderWBUid_no, String strAction, String strOrderType) {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = strUserid + strOrderWBUid_no + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);
        Log.e("delivrApp", "Signature: " + StrSignature);
        Log.e("delivrApp", "Sign: " + sign);
        Log.e("delivrApp", "StraipKey: " + Strapikey);
        Log.e("delivrApp", "StraipCode: " + Strapicode);
        if (strOrderType.equals("TS")) {
            callassignedActionAQ = RetrofitClient.getInstance().getApiInterface().sendactionBidAQ(
                    new PostAssignedBitAQ(strUserid,strOrderWBUid_no,strAction ,Strapikey, StrSignature));
            // Strapimethod = "RiderQueue/BidAQ";
        } else {
            callassignedActionAQ = RetrofitClient.getInstance().getApiInterface().sendassignedActionAQ(
                    new PostAssignedActionAQ(strUserid,strOrderWBUid_no,strAction ,Strapikey, StrSignature));
            //  Strapimethod = "RiderQueue/ActionAQ";
        }

      /*  if (strOrderType.equals("TS")) {
            nameValuePairs.add(new BasicNameValuePair("OrderNo", strOrderWBUid_no));
        } else {
            nameValuePairs.add(new BasicNameValuePair("OrderWBUid", strOrderWBUid_no));
        }*/
        progressDialog.show();


        callassignedActionAQ.enqueue(new Callback<ResponseAssignedActionAQ>() {
            @Override
            public void onResponse(Call<ResponseAssignedActionAQ> call,
                                   final Response<ResponseAssignedActionAQ> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if(response.body().getStatus().equalsIgnoreCase("Success")) {
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("AQPosition", "" + riderPos);
                        setResult(MYJOBSQUEUEAQ_success, resultIntent);
                        finish();
                    }
                   /* riderQueues = response.body();
                    setAdapter(riderQueues);*/
                } else {
                    Utils.showMessageDialog(MyJobsQueue_Details.this,
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ResponseAssignedActionAQ> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(MyJobsQueue_Details.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  setResult(MYJOB_DETAILS_back);
        finish();
    }
}
