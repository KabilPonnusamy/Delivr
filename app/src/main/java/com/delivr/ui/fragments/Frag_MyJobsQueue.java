package com.delivr.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivr.R;
import com.delivr.backend.RetrofitClient;
import com.delivr.backend.postmodels.PostAssignedActionAQ;
import com.delivr.backend.postmodels.PostAssignedBitAQ;
import com.delivr.backend.postmodels.PostDoRiderQueue;
import com.delivr.backend.postmodels.PostgetAssignedQueue;
import com.delivr.backend.responsemodels.ResponseAssignedActionAQ;
import com.delivr.backend.responsemodels.ResponseAssignedQueue;
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.ui.adapters.JobsQueueAdapter;
import com.delivr.ui.adapters.MyJobsAdapter;
import com.delivr.ui.interfaces.SHAInterface;
import com.delivr.utils.CheckNetwork;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_MyJobsQueue extends Fragment implements View.OnClickListener {

    Toolbar dash_toolbar;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    private Call<ArrayList<ResponseAssignedQueue>> callAssignedQueue;
    private Call<ResponseAssignedActionAQ> callassignedActionAQ;
    RecyclerView queuedjobs_recycler;
    JobsQueueAdapter jobsQueueAdapter;
    String userId;
    ResponseAssignedQueue assignedQueue;
    ArrayList<ResponseAssignedQueue> riderQueues;
    private int selectedjobpos;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myjobsqueue, container, false);

        toolbarInit();
        initView(view);
        initiateProgress();

        return view;
        }

    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myjobs_queue));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    private void initiateProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        if (!CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network));
        }

        gatherRiders();
    }
    private void initView(View view) {
        userId = Prefs.getUserId();
        Log.e("delivrApp", "UserId: " + userId);

        riderQueues = new ArrayList<ResponseAssignedQueue>();
        queuedjobs_recycler = view.findViewById(R.id.jobsqueue_recycler);

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
        callAssignedQueue = RetrofitClient.getInstance().getApiInterface().getAssignedQueue(
                new PostgetAssignedQueue( userId, Strapikey, StrSignature));

        callAssignedQueue.enqueue(new Callback<ArrayList<ResponseAssignedQueue>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseAssignedQueue>> call,
                                   final Response<ArrayList<ResponseAssignedQueue>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    riderQueues = response.body();
                    setAdapter(riderQueues);
                } else {
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ResponseAssignedQueue>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });

    }

    private void setAdapter(ArrayList<ResponseAssignedQueue> jobArrayList) {

        progressDialog.dismiss();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        queuedjobs_recycler.setLayoutManager(linearLayoutManager);
        queuedjobs_recycler.setHasFixedSize(true);
        //creating recyclerview adapter
        jobsQueueAdapter = new JobsQueueAdapter(jobArrayList, getActivity(),Frag_MyJobsQueue.this);

        //setting adapter to recyclerview
        queuedjobs_recycler.setAdapter(jobsQueueAdapter);
        queuedjobs_recycler.setNestedScrollingEnabled(true);

        jobsQueueAdapter.setOnClickListener(new JobsQueueAdapter.JobQueueListItem() {
            @Override
            public void onItemClickListener(String identify, View view, int position, ResponseAssignedQueue riderQueues) {
                assignedQueue = riderQueues;
                selectedjobpos = position;
                String UserId = Prefs.getUserId();
                if (identify.equals("ViewDetails")) {

                } else if (identify.equalsIgnoreCase("Accept")) {
                    Log.e("DelivrApp", "Selected Job Position after accept:" + selectedjobpos);

                    if (assignedQueue.getPassType() == null || assignedQueue.getPassType().equals("")) {
                        // check times senstive and use order bidding
                        if (assignedQueue.getOrderType().equals("TS")) {

                            SendRidersAction(UserId,assignedQueue.getWBno(),"RidAcc", "TS");
                           // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("WBno"), "RidAcc", "TS"});
                        } else {
                            SendRidersAction(UserId,assignedQueue.getOrderWBUid(),"RidAcc", "normal");
                           // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "RidAcc", "normal"});
                        }
                    } else {
                        SendRidersAction(UserId,assignedQueue.getOrderWBUid(),"PassAcc", "normal");
                       // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "PassAcc", "normal"});
                    }
                } else if (identify.equalsIgnoreCase("Reject")) {
                    Log.e("DelivrApp", "Selected Job Position after reject:" + selectedjobpos);
                    if (assignedQueue.getPassType() == null || assignedQueue.getPassType().equals("")) {
                        if (assignedQueue.getOrderType().equals("TS")) {
                            SendRidersAction(UserId,assignedQueue.getWBno(),"RidRej", "TS");
                           // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("WBno"), "RidRej", "TS"});
                        } else {
                            SendRidersAction(UserId,assignedQueue.getOrderWBUid(),"RidRej", "normal");
                         //   ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "RidRej", "normal"});
                        }
                    } else {
                           SendRidersAction(UserId,assignedQueue.getOrderWBUid(),"PassRej", "normal");
                       // ActAsunc.execute(new String[]{MainActivity.UserId, AQdata.getString("OrderWBUid"), "PassRej", "normal"});
                    }
                }

            }
        });
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
                    new PostAssignedBitAQ( userId,strOrderWBUid_no,strAction ,Strapikey, StrSignature));
           // Strapimethod = "RiderQueue/BidAQ";
        } else {
            callassignedActionAQ = RetrofitClient.getInstance().getApiInterface().sendassignedActionAQ(
                    new PostAssignedActionAQ( userId,strOrderWBUid_no,strAction ,Strapikey, StrSignature));
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
                        Log.e("DelivrApp", "Selected Position before remove:" + selectedjobpos);
                        riderQueues.remove(selectedjobpos);
                        jobsQueueAdapter.notifyDataSetChanged();
                    }
                   /* riderQueues = response.body();
                    setAdapter(riderQueues);*/
                } else {
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ResponseAssignedActionAQ> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
    public void AlertBox(final String head, final String meg) {
        LayoutInflater in = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vv = in.inflate(R.layout.alertbox, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
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
                    getActivity().finish();
                } else if (head.equalsIgnoreCase("Alert")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }
}
