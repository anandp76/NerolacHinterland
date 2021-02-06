package com.nerolac;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.AttendenceListAdapter;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.Adpter.RoutPlanListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Attendence;
import com.nerolac.Modal.Retailers;
import com.nerolac.Modal.RoutPlan;
import com.nerolac.Utils.GPSTracker;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.fragment.app.Fragment;

import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.Utils.CommonData.CheckCurrentDate;
import static com.nerolac.Utils.CommonData.getTimeformat;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTAttendenceList extends Fragment {

    public static ArrayList<Attendence> mListItem = new ArrayList<>();
    public static ListView mListView;
    public static RequestQueue queue;
    public static RelativeLayout mLayoutDayIn;
    public static RelativeLayout mLayoutDayOut;
    Handler handler;
    public static int a = 0;
    public static int b = 0;

    public static Activity mActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_attendence, container, false);
        queue = Volley.newRequestQueue(getActivity());

        mActivity = getActivity();
        mListView = (ListView)view.findViewById(R.id.mListView);
        mLayoutDayIn = (RelativeLayout)view.findViewById(R.id.mLayoutDayIn);
        mLayoutDayOut = (RelativeLayout)view.findViewById(R.id.mLayoutDayOut);


        mLayoutDayIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getActivity().startForegroundService(new Intent(getActivity(), AndroidLocationServices.class));
                if(a==0){
                    showProgress(getActivity());
                    mFunDayIn();
                }

            }
        });

        mLayoutDayOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b==0){
                    final Dialog dialog = new Dialog(getActivity());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.popup_dialoge);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    final EditText mEditExtraText = dialog.findViewById(R.id.mEditExtraText);
                    RelativeLayout mLayoutSubmit = dialog.findViewById(R.id.mLayoutSubmit);
                    RelativeLayout mLayoutCancel = dialog.findViewById(R.id.mLayoutCancel);
                    mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String mStrComment = mEditExtraText.getText().toString();
                            showProgress(getActivity());
                            mFunDayOut(mStrComment);
                            dialog.dismiss();
                        }
                    });

                    mLayoutCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                }

            }
        });
        
        

        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();



        return view;
    }

    public static void CallMe(){
        mListItem.clear();
        showProgress(mActivity);
        mFunGetMataData1();
    }
    

    public static void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/getAttendance",
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
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                    String mStrId = jsonObject.getString("fld_aid");
                                    String mStrInTime = jsonObject.getString("fld_time_in");
                                    String mStrOutTime = jsonObject.getString("fld_time_out");
                                    String mStrHours = jsonObject.getString("fld_total_hours");
                                    String mStrComment = jsonObject.getString("fld_comment");
                                    String mStrDate = jsonObject.getString("fld_created");
                                    Attendence attendence = new Attendence();
                                    attendence.setmStrId(mStrId);
                                    attendence.setmStrComment(mStrComment);
                                    attendence.setmStrHurs(mStrHours);
                                    attendence.setmStrTimeIn(mStrInTime);
                                    attendence.setmStrTimeOut(mStrOutTime);
                                    attendence.setmStrDate(mStrDate);
                                    mListItem.add(attendence);
                                    if(CheckCurrentDate(mStrDate)){
                                    a=1;
                                    mLayoutDayIn.setBackgroundColor(Color.parseColor("#B3B3B3"));
                                    if(mStrOutTime.equals("0000-00-00 00:00:00")){

                                    }else {
                                    b=1;
                                    mLayoutDayOut.setBackgroundColor(Color.parseColor("#B3B3B3"));
                                    }

                                    }

                                }
                                AttendenceListAdapter retailerListAdapter = new AttendenceListAdapter(mActivity,mListItem);
                                mListView.setAdapter(retailerListAdapter);

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


    void mFunDayIn() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/addTimeIn",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                            String mStrMsg = response.getString("message");
                            mShowAlert(mStrMsg, getActivity());
                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                      // finish();
                                        getActivity().startForegroundService(new Intent(getActivity(), AndroidLocationServices.class));
                                        mListItem.clear();
                                        showProgress(getActivity());
                                        mFunGetMataData1();
                                    }
                                }, 1000);

                            } else {
                                mShowAlert(getResources().getString(R.string.Something), getActivity());
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
                        mShowAlert(getString(R.string.Something), getActivity());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(getActivity()));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(getActivity()));
                params.put("in_time",getTimeformat());
                params.put("latitude", PreferenceManager.getNEROLAT(getActivity()));
                params.put("longitude", PreferenceManager.getNEROLONG(getActivity()));
                return params;
            }
        };
        queue.add(strRequest);
    }


    void mFunDayOut(final String mStrComment) {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/addTimeOut",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                String mStrMsg = response.getString("message");
                                mShowAlert(mStrMsg, getActivity());
                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        // finish();
                                        getActivity().stopService(new Intent(getActivity(), AndroidLocationServices.class));
                                        mListItem.clear();
                                        showProgress(getActivity());
                                        mFunGetMataData1();
                                    }
                                }, 1000);

                            } else {
                                mShowAlert(getResources().getString(R.string.Something), getActivity());
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
                        mShowAlert(getString(R.string.Something), getActivity());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(getActivity()));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(getActivity()));
                params.put("out_time",getTimeformat());
                params.put("latitude",PreferenceManager.getNEROLAT(getActivity()));
                params.put("longitude",PreferenceManager.getNEROLONG(getActivity()));
                params.put("comment",mStrComment);
                return params;
            }
        };
        queue.add(strRequest);
    }


}

