package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTUpdatePassword extends Fragment {


    RequestQueue queue;
    EditText mEditPassword;
    RelativeLayout mLayoutSubmit;
    String mStrPaasowrd;

    int r = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_form_password, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mEditPassword = (EditText) view.findViewById(R.id.mEditPassword);
        mLayoutSubmit = (RelativeLayout) view. findViewById(R.id.mLayoutSubmit);


        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrPaasowrd = mEditPassword.getText().toString();
                if(mStrPaasowrd.length()<=0){
                    mShowAlert("Please enter password!",getActivity());
                    return;
                }
                showProgress(getActivity());
                mFunGetMataData1();

            }
        });
        return view;
    }






       void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/changePassword",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                            String mStrMessage = response.getString("message");
                            mShowAlert(mStrMessage, getActivity());
                            mEditPassword.setText("");
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
                        mShowAlert(getString(R.string.Something),getActivity());
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
                params.put("password",mStrPaasowrd);
                return params;
            }
        };
        queue.add(strRequest);
    }


}

