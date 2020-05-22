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
import com.delivr.backend.postmodels.PostDoActionAQ;
import com.delivr.backend.responsemodels.ResponseActionAQ;
import com.delivr.backend.responsemodels.ResponseCompletedJobs;
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.ui.interfaces.Intent_Constants;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCompletedJobs_Details extends AppCompatActivity implements View.OnClickListener,
        Intent_Constants {

    ProgressDialog progressDialog;
    ImageView jbd_back;
//    Button btn_save, btn_pass;
    TextView wb_no, cpny_name, pickup_address, delivery_address,
        consig_value, service_type, type_value, job_type_value,pickupdtime_value , spt_inst, status_value;
    // status_value, spt_inst, sender_name, contact_num
//    RelativeLayout cpny_layout;

    ArrayList<ResponseCompletedJobs> mycompletedJobs;
    int riderPos = 0;
    String chkstatus = "";
    String newstatus = "", strUserId = "";
    private Call<ResponseActionAQ> callRiderAQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycompletedjobs_details);

        initView();
    }

    private void initView() {
//        btn_save = findViewById(R.id.btn_save);
//        btn_pass = findViewById(R.id.btn_pass);
        wb_no = findViewById(R.id.wb_no);
        cpny_name = findViewById(R.id.cpny_name);
        pickup_address = findViewById(R.id.pickup_address);
        delivery_address = findViewById(R.id.delivery_address);
//        sender_name = findViewById(R.id.sender_name);
//        contact_num = findViewById(R.id.contact_num);
        consig_value = findViewById(R.id.consig_value);
        service_type = findViewById(R.id.service_type);
        type_value = findViewById(R.id.type_value);
        job_type_value = findViewById(R.id.job_type_value);
        pickupdtime_value = findViewById(R.id.pickupdtime_value);
        spt_inst = findViewById(R.id.spt_inst);
        status_value = findViewById(R.id.status_value);

//        cpny_layout = findViewById(R.id.cpny_layout);
        mycompletedJobs = new ArrayList<ResponseCompletedJobs>();
        mycompletedJobs = StoredDatas.getInstance().getCompletedJobs();
        riderPos = StoredDatas.getInstance().getrQuePos();

        jbd_back = findViewById(R.id.jbd_back);
        strUserId = Prefs.getUserId();
        Log.e("delivrApp", "UserId: " + strUserId);
        setValues();
        setListeners();

    }

    private void setValues() {
//        sender_name.setText(mycompletedJobs.get(riderPos).getPickupSender());
//        contact_num.setText(mycompletedJobs.get(riderPos).getPickupContactno());
        wb_no.setText(mycompletedJobs.get(riderPos).getWBno());
        /*if (mycompletedJobs.get(riderPos).getSenderCompName().equalsIgnoreCase("")) {
            cpny_layout.setVisibility(View.GONE);
        } else {
            cpny_name.setText(mycompletedJobs.get(riderPos).getSenderCompName());
        }*/

        String deliveryaddr = "";
        if (mycompletedJobs.get(riderPos).getDlvyRoadno() != null && !mycompletedJobs.get(riderPos).getDlvyRoadno().isEmpty())  {
            deliveryaddr = mycompletedJobs.get(riderPos).getDlvyRoadno();
        }
        if (mycompletedJobs.get(riderPos).getDlvyRoadName() != null && !mycompletedJobs.get(riderPos).getDlvyRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + mycompletedJobs.get(riderPos).getDlvyRoadName();
        }
        if (mycompletedJobs.get(riderPos).getDlvyBuilding() != null && !mycompletedJobs.get(riderPos).getDlvyBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + mycompletedJobs.get(riderPos).getDlvyBuilding();
        }
        if (mycompletedJobs.get(riderPos).getDlvyBuildingType() != null && !mycompletedJobs.get(riderPos).getDlvyBuildingType().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + mycompletedJobs.get(riderPos).getDlvyBuildingType();
        }
        if (mycompletedJobs.get(riderPos).getDlvyZipcode() != null && !mycompletedJobs.get(riderPos).getDlvyZipcode().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + mycompletedJobs.get(riderPos).getDlvyZipcode();
        }
        delivery_address.setText(deliveryaddr);
        String pickupaddr = "";
        if (mycompletedJobs.get(riderPos).getPickupRoadno() != null && !mycompletedJobs.get(riderPos).getPickupRoadno().isEmpty())  {
            pickupaddr = mycompletedJobs.get(riderPos).getPickupRoadno();
        }
        if (mycompletedJobs.get(riderPos).getPickupRoadName() != null && !mycompletedJobs.get(riderPos).getPickupRoadName().isEmpty())  {
            pickupaddr = pickupaddr + "," + mycompletedJobs.get(riderPos).getPickupRoadName();
        }
        if (mycompletedJobs.get(riderPos).getPickupBuilding() != null && !mycompletedJobs.get(riderPos).getPickupBuilding().isEmpty())  {
            pickupaddr = pickupaddr + "," + mycompletedJobs.get(riderPos).getPickupBuilding();
        }
        if (mycompletedJobs.get(riderPos).getPickupBuildingType() != null && !mycompletedJobs.get(riderPos).getPickupBuildingType().isEmpty())  {
            pickupaddr = pickupaddr + "," + mycompletedJobs.get(riderPos).getPickupBuildingType();
        }
        if (mycompletedJobs.get(riderPos).getPickupZipcode() != null && !mycompletedJobs.get(riderPos).getPickupZipcode().isEmpty())  {
            pickupaddr = pickupaddr + "," + mycompletedJobs.get(riderPos).getPickupZipcode();
        }
        pickup_address.setText(pickupaddr);

        consig_value.setText(mycompletedJobs.get(riderPos).getConsignmentType());
        service_type.setText(mycompletedJobs.get(riderPos).getServiceType());
        if (mycompletedJobs.get(riderPos).getDelivery_CO().equalsIgnoreCase("False")) {
            type_value.setText("Delivery Only");
        } else {
            type_value.setText("Delivery and Collect Order");
        }
        job_type_value.setText(mycompletedJobs.get(riderPos).getJobType());
        pickupdtime_value.setText(mycompletedJobs.get(riderPos).getPickupdatetime());

        spt_inst.setText(mycompletedJobs.get(riderPos).getSpInst());
        status_value.setText(mycompletedJobs.get(riderPos).getStatus());




    }

    private void setListeners() {
        jbd_back.setOnClickListener(this);
//        btn_save.setOnClickListener(this);
//        btn_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jbd_back:
                onBackPressed();
                break;
            /*case R.id.btn_save:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait..");
                progressDialog.setCancelable(false);
                saveActionQ();
                break;*/
            case R.id.btn_pass:
                break;
        }
    }

