package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.OrderListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Retailers;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTOrderList extends Fragment {

    public static ArrayList<Retailers> mListItem = new ArrayList<>();
    public static ListView mListView;
    ImageView mImgAddNew;
    public static RequestQueue queue;
    public static OrderListAdapter retailerListAdapter;
    public static Activity mActivity;
    int r = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_order, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mActivity = getActivity();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mImgAddNew = (ImageView)view. findViewById(R.id.mImgAddNew);
        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTAddOrderDilerList.class);
                startActivity(intent);
            }
        });


        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();
        retailerListAdapter = new OrderListAdapter(getActivity(),mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();



        return view;
    }

    public static void myDream(){
        mListItem.clear();
        showProgress(mActivity);
        mFunGetMataData1();
        retailerListAdapter = new OrderListAdapter(mActivity,mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();
    }



    public static void mLoadMetadata(){


    }



    public static void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getRetailer",
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
                                    String mStrRetailerName = jsonObject.getString("Retailer_name");
                                    String mStrPhoto = jsonObject.getString("photo");
                                    String mStrVillage = jsonObject.getString("village");
                                    String mStrAddress = jsonObject.getString("address1");
                                    String mStrMobile = jsonObject.getString("mobile");
                                    String mStrOwnerName = jsonObject.getString("owner");
                                    String mStrTehsil = jsonObject.getString("tehsil");
                                    String mStrBlock = jsonObject.getString("block");
                                    Retailers retailers = new Retailers();
                                    retailers.setTbFirstName(mStrOwnerName);
                                    retailers.setTbShopName(mStrRetailerName);
                                    retailers.setTbAddress1(mStrAddress);
                                    retailers.setTbVillage(mStrVillage);
                                    retailers.setTbImgOne(mStrPhoto);
                                    retailers.setTbMobile(mStrMobile);
                                    retailers.setTbTehsil(mStrTehsil);
                                    retailers.setTbBlock(mStrBlock);
                                    mListItem.add(retailers);

                                }
                                retailerListAdapter = new OrderListAdapter(mActivity,mListItem);
                                mListView.setAdapter(retailerListAdapter);
                                retailerListAdapter.notifyDataSetChanged();

                            } else {
                                mShowAlert(mActivity.getResources().getString(R.string.Something), mActivity);
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

