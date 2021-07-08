package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.OrderListAdapter;
import com.nerolac.Adpter.SearchDealerListAdapter;
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
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTAddOrderDilerList extends Activity {

    public static ArrayList<Retailers> mListItem = new ArrayList<>();
    public static ListView mListView;
    public static RequestQueue queue;
    public static SearchDealerListAdapter retailerListAdapter;
    int r = 0;
    public static Activity mActivity;
    EditText mEditByName;
    RelativeLayout search_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_diler_order);
        mActivity = this;
        setTranceprent(ACTAddOrderDilerList.this, R.color.white);
        queue = Volley.newRequestQueue(ACTAddOrderDilerList.this);
        mListView = (ListView)findViewById(R.id.mListView);
        mEditByName = findViewById(R.id.mEditByName);
        search_btn = findViewById(R.id.search_btn);
        retailerListAdapter = new SearchDealerListAdapter(ACTAddOrderDilerList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);
        mFunGetMataData1() ;



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
                                    String mid = jsonObject.getString("id");
                                    String mStrRetailerName = jsonObject.getString("Retailer_name");
                                    String mStrPhoto = jsonObject.getString("photo");
                                    String mStrVillage = jsonObject.getString("village");
                                    String mStrAddress = jsonObject.getString("address1");
                                    String mStrMobile = jsonObject.getString("mobile");
                                    String mStrOwnerName = jsonObject.getString("owner");
                                    String mStrTehsil = jsonObject.getString("tehsil");
                                    String mStrBlock = jsonObject.getString("block");
                                    String last_order = jsonObject.getString("last_order");
                                    String gst_available = jsonObject.getString("gst_available");
                                    String fld_gst_number = jsonObject.getString("fld_gst_number");
                                    String mStrAddress2 = jsonObject.getString("address2");
                                    Retailers retailers = new Retailers();
                                    retailers.setTbId(mid);
                                    retailers.setTbFirstName(mStrOwnerName);
                                    retailers.setTbShopName(mStrRetailerName);
                                    retailers.setTbAddress1(mStrAddress);
                                    retailers.setTbAddress2(mStrAddress2);
                                    retailers.setTbVillage(mStrVillage);
                                    retailers.setTbImgOne(mStrPhoto);
                                    retailers.setTbMobile(mStrMobile);
                                    retailers.setTbTehsil(mStrTehsil);
                                    retailers.setTbBlock(mStrBlock);
                                    retailers.setlast_order(last_order);
                                    retailers.setgst_available(gst_available);
                                    retailers.setfld_gst_number(fld_gst_number);
                                    mListItem.add(retailers);

                                }
                                retailerListAdapter = new SearchDealerListAdapter(mActivity,mListItem);
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

