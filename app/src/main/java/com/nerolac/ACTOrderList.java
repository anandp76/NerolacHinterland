package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.OrderListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.AddcartModal;
import com.nerolac.Modal.OderRetailerModal;
import com.nerolac.Modal.Retailers;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;



import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTOrderList extends Fragment {

    public  ArrayList<OderRetailerModal> mListItem = new ArrayList<>();
    public  ListView mListView;
    ImageView mImgAddNew;
    public  RequestQueue queue;
    public  OrderListAdapter retailerListAdapter;
    public  Activity mActivity;
    int r = 0;
    public  static  ACTOrderList instance;
    public EditText mEditByName;
    RelativeLayout search_btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_order, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mActivity = getActivity();
        instance = ACTOrderList.this;
        mListView = (ListView)view.findViewById(R.id.mListView);
        mEditByName = view.findViewById(R.id.mEditByName);
        search_btn = view.findViewById(R.id.search_btn);
        mImgAddNew = (ImageView)view. findViewById(R.id.mImgAddNew);
        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTAddOrderDilerList.class);
                startActivity(intent);

            }
        });



        mEditByName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (retailerListAdapter != null) {
                    retailerListAdapter.getFilter().filter(mEditByName.getText().toString().trim());
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
                    retailerListAdapter.getFilter().filter(mEditByName.getText().toString().trim());
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),ViewOrdersummryActivity.class);
                OderRetailerModal order = mListItem.get(i);
                intent.putExtra("order_id",order.getfld_rorder_id());
                startActivity(intent);
            }
        });

        return view;
    }
    public void removeimage(String id){
        showProgress(mActivity);
        deleteimage(id);
    }


    @Override
    public void onResume() {
        super.onResume();
        myDream();
    }

    public  void myDream(){
        mListItem.clear();
        showProgress(mActivity);
        mFunGetMataData1();
        retailerListAdapter = new OrderListAdapter(mActivity,mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();



    }



    public  void mLoadMetadata(){


    }

    public  void deleteimage(final String strid) {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"deleteorder",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                       // hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {

                                mFunGetMataData1();
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
                params.put("Authorization",PreferenceManager.getNEROTOKEN(mActivity));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("fld_rorder_id",strid);
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }

    public  void mFunGetMataData1() {
        mListItem = new ArrayList<>();
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getOrderRetailer",
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
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                    String fld_rorder_id = jsonObject.getString("fld_rorder_id");
                                    String fld_name = jsonObject.getString("fld_name");
                                  String fld_shop_name = jsonObject.getString("fld_shop_name");
                                   String mStrAddress = jsonObject.getString("fld_address1");
                                    String fld_village = jsonObject.getString("fld_village");
                                    String fld_block = jsonObject.getString("fld_block");
                                    String fld_tehsil = jsonObject.getString("fld_tehsil");
                                   String fld_img = jsonObject.getString("fld_img");
                                    String fld_mobile = jsonObject.getString("fld_mobile");
                                    String fld_order_amount = jsonObject.getString("fld_order_amount");
                                    String fld_estimate_number = jsonObject.getString("fld_estimate_number");
                                    String fld_bill_copy = "";
                                    if(jsonObject.getString("fld_bill_copy") == "null"){
                                        fld_bill_copy = jsonObject.getString("fld_bill_copy");
                                    }
                                    String fld_order_date = jsonObject.getString("fld_order_date");
                                    OderRetailerModal retailers = new OderRetailerModal();
                                    retailers.setfld_rorder_id(fld_rorder_id);
                                    retailers.setfld_name(fld_name);
                                    retailers.setfld_shop_name(fld_shop_name);
                                    retailers.setfld_address1(mStrAddress);
                                    retailers.setTbVillage(fld_village);
                                    retailers.setTbBlock(fld_block);
                                    retailers.setfld_tehsil(fld_tehsil);
                                    retailers.setfld_img(fld_img);
                                    retailers.setTbMobile(fld_mobile);
                                    retailers.setfld_bill_copy(fld_bill_copy);
                                    retailers.setfld_order_date(fld_order_date);
                                    retailers.setfld_order_amount(fld_order_amount);
                                    retailers.setfld_estimate_number(fld_estimate_number);

                                    mListItem.add(retailers);

                                }
                                retailerListAdapter = new OrderListAdapter(mActivity,mListItem);
                                mListView.setAdapter(retailerListAdapter);
                                retailerListAdapter.notifyDataSetChanged();

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
                params.put("Authorization",PreferenceManager.getNEROTOKEN(mActivity));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(mActivity));
                return params;
            }
        };
        queue.add(strRequest);
    }


}

