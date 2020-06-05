package sg.delivr.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import sg.delivr.R;
import sg.delivr.ui.adapters.SearchProduct;
import sg.delivr.ui.interfaces.Intent_Constants;
import sg.delivr.ui.models.SearchDatas;

public class SearchLocation extends AppCompatActivity implements Intent_Constants,
        View.OnClickListener {

    AutoCompleteTextView search_view;
    ArrayList<SearchDatas> searchDatas;
    SearchProduct searchProduct;
    private SearchDatas selectedPerson;
    String strLocid = "", strLocName = "";
    ImageView sch_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search_loccation);

        initView();

    }

    private void initView() {
        sch_back = findViewById(R.id.sch_back);
        sch_back.setOnClickListener(this);
        search_view = findViewById(R.id.search_view);
        searchDatas = new ArrayList<SearchDatas>();
        gatherDatas();
    }

    private void gatherDatas() {
        SearchDatas item = new SearchDatas("1", "21  Park Street  Marina Bay MRT (CE2)  018925", "04-06-2020");
        SearchDatas item2 = new SearchDatas("2", "23  Park Street  Marina Bay MRT (TE20) (U/C - TOP:2021)  018926", "04-06-2020");
        SearchDatas item3 = new SearchDatas("3", "20A  Park Street    018927", "04-06-2020");
        SearchDatas item4 = new SearchDatas("4", "51  Marina South Drive   Public Transportation  018930", "04-06-2020");
        SearchDatas item5 = new SearchDatas("5", "5  Straits View   Commercial Building  018935", "04-06-2020");
        SearchDatas item6 = new SearchDatas("6", "7  Straits View   Commercial Building  018936", "04-06-2020");
        SearchDatas item7 = new SearchDatas("7", "9  Straits View   Commercial Building  018937", "04-06-2020");
        SearchDatas item8 = new SearchDatas("8", "11  Marina Boulevard   Commercial Building  018940", "04-06-2020");
        SearchDatas item9 = new SearchDatas("9", "71  Marina Coastal Drive  Marina South Wharves   018946", "04-06-2020");
        SearchDatas item10 = new SearchDatas("10", "33  Marina Coastal Drive  Marina South Pier MRT   018948", "04-06-2020");
        SearchDatas item11 = new SearchDatas("11", "8  Marina Gardens Drive  Marina Barrage   018951", "04-06-2020");
        SearchDatas item12 = new SearchDatas("12", "18  Marina Gardens Drive  Gardens By The Bay | Satay by the Bay   018952", "04-06-2020");
        SearchDatas item13 = new SearchDatas("13", "2  Marina Gardens Drive   Commercial Building  018954", "04-06-2020");
        SearchDatas item14 = new SearchDatas("14", "8  Bayfront Avenue  Casino | Marina Bay Sands   018955", "04-06-2020");
        SearchDatas item15 = new SearchDatas("15", "10  Bayfront Avenue  Marina Bay Sands  018956", "04-06-2020");
        SearchDatas item16 = new SearchDatas("16", "11  Bayfront Avenue  Bayfront MRT   018957", "04-06-2020");
        SearchDatas item17 = new SearchDatas("17", "2A  Bayfront Avenue   Commercial Building  018958", "04-06-2020");
        SearchDatas item18 = new SearchDatas("18", "11A  Bayfront Avenue   Commercial Building  018959", "04-06-2020");
        SearchDatas item19 = new SearchDatas("19", "8  Marina View  Asia Square Tower 1   018960", "04-06-2020");
        SearchDatas item20 = new SearchDatas("20", "12  Marina View  Asia Square Tower 2   018961", "04-06-2020");


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

        show_search_adapters();
    }

    private void show_search_adapters() {
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

                Log.e("appSample", "LocationName: " + strLocName);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("location_value", strLocName);
                setResult(SEARCH_success, resultIntent);
                finish();
            }
        });
    }

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
