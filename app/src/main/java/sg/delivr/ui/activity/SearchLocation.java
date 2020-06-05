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
