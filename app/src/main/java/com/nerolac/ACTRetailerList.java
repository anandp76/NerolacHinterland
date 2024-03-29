package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.RawData;
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

public class ACTRetailerList extends Fragment {

    public static ArrayList<Retailers> mListItem = new ArrayList<>();
    public static ListView mListView;
    ImageView mImgAddNew;
    public static RequestQueue queue;
    public static Spinner mSpinnerTehsil;
    public static Spinner mSpinnerBlock;
    public static Database database;
    public static ArrayList<String> mListTehsil = new ArrayList<String>();
    public static ArrayList<String> mListBlock = new ArrayList<String>();
    public static RetailerListAdapter retailerListAdapter;
    public static String mStrBlock[] = {"ALL BLOCKS"};
    public static Activity mActivity;
    public static TextView mTxer;
    int r = 0;
    EditText searchbox;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_retailer, container, false);
        queue = Volley.newRequestQueue(getActivity());
        database = new Database(getActivity());
        mActivity = getActivity();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mImgAddNew = (ImageView)view. findViewById(R.id.mImgAddNew);
        mSpinnerTehsil = (Spinner)view. findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)view. findViewById(R.id.mSpinnerBlock);
        mTxer = (TextView) view. findViewById(R.id.mTxer);
        searchbox = view.findViewById(R.id.mEditByName);
        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTRetailerForm.class);
                startActivity(intent);
            }
        });
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
//        mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(mSpinnerTehsil.getSelectedItem().toString().equals("ALL TEHSIL") && mSpinnerBlock.getSelectedItem().toString().equals("ALL BLOCKS")){
//                    String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
//                    String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
//                    if(mStrMBlock.equals("ALL BLOCKS")){
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
//                    }else {
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
//                    }
//                }else {
//                    String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
//                    String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
//                    if(mStrMBlock.equals("ALL BLOCKS")){
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
//                    }else {
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
//                    }
//                    //retailerListAdapter.getFilter().filter(mSpinnerTehsil.getSelectedItem().toString());
//                }
//                //retailerListAdapter.getFilter().filter(mSpinnerTehsil.getSelectedItem().toString());
//                mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
//                mListBlock.add(0,"ALL BLOCKS");
//                ArrayAdapter arrayAdapterBlock  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
//                arrayAdapterBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                mSpinnerBlock.setAdapter(arrayAdapterBlock);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        mSpinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if(mSpinnerTehsil.getSelectedItem().toString().equals("ALL TEHSIL") && mSpinnerBlock.getSelectedItem().toString().equals("BLOCK")){
//                    String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
//                    String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
//                    if(mStrMBlock.equals("ALL BLOCKS")){
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
//                    }else {
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
//                    }
//                }else {
//                    String mStrMTehsil = mSpinnerTehsil.getSelectedItem().toString();
//                    String mStrMBlock = mSpinnerBlock.getSelectedItem().toString();
//                    if(mStrMBlock.equals("ALL BLOCKS")){
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#");
//                    }else {
//                        retailerListAdapter.getFilter().filter(mStrMTehsil+"#"+mStrMBlock);
//                    }
//                    //retailerListAdapter.getFilter().filter(mSpinnerTehsil.getSelectedItem().toString());
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();
        retailerListAdapter = new RetailerListAdapter(getActivity(),mListItem);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();



        return view;
    }

    public static void myDream(){
        mListItem.clear();
        showProgress(mActivity);
        mFunGetMataData1();
        retailerListAdapter = new RetailerListAdapter(mActivity,mListItem);
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
                                mTxer.setText("Dealer ("+jsonArrayProducts.length()+")");
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                    String id = jsonObject.getString("id");
                                    String mStrRetailerName = jsonObject.getString("Retailer_name");
                                    String mStrPhoto = jsonObject.getString("photo");
                                    String mStrVillage = jsonObject.getString("village");
                                    String mStrAddress = jsonObject.getString("address1");
                                    String mStrMobile = jsonObject.getString("mobile");
                                    String mStrOwnerName = jsonObject.getString("owner");
                                    String mStrTehsil = jsonObject.getString("tehsil");
                                    String mStrBlock = jsonObject.getString("block");
                                    String last_order = jsonObject.getString("last_order");
                                    Retailers retailers = new Retailers();
                                    retailers.setTbId(id);
                                    retailers.setTbFirstName(mStrOwnerName);
                                    retailers.setTbShopName(mStrRetailerName);
                                    retailers.setTbAddress1(mStrAddress);
                                    retailers.setTbVillage(mStrVillage);
                                    retailers.setTbImgOne(mStrPhoto);
                                    retailers.setTbMobile(mStrMobile);
                                    retailers.setTbTehsil(mStrTehsil);
                                    retailers.setTbBlock(mStrBlock);
                                    retailers.setlast_order(last_order);
                                    mListItem.add(retailers);

                                }
                                retailerListAdapter = new RetailerListAdapter(mActivity,mListItem);
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
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }


}

