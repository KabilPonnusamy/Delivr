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

import java.text.ParseException;
import java.util.ArrayList;

import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.responsemodels.ResponseOrderHistory;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.ui.activity.MyJobs_Details;
import sg.delivr.ui.activity.OrderHistory_Details;
import sg.delivr.ui.fragments.Frag_NewOrderHistory;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;


public class MyOrderHisAdapter extends RecyclerView.Adapter<MyOrderHisAdapter.ViewHolder> implements
        Intent_Constants  {

    private ArrayList<ResponseOrderHistory> orderHistoryList;
    private Context context;
    private Frag_NewOrderHistory frag_obj;

    public MyOrderHisAdapter(ArrayList<ResponseOrderHistory> orderHistoryList, Context context, Frag_NewOrderHistory frag_obj) {
        this.orderHistoryList = orderHistoryList;
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
        holder.job_wbno_value.setText(orderHistoryList.get(position).getWBNo());
        holder.job_type.setText(orderHistoryList.get(position).getConsignmentType());
        holder.status_job_item.setText(orderHistoryList.get(position).getStatus());
        if (orderHistoryList.get(position).getStatus().equalsIgnoreCase("Completed")) {
            holder.status_job_item.setBackground(context.getResources().getDrawable(R.drawable.filled_box_green));
        } else {
            holder.status_job_item.setBackground(context.getResources().getDrawable(R.drawable.filled_box_orange));
        }

        holder.job_price.setText("$ " + orderHistoryList.get(position).getRate());
        // holder.pickupdatetime.setText(orderHistoryList.get(position).getPickupdatetime().trim());
        /*String deliveryaddr = orderHistoryList.get(position).getDRoadNO()+","+orderHistoryList.get(position).getDRoadName()+","+
                orderHistoryList.get(position).getDBuilding()+","+
                orderHistoryList.get(position).getZipcode();*/

        String deliveryaddr = "";
        if (orderHistoryList.get(position).getDRoadNO() != null && !orderHistoryList.get(position).getDRoadNO().isEmpty())  {
            deliveryaddr = orderHistoryList.get(position).getDRoadNO();
        }
        if (orderHistoryList.get(position).getDRoadName() != null  && !orderHistoryList.get(position).getDRoadName().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + orderHistoryList.get(position).getDRoadName();
        }
        if (orderHistoryList.get(position).getDBuilding() != null && !orderHistoryList.get(position).getDBuilding().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + orderHistoryList.get(position).getDBuilding();
        }
        /*
          // Todo => This one was not coming from API  By Kabil Dev
        if (orderHistoryList.get(position).getDlvyBuildingType() != null && !orderHistoryList.get(position).getDlvyBuildingType().isEmpty())  {
            deliveryaddr = deliveryaddr + "," + orderHistoryList.get(position).getDlvyBuildingType();
        }*/

        if (orderHistoryList.get(position).getZipcode() != null && !orderHistoryList.get(position).getZipcode().isEmpty())  {
            if (!deliveryaddr.isEmpty()) {
                deliveryaddr = deliveryaddr + "," + orderHistoryList.get(position).getZipcode();
            } else {
                deliveryaddr = orderHistoryList.get(position).getZipcode();
            }
        }
        holder.delivery_address.setText(deliveryaddr);
        String pickupaddr = "";
        if (orderHistoryList.get(position).getPickupUnitNo() != null && !orderHistoryList.get(position).getPickupUnitNo().isEmpty())  {
            pickupaddr = orderHistoryList.get(position).getPickupUnitNo();
        }
        if (orderHistoryList.get(position).getPickupContactName() != null && !orderHistoryList.get(position).getPickupContactName().isEmpty())  {
            pickupaddr = pickupaddr + "," + orderHistoryList.get(position).getPickupContactName();
        }
        if (orderHistoryList.get(position).getPickupCompName() != null && !orderHistoryList.get(position).getPickupCompName().isEmpty())  {
            pickupaddr = pickupaddr + "," + orderHistoryList.get(position).getPickupCompName();
        }
        if (orderHistoryList.get(position).getPickupContactNo() != null && !orderHistoryList.get(position).getPickupContactNo().isEmpty())  {
            pickupaddr = pickupaddr + "," + orderHistoryList.get(position).getPickupContactNo();
        }
        if (orderHistoryList.get(position).getPickupZipCode() != null && !orderHistoryList.get(position).getPickupZipCode().isEmpty())  {
            if (!pickupaddr.isEmpty()) {
                pickupaddr = pickupaddr + "," + orderHistoryList.get(position).getPickupZipCode();
            } else {
                pickupaddr = orderHistoryList.get(position).getPickupZipCode();
            }
        }
        holder.pickup_address.setText(pickupaddr);
// +orderHistoryList.get(position).getDlvyBuildingType()+","+
        try {
            String pdate = orderHistoryList.get(position).getPickupDateTime().replaceAll("\\s+"," ");
            Log.e("delivrApp", "Pickup Date & Time:"+ pdate);
            String myDate = SHAInterface.getDateFormattedNew(pdate);
            Log.e("delivrApp", "Pickup DATE:"+ myDate);
            holder.pickupdate_value.setText(myDate);
            String myTime = SHAInterface.getTimeFormattedNew(pdate);
            Log.e("delivrApp", "Pickup  & Time:"+ myTime);
            holder.pickuptime_value.setText(myTime);
        } catch (ParseException e) {
            Log.e("delivrApp", "Exception: " + e.getMessage());
            holder.job_date.setText(orderHistoryList.get(position).getPickupDateTime());
            holder.job_time.setText(orderHistoryList.get(position).getPickupDateTime());
            e.printStackTrace();
        }

        holder.cardview_myjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredDatas.getInstance().setoHisPos(position);
                StoredDatas.getInstance().setOrderHistoryList(orderHistoryList);

                Intent viewIntet = new Intent(context, OrderHistory_Details.class);
                frag_obj.startActivityForResult(viewIntet, ORDER_HIS_toORDER_HIS_DETAILS);
            }
        });
            }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView job_wbno_value, job_type, job_date, job_time, job_price, pickupdatetime, delivery_address,
                pickup_address, pickupdate_value,pickuptime_value , status_job_item;
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
            status_job_item = (TextView) itemView.findViewById(R.id.status_job_item);
        }
    }
}
