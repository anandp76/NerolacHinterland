package com.nerolac;


import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.HandbookListAdapter;
import com.nerolac.Adpter.InboxListAdapter;
import com.nerolac.Modal.Handbook;
import com.nerolac.Modal.Inbox;
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
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTHandbookList extends Fragment {

    ArrayList<Handbook> mListItem = new ArrayList<>();
    ListView mListView;
    RequestQueue queue;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_handbook, container, false);
        queue = Volley.newRequestQueue(getActivity());
        mListView = (ListView)view.findViewById(R.id.mListView);

        mListItem.clear();
        showProgress(getActivity());
        mFunGetMataData1();



        return view;
    }


    

    void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"getHandbook",
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
                                    String mStrTitle = jsonObject.getString("title");
                                    String mStrFileUrl = jsonObject.getString("filename");
                                    String mStrFileStatus = jsonObject.getString("status");
                                    String mStrCreated = jsonObject.getString("created");
                                    Handbook inbox = new Handbook();
                                    inbox.setmStrFileUrl(mStrFileUrl);
                                    inbox.setmStrTitle(mStrTitle);
                                    mListItem.add(inbox);
                                 }
                                HandbookListAdapter retailerListAdapter = new HandbookListAdapter(getActivity(),mListItem);
                                mListView.setAdapter(retailerListAdapter);



                            } else {
                                mShowAlert(getResources().getString(R.string.Something),getActivity());
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
                params.put("Authorization", PreferenceManager.getNEROTOKEN(getActivity()));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        queue.add(strRequest);
    }


}

