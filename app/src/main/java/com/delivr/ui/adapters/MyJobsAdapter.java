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

import androidx.cardview.widget.CardView;
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
        holder.job_type.setText(riderQueues.get(position).getConsignmentType());


        holder.job_price.setText("$ " + riderQueues.get(position).getAmount());
       // holder.pickupdatetime.setText(riderQueues.get(position).getPickupdatetime().trim());
        /*String deliveryaddr = riderQueues.get(position).getDlvyRoadno()+","+riderQueues.get(position).getDlvyRoadName()+","+
                riderQueues.get(position).getDlvyBuilding()+","+
                riderQueues.get(position).getDlvyZipcode();*/

        String deliveryaddr = "";
        if (riderQueues.get(position).getDlvyRoadno() != null && !riderQueues.get(position).getDlvyRoadno().isEmpty())  {
            deliveryaddr = riderQueues.get(position).getDlvyRoadno();
        }
        if (riderQueues.get(position).getDlvyRoadName() != null  && !riderQueues.get(position).getDlvyRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(position).getDlvyRoadName();
        }
        if (riderQueues.get(position).getDlvyBuilding() != null && !riderQueues.get(position).getDlvyBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(position).getDlvyBuilding();
        }
        if (riderQueues.get(position).getDlvyBuildingType() != null && !riderQueues.get(position).getDlvyBuildingType().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(position).getDlvyBuildingType();
        }
        if (riderQueues.get(position).getDlvyZipcode() != null && !riderQueues.get(position).getDlvyZipcode().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + riderQueues.get(position).getDlvyZipcode();
        }
        holder.delivery_address.setText(deliveryaddr);
        String pickupaddr = "";
        if (riderQueues.get(position).getPickupRoadno() != null && !riderQueues.get(position).getPickupRoadno().isEmpty())  {
            pickupaddr = riderQueues.get(position).getPickupRoadno();
        }
        if (riderQueues.get(position).getPickupRoadName() != null && !riderQueues.get(position).getPickupRoadName().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(position).getPickupRoadName();
        }
        if (riderQueues.get(position).getPickupBuilding() != null && !riderQueues.get(position).getPickupBuilding().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(position).getPickupBuilding();
        }
        if (riderQueues.get(position).getPickupBuildingType() != null && !riderQueues.get(position).getPickupBuildingType().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(position).getPickupBuildingType();
        }
        if (riderQueues.get(position).getPickupZipcode() != null && !riderQueues.get(position).getPickupZipcode().isEmpty())  {
            pickupaddr = pickupaddr + "," + riderQueues.get(position).getPickupZipcode();
        }
        holder.pickup_address.setText(pickupaddr);
// +riderQueues.get(position).getDlvyBuildingType()+","+
        try {
            String pdate = riderQueues.get(position).getPickupdatetime().replaceAll("\\s+"," ");
            Log.e("delivrApp", "Pickup Date & Time:"+ pdate);
            String myDate = SHAInterface.getDateFormatted(riderQueues.get(position).getPickupdatetime());
            Log.e("delivrApp", "Pickup DATE:"+ myDate);
            holder.pickupdate_value.setText(myDate);
            String myTime = SHAInterface.getTimeFormatted(riderQueues.get(position).getPickupdatetime());
            Log.e("delivrApp", "Pickup  & Time:"+ myTime);
            holder.pickuptime_value.setText(myTime);
        } catch (ParseException e) {
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
        holder.cardview_myjobs.setOnClickListener(new View.OnClickListener() {
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

        TextView job_wbno_value, job_type, job_date, job_time, job_price, pickupdatetime, delivery_address,
                pickup_address, pickupdate_value,pickuptime_value ;
        Button viewdetails_btn;
        CardView cardview_myjobs;
        ViewHolder(View itemView) {
            super(itemView);

            job_wbno_value = (TextView) itemView.findViewById(R.id.job_wbno_value);
            job_type = (TextView) itemView.findViewById(R.id.job_consignmenttype);
            job_date = (TextView) itemView.findViewById(R.id.job_date);
            job_time = (TextView) itemView.findViewById(R.id.job_time);
           // pickupdatetime = (TextView) itemView.findViewById(R.id.job_pickupdatetime_value);
            pickup_address = (TextView) itemView.findViewById(R.id.pickup_address_item);
            job_price = (TextView) itemView.findViewById(R.id.job_price);
            delivery_address = (TextView) itemView.findViewById(R.id.delivery_address_item);
            viewdetails_btn = (Button) itemView.findViewById(R.id.viewdetails_btn);
            cardview_myjobs = (CardView) itemView.findViewById(R.id.cardview_myjobs);
            pickupdate_value = (TextView) itemView.findViewById(R.id.job_pickupdate_value);
            pickuptime_value = (TextView) itemView.findViewById(R.id.job_pickuptime_value);
        }
    }
}
