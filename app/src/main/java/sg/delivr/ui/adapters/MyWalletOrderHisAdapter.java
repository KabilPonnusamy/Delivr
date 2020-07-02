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
import sg.delivr.backend.responsemodels.ResponseWalletHistory;
import sg.delivr.ui.activity.OrderHistory_Details;
import sg.delivr.ui.fragments.Frag_NewOrderHistory;
import sg.delivr.ui.fragments.Frag_WalletHistory;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;


public class MyWalletOrderHisAdapter extends RecyclerView.Adapter<MyWalletOrderHisAdapter.ViewHolder> implements
        Intent_Constants  {

    private ArrayList<ResponseWalletHistory> orderHistoryList;
    private Context context;
    private Frag_WalletHistory frag_obj;

    public MyWalletOrderHisAdapter(ArrayList<ResponseWalletHistory> orderHistoryList, Context context, Frag_WalletHistory frag_obj) {
        this.orderHistoryList = orderHistoryList;
        this.context = context;
        this.frag_obj = frag_obj;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywallethistory_items, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.wallet_id.setText(orderHistoryList.get(position).getWalletDetlId());
        String currency = frag_obj.getResources().getString(R.string.txt_currency);
        holder.wallet_amount_val.setText(currency + " " +orderHistoryList.get(position).getCreditAmt());
        holder.balance_amount_item.setText(currency + " " +orderHistoryList.get(position).getBalance());
        if (orderHistoryList.get(position).getActiontype().equalsIgnoreCase("Credit")) {
            holder.status_actiontype.setText(orderHistoryList.get(position).getActiontype());
            holder.status_actiontype.setBackground(context.getResources().getDrawable(R.drawable.filled_box_green));
        } else {
            holder.status_actiontype.setText(orderHistoryList.get(position).getActiontype());
            holder.status_actiontype.setBackground(context.getResources().getDrawable(R.drawable.filled_box_orange));
        }

        try {
            String pdate = orderHistoryList.get(position).getCreditDate().replaceAll("\\s+"," ");
            Log.e("delivrApp", "Pickup Date & Time:"+ pdate + "d" +orderHistoryList.get(position).getCreditDate());
            String myDate = SHAInterface.getDateFormattedTwo(orderHistoryList.get(position).getCreditDate());
            Log.e("delivrApp", "Pickup DATE:"+ myDate);
            holder.wallet_creditdate_value.setText(myDate);

        } catch (ParseException e) {
            Log.e("delivrApp", "Exception: " + e.getMessage());
            holder.wallet_creditdate_value.setText(orderHistoryList.get(position).getCreditDate());
            e.printStackTrace();
        }

        /*holder.cardview_myjobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoredDatas.getInstance().setoHisPos(position);
                StoredDatas.getInstance().setOrderHistoryList(orderHistoryList);

                Intent viewIntet = new Intent(context, OrderHistory_Details.class);
                frag_obj.startActivityForResult(viewIntet, ORDER_HIS_toORDER_HIS_DETAILS);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return orderHistoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView wallet_id, wallet_amount_val, wallet_creditdate_value,  balance_amount_item, status_actiontype;
        Button viewdetails_btn;
        CardView cardview_mywallethistory;
        ViewHolder(View itemView) {
            super(itemView);

            wallet_id = (TextView) itemView.findViewById(R.id.walletdet_value);
            wallet_amount_val = (TextView) itemView.findViewById(R.id.wallet_amount_val);
            wallet_creditdate_value = (TextView) itemView.findViewById(R.id.wallet_creditdate_value);
            balance_amount_item = (TextView) itemView.findViewById(R.id.balance_amount_item);

            viewdetails_btn = (Button) itemView.findViewById(R.id.viewdetails_btn);
            cardview_mywallethistory = (CardView) itemView.findViewById(R.id.cardview_mywallethistory);
            status_actiontype = (TextView) itemView.findViewById(R.id.status_actiontype);

        }
    }
}
