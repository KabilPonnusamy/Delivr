package com.delivr.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.delivr.R;
import com.delivr.data.model.MyJobsModel;

import java.util.ArrayList;
import java.util.List;


public class MyJobsAdapter extends RecyclerView.Adapter<MyJobsAdapter.ViewHolder>  {

    private List<MyJobsModel> myJobsmodel;
    private Context context;

    public MyJobsAdapter(ArrayList<MyJobsModel> myJobsmodel, Context context) {
        this.myJobsmodel = myJobsmodel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myjobs_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.job_wbno_value.setText(myJobsmodel.get(position).getStr_jobwbno());
        holder.job_type.setText(myJobsmodel.get(position).getStr_jobtype());
        holder.job_date.setText(myJobsmodel.get(position).getStr_jobpickdate());
        holder.job_time.setText(myJobsmodel.get(position).getStr_jobpicktime());
        holder.job_price.setText("$ " + myJobsmodel.get(position).getStr_jobamount());

    }

    @Override
    public int getItemCount() {
        return myJobsmodel.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView job_wbno_value, job_type, job_date, job_time, job_price;
        Button viewdetails_btn;

        ViewHolder(View itemView) {
            super(itemView);

            job_wbno_value = (TextView) itemView.findViewById(R.id.job_wbno_value);
            job_type = (TextView) itemView.findViewById(R.id.job_type);
            job_date = (TextView) itemView.findViewById(R.id.job_date);
            job_time = (TextView) itemView.findViewById(R.id.job_time);
            job_price = (TextView) itemView.findViewById(R.id.job_price);
            viewdetails_btn = (Button) itemView.findViewById(R.id.viewdetails_btn);
        }
    }
}
