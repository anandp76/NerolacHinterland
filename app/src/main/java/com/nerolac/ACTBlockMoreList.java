package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nerolac.Adpter.ViewMoreListAdapter;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTBlockMoreList extends Activity {

    ArrayList<String> mListItem = new ArrayList<>();
    ListView mListView;
    String mStrTitle;
    //TextView mTxer;
    String mStrTehsil[] = {"Behat","Kairana","Najibabad","Thakurdwara","Dhanaura","Sardhana","Sardhana"};
    Spinner mSpinnerDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_tehsil_more);
        setTranceprent(ACTBlockMoreList.this,R.color.appblue);
        mListView = (ListView)findViewById(R.id.mListView);
        //mTxer = (TextView) findViewById(R.id.mTxer);
        mSpinnerDistrict = (Spinner) findViewById(R.id.mSpinnerDistrict);
        init();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mStrTitle = bundle.getString("name");
            mStrTehsil[0]=mStrTitle;
            //mTxer.setText(mStrTitle);
        }
        ViewMoreListAdapter retailerListAdapter = new ViewMoreListAdapter(ACTBlockMoreList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ACTBlockMoreList.this,ACTRetailerListViewMore.class);
                intent.putExtra("name",mListItem.get(i));
                startActivity(intent);
            }
        });

        ArrayAdapter arrayPaintTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrTehsil);
        arrayPaintTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistrict.setAdapter(arrayPaintTurnover);

    }
    void init(){
        mListItem.add("Bahedi");
        mListItem.add("Bhadpura");
        mListItem.add("Fareedpur");
        mListItem.add("Kyara");
        mListItem.add("Majhgawa");
        mListItem.add("Meerganj");
        mListItem.add("Nawabganj");
    }


}

