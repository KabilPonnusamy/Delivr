package sg.delivr.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.postmodels.PostDoRiderQueue;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.data.model.MyJobsModel;
import sg.delivr.ui.activity.SearchLocation;
import sg.delivr.ui.adapters.MyJobsAdapter;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;


public class Frag_MyOrders extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {

    /*ProgressDialog progressDialog;
    private Call<ArrayList<ResponseRiderQueue>> callRiderQueue;
   */

    Toolbar dash_toolbar;
    TextView toolbar_title;
    EditText edt_pickupaddr_name, edt_pickupaddr,edt_pickupaddr_unitno,edt_pickupaddr_contact,
            edt_dlvryaddr_name, edt_dlvryaddr_email, edt_dlvryaddr, edt_dlvryaddr_unitno, edt_dlvryaddr_contact,
            edt_fooddetails, edt_delivryinstruction;
    LinearLayout submit_createorder;
    static TextView edt_pickupdatetime, txt_pickuptime;
    String pickupadddress_name, pickupadddress, pickupadddress_unitno, pickupadddress_contact, deliveryaddress_name,
            deliveryaddress_email, deliveryaddress, deliveryaddress_unitno, deliveryaddress_contact,
            fooddetails, deliveryinstruction;
    String userId;
    private static String format = "", pickupdatetime = "", selectedpickupdate = "", selectedpickuptime = "";


    TextView txt_address;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myorders, container, false);

        toolbarInit();
        initView(view);
//        initiateProgress();

        return view;
    }

    /*private void initiateProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        if (!CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network));
        }

        gatherRiders();
    }

    private void gatherRiders() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign =  userId + Strapikey + Strapicode;
        String StrSignature = SHAInterface.SHA1(sign);
        Log.e("delivrApp", "Signature: " + StrSignature);
        Log.e("delivrApp", "Sign: " + sign);
        Log.e("delivrApp", "StraipKey: " + Strapikey);
        Log.e("delivrApp", "StraipCode: " + Strapicode);

        progressDialog.show();
        callRiderQueue = RetrofitClient.getInstance().getApiInterface().getRiderQueue(
                new PostDoRiderQueue( userId, Strapikey, StrSignature));

        callRiderQueue.enqueue(new Callback<ArrayList<ResponseRiderQueue>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseRiderQueue>> call,
                                   final Response<ArrayList<ResponseRiderQueue>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    riderQueues = response.body();
                    showAdapters();
                } else {
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ResponseRiderQueue>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });

    }

    private void showAdapters() {
        if(riderQueues != null) {
            if(riderQueues.size() > 0) {
                jobs_recycler.setVisibility(View.VISIBLE);
                label_empty_myjobs.setVisibility(View.GONE);
                jobs_recycler.setHasFixedSize(true);
                jobs_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                myjobsAdapter = new MyJobsAdapter(riderQueues, getActivity(),
                        Frag_MyOrders.this);
                jobs_recycler.setAdapter(myjobsAdapter);

            } else {
                jobs_recycler.setVisibility(View.GONE);
                label_empty_myjobs.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView(View view) {
        userId = Prefs.getUserId();

        riderQueues = new ArrayList<ResponseRiderQueue>();
        jobs_recycler = view.findViewById(R.id.jobs_recycler);
        label_empty_myjobs = view.findViewById(R.id.label_empty_myjobs);
        *//*bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        bottom_nav.setVisibility(View.VISIBLE);*//*
    }*/

    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myorders));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    public static void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }
        selectedpickuptime = "" + hour + ":" + min + " "+ format;
        Log.e("delivrApp", "Selected PickUp Time" + selectedpickuptime);
        edt_pickupdatetime.setText(selectedpickupdate + " " + selectedpickuptime);
        txt_pickuptime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }
    private void validation() {
        pickupadddress_name = edt_pickupaddr_name.getText().toString().trim();
        pickupadddress = edt_pickupaddr.getText().toString().trim();
        pickupadddress_unitno = edt_pickupaddr_contact.getText().toString().trim();
        pickupadddress_contact = edt_pickupaddr_contact.getText().toString().trim();
        deliveryaddress_name = edt_dlvryaddr_name.getText().toString().trim();
        deliveryaddress_email = edt_dlvryaddr_email.getText().toString().trim();
        deliveryaddress = edt_dlvryaddr.getText().toString().trim();
        deliveryaddress_unitno = edt_dlvryaddr_unitno.getText().toString().trim();
        deliveryaddress_contact = edt_dlvryaddr_contact.getText().toString().trim();
        //pickupdatetime = edt_pickupdatetime.getText().toString().trim();
        fooddetails = edt_fooddetails.getText().toString().trim();
        deliveryinstruction = edt_delivryinstruction.getText().toString().trim();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == FRAG_MYORDERS_to_SEARCH_LOCATION) {
            if(resultCode == SEARCH_success) {
                String location_value = data.getStringExtra("location_value");
                Log.e("delivrApp", "Location: " + location_value);
                txt_address.setText(location_value);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
