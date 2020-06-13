package sg.delivr.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sg.delivr.R;
import sg.delivr.backend.RetrofitClient;
import sg.delivr.backend.RetrofitClientTwo;
import sg.delivr.backend.postmodels.PostDoRiderQueue;
import sg.delivr.backend.responsemodels.ResponseLocationDatas;
import sg.delivr.backend.responsemodels.ResponseRiderQueue;
import sg.delivr.ui.adapters.LocationsAdapter;
import sg.delivr.ui.adapters.MyJobsAdapter;
import sg.delivr.ui.adapters.SearchProduct;
import sg.delivr.ui.fragments.Frag_MyJobs;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.models.SearchDatas;
import sg.delivr.utils.CheckNetwork;
import sg.delivr.utils.Prefs;
import sg.delivr.utils.Utils;

public class SearchLocation extends AppCompatActivity implements Intent_Constants,
        View.OnClickListener {

//    AutoCompleteTextView search_view;
    EditText search_view;
    /*ArrayList<SearchDatas> searchDatas;
    ArrayList<SearchDatas> searchDatasTemp;*/
    SearchProduct searchProduct;
    private SearchDatas selectedPerson;
    String strLocid = "", strLocName = "";
    ImageView sch_back;
    LocationsAdapter locadapter;
    RecyclerView location_recycler;
    ArrayList<ResponseLocationDatas> LocationDatas;
    ArrayList<ResponseLocationDatas> LocationDatasTemp;
    ProgressDialog progressDialog;
    private Call<ArrayList<ResponseLocationDatas>> callResponseDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_loccation);

        initView();
        initiateProgress();

    }

    private void initiateProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait..");
        progressDialog.setCancelable(false);

        if (!CheckNetwork.isInternetAvailable(this))  //if connection available
        {
            AlertBox(getResources().getString(R.string.error), getResources().getString(R.string.network));
        }

        gatherLocations();
    }

    private void gatherLocations() {
        progressDialog.show();
        callResponseDatas = RetrofitClientTwo.getInstance().getApiInterface().getLocations();

        callResponseDatas.enqueue(new Callback<ArrayList<ResponseLocationDatas>>() {
            @Override
            public void onResponse(Call<ArrayList<ResponseLocationDatas>> call,
                                   final Response<ArrayList<ResponseLocationDatas>> response) {
                progressDialog.dismiss();
                if (response.body() != null) {
                    LocationDatas = response.body();
                    LocationDatasTemp.addAll(LocationDatas);
                    show_search_adapters();
//                    showAdapters();
                } else {
                    Utils.showMessageDialog(SearchLocation.this,
                            getString(R.string.dialog_title_sorry),
                            "No Data found");
                }
            }


            @Override
            public void onFailure(Call<ArrayList<ResponseLocationDatas>> call, Throwable t) {
                progressDialog.dismiss();
                t.printStackTrace();
                Prefs.setLoginVerified("failure");
                Utils.showGenericErrorDialog(SearchLocation.this);
            }
        });
    }

    private void initView() {
        location_recycler = findViewById(R.id.location_recycler);
        sch_back = findViewById(R.id.sch_back);
        sch_back.setOnClickListener(this);
        search_view = findViewById(R.id.search_view);
        /*searchDatas = new ArrayList<SearchDatas>();
        searchDatasTemp = new ArrayList<SearchDatas>();*/
        LocationDatas = new ArrayList<ResponseLocationDatas>();
        LocationDatasTemp = new ArrayList<ResponseLocationDatas>();
        gatherDatas();
    }

    private void gatherDatas() {
        /*SearchDatas item = new SearchDatas("1", "Yishun", "04-06-2020");
        SearchDatas item2 = new SearchDatas("2", "Serangoon", "04-06-2020");
        SearchDatas item3 = new SearchDatas("3", "Jurong West", "04-06-2020");
        SearchDatas item4 = new SearchDatas("4", "Clementi", "04-06-2020");
        SearchDatas item5 = new SearchDatas("5", "Tampines", "04-06-2020");
        SearchDatas item6 = new SearchDatas("6", "Punggol", "04-06-2020");
        SearchDatas item7 = new SearchDatas("7", "Woodlands", "04-06-2020");
        SearchDatas item8 = new SearchDatas("8", "Kampong Glam", "04-06-2020");
        SearchDatas item9 = new SearchDatas("9", "Jurong East", "04-06-2020");
        SearchDatas item10 = new SearchDatas("10", "Pasir Ris", "04-06-2020");
        SearchDatas item11 = new SearchDatas("11", "SengKang", "04-06-2020");
        SearchDatas item12 = new SearchDatas("12", "Tengah", "04-06-2020");
        SearchDatas item13 = new SearchDatas("13", "Choa Chu Kang", "04-06-2020");
        SearchDatas item14 = new SearchDatas("14", "Bukit Batok", "04-06-2020");
        SearchDatas item15 = new SearchDatas("15", "Ang Mo Kio", "04-06-2020");
        SearchDatas item16 = new SearchDatas("16", "Rachor", "04-06-2020");
        SearchDatas item17 = new SearchDatas("17", "China Square", "04-06-2020");
        SearchDatas item18 = new SearchDatas("18", "ChinaTown", "04-06-2020");
        SearchDatas item19 = new SearchDatas("19", "Holland Drive", "04-06-2020");
        SearchDatas item20 = new SearchDatas("20", "Kent Ridge", "04-06-2020");

        searchDatas.add(item);
        searchDatas.add(item2);
        searchDatas.add(item3);
        searchDatas.add(item4);
        searchDatas.add(item5);
        searchDatas.add(item6);
        searchDatas.add(item7);
        searchDatas.add(item8);
        searchDatas.add(item9);
        searchDatas.add(item10);
        searchDatas.add(item11);
        searchDatas.add(item12);
        searchDatas.add(item13);
        searchDatas.add(item14);
        searchDatas.add(item15);
        searchDatas.add(item16);
        searchDatas.add(item17);
        searchDatas.add(item18);
        searchDatas.add(item19);
        searchDatas.add(item20);
        searchDatasTemp.addAll(searchDatas);*/

//        show_search_adapters();
        setListeners();
    }

    private void setListeners() {
        search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                shortList(s);
            }
        });

        location_recycler.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector = new GestureDetector(getApplicationContext(),
                    new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && gestureDetector.onTouchEvent(e)) {
                    int position = rv.getChildAdapterPosition(child);
                    String strName = LocationDatas.get(position).getV();
                    Log.e("delivrApp", "Location: " + strName);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("location_value", strName);
                    setResult(LOCATION_success, resultIntent);
                    finish();
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });
    }

    private void shortList(Editable s) {
        Log.e("delivrApp" , "SValue: " + s);

        if (LocationDatasTemp != null) {
            if (LocationDatasTemp.size() > 0) {
                String value = "" + s;
                LocationDatas.clear();

                if (value != null) {
                    if (value.equals("")) {
                        Log.e("delivrApp" , "SValue: Empty: " + value);
                        LocationDatas.clear();
//                        LocationDatas.addAll(LocationDatasTemp);
                    } else {
                        Log.e("delivrApp" , "SValue: Has Datas: " + value);
                        for (int i = 0; i < LocationDatasTemp.size(); i++) {
                            if (LocationDatasTemp.get(i).getV().toLowerCase().
                                    startsWith(value.toLowerCase())) {
                                ResponseLocationDatas item = new ResponseLocationDatas();
                                item.setP(LocationDatasTemp.get(i).getP());
                                item.setD(LocationDatasTemp.get(i).getD());
                                item.setV(LocationDatasTemp.get(i).getV());
                                LocationDatas.add(item);
                            }
                        }
                    }
                } else {
                    Log.e("delivrApp" , "SValue: Null");
                }
                locadapter.notifyDataSetChanged();
            }
        }
    }

    private void show_search_adapters() {
        LocationDatas.clear();
        location_recycler.setHasFixedSize(true);
        location_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        locadapter = new LocationsAdapter(LocationDatas, getApplicationContext());
        location_recycler.setAdapter(locadapter);
    }

    /*private void show_search_adapters() {
        searchProduct = new SearchProduct(this, R.layout.act_search_loccation, R.id.lbl_name, searchDatas);
        search_view.setAdapter(searchProduct);
        setListeners();
    }

    private void setListeners() {
        search_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                selectedPerson = (SearchDatas) adapterView.getItemAtPosition(pos);
                strLocid = selectedPerson.getLoc_id();
                strLocName = selectedPerson.getLoc_name();

                Log.e("delivrApp", "LocationName: " + strLocName);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("location_value", strLocName);
                setResult(SEARCH_success, resultIntent);
                finish();
            }
        });
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sch_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void AlertBox(final String head, final String meg) {
        LayoutInflater in = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vv = in.inflate(R.layout.alertbox, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(vv);
        dialog.setCancelable(false);
        TextView content = (TextView) vv.findViewById(R.id.content);
        TextView header = (TextView) vv.findViewById(R.id.header);
        header.setText(head);
        TextView no = (TextView) vv.findViewById(R.id.no);
        TextView yes = (TextView) vv.findViewById(R.id.yes);
        LinearLayout cancel = (LinearLayout) vv.findViewById(R.id.cancel);
        cancel.setVisibility(View.GONE);
        LinearLayout ok = (LinearLayout) vv.findViewById(R.id.ok);
        content.setText(meg);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (head.equalsIgnoreCase("Error")) {
                    finish();
                } else if (head.equalsIgnoreCase("Alert")) {
                    dialog.dismiss();
                } else {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

}
