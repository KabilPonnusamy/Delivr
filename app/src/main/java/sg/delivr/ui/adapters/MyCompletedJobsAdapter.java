package sg.delivr.ui.adapters;

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

import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.responsemodels.ResponseCompletedJobs;
import sg.delivr.ui.activity.MyCompletedJobs_Details;
import sg.delivr.ui.fragments.Frag_CompletedJobs;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;

import java.text.ParseException;
import java.util.ArrayList;


public class MyCompletedJobsAdapter extends RecyclerView.Adapter<MyCompletedJobsAdapter.ViewHolder> implements
        Intent_Constants {

    private ArrayList<ResponseCompletedJobs> completedJobs;
    private Context context;
    private Frag_CompletedJobs frag_obj;

    public MyCompletedJobsAdapter(ArrayList<ResponseCompletedJobs> completedJobs, Context context,
                                  Frag_CompletedJobs frag_obj) {
        this.completedJobs = completedJobs;
        this.context = context;
        this.frag_obj = frag_obj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mycompletedjob_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.job_wbno_value.setText(completedJobs.get(position).getWBno());
        holder.job_type.setText(completedJobs.get(position).getConsignmentType());


        holder.job_price.setText("$ " + completedJobs.get(position).getAmount());
       // holder.pickupdatetime.setText(completedJobs.get(position).getPickupdatetime().trim());
        String deliveryaddr = "";
        if (completedJobs.get(position).getDlvyRoadno() != null && !completedJobs.get(position).getDlvyRoadno().isEmpty())  {
            deliveryaddr = completedJobs.get(position).getDlvyRoadno();
        }
        if (completedJobs.get(position).getDlvyRoadName() != null  && !completedJobs.get(position).getDlvyRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + completedJobs.get(position).getDlvyRoadName();
        }
        if (completedJobs.get(position).getDlvyBuilding() != null && !completedJobs.get(position).getDlvyBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + completedJobs.get(position).getDlvyBuilding();
        }
        if (completedJobs.get(position).getDlvyBuildingType() != null && !completedJobs.get(position).getDlvyBuildingType().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + completedJobs.get(position).getDlvyBuildingType();
        }
        if (completedJobs.get(position).getDlvyZipcode() != null && !completedJobs.get(position).getDlvyZipcode().isEmpty())  {
            if (!deliveryaddr.isEmpty()) {
                deliveryaddr = deliveryaddr + "," + completedJobs.get(position).getDlvyZipcode();
            } else {
                deliveryaddr = completedJobs.get(position).getDlvyZipcode();
            }
        }
        holder.delivery_address.setText(deliveryaddr);
        String pickupaddr = "";
        if (completedJobs.get(position).getPickupRoadno() != null && !completedJobs.get(position).getPickupRoadno().isEmpty())  {
            pickupaddr = completedJobs.get(position).getPickupRoadno();
        }
        if (completedJobs.get(position).getPickupRoadName() != null && !completedJobs.get(position).getPickupRoadName().isEmpty())  {
            pickupaddr = pickupaddr + "," + completedJobs.get(position).getPickupRoadName();
        }
        if (completedJobs.get(position).getPickupBuilding() != null && !completedJobs.get(position).getPickupBuilding().isEmpty())  {
            pickupaddr = pickupaddr + "," + completedJobs.get(position).getPickupBuilding();
        }
        if (completedJobs.get(position).getPickupBuildingType() != null && !completedJobs.get(position).getPickupBuildingType().isEmpty())  {
            pickupaddr = pickupaddr + "," + completedJobs.get(position).getPickupBuildingType();
        }
        if (completedJobs.get(position).getPickupZipcode() != null && !completedJobs.get(position).getPickupZipcode().isEmpty())  {
            if (!pickupaddr.isEmpty()) {
                pickupaddr = pickupaddr + "," + completedJobs.get(position).getPickupZipcode();
            } else {
                pickupaddr = completedJobs.get(position).getPickupZipcode();
            }

        }
        holder.pickup_address.setText(pickupaddr);
        try {
            String pdate = completedJobs.get(position).getPickupdatetime().replaceAll("\\s+"," ");
            Log.e("delivrApp", "Pickup Date & Time:"+ pdate);
            String myDate = SHAInterface.getDateFormatted(completedJobs.get(position).getPickupdatetime());
            Log.e("delivrApp", "Pickup DATE:"+ myDate);
            holder.pickupdate_value.setText(myDate);
            String myTime = SHAInterface.getTimeFormatted(completedJobs.get(position).getPickupdatetime());
            Log.e("delivrApp", "Pickup  & Time:"+ myTime);
            holder.pickuptime_value.setText(myTime);
        } catch (ParseException e) {
            holder.job_date.setText(completedJobs.get(position).getPickupdatetime());
            holder.job_time.setText(completedJobs.get(position).getPickupdatetime());
            e.printStackTrace();
        }

        holder.viewdetails_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StoredDatas.getInstance().setrQuePos(position);
                StoredDatas.getInstance().setCompletedJobs(completedJobs);
                Intent viewIntet = new Intent(context, MyCompletedJobs_Details.class);
                frag_obj.startActivityForResult(viewIntet, RIDER_COMPJOBS_to_COMPJOBS_DETAILS);
            }
        });

        holder.cardview_myjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredDatas.getInstance().setrQuePos(position);
                StoredDatas.getInstance().setCompletedJobs(completedJobs);
                Intent viewIntet = new Intent(context, MyCompletedJobs_Details.class);
                frag_obj.startActivityForResult(viewIntet, RIDER_COMPJOBS_to_COMPJOBS_DETAILS);
            }
        });
    }

    @Override
    public int getItemCount() {
        return completedJobs.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView job_wbno_value, job_type, job_date, job_time, job_price, pickupdatetime, delivery_address, pickup_address,
                pickupdate_value,pickuptime_value;
        Button viewdetails_btn;
        CardView cardview_myjobs;
        ViewHolder(View itemView) {
            super(itemView);

            job_wbno_value = (TextView) itemView.findViewById(R.id.job_wbno_value);
            job_type = (TextView) itemView.findViewById(R.id.job_consignmenttype);
            job_date = (TextView) itemView.findViewById(R.id.job_date);
            job_time = (TextView) itemView.findViewById(R.id.job_time);
           // pickupdatetime = (TextView) itemView.findViewById(R.id.job_pickupdatetime_value);
            job_price = (TextView) itemView.findViewById(R.id.job_price);
            delivery_address = (TextView) itemView.findViewById(R.id.delivery_address_item);
            pickup_address = (TextView) itemView.findViewById(R.id.pickup_address_item);
            viewdetails_btn = (Button) itemView.findViewById(R.id.viewdetails_btn);
            cardview_myjobs = (CardView) itemView.findViewById(R.id.cardview_myjobs);
            pickupdate_value = (TextView) itemView.findViewById(R.id.job_pickupdate_value);
            pickuptime_value = (TextView) itemView.findViewById(R.id.job_pickuptime_value);
        }
    }
}
