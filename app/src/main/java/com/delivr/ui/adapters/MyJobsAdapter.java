package com.delivr.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.delivr.Common.StoredDatas;
import com.delivr.R;
import com.delivr.backend.responsemodels.ResponseRiderQueue;
import com.delivr.data.model.MyJobsModel;
import com.delivr.ui.activity.MyJobs_Details;
import com.delivr.ui.fragments.Frag_MyJobs;
import com.delivr.ui.interfaces.Intent_Constants;
import com.delivr.ui.interfaces.SHAInterface;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MyJobsAdapter extends RecyclerView.Adapter<MyJobsAdapter.ViewHolder> implements
        Intent_Constants  {

    private ArrayList<ResponseRiderQueue> riderQueues;
    private Context context;
    private Frag_MyJobs frag_obj;

    public MyJobsAdapter(ArrayList<ResponseRiderQueue> riderQueues, Context context, Frag_MyJobs frag_obj) {
        this.riderQueues = riderQueues;
        this.context = context;
        this.frag_obj = frag_obj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myjobs_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.job_wbno_value.setText(riderQueues.get(position).getWBno());
        holder.job_type.setText(riderQueues.get(position).getJobType());


        holder.job_price.setText("$ " + riderQueues.get(position).getAmount());

        try {
            String myDate = SHAInterface.getDateFormatted(riderQueues.get(position).getPickupdatetime());
            holder.job_date.setText(myDate);
            String myTime = SHAInterface.getTimeFormatted(riderQueues.get(position).getPickupdatetime());
            holder.job_time.setText(myTime);
        } catch (ParseException e) {
            Log.e("delivrApp", "Exception: " + e.getMessage());
            holder.job_date.setText(riderQueues.get(position).getPickupdatetime());
            holder.job_time.setText(riderQueues.get(position).getPickupdatetime());
            e.printStackTrace();
        }

        holder.viewdetails_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoredDatas.getInstance().setrQuePos(position);
                StoredDatas.getInstance().setRiderQueues(riderQueues);
                Intent viewIntet = new Intent(context, MyJobs_Details.class);
                frag_obj.startActivityForResult(viewIntet, RIDER_MYJOBS_to_MYJOB_DETAILS);
            }
        });

    }

    @Override
    public int getItemCount() {
        return riderQueues.size();
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
