package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

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

    public static ArrayList<String> mListItem = new ArrayList<>();
    public static ListView mListView;
    public static RequestQueue queue;
    public static SearchDealerListAdapter retailerListAdapter;
    int r = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_diler_order);
        setTranceprent(ACTAddOrderDilerList.this, R.color.white);
        queue = Volley.newRequestQueue(ACTAddOrderDilerList.this);
        mListView = (ListView)findViewById(R.id.mListView);
        mListItem.add("Hello");
        retailerListAdapter = new SearchDealerListAdapter(ACTAddOrderDilerList.this,mListItem);
        mListView.setAdapter(retailerListAdapter);
    }


}

