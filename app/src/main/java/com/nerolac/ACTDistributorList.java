package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.nerolac.Adpter.DistributorListAdapter;
import com.nerolac.Adpter.HaatMarketListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Distributor;
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
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTDistributorList extends Fragment {

    public static ArrayList<Distributor> mListItem = new ArrayList<>();
    public static ListView mListView;
    ImageView mImgAddNew;
    public static RequestQueue queue;
    public static Spinner mSpinnerTehsil;
    public static Spinner mSpinnerBlock;
    public static String mStrBlock[] = {"ALL BLOCKS"};
    public static Database database;
    public static ArrayList<String> mListTehsil = new ArrayList<String>();
    public static ArrayList<String> mListBlock = new ArrayList<String>();
    public static DistributorListAdapter retailerListAdapter;
    public static Activity mActivity;
    public static TextView mTxer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_distributor, container, false);
        queue = Volley.newRequestQueue(getActivity());
        database = new Database(getActivity());
        mActivity = getActivity();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mImgAddNew = (ImageView)view. findViewById(R.id.mImgAddNew);
        mSpinnerTehsil = (Spinner)view. findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)view. findViewById(R.id.mSpinnerBlock);
        mTxer = (TextView) view. findViewById(R.id.mTxer);

        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTDistributorForm.class);
                startActivity(intent);
            }
        });
        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();
        retailerListAdapter = new DistributorListAdapter(getActivity(),mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();
       
        

        return view;
    }

    public static void CallTyu(){
        mListItem.clear();
        showProgress(mActivity);
        mFunGetMataData1();
        retailerListAdapter = new DistributorListAdapter(mActivity,mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();
    }
    
    


        public static void mLoadMetadata(){
        mListTehsil.clear();
        mListBlock.clear();
        mListTehsil = database.GT_RAW_LOCATION_TEHSIL(TBL_USER_ID,PreferenceManager.getNEROUSERID(mActivity));
        mListTehsil.add(0,"ALL TEHSIL");

        ArrayAdapter arrayAdapterTehsil  = new ArrayAdapter(mActivity,android.R.layout.simple_spinner_item,mListTehsil.toArray(new String[mListTehsil.size()]));
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterTehsil);

        ArrayAdapter arrayAdapterBlock  = new ArrayAdapter(mActivity,android.R.layout.simple_spinner_item,mStrBlock);
        arrayAdapterBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayAdapterBlock);

            mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                    if(mSpinnerTehsil.getSelectedItem().toString().equals("ALL TEHSIL") && mSpinnerBlock.getSelectedItem().toString().equals("ALL BLOCKS")){
                        String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
                        String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
                        if(mStrMBlock.equals("ALL BLOCKS")){
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
                        }else {
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
                        }
                    }else {
                        String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
                        String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
                        if(mStrMBlock.equals("ALL BLOCKS")){
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
                        }else {
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
                        }
                        //retailerListAdapter.getFilter().filter(mSpinnerTehsil.getSelectedItem().toString());
                    }

                    mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
                    mListBlock.add(0,"ALL BLOCKS");
                    ArrayAdapter arrayAdapterBlock  = new ArrayAdapter(mActivity,android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
                    arrayAdapterBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerBlock.setAdapter(arrayAdapterBlock);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

        mSpinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if(mSpinnerTehsil.getSelectedItem().toString().equals("ALL TEHSIL") && mSpinnerBlock.getSelectedItem().toString().equals("ALL BLOCKS")){
                        String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
                        String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
                        if(mStrMBlock.equals("ALL BLOCKS")){
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
                        }else {
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
                        }
                    }else {
                        String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
                        String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
                        if(mStrMBlock.equals("ALL BLOCKS")){
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
                        }else {
                            retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
                        }
                        //retailerListAdapter.getFilter().filter(mSpinnerTehsil.getSelectedItem().toString());
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
    }



    public static void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getDistributor",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        System.out.println("<><><>## call");
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                JSONArray jsonArrayProducts = response.getJSONArray("data");
                                mTxer.setText("Distributor Prospects ("+jsonArrayProducts.length()+")");
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                    String mStrRetailerName = jsonObject.getString("Business_name");
                                    String mStrPhoto = jsonObject.getString("photo");
                                    String mStrLat = jsonObject.getString("latitude");
                                    String mStrLong = jsonObject.getString("longitude");
                                    String mStrVillage = jsonObject.getString("village");
                                    String mStrOwnerName = jsonObject.getString("owner_name");
                                    String mStrAddress = jsonObject.getString("address1");
                                    String mStrMobile = jsonObject.getString("mobile");
                                    String mStrTehsil = jsonObject.getString("tehsil");
                                    String mStrBlock = jsonObject.getString("block");

                                    Distributor distributor = new Distributor();
                                    distributor.setmStrDisName(mStrRetailerName);
                                    distributor.setmStrDisPhoto(mStrPhoto);
                                    distributor.setmStrDisLat(mStrLat);
                                    distributor.setmStrDisLong(mStrLong);
                                    distributor.setmStrDisVilage(mStrVillage);
                                    distributor.setmStrDisOwnerName(mStrOwnerName);
                                    distributor.setmStrDisAddress(mStrAddress);
                                    distributor.setmStrDisMobile(mStrMobile);
                                    distributor.setmStrDisTehsil(mStrTehsil);
                                    distributor.setmStrDisBlock(mStrBlock);
                                    mListItem.add(distributor);

                                }
                                retailerListAdapter = new DistributorListAdapter(mActivity,mListItem);
                                mListView.setAdapter(retailerListAdapter);
                                retailerListAdapter.notifyDataSetChanged();

                            } else {
                                mShowAlert(mActivity.getResources().getString(R.string.Something),mActivity);
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
                params.put("user_id",PreferenceManager.getNEROUSERID(mActivity));
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }


}

