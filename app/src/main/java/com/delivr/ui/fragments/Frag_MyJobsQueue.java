package com.delivr.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.delivr.R;


public class Frag_MyJobsQueue extends Fragment implements View.OnClickListener {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myjobsqueue, container, false);

        Toolbar dash_toolbar;
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        dash_toolbar.setVisibility(View.GONE);

        return view;
        }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}
