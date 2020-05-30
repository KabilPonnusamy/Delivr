package sg.delivr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import sg.delivr.R;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;


public class Frag_WalletTopUp extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {

    /*ProgressDialog progressDialog;
    private Call<ArrayList<ResponseRiderQueue>> callRiderQueue;
    private boolean isShowingPassword;*/

    Toolbar dash_toolbar;
    TextView toolbar_title;
    /*RecyclerView jobs_recycler;
    ArrayList<MyJobsModel> myJobsModel;
    MyJobsAdapter myjobsAdapter;
    String userId;
    ArrayList<ResponseRiderQueue> riderQueues;
    TextView label_empty_myjobs;*/
    //BottomNavigationView bottom_nav;

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
//        initView(view);
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
        toolbar_title.setText(getResources().getString(R.string.txt_wallet_topup));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    /*public void AlertBox(final String head, final String meg) {
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
    }*/

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == RIDER_MYJOBS_to_MYJOB_DETAILS) {
            if(resultCode == MYJOBSAQ_success) {
                String listAQPos = data.getStringExtra("AQPosition");
                String jobstatus = data.getStringExtra("AQStatus");
                int position = Integer.parseInt(listAQPos);
                Log.e("delivrApp", "LastPos: " + position  + "Job Status:" +  jobstatus);

                if (jobstatus.equalsIgnoreCase("Del")) {
                    riderQueues.remove(position);
                    myjobsAdapter.notifyDataSetChanged();
                } else {
                    String chkstatus = "";
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Please wait..");
                    progressDialog.setCancelable(false);
                    if (jobstatus.equalsIgnoreCase("Pic")) {
                        gatherRiders();
                    }
                    riderQueues.get(position).setStatus(jobstatus);
                    StoredDatas.getInstance().setRiderQueues(riderQueues);
                    riderQueues = StoredDatas.getInstance().getRiderQueues();
                    Log.e("delivrApp", "Inside My Jobs get current data : Check Status:" + chkstatus + "New Status:"  + riderQueues.get(position).getStatus()+
                            "Status Code:" +riderQueues.get(position).getStatusCode() + "Delivery_CO" + riderQueues.get(position).getDelivery_CO());
                    if (riderQueues.get(position).getStatusCode().equals("RidAcc") || riderQueues.get(position).getStatusCode().equals("PassAcc")
                            || riderQueues.get(position).getStatusCode().equals("PassRej")) {
                        chkstatus = riderQueues.get(position).getLastStatusCode();
                    } else {
                        chkstatus = riderQueues.get(position).getStatusCode();
                    }
                    Log.e("delivrApp", "Inside My Jobs else : Check Status:" + chkstatus + "New Statusd:"  + riderQueues.get(position).getStatus() + "Status Code:" +
                            riderQueues.get(position).getLastStatusCode() + "Delivery_CO" + riderQueues.get(position).getDelivery_CO());
                    myjobsAdapter.notifyDataSetChanged();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