/*
    private void saveActionQ() {

        progressDialog.show();
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = strUserId + riderQueues.get(riderPos).getOrderWBUid() + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);

        Log.e("delivrApp", "OrderWBUid: " + riderQueues.get(riderPos).getOrderWBUid());
        Log.e("delivrApp", "Sign: " + StrSignature);
        Log.e("delivrApp", "StrapiKey: " + Strapikey);
        Log.e("delivrApp", "NewStatus: " + newstatus);
        Log.e("delivrApp", "UserId: " + strUserId);

        callRiderAQ = RetrofitClient.getInstance().getApiInterface().saveActionAQ(
                new PostDoActionAQ(
                        strUserId, riderQueues.get(riderPos).getOrderWBUid(), newstatus,
                        "", "", "", Strapikey, StrSignature));

        callRiderAQ.enqueue(new Callback<ResponseActionAQ>() {
            @Override
            public void onResponse(Call<ResponseActionAQ> call,
                                   final Response<ResponseActionAQ> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("success")) {
                        // If The status is Delivered remove from My Job.
                        if (newstatus.equals("Del")) {
                            //RiderQueue.RQResult = RemoveJSONArray(RiderQueue.RQResult, MainActivity.arrpos);
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("AQPosition", "" + riderPos);
                            resultIntent.putExtra("AQStatus", newstatus);
                            setResult(MYJOBSAQ_success, resultIntent);
                            finish();
                        } else {
                            riderQueues.get(riderPos).setStatus(newstatus);
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("AQPosition", "" + riderPos);
                            resultIntent.putExtra("AQStatus", newstatus);
                            setResult(MYJOBSAQ_success, resultIntent);
                            finish();
                         //   RiderQueue.RQResult = UpdateJSONArray(RiderQueue.RQResult, MainActivity.arrpos, "Status", newstatus);
                        }

                    } else {
                        Utils.showMessageDialog(MyCompletedJobs_Details.this,
                                getString(R.string.dialog_title_sorry),
                                response.body().getStatus());
                    }
                } else {
                    Utils.showMessageDialog(MyCompletedJobs_Details.this,
                            getString(R.string.dialog_title_sorry),
                            response.body().getStatus());
                }
            }


            @Override
            public void onFailure(Call<ResponseActionAQ> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(MyCompletedJobs_Details.this);
            }
        });

    }
*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(MYJOB_DETAILS_back);
        finish();
    }
}
