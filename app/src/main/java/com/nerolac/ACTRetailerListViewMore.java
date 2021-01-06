package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nerolac.Adpter.RetailerListAdapter;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTRetailerListViewMore extends Activity {

    ArrayList<String> mListItem = new ArrayList<>();
    ListView mListView;
    String mStrTitle;
    //TextView mTxer;
    String mStrBlock[] = {"Bahedi","Bhadpura","Fareedpur","Kyara","Majhgawa","Meerganj","Nawabganj"};
    Spinner mSpinnerDistrict;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_retailer_view_more);
        setTranceprent(ACTRetailerListViewMore.this,R.color.appblue);
        mListView = (ListView)findViewById(R.id.mListView);
        //mTxer = (TextView) findViewById(R.id.mTxer);
        mSpinnerDistrict = (Spinner) findViewById(R.id.mSpinnerDistrict);
        init();
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            mStrTitle = bundle.getString("name");
            mStrBlock[0]=mStrTitle;
            //mTxer.setText(mStrTitle);
        }
        RetailerListAdapter retailerListAdapter = new RetailerListAdapter(ACTRetailerListViewMore.this,mListItem);
        mListView.setAdapter(retailerListAdapter);

        ArrayAdapter arrayPaintTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBlock);
        arrayPaintTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistrict.setAdapter(arrayPaintTurnover);


    }
    void init(){
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
        mListItem.add("hello");
    }


}

