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
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.ui.interfaces.Intent_Constants;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyJobs_Details extends AppCompatActivity implements View.OnClickListener,
        Intent_Constants {

    ProgressDialog progressDialog;
    ImageView jbd_back;
    Button btn_save, btn_pass;
    TextView wb_no, cpny_name, pickup_address, delivery_address, sender_name, contact_num,
            consig_value, service_type, type_value, job_type_value, spt_inst, status_value, pickupdtime_value;
    RelativeLayout cpny_layout;

    ArrayList<ResponseRiderQueue> riderQueues;
    int riderPos = 0;
    String chkstatus = "";
    String newstatus = "", strUserId = "";
    private Call<ResponseActionAQ> callRiderAQ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myjobs_details);

        initView();
    }

    private void initView() {
        btn_save = findViewById(R.id.btn_save);
        btn_pass = findViewById(R.id.btn_pass);
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
        pickupdtime_value = findViewById(R.id.pickupdtime_value);
        spt_inst = findViewById(R.id.spt_inst);
        status_value = findViewById(R.id.status_value);

        cpny_layout = findViewById(R.id.cpny_layout);
        riderQueues = new ArrayList<ResponseRiderQueue>();
        riderQueues = StoredDatas.getInstance().getRiderQueues();
        riderPos = StoredDatas.getInstance().getrQuePos();

        jbd_back = findViewById(R.id.jbd_back);
        strUserId = Prefs.getUserId();
        Log.e("delivrApp", "UserId: " + strUserId);
        setValues();
        setListeners();

    }

    private void setValues() {
        sender_name.setText(riderQueues.get(riderPos).getPickupSender());
        contact_num.setText(riderQueues.get(riderPos).getPickupContactno());
        wb_no.setText(riderQueues.get(riderPos).getWBno());
        if (riderQueues.get(riderPos).getSenderCompName().equalsIgnoreCase("")) {
            cpny_layout.setVisibility(View.GONE);
        } else {
            cpny_name.setText(riderQueues.get(riderPos).getSenderCompName());
        }
        String deliveryaddr = "";
        if (riderQueues.get(riderPos).getDlvyRoadno() != null && !riderQueues.get(riderPos).getDlvyRoadno().isEmpty())  {
            deliveryaddr = riderQueues.get(riderPos).getDlvyRoadno();
        }
        if (riderQueues.get(riderPos).getDlvyRoadName() != null && !riderQueues.get(riderPos).getDlvyRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(riderPos).getDlvyRoadName();
        }
        if (riderQueues.get(riderPos).getDlvyBuilding() != null && !riderQueues.get(riderPos).getDlvyBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(riderPos).getDlvyBuilding();
        }
        if (riderQueues.get(riderPos).getDlvyBuildingType() != null && !riderQueues.get(riderPos).getDlvyBuildingType().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(riderPos).getDlvyBuildingType();
        }
        if (riderQueues.get(riderPos).getDlvyZipcode() != null && !riderQueues.get(riderPos).getDlvyZipcode().isEmpty())  {
            if (!deliveryaddr.isEmpty()) {
                deliveryaddr = deliveryaddr + "," + riderQueues.get(riderPos).getDlvyZipcode();
            } else {
                deliveryaddr = riderQueues.get(riderPos).getDlvyZipcode();
            }

        }
        delivery_address.setText(deliveryaddr);
        String pickupaddr = "";
        if (riderQueues.get(riderPos).getPickupRoadno() != null && !riderQueues.get(riderPos).getPickupRoadno().isEmpty())  {
            pickupaddr = riderQueues.get(riderPos).getPickupRoadno();
        }
        if (riderQueues.get(riderPos).getPickupRoadName() != null && !riderQueues.get(riderPos).getPickupRoadName().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(riderPos).getPickupRoadName();
        }
        if (riderQueues.get(riderPos).getPickupBuilding() != null && !riderQueues.get(riderPos).getPickupBuilding().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(riderPos).getPickupBuilding();
        }
        if (riderQueues.get(riderPos).getPickupBuildingType() != null && !riderQueues.get(riderPos).getPickupBuildingType().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(riderPos).getPickupBuildingType();
        }
        if (riderQueues.get(riderPos).getPickupZipcode() != null && !riderQueues.get(riderPos).getPickupZipcode().isEmpty())  {
            if (!pickupaddr.isEmpty()) {
                pickupaddr = pickupaddr + "," + riderQueues.get(riderPos).getPickupZipcode();
            } else {
                pickupaddr = riderQueues.get(riderPos).getPickupZipcode();
            }

        }
        pickup_address.setText(pickupaddr);
        /*pickup_address.setText(riderQueues.get(riderPos).getPickupRoadno() + ", " + riderQueues.get(riderPos).getPickupRoadName() + ",\n" +
                riderQueues.get(riderPos).getPickupBuilding() + ",\n" +
              //  riderQueues.get(riderPos).getPickupBuildingType() + ",\n" +
                riderQueues.get(riderPos).getPickupUnitNo() + ", " + riderQueues.get(riderPos).getPickupZipcode() + ".");

        delivery_address.setText(riderQueues.get(riderPos).getDlvyRoadno() + ", " + riderQueues.get(riderPos).getDlvyRoadName() + ",\n" +
                riderQueues.get(riderPos).getDlvyBuilding() + ",\n" +
             //   riderQueues.get(riderPos).getDlvyBuildingType() + ",\n" +
                riderQueues.get(riderPos).getDlvyUnitNo() + ", " + riderQueues.get(riderPos).getDlvyZipcode() + ".");*/

        consig_value.setText(riderQueues.get(riderPos).getConsignmentType());
        service_type.setText(riderQueues.get(riderPos).getServiceType());
        if (riderQueues.get(riderPos).getDelivery_CO().equalsIgnoreCase("False")) {
            type_value.setText("Delivery Only");
        } else {
            type_value.setText("Delivery and Collect Order");
        }
        job_type_value.setText(riderQueues.get(riderPos).getJobType());
        pickupdtime_value.setText(riderQueues.get(riderPos).getPickupdatetime());

        spt_inst.setText(riderQueues.get(riderPos).getSpInst());
        status_value.setText(riderQueues.get(riderPos).getStatus());
        if (riderQueues.get(riderPos).getStatusCode().equals("RidAcc") || riderQueues.get(riderPos).getStatusCode().equals("PassAcc")
                || riderQueues.get(riderPos).getStatusCode().equals("PassRej")) {
            chkstatus = riderQueues.get(riderPos).getLastStatusCode();
        } else {
            chkstatus = riderQueues.get(riderPos).getStatusCode();
        }
        Log.e("delivrApp", "Check Status:" + chkstatus + "New Statusd:"  + newstatus + "Status Code:" +
                riderQueues.get(riderPos).getLastStatusCode() + "Delivery_CO" + riderQueues.get(riderPos).getDelivery_CO());
        if ((riderQueues.get(riderPos).getStatusCode().equals("RidAcc") ||
                riderQueues.get(riderPos).getStatusCode().equals("PassRej")) && chkstatus.equals("")) {
//            newst.setText("Pickedup");
            newstatus = "Pic";
//            lblproof1.setText("Picked up Proof");
//            lblproof2.setVisibility(View.GONE);
            btn_save.setText("PickedUp"); //Ragu

        } else if (chkstatus.equals("Pic") && riderQueues.get(riderPos).getDelivery_CO().toString().equals("False")) {
//            newst.setText("Delivered");
            newstatus = "Del";
//            lblproof1.setText("Delivery Proof");
            btn_save.setText("Delivered"); //Ragu
//            lblproof2.setVisibility(View.GONE);
        } else if (chkstatus.equals("Pic") && riderQueues.get(riderPos).getDelivery_CO().toString().equals("True")) {
//            newst.setText("Delivered & Pickedup");
            newstatus = "DelPic";
            /*lblproof1.setText("Delivery Proof");
            lblproof2.setText("Picked up Proof");
            lblproof2.setVisibility(View.VISIBLE);
            imgv.setVisibility(View.VISIBLE);
            imgv1.setVisibility(View.VISIBLE);
            imgv2.setVisibility(View.VISIBLE);*/
        } else if (chkstatus.equals("DelPic")) {
//            newst.setText("Delivered");
            newstatus = "Del";
        } else {
            btn_save.setVisibility(View.GONE);
        }

        Log.e("delivrApp", "NewStatus: " + newstatus);

    }

    private void setListeners() {
        jbd_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_pass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.jbd_back:
                onBackPressed();
                break;
            case R.id.btn_save:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Please wait..");
                progressDialog.setCancelable(false);
                saveActionQ();
                break;
            case R.id.btn_pass:
                break;
        }
    }

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
                        Utils.showMessageDialog(MyJobs_Details.this,
                                getString(R.string.dialog_title_sorry),
                                response.body().getStatus());
                    }
                } else {
                    Utils.showMessageDialog(MyJobs_Details.this,
                            getString(R.string.dialog_title_sorry),
                            response.body().getStatus());
                }
            }


            @Override
            public void onFailure(Call<ResponseActionAQ> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(MyJobs_Details.this);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(MYJOB_DETAILS_back);
        finish();
    }
}
