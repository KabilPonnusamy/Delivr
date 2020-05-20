package com.delivr.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.delivr.Common.StoredDatas;
import com.delivr.R;
import com.delivr.backend.responsemodels.ResponseAssignedQueue;
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.ui.activity.MyJobs_Details;
import com.delivr.ui.fragments.Frag_MyJobs;
import com.delivr.ui.fragments.Frag_MyJobsQueue;
import com.delivr.ui.interfaces.Intent_Constants;
import com.delivr.ui.interfaces.SHAInterface;

import java.text.ParseException;
import java.util.ArrayList;


public class JobsQueueAdapter extends RecyclerView.Adapter<JobsQueueAdapter.ViewHolder> implements
        Intent_Constants  {

    private ArrayList<ResponseAssignedQueue> riderQueues;
    private Context context;
    private Frag_MyJobsQueue frag_obj;
    JobQueueListItem jobQueueListItem;
    public JobsQueueAdapter(ArrayList<ResponseAssignedQueue> riderQueues, Context context, Frag_MyJobsQueue frag_obj) {
        this.riderQueues = riderQueues;
        this.context = context;
        this.frag_obj = frag_obj;
    }

    public void setOnClickListener(JobQueueListItem jobQueueListItem) {
        this.jobQueueListItem = jobQueueListItem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignedjobsqueue_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.job_wbno_value.setText(riderQueues.get(position).getWBno());
        holder.job_type.setText(riderQueues.get(position).getJobType());


        holder.job_price.setText("$ " + riderQueues.get(position).getAmount());
        ResponseAssignedQueue responseRiderQueue = riderQueues.get(position);
        try {
            String myDate = SHAInterface.getDateFormattedTwo(riderQueues.get(position).getPickupdatetime());
            holder.job_date.setText(myDate);
            String myTime = SHAInterface.getTimeFormattedTwo(riderQueues.get(position).getPickupdatetime());
            holder.job_time.setText(myTime);
        } catch (ParseException e) {
            holder.job_date.setText(riderQueues.get(position).getPickupdatetime());
            holder.job_time.setText(riderQueues.get(position).getPickupdatetime());
            e.printStackTrace();
        }

        holder.itemjob_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                jobQueueListItem.onItemClickListener("ViewDetails", v, position, responseRiderQueue);
            }
        });

        holder.accept_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobQueueListItem.onItemClickListener("Accept", v, position, responseRiderQueue);
            }
        });
        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jobQueueListItem.onItemClickListener("Reject", v, position, responseRiderQueue);
            }
        });


    }

    @Override
    public int getItemCount() {
        return riderQueues.size();
    }

    public interface JobQueueListItem {
        //void onItemClickListener(View view, int position, Categorylistvalue categorylistmodel);
        void onItemClickListener(String identify, View view, int position, ResponseAssignedQueue riderQueues);

    }
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView job_wbno_value, job_type, job_date, job_time, job_price;
        Button accept_btn, reject_btn;
        CardView itemjob_cardview;
        ViewHolder(View itemView) {
            super(itemView);
            itemjob_cardview = (CardView) itemView.findViewById(R.id.cardview);
            job_wbno_value = (TextView) itemView.findViewById(R.id.job_wbno_value);
            job_type = (TextView) itemView.findViewById(R.id.job_type);
            job_date = (TextView) itemView.findViewById(R.id.job_date);
            job_time = (TextView) itemView.findViewById(R.id.job_time);
            job_price = (TextView) itemView.findViewById(R.id.job_price);
            accept_btn = (Button) itemView.findViewById(R.id.accept_btn);
            reject_btn = (Button) itemView.findViewById(R.id.reject_btn);
        }
    }
}
