package sg.delivr.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import sg.delivr.R;
import sg.delivr.ui.activity.SearchLocation;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.interfaces.SHAInterface;


public class Frag_MyOrders_New extends Fragment implements View.OnClickListener, SHAInterface,
        Intent_Constants {


    Toolbar dash_toolbar;
    TextView toolbar_title;
    LinearLayout pickup_layout, delivery_layout, delivery_datas_layout;
    TextView pickup_txt, delivery_txt;
    ImageView delivery_sel_icon, pickup_sel_icon;
    int dVisible = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_myorders_new, container, false);

        toolbarInit();
        initView(view);
//        initiateProgress();

        return view;
    }

    private void initView(View view) {
        pickup_layout = view.findViewById(R.id.pickup_layout);
        delivery_layout = view.findViewById(R.id.delivery_layout);
        delivery_datas_layout = view.findViewById(R.id.delivery_datas_layout);
        delivery_txt = view.findViewById(R.id.delivery_txt);
        pickup_txt = view.findViewById(R.id.pickup_txt);
        pickup_sel_icon = view.findViewById(R.id.pickup_sel_icon);
        delivery_sel_icon = view.findViewById(R.id.delivery_sel_icon);
        delivery_datas_layout.setVisibility(View.GONE);
        pickup_txt.setOnClickListener(this);
        delivery_txt.setOnClickListener(this);
        delivery_sel_icon.setOnClickListener(this);
        pickup_sel_icon.setOnClickListener(this);
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
            case R.id.pickup_txt:
                Intent pickupIntent = new Intent(getActivity(), SearchLocation.class);
                startActivityForResult(pickupIntent, MYORDERS_to_PICKUP);
                break;

            case R.id.delivery_txt:
                Intent deliveryIntent = new Intent(getActivity(), SearchLocation.class);
                startActivityForResult(deliveryIntent, MYORDERS_to_DELIVERY);
                break;

            case R.id.delivery_sel_icon:
                if(dVisible == 0) {
                    dVisible = 1;
                    delivery_datas_layout.setVisibility(View.VISIBLE);

                } else {
                    dVisible = 0;
                    delivery_datas_layout.setVisibility(View.GONE);

                }

                break;

            case R.id.pickup_sel_icon:

                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /*if(requestCode == FRAG_MYORDERS_to_SEARCH_LOCATION) {
            if(resultCode == SEARCH_success) {
                String location_value = data.getStringExtra("location_value");
                Log.e("delivrApp", "Location: " + location_value);
                txt_address.setText(location_value);
            }
        }*/

        if(requestCode == MYORDERS_to_PICKUP) {
            if(resultCode == LOCATION_success) {
                String location_value = data.getStringExtra("location_value");
                pickup_txt.setText(location_value);
            }
        }

        if(requestCode == MYORDERS_to_DELIVERY) {
            if(resultCode == LOCATION_success) {
                String location_value = data.getStringExtra("location_value");
                delivery_txt.setText(location_value);
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
