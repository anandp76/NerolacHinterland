package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.ProductListAdapter;
import com.nerolac.Adpter.SearchDealerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.AddcartModal;
import com.nerolac.Modal.productsModal;

import org.json.JSONObject;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTProductList extends Activity {

    public static ArrayList<productsModal> mListItem = new ArrayList<>();
    public static ListView mListView;
    public static RequestQueue queue;
    public static ProductListAdapter retailerListAdapter;
    int r = 0;
    RelativeLayout mLayoutPlaceOrder,search_btn;
    Database database;
    String retailerId;
    String retailerName;
    String retailerowner;
    String retailerImage;
    String productids = "0";
    EditText searchbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        setTranceprent(ACTProductList.this, R.color.white);
        database = new Database(ACTProductList.this);
        queue = Volley.newRequestQueue(ACTProductList.this);
        mListView = (ListView)findViewById(R.id.mListView);
        searchbox = findViewById(R.id.mEditByName);
        search_btn = findViewById(R.id.search_btn);

        mLayoutPlaceOrder = (RelativeLayout) findViewById(R.id.mLayoutPlaceOrder);
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (retailerListAdapter != null) {
                    retailerListAdapter.getFilter().filter(searchbox.getText().toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (retailerListAdapter != null) {
                    retailerListAdapter.getFilter().filter(searchbox.getText().toString().trim());
                }
            }
        });

        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        retailerName = bundle.getString("retailerName");
        retailerowner = bundle.getString("retailerowner");
        retailerImage = bundle.getString("retailerImage");
        productids = bundle.getString("productids");
        mListItem = database.GT_AllProducts(productids);
        retailerListAdapter = new ProductListAdapter(ACTProductList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLayoutPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < retailerListAdapter.mListItems.size(); i++) {
                    if(retailerListAdapter.mListItems.get(i).isChecked()) {
                        productsModal productdetail = retailerListAdapter.mListItems.get(i);
                        AddcartModal addcart = new AddcartModal();
                        addcart.setproduct_id(productdetail.getproduct_id());
                        addcart.setowner(retailerowner);
                        addcart.setRetailer_photo(retailerImage);
                        addcart.setRetailer_id(retailerId);
                        addcart.setRetailer_name(retailerName);
                        addcart.setcategory(productdetail.getcategory());
                        addcart.setdescription(productdetail.getdescription());
                        addcart.setamount(productdetail.getamount());
                        addcart.setpack(productdetail.getpack());
                        addcart.setsku(productdetail.getsku());
                        addcart.setquntity(1);
                        addcart.setprice("0");
                       database.IN_RAW_RMD_ADDtocart(addcart);
                    }

                }
                Intent intent = new Intent(ACTProductList.this,ACTOrderSummry.class);
               intent.putExtra("retailerId",retailerId);
                intent.putExtra("retailerName",retailerName);
                intent.putExtra("retailerowner",retailerowner);
                intent.putExtra("retailerImage",retailerImage);

                startActivity(intent);
                finish();
            }
        });
    }


}

