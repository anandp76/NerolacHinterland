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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.Adpter.RoutPlanListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.RoutPlan;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTTravelLog extends Fragment {

    public static ArrayList<RoutPlan> mListItem = new ArrayList<>();
    public static ListView mListView;
    ImageView mImgAddNew;
    public static RequestQueue queue;
    public static Activity mActitivty;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_routplan, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mActitivty = getActivity();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mImgAddNew = (ImageView)view. findViewById(R.id.mImgAddNew);
        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();

        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTRoutPlanForm.class);
                intent.putExtra("mStrRawOption","0");
                startActivity(intent);
            }
        });




        return view;
    }

    public static void mycall(){
        mListItem.clear();
        showProgress(mActitivty);
        mFunGetMataData1();
    }


    public static void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getUserTravelDetails",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                if(response.has("data")){
                                    JSONArray jsonArrayProducts = response.getJSONArray("data");
                                    for (int j = 0; j<jsonArrayProducts.length();j++) {
                                        JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                        String mStrId = jsonObject.getString("fld_utid");
                                        String mStrUserid = jsonObject.getString("fld_user_id");
                                        String mStrState = jsonObject.getString("fld_state");
                                        String mStrDistrict = jsonObject.getString("fld_district");
                                        String mStrTehsil = jsonObject.getString("fld_tehsil");
                                        String mStrBlock = jsonObject.getString("fld_block");
                                        String mStrVillage = jsonObject.getString("fld_village");
                                        String mStrKiloMeter = jsonObject.getString("fld_kms");
                                        String mStrCreated = jsonObject.getString("fld_created_at");
                                        RoutPlan routPlan = new RoutPlan();
                                        routPlan.setmStrId(mStrId);
                                        routPlan.setmStrBlock(mStrBlock);
                                        routPlan.setmStrCreated(mStrCreated);
                                        routPlan.setmStrDistrict(mStrDistrict);
                                        routPlan.setmStrKiloMeter(mStrKiloMeter);
                                        routPlan.setmStrState(mStrState);
                                        routPlan.setmStrTehsil(mStrTehsil);
                                        routPlan.setmStrUserid(mStrUserid);
                                        routPlan.setmStrVillage(mStrVillage);
                                        mListItem.add(routPlan);

                                    }
                                RoutPlanListAdapter retailerListAdapter = new RoutPlanListAdapter(mActitivty,mListItem);
                                mListView.setAdapter(retailerListAdapter);
                                }else {
                                RoutPlanListAdapter retailerListAdapter = new RoutPlanListAdapter(mActitivty,mListItem);
                                mListView.setAdapter(retailerListAdapter);
                                }
                            } else {
                                mShowAlert(mActitivty.getResources().getString(R.string.Something),mActitivty);
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
                        mShowAlert(mActitivty.getString(R.string.Something),mActitivty);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", PreferenceManager.getNEROTOKEN(mActitivty));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(mActitivty));
                return params;
            }
        };
        queue.add(strRequest);
    }


}

