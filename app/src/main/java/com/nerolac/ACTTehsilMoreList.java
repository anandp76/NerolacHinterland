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

public class ACTTehsilMoreList extends Activity {

    ArrayList<String> mListItem = new ArrayList<>();
    ListView mListView;
    String mStrTitle;
    //TextView mTxer;
    String mStrDistrict[] = {"Ayodhya","Gorakhpur","Jhansi","Kanpur","Lucknow","Meerut","Mirzapur"};
    Spinner mSpinnerDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_tehsil_more);
        setTranceprent(ACTTehsilMoreList.this,R.color.appblue);
        mListView = (ListView)findViewById(R.id.mListView);
        //mTxer = (TextView) findViewById(R.id.mTxer);
        mSpinnerDistrict = (Spinner) findViewById(R.id.mSpinnerDistrict);
        init();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        mStrTitle = bundle.getString("name");
        mStrDistrict[0]=mStrTitle;
        //mTxer.setText(mStrTitle);
        }

        ViewMoreListAdapter retailerListAdapter = new ViewMoreListAdapter(ACTTehsilMoreList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ACTTehsilMoreList.this,ACTBlockMoreList.class);
                intent.putExtra("name",mListItem.get(i));
                startActivity(intent);
            }
        });

        ArrayAdapter arrayPaintTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrDistrict);
        arrayPaintTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistrict.setAdapter(arrayPaintTurnover);

    }
    void init(){
        mListItem.add("Behat");
        mListItem.add("Kairana");
        mListItem.add("Najibabad");
        mListItem.add("Thakurdwara");
        mListItem.add("Dhanaura");
        mListItem.add("Sardhana");
        mListItem.add("Dadri");
    }


}

