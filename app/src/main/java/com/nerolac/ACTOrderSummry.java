package com.nerolac;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.ProductListAdapter;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTOrderSummry extends Activity {

    Spinner mSpinnerScheme;
    Spinner mSpinnerDistributor;
    Spinner mSpinnerBlock;
    String mStrDistributoreee[] = {"JK Distributor","Gandhi Distributor","MP Distributor"};
    String mStrScheme[] = {"6%","7%","8%","9%","10%"};
    String mStrDistributor[] = {"0%","0.5%","1%","1.5%","2%","2.5%","3%","3.5%","4%","4.5%","5%"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        setTranceprent(ACTOrderSummry.this, R.color.white);
        mSpinnerScheme = (Spinner)findViewById(R.id.mSpinnerScheme);
        mSpinnerDistributor = (Spinner)findViewById(R.id.mSpinnerDistributor);
        mSpinnerBlock = (Spinner)findViewById(R.id.mSpinnerBlock);

        ArrayAdapter arrayScheme  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrScheme);
        arrayScheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerScheme.setAdapter(arrayScheme);

        ArrayAdapter arrayDistributor  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrDistributor);
        arrayDistributor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistributor.setAdapter(arrayDistributor);

        ArrayAdapter arrayDistributorName  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrDistributoreee);
        arrayDistributorName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayDistributorName);

    }


}

