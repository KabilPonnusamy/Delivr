package sg.delivr.ui.activity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import sg.delivr.R;
import sg.delivr.ui.adapters.LocationsAdapter;
import sg.delivr.ui.adapters.MyJobsAdapter;
import sg.delivr.ui.adapters.SearchProduct;
import sg.delivr.ui.fragments.Frag_MyJobs;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.models.SearchDatas;

public class SearchLocation extends AppCompatActivity implements Intent_Constants,
        View.OnClickListener {

//    AutoCompleteTextView search_view;
    EditText search_view;
    ArrayList<SearchDatas> searchDatas;
    ArrayList<SearchDatas> searchDatasTemp;
    SearchProduct searchProduct;
    private SearchDatas selectedPerson;
    String strLocid = "", strLocName = "";
    ImageView sch_back;
    LocationsAdapter locadapter;
    RecyclerView location_recycler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_loccation);

        initView();

    }

    private void initView() {
        location_recycler = findViewById(R.id.location_recycler);
        sch_back = findViewById(R.id.sch_back);
        sch_back.setOnClickListener(this);
        search_view = findViewById(R.id.search_view);
        searchDatas = new ArrayList<SearchDatas>();
        searchDatasTemp = new ArrayList<SearchDatas>();
        gatherDatas();
    }

    private void gatherDatas() {
        SearchDatas item = new SearchDatas("1", "Yishun", "04-06-2020");
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
        searchDatasTemp.addAll(searchDatas);

        show_search_adapters();
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
                    String strName = searchDatas.get(position).getLoc_name();
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

        if (searchDatasTemp != null) {
            if (searchDatasTemp.size() > 0) {
                String value = "" + s;
                searchDatas.clear();

                if (value != null) {
                    if (value.equals("")) {
                        Log.e("delivrApp" , "SValue: Empty: " + value);
                        searchDatas.clear();
//                        searchDatas.addAll(searchDatasTemp);
                    } else {
                        Log.e("delivrApp" , "SValue: Has Datas: " + value);
                        for (int i = 0; i < searchDatasTemp.size(); i++) {
                            if (searchDatasTemp.get(i).getLoc_name().toLowerCase().
                                    startsWith(value.toLowerCase())) {
                                SearchDatas item = new SearchDatas();
                                item.setLoc_id(searchDatasTemp.get(i).getLoc_id());
                                item.setLoc_name(searchDatasTemp.get(i).getLoc_name());
                                item.setLoc_date(searchDatasTemp.get(i).getLoc_date());
                                searchDatas.add(item);
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
        searchDatas.clear();
        location_recycler.setHasFixedSize(true);
        location_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        locadapter = new LocationsAdapter(searchDatas, getApplicationContext());
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
}
