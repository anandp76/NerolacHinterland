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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTRoutPlanList extends Activity {

    ArrayList<MyPlan> mListItem = new ArrayList<>();
    ArrayList<RawLocation> mListItemN = new ArrayList<>();
    ListView mListView;
    TextView mTxtVillage;
    TextView mTxtAdditional;
    RequestQueue queue;
    Spinner mSpinnerTehsil;
    Spinner mSpinnerBlock;
    Database database;
    ArrayList<String> mListTehsil = new ArrayList<String>();
    ArrayList<String> mListBlock = new ArrayList<String>();
    String mStrBlock[] = {"ALL BLOCKS"};
    MyPlanListAdapter retailerListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_my_plan);
        setTranceprent(ACTRoutPlanList.this,R.color.white);
        queue = Volley.newRequestQueue(ACTRoutPlanList.this);
        database = new Database(ACTRoutPlanList.this);
        mListView = (ListView)findViewById(R.id.mListView);
        mTxtVillage = (TextView)findViewById(R.id.mTxtVillage);
        mTxtAdditional = (TextView)findViewById(R.id.mTxtAdditional);
        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)findViewById(R.id.mSpinnerBlock);

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
                ArrayAdapter arrayAdapterBlock  = new ArrayAdapter(ACTRoutPlanList.this,android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
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
        mListItemN = database.GT_RAW_LOCATION(TBL_USER_ID, PreferenceManager.getNEROUSERID(ACTRoutPlanList.this));
        retailerListAdapter = new MyPlanListAdapter(ACTRoutPlanList.this,mListItemN);
        mListView.setAdapter(retailerListAdapter);
        mLoadMetadata();

    }
    



        void mLoadMetadata(){
        mListTehsil.clear();
        mListBlock.clear();
        mListTehsil = database.GT_RAW_LOCATION_TEHSIL(TBL_USER_ID,PreferenceManager.getNEROUSERID(ACTRoutPlanList.this));
        mListTehsil.add(0,"ALL TEHSIL");

        ArrayAdapter arrayAdapterTehsil  = new ArrayAdapter(ACTRoutPlanList.this,android.R.layout.simple_spinner_item,mListTehsil.toArray(new String[mListTehsil.size()]));
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterTehsil);

        ArrayAdapter arrayAdapterBlock  = new ArrayAdapter(ACTRoutPlanList.this,android.R.layout.simple_spinner_item,mStrBlock);
        arrayAdapterBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayAdapterBlock);


    }



  /*  public static void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"route_plan",
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
    }*/


}

