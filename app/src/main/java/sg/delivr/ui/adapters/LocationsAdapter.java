package sg.delivr.ui.adapters;

import android.content.Context;
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

import sg.delivr.R;
import sg.delivr.backend.responsemodels.ResponseAssignedQueue;
import sg.delivr.backend.responsemodels.ResponseLocationDatas;
import sg.delivr.ui.fragments.Frag_MyJobsQueue;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.ui.models.SearchDatas;


public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.ViewHolder> implements
        Intent_Constants {

    private ArrayList<ResponseLocationDatas> searchDatas;
    private Context context;

    public LocationsAdapter(ArrayList<ResponseLocationDatas> searchDatas, Context context) {
        this.searchDatas = searchDatas;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_locatios, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.location_title.setText(searchDatas.get(position).getV());
    }

    @Override
    public int getItemCount() {
        return searchDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView location_title;

        ViewHolder(View itemView) {
            super(itemView);
            location_title = itemView.findViewById(R.id.location_title);
        }
    }
}
