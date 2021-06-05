package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.ProductListAdapter;
import com.nerolac.Adpter.SearchDealerListAdapter;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTProductList extends Activity {

    public static ArrayList<String> mListItem = new ArrayList<>();
    public static ListView mListView;
    public static RequestQueue queue;
    public static ProductListAdapter retailerListAdapter;
    int r = 0;
    RelativeLayout mLayoutPlaceOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        setTranceprent(ACTProductList.this, R.color.white);
        queue = Volley.newRequestQueue(ACTProductList.this);
        mListView = (ListView)findViewById(R.id.mListView);
        mLayoutPlaceOrder = (RelativeLayout) findViewById(R.id.mLayoutPlaceOrder);
        mListItem.add("Hello");
        mListItem.add("Hello");
        mListItem.add("Hello");
        mListItem.add("Hello");
        mListItem.add("Hello");
        retailerListAdapter = new ProductListAdapter(ACTProductList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);

        mLayoutPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTProductList.this,ACTOrderSummry.class);
                startActivity(intent);
            }
        });
    }


}

