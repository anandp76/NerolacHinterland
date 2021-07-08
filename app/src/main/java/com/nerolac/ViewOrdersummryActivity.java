package com.nerolac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.AddToCartAdapter;
import com.nerolac.Adpter.ViewAddToCartAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.AddcartModal;
import com.nerolac.Modal.RawData;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;

public class ViewOrdersummryActivity extends Activity {
    Spinner mSpinnerScheme;
    TextView mSpinnerDistributor;
    Spinner mSpinnerBlock;
    public RecyclerView mListView;
    public static ViewOrdersummryActivity instance;
    // String mStrDistributoreee[] = {"JK Distributor","Gandhi Distributor","MP Distributor"};
    String mStrScheme[] = {"6%","7%","8%","9%","10%"};
    String mStrDistributor[] = {"0%","0.5%","1%","1.5%","2%","2.5%","3%","3.5%","4%","4.5%","5%"};

    public  Activity mActivity;
    public  ArrayList<RawData> mStrDistributoreee = new ArrayList<RawData>();
    ArrayList<String> ardisstrin = new ArrayList<>();
    public  ArrayList<AddcartModal> mListItem = new ArrayList<AddcartModal>();
    public RequestQueue queue;
    public ArrayAdapter arrayDistributorName ;
    Database database;
    ViewAddToCartAdapter retailerListAdapter;
    String order_id;

    TextView retailernametx,retailerownertx;
    TextView totalamount;
    RelativeLayout addmore,placeorder;
    String selectedDistributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ordersummry);
        setTranceprent(ViewOrdersummryActivity.this, R.color.white);
        // mSpinnerScheme = (Spinner)findViewById(R.id.mSpinnerScheme);
        mSpinnerDistributor = findViewById(R.id.mSpinnerDistributor);
        mListView = findViewById(R.id.mListView);
        totalamount = findViewById(R.id.totalamount);
        retailerownertx = findViewById(R.id.mTextAddresssgh);
        retailernametx = findViewById(R.id.mretailername);
        addmore = findViewById(R.id.mLayoutAddMore);
        placeorder = findViewById(R.id.mLayoutPlaceOrder);
        instance = ViewOrdersummryActivity.this;
        Bundle bundle = getIntent().getExtras();
        order_id = bundle.getString("order_id");

        mActivity = this;
        queue = Volley.newRequestQueue(ViewOrdersummryActivity.this);

        retailerListAdapter = new ViewAddToCartAdapter(ViewOrdersummryActivity.this,mListItem);
        // retailerListAdapter.set_ShopAreaSqFt_Selection(this);

        mListView.setAdapter(retailerListAdapter);


        mFunGetMataData1();




    }
    public void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getOrderDetails",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                JSONArray jsonArrayProducts = response.getJSONArray("data");
                                //mTxer.setText("Dealer ("+jsonArrayProducts.length()+")");
                                JSONObject jsonObject = jsonArrayProducts.getJSONObject(0);
                                JSONArray jsonArrayorder = jsonObject.getJSONArray("order_details");
                                for (int j = 0; j<jsonArrayorder.length();j++) {
                                    JSONObject jsonorderObject = jsonArrayorder.getJSONObject(j);

                                    String fld_sku_id = jsonorderObject.getString("fld_sku_id");
                                    String fld_item_name = jsonorderObject.getString("fld_item_name");
                                    String fld_amount = jsonorderObject.getString("fld_amount");
                                    String fld_item_rate = jsonorderObject.getString("fld_item_rate");
                                    String fld_qty = jsonorderObject.getString("fld_qty");
                                    String fld_pack_size = jsonorderObject.getString("fld_pack_size");
                                    AddcartModal add = new AddcartModal();
                                    add.setsku(fld_sku_id);
                                    add.setamount(fld_amount);
                                    add.setprice(fld_item_rate);
                                    add.setquntity(Integer.parseInt(fld_qty));
                                    add.setdescription(fld_item_name);
                                    add.setpack(fld_pack_size);



                                    mListItem.add(add);
                                }

                                retailerListAdapter.notifyDataSetChanged();
                                mSpinnerDistributor.setText(jsonObject.getString("fld_business_name"));

                                retailernametx.setText(jsonObject.getString("fld_shop_name"));
                                retailerownertx.setText(jsonObject.getString("fld_name"));
                                totalamount.setText("Rs. "+jsonObject.getString("fld_order_amount"));


                            } else {
                                mShowAlert(response.getString("message"), mActivity);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog();
                        mShowAlert(mActivity.getString(R.string.Something),mActivity);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", PreferenceManager.getNEROTOKEN(mActivity));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id",order_id);
                return params;
            }
        };
        queue.add(strRequest);
    }
}