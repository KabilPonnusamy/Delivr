package com.delivr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.delivr.R;
import com.delivr.data.model.MyJobsModel;
import com.delivr.ui.adapters.MyJobsAdapter;

import java.util.ArrayList;


public class Frag_MyJobs extends Fragment implements View.OnClickListener {

    Toolbar dash_toolbar;
    TextView toolbar_title;
    RecyclerView jobs_recycler;
    ArrayList<MyJobsModel> myJobsModel;
    MyJobsAdapter myjobsAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myjobs, container, false);

        toolbarInit();
        initView(view);

        return view;
        }

    private void initView(View view) {
        jobs_recycler = view.findViewById(R.id.jobs_recycler);
        myJobsModel = new ArrayList<MyJobsModel>();
        myJobsModel = getJobsModel();

        if(myJobsModel != null) {
            if(myJobsModel.size() > 0) {
                jobs_recycler.setHasFixedSize(true);
                jobs_recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
                myjobsAdapter = new MyJobsAdapter(myJobsModel, getActivity());
                jobs_recycler.setAdapter(myjobsAdapter);
            }
        }
    }

    private ArrayList<MyJobsModel> getJobsModel() {
        ArrayList<MyJobsModel> item = new ArrayList<MyJobsModel>();

        MyJobsModel item1 = new MyJobsModel("JOBS11352", "Food Delivery", "17 May 2020",
                "05:20 PM", "4/52 Novena Street, Novena", "45.50", "Immediate work required, Have to deliver the parcel within half hour. Otherwise the amount will not be added on accunt",
                "+65 452 120 32", "John Peter", "17 May 2020", "07:00 PM", "Shop no:52, Albert street");

        MyJobsModel item2 = new MyJobsModel("JOBS11365", "Parcel Delivery", "17 May 2020",
                "06:30 PM", "5/65 Club Street", "30.50", "It has an Medicines, so handle it very safely. Delivery time was not an important, but need to deliver safely.",
                "+65 450 325 95", "Williamson", "17 May 2020", "09:00 PM", "56, Seng kang Street, Seng kang");

        MyJobsModel item3 = new MyJobsModel("JOBS11312", "Laundry Dress Delivery", "18 May 2020",
                "08:00 AM", "6/32 Short Street", "32.00", "We have washed all my customer dresses, but we can't able to deliver it. Its an 2 Dozen dress and we bundled it very carefully. So deliver it safely.",
                "+65 459 153 63", "Mary Fernandous", "18 May 2020", "09:00 PM", "89/10 Adam Park road");

        MyJobsModel item4 = new MyJobsModel("JOBS11396", "Grocery Devliery", "17 May 2020",
                "08:00 AM", "96 Duxton Hill/Road", "20.00", "Customer has ordered the grocery things yesterday and he leaves the house on 10o clock. So need to deliver the grocery items before he leaves the house.",
                "+65 409 635 32","Annie",  "17 May 2020", "10:00 PM", "56, Thomson Road");

        MyJobsModel item5 = new MyJobsModel("JOBS11321", "Makeup Kit Delivery", "18 May 2020",
                "09:00 PM", "3/20 Rotan Lane", "25.50", "My customer was mentioned their office address, also she said if she was not in office, then need to deliver into security.",
                "+65 463 215 47", "Olive Johnson", "18 May 2020", "12:00 PM", "Abdul Corporate, Jalan Beser");

        item.add(item1);
        item.add(item2);
        item.add(item3);
        item.add(item4);
        item.add(item5);

        return item;
    }

    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myjobs));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
