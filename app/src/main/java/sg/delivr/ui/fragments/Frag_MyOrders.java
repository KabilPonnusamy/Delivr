package sg.delivr.ui.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.Common.StoredDatas;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.postmodels.PostDoRiderQueue;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.data.model.MyJobsModel;
import sg.delivr.ui.activity.SearchLocation;
import sg.delivr.ui.adapters.MyJobsAdapter;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;


public class Frag_MyOrders extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {

    /*ProgressDialog progressDialog;
    private Call<ArrayList<ResponseRiderQueue>> callRiderQueue;
    private boolean isShowingPassword;*/

    Toolbar dash_toolbar;
    TextView toolbar_title;
    /*RecyclerView jobs_recycler;
    ArrayList<MyJobsModel> myJobsModel;
    MyJobsAdapter myjobsAdapter;
    String userId;
    ArrayList<ResponseRiderQueue> riderQueues;
    TextView label_empty_myjobs;*/
    //BottomNavigationView bottom_nav;

    TextView txt_address;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myorders, container, false);

        toolbarInit();
        initView(view);
//        initiateProgress();

        return view;
    }

    private void initView(View view) {
        txt_address = view.findViewById(R.id.txt_address);
        txt_address.setOnClickListener(this);
    }


    private void toolbarInit() {
        dash_toolbar = getActivity().findViewById(R.id.dash_toolbar);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.txt_myorders));
        dash_toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_address:
                Intent intent = new Intent(getActivity(), SearchLocation.class);
                startActivityForResult(intent, FRAG_MYORDERS_to_SEARCH_LOCATION);
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == FRAG_MYORDERS_to_SEARCH_LOCATION) {
            if(resultCode == SEARCH_success) {
                String location_value = data.getStringExtra("location_value");
                Log.e("delivrApp", "Location: " + location_value);
                txt_address.setText(location_value);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
