package com.nerolac;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
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
import com.nerolac.Adpter.InboxListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.Adpter.RoutPlanListAdapter;
import com.nerolac.Adpter.SalesOrderListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Inbox;
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
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTInboxList extends Fragment {

    public static ArrayList<Inbox> mListItem = new ArrayList<>();
    public static ListView mListView;
    public static RequestQueue queue;
    public static Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_inbox, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mListView = (ListView)view.findViewById(R.id.mListView);
        mActivity = getActivity();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String mStrBody = mListItem.get(i).getmStrBody();
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.popup_box_msg);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
                TextView mTextMessage = dialog.findViewById(R.id.mTextMessage);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    mTextMessage.setText(Html.fromHtml(mStrBody, Html.FROM_HTML_MODE_COMPACT));
                } else {
                    mTextMessage.setText(Html.fromHtml(mStrBody));
                }
                RelativeLayout mLayoutCancel = dialog.findViewById(R.id.mLayoutCancel);


                mLayoutCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();



        return view;
    }

    public static void CallThere(){
        mListItem.clear();
        showProgress(mActivity);
        mFunGetMataData1();
    }
    

    public static void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/getMessages",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                            if (response.has("data")) {
                                JSONArray jsonArrayProducts = response.getJSONArray("data");
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                    String mStrUserId = jsonObject.getString("user_id");
                                    String mStrTitle = jsonObject.getString("title");
                                    String mStrBody = jsonObject.getString("body");
                                    String mStrCreated = jsonObject.getString("created");
                                    Inbox inbox = new Inbox();
                                    inbox.setmStrBody(mStrBody);
                                    inbox.setmStrCreated(mStrCreated);
                                    inbox.setmStrTitle(mStrTitle);
                                    inbox.setmStrUserId(mStrUserId);
                                    mListItem.add(inbox);
                                 }
                                InboxListAdapter retailerListAdapter = new InboxListAdapter(mActivity,mListItem);
                                mListView.setAdapter(retailerListAdapter);

                            }else {
                                mShowAlert("No data where found!",mActivity);
                            }

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
                        mShowAlert(mActivity.getString(R.string.Something), mActivity);
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
        queue.add(strRequest);
    }


}

