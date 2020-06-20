package sg.delivr.ui.activity;

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

import java.text.ParseException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.postmodels.PostDoActionAQ;
import sg.delivr.backend.responsemodels.ResponseActionAQ;
import sg.delivr.backend.responsemodels.ResponseOrderHistory;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;

public class OrderHistory_Details extends AppCompatActivity implements View.OnClickListener,
        Intent_Constants {

    ImageView ohis_back;
    TextView wb_no, cpny_name, pickup_address, delivery_address, sender_name, contact_num,
            consig_value, service_type, type_value, job_type_value, spt_inst, status_value, pickupdtime_value;
    RelativeLayout cpny_layout;

    ArrayList<ResponseOrderHistory> orderHistoryList;
    int oHisPos = 0;
    String chkstatus = "";
    String newstatus = "", strUserId = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ohis_details);

        initView();
    }

    private void initView() {
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
        orderHistoryList = new ArrayList<ResponseOrderHistory>();
        orderHistoryList = StoredDatas.getInstance().getOrderHistoryList();
        oHisPos = StoredDatas.getInstance().getoHisPos();

        ohis_back = findViewById(R.id.ohis_back);
        strUserId = Prefs.getUserId();
        Log.e("delivrApp", "UserId: " + strUserId);
        setValues();
        setListeners();

    }

    private void setValues() {
        setDatewithFormation();

        sender_name.setText(orderHistoryList.get(oHisPos).getSenderName());
        contact_num.setText(orderHistoryList.get(oHisPos).getDeliveryContactNo());
        wb_no.setText(orderHistoryList.get(oHisPos).getWBNo());
        if (orderHistoryList.get(oHisPos).getCompanyName().equalsIgnoreCase("")) {
            cpny_layout.setVisibility(View.GONE);
        } else {
            cpny_name.setText(orderHistoryList.get(oHisPos).getCompanyName());
        }
        String deliveryaddr = "";
        if (orderHistoryList.get(oHisPos).getDRoadNO() != null && !orderHistoryList.get(oHisPos).getDRoadNO().isEmpty())  {
            deliveryaddr = orderHistoryList.get(oHisPos).getDRoadNO();
        }
        if (orderHistoryList.get(oHisPos).getDRoadName() != null && !orderHistoryList.get(oHisPos).getDRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + orderHistoryList.get(oHisPos).getDRoadName();
        }
        if (orderHistoryList.get(oHisPos).getDBuilding() != null && !orderHistoryList.get(oHisPos).getDBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + orderHistoryList.get(oHisPos).getDBuilding();
        }

        if (orderHistoryList.get(oHisPos).getDeliveryZipCode() != null && !orderHistoryList.get(oHisPos).getDeliveryZipCode().isEmpty())  {
            if (!deliveryaddr.isEmpty()) {
                deliveryaddr = deliveryaddr + "," + orderHistoryList.get(oHisPos).getDeliveryZipCode();
            } else {
                deliveryaddr = orderHistoryList.get(oHisPos).getDeliveryZipCode();
            }
        }
        delivery_address.setText(deliveryaddr);


        String pickupaddr = "";
        if (orderHistoryList.get(oHisPos).getPRoadNO() != null && !orderHistoryList.get(oHisPos).getPRoadNO().isEmpty())  {
            pickupaddr = orderHistoryList.get(oHisPos).getPRoadNO();
        }
        if (orderHistoryList.get(oHisPos).getPRoadName() != null && !orderHistoryList.get(oHisPos).getPRoadName().isEmpty())  {
            pickupaddr = pickupaddr + "," + orderHistoryList.get(oHisPos).getPRoadName();
        }
        if (orderHistoryList.get(oHisPos).getPBuilding() != null && !orderHistoryList.get(oHisPos).getPBuilding().isEmpty())  {
            pickupaddr = pickupaddr + "," + orderHistoryList.get(oHisPos).getPBuilding();
        }
        if (orderHistoryList.get(oHisPos).getPickupZipCode() != null && !orderHistoryList.get(oHisPos).getPickupZipCode().isEmpty())  {
            if (!pickupaddr.isEmpty()) {
                pickupaddr = pickupaddr + "," + orderHistoryList.get(oHisPos).getPickupZipCode();
            } else {
                pickupaddr = orderHistoryList.get(oHisPos).getPickupZipCode();
            }
        }
        pickup_address.setText(pickupaddr);


        consig_value.setText(orderHistoryList.get(oHisPos).getConsignmentType());
        service_type.setText(orderHistoryList.get(oHisPos).getServiceType());
        if (orderHistoryList.get(oHisPos).getDelivery_CO().equalsIgnoreCase("False")) {
            type_value.setText("Delivery Only");
        } else {
            type_value.setText("Delivery and Collect Order");
        }
        job_type_value.setText(orderHistoryList.get(oHisPos).getServiceType());

        spt_inst.setText(orderHistoryList.get(oHisPos).getSpInst());
        status_value.setText(orderHistoryList.get(oHisPos).getStatus());
        if (orderHistoryList.get(oHisPos).getStatus().equals("RidAcc") || orderHistoryList.get(oHisPos).getStatus().equals("PassAcc")
                || orderHistoryList.get(oHisPos).getStatus().equals("PassRej")) {
            chkstatus = orderHistoryList.get(oHisPos).getStatus();
        } else {
            chkstatus = orderHistoryList.get(oHisPos).getStatus();
        }
        Log.e("delivrApp", "Check Status:" + chkstatus + "New Statusd:"  + newstatus + "Status Code:" +
                orderHistoryList.get(oHisPos).getStatus() + "Delivery_CO" + orderHistoryList.get(oHisPos).getDelivery_CO());
        if ((orderHistoryList.get(oHisPos).getStatus().equals("RidAcc") ||
                orderHistoryList.get(oHisPos).getStatus().equals("PassRej")) && chkstatus.equals("")) {
//            newst.setText("Pickedup");
            newstatus = "Pic";


        } else if (chkstatus.equals("Pic") && orderHistoryList.get(oHisPos).getDelivery_CO().toString().equals("False")) {
//            newst.setText("Delivered");
            newstatus = "Del";
//            lblproof1.setText("Delivery Proof");
//            lblproof2.setVisibility(View.GONE);
        } else if (chkstatus.equals("Pic") && orderHistoryList.get(oHisPos).getDelivery_CO().toString().equals("True")) {
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
        }

        Log.e("delivrApp", "NewStatus: " + newstatus);

    }

    private void setDatewithFormation() {

        try {
            String pdate = orderHistoryList.get(oHisPos).getPickupDateTime().replaceAll("\\s+"," ");
            String myDate = SHAInterface.getDateFormattedNew(pdate);
            String myTime = SHAInterface.getTimeFormattedNew(pdate);
            Log.e("delivrApp", "DateTime:"+ myDate + " " + myTime);
            pickupdtime_value.setText(myDate + " " + myTime);
        } catch (ParseException e) {
            Log.e("delivrApp", "Exception: " + e.getMessage());
            pickupdtime_value.setText(orderHistoryList.get(oHisPos).getPickupDateTime());
            e.printStackTrace();
        }
    }

    private void setListeners() {
        ohis_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ohis_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(MYJOB_DETAILS_back);
        finish();
    }
}
