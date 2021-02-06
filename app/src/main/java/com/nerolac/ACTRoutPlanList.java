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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.MyPlanListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.MyPlan;
import com.nerolac.Modal.RawLocation;
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
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTRoutPlanList extends Fragment {

    public static ArrayList<MyPlan> mListItem = new ArrayList<>();
    public static ArrayList<RawLocation> mListItemN = new ArrayList<>();
    public static ListView mListView;
    public static TextView mTxtVillage;
    public static TextView mTxtAdditional;
    public static RequestQueue queue;
    public static Spinner mSpinnerTehsil;
    public static Spinner mSpinnerBlock;
    public static Database database;
    public static ArrayList<String> mListTehsil = new ArrayList<String>();
    public static ArrayList<String> mListBlock = new ArrayList<String>();
    public static String mStrBlock[] = {"ALL BLOCKS"};
    public static MyPlanListAdapter retailerListAdapter;
    public static Activity mActivity;
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_my_plan, container, false);
        queue = Volley.newRequestQueue(getActivity());
        database = new Database(getActivity());
        mActivity = getActivity();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mTxtVillage = (TextView)view.findViewById(R.id.mTxtVillage);
        mTxtAdditional = (TextView)view.findViewById(R.id.mTxtAdditional);
        mSpinnerTehsil = (Spinner)view.findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)view.findViewById(R.id.mSpinnerBlock);



                mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(mSpinnerTehsil.getSelectedItem().toString().equals("ALL TEHSIL") && mSpinnerBlock.getSelectedItem().toString().equals("BLOCK")){
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

                        }
                        //retailerListAdapter.getFilter().filter(mSpinnerTehsil.getSelectedItem().toString());
                        mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
                        mListBlock.add(0,"ALL BLOCKS");
                        ArrayAdapter arrayAdapterBlock  = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
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
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        mListItemN.clear();
        //showProgress(getActivity());
        //mFunGetMataData1();
        mListItemN = database.GT_RAW_LOCATION(TBL_USER_ID, PreferenceManager.getNEROUSERID(getActivity()));
        retailerListAdapter = new MyPlanListAdapter(getActivity(),mListItemN);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();



        return view;
    }
    
    public static void callMyDit(){
        mListItemN.clear();
        //showProgress(getActivity());
        //mFunGetMataData1();
        mListItemN = database.GT_RAW_LOCATION(TBL_USER_ID, PreferenceManager.getNEROUSERID(mActivity));
        retailerListAdapter = new MyPlanListAdapter(mActivity,mListItemN);
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
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/route_plan",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                JSONObject jsonObject = response.getJSONObject("data");
                                String visit_status = jsonObject.getString("visit_status");
                                String additional_villages = jsonObject.getString("additional_villages");
                                mTxtVillage.setText("Villages Covered : "+visit_status);
                                mTxtAdditional.setText("Additional Villages : "+additional_villages);
                                JSONArray jsonArrayProducts = jsonObject.getJSONArray("pending_villages");
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonVillage = jsonArrayProducts.getJSONObject(j);
                                    String mStrState = jsonVillage.getString("state");
                                    String mStrDistrict = jsonVillage.getString("district");
                                    String mStrTehsil = jsonVillage.getString("tehsil");
                                    String mStrBlock = jsonVillage.getString("block");
                                    String mStrVisited = jsonVillage.getString("visited");
                                    String mStrVillage = jsonVillage.getString("village");
                                    MyPlan myPlan = new MyPlan();
                                    myPlan.setmStrBlock(mStrBlock);
                                    myPlan.setmStrDistrict(mStrDistrict);
                                    myPlan.setmStrState(mStrState);
                                    myPlan.setmStrTehsil(mStrTehsil);
                                    myPlan.setmStrVillage(mStrVillage);
                                    myPlan.setmStrVisited(mStrVisited);
                                    mListItem.add(myPlan);
                                }
                                //retailerListAdapter = new MyPlanListAdapter(mActivity,mListItem);
                                //mListView.setAdapter(retailerListAdapter);
                                //retailerListAdapter.notifyDataSetChanged();

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
                //params.put("user_id","18");
                return params;
            }
        };
        queue.add(strRequest);
    }


}

