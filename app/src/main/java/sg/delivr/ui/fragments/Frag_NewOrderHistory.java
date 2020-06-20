package sg.delivr.ui.fragments;

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

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.postmodels.PostDoOrderHistory;
import sg.delivr.backend.postmodels.PostDoRiderQueue;
import sg.delivr.backend.responsemodels.ResponseOrderHistory;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.data.model.MyJobsModel;
import sg.delivr.ui.adapters.MyOrderHisAdapter;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;


public class Frag_NewOrderHistory extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {  // , SwipeRefreshLayout.OnRefreshListener

    ProgressDialog progressDialog;
    private boolean isShowingPassword;

    Toolbar dash_toolbar;
    TextView toolbar_title;
    RecyclerView odetails_recycler;
    ArrayList<MyJobsModel> myJobsModel;
    MyOrderHisAdapter myjobsAdapter;
    TextView label_empty_myjobs;
    LinearLayoutManager mLayoutManager;
    String UserId = "", Userrole = "";
    int strRowFrom = 1, strRowTo = 10;
    ArrayList<ResponseOrderHistory> orderHistoryList;
    private Call<ArrayList<ResponseOrderHistory>> callOrderHisQueue;
    boolean endReached = false;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_neworderhistory, container, false);

        toolbarInit();
        initView(view);
        initiateProgress();

        return view;
        }

    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_order_history));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
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

    private void gatherRiders() {
        progressDialog.dismiss();
        UserId = Prefs.getUserId();
        Userrole = Prefs.getUserRole();

        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);

        /*Log.e("delivrApp", "strSignature: " + StrSignature);
        Log.e("delivrApp", "Strapikey: " + Strapikey);
        Log.e("delivrApp", "UserId: " + UserId);
        Log.e("delivrApp", "Userrole: " + Userrole);*/

        progressDialog.show();
        callOrderHisQueue = RetrofitClient.getInstance().getApiInterface().getOrderHistory(
                new PostDoOrderHistory( UserId, "", "", Userrole, Strapikey,
                        StrSignature, ""+ strRowFrom, ""+ strRowTo));

        callOrderHisQueue.enqueue(new Callback<ArrayList<ResponseOrderHistory>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOrderHistory>> call,
                                   final Response<ArrayList<ResponseOrderHistory>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    orderHistoryList = response.body();
                    showAdapters();
                } else {
                    endReached = true;
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOrderHistory>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });
    }

    private void showAdapters() {
        if(orderHistoryList != null) {
            if(orderHistoryList.size() > 0) {
                odetails_recycler.setVisibility(View.VISIBLE);
                label_empty_myjobs.setVisibility(View.GONE);
                odetails_recycler.setHasFixedSize(true);
                mLayoutManager = new LinearLayoutManager(getActivity());

                odetails_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                myjobsAdapter = new MyOrderHisAdapter(orderHistoryList, getActivity(),
                        Frag_NewOrderHistory.this);
                odetails_recycler.setAdapter(myjobsAdapter);

            } else {
                odetails_recycler.setVisibility(View.GONE);
                label_empty_myjobs.setVisibility(View.VISIBLE);
            }
        }

        odetails_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
                    if(endReached == false) {
                        strRowFrom = orderHistoryList.size() + 1;
                        strRowTo = orderHistoryList.size() + 10;
                        add_fewDatas();
                    } else {
//                        Log.e("delivrApp", "Size_Reached: " + endReached);
                    }
                }
            }
        });
    }

    private void add_fewDatas() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign = UserId + Strapikey + Strapicode;
        String StrSignature = Utils.SHA1(sign);

        /*Log.e("delivrApp", "From: " + strRowFrom);
        Log.e("delivrApp", "To: " + strRowTo);*/

        progressDialog.show();
        callOrderHisQueue = RetrofitClient.getInstance().getApiInterface().getOrderHistory(
                new PostDoOrderHistory( UserId, "", "", Userrole, Strapikey,
                        StrSignature, ""+ strRowFrom, ""+ strRowTo));

        callOrderHisQueue.enqueue(new Callback<ArrayList<ResponseOrderHistory>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseOrderHistory>> call,
                                   final Response<ArrayList<ResponseOrderHistory>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    if(response.body().size() > 0) {
                        ArrayList<ResponseOrderHistory> orderHis = new ArrayList<ResponseOrderHistory>();
                        orderHis = response.body();
                        orderHistoryList.addAll(orderHistoryList.size(),orderHis);
                        myjobsAdapter.notifyDataSetChanged();
                    } else {
                        endReached = true;
                    }

                } else {
                    endReached = true;
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ResponseOrderHistory>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });
    }

    private void initView(View view) {
        odetails_recycler = view.findViewById(R.id.odetails_recycler);

        orderHistoryList = new ArrayList<ResponseOrderHistory>();
        label_empty_myjobs = view.findViewById(R.id.label_empty_myjobs);
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
