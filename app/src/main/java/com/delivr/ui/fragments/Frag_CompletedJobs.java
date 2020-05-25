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
import com.delivr.backend.postmodels.PostDoCompletedJobs;
import com.delivr.backend.postmodels.PostDoRiderQueue;
import com.delivr.backend.responsemodels.ResponseCompletedJobs;
import com.delivr.ui.adapters.MyCompletedJobsAdapter;
import com.delivr.ui.adapters.MyJobsAdapter;
import com.delivr.ui.interfaces.Intent_Constants;
import com.delivr.ui.interfaces.SHAInterface;
import com.delivr.utils.CheckNetwork;
import com.delivr.utils.Prefs;
import com.delivr.utils.Utils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Frag_CompletedJobs extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {

    RecyclerView compjobs_recycler;
    Toolbar dash_toolbar;
    TextView toolbar_title;
    ProgressDialog progressDialog;
    String userId;
    MyCompletedJobsAdapter mycompjobsAdapter;
    private Call<ArrayList<ResponseCompletedJobs>> callCompJobs;
    ArrayList<ResponseCompletedJobs> completedJobs;
    TextView label_empty_completedjobs;
    //BottomNavigationView bottom_nav;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_completejobs, container, false);

        toolbar_Init();
        initView(view);
        initiateProgress();

        return view;
        }

    private void initiateProgress() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        if (!CheckNetwork.isInternetAvailable(getActivity()))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network));
        }

        getCompletedJobs();
    }

    private void getCompletedJobs() {
        String Strapikey = getString(R.string.apikey);
        String Strapicode = getString(R.string.apicode);
        String sign =  userId + Strapikey + Strapicode;
        String StrSignature = SHAInterface.SHA1(sign);
        Log.e("delivrApp", "Signature: " + StrSignature);
        Log.e("delivrApp", "Sign: " + sign);
        Log.e("delivrApp", "StraipKey: " + Strapikey);
        Log.e("delivrApp", "StraipCode: " + Strapicode);

        progressDialog.show();
        callCompJobs = RetrofitClient.getInstance().getApiInterface().getCompletedJobs(
                new PostDoCompletedJobs( userId, Strapikey, StrSignature));
        callCompJobs.enqueue(new Callback<ArrayList<ResponseCompletedJobs>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseCompletedJobs>> call,
                                   final Response<ArrayList<ResponseCompletedJobs>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    completedJobs = response.body();
                    showAdapters();
                } else {
                    Utils.showMessageDialog(getActivity(),
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ResponseCompletedJobs>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(getActivity());
            }
        });
    }

    private void showAdapters() {
        if(completedJobs != null) {
            if(completedJobs.size() > 0) {
                compjobs_recycler.setVisibility(View.VISIBLE);
                label_empty_completedjobs.setVisibility(View.GONE);
                compjobs_recycler.setHasFixedSize(true);
                compjobs_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                mycompjobsAdapter = new MyCompletedJobsAdapter(completedJobs, getActivity(),
                        Frag_CompletedJobs.this);
                compjobs_recycler.setAdapter(mycompjobsAdapter);
            } else {
                compjobs_recycler.setVisibility(View.GONE);
                label_empty_completedjobs.setVisibility(View.VISIBLE);
            }
        }
    }

    private void initView(View view) {
        userId = Prefs.getUserId();
        Log.e("delivrApp", "UserId: " + userId);
        compjobs_recycler = view.findViewById(R.id.compjobs_recycler);
        completedJobs = new ArrayList<ResponseCompletedJobs>();
        label_empty_completedjobs = view.findViewById(R.id.label_empty_completedjobs);
       // bottom_nav = getActivity().findViewById(R.id.bottom_nav);
       // bottom_nav.setVisibility(View.GONE);
    }

    private void toolbar_Init() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_comp_jobs));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

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
