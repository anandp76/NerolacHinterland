package com.nerolac;

import android.os.Handler;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import androidx.fragment.app.Fragment;


public class ACTRoutePlanTabs extends Fragment {


    public static TabHost tabHost;
    TabHost.TabSpec spec;
    Bundle instanceState;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.screen_tab_host, container, false);
        instanceState = savedInstanceState;
        tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        setupTabs();
        return view;
    }

    public void setupTabs() {
        tabHost.setup();
        Intent IntentRoutePlan = new Intent().setClass(getActivity(), ACTRoutPlanList.class);
        TabHost.TabSpec spec0 = tabHost.newTabSpec("Route plan").setIndicator(getTabIndicator(getActivity(), "Route plan","#ffffff"))
                .setContent(IntentRoutePlan);
        LocalActivityManager mLocalActivityManager = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager.dispatchCreate(instanceState);
        IntentRoutePlan.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabHost.setup(mLocalActivityManager);
        tabHost.addTab(spec0);

        Intent IntentPending = new Intent().setClass(getActivity(), ACTRoutPlanPendingList.class);
        TabHost.TabSpec spec1 = tabHost.newTabSpec("Pending").setIndicator(getTabIndicator(getActivity(), "Pending","#636363"))
                .setContent(IntentPending);
        LocalActivityManager mLocalActivityManager88 = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager88.dispatchCreate(instanceState);
        IntentPending.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabHost.setup(mLocalActivityManager88);
        tabHost.addTab(spec1);

        Intent IntentCovered = new Intent().setClass(getActivity(), ACTRoutPlanCoveredList.class);
        TabHost.TabSpec spec2 = tabHost.newTabSpec("Covered").setIndicator(getTabIndicator(getActivity(), "Covered","#636363"))
                .setContent(IntentCovered);
        LocalActivityManager mLocalActivityManager4 = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager4.dispatchCreate(instanceState);
        IntentCovered.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabHost.setup(mLocalActivityManager4);
        tabHost.addTab(spec2);

        Intent IntentAdditional = new Intent().setClass(getActivity(), ACTRoutPlanAdditionalList.class);
        TabHost.TabSpec spec3 = tabHost.newTabSpec("Additional").setIndicator(getTabIndicator(getActivity(), "Additional","#636363"))
                .setContent(IntentAdditional);
        LocalActivityManager mLocalActivityManager2 = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager2.dispatchCreate(instanceState);
        IntentAdditional.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        tabHost.setup(mLocalActivityManager2);
        tabHost.addTab(spec3);







        System.out.println("<><><><><>call setuptab" + spec);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int tab = tabHost.getCurrentTab();
                System.out.println("<><><><><>call" + tab);
                if (tab == 0) {
                    View view0 = tabHost.getTabWidget().getChildTabViewAt(0);
                    TextView tv0 = (TextView) view0.findViewById(R.id.textView);
                    tv0.setText("Route plan");
                    tv0.setTextColor(Color.parseColor("#ffffff"));



                    View view88 = tabHost.getTabWidget().getChildTabViewAt(1);
                    TextView tv88 = (TextView) view88.findViewById(R.id.textView);
                    tv88.setText("Pending");
                    tv88.setTextColor(Color.parseColor("#636363"));


                    View view52 = tabHost.getTabWidget().getChildTabViewAt(2);
                    TextView tv52 = (TextView) view52.findViewById(R.id.textView);
                    tv52.setText("Covered");
                    tv52.setTextColor(Color.parseColor("#636363"));



                    View view1 = tabHost.getTabWidget().getChildTabViewAt(3);
                    TextView tv1 = (TextView) view1.findViewById(R.id.textView);
                    tv1.setText("Additional");
                    tv1.setTextColor(Color.parseColor("#636363"));


                }else if (tab == 1) {
                    View view0 = tabHost.getTabWidget().getChildTabViewAt(0);
                    TextView tv0 = (TextView) view0.findViewById(R.id.textView);
                    tv0.setText("Route plan");
                    tv0.setTextColor(Color.parseColor("#636363"));



                    View view88 = tabHost.getTabWidget().getChildTabViewAt(1);
                    TextView tv88 = (TextView) view88.findViewById(R.id.textView);
                    tv88.setText("Pending");
                    tv88.setTextColor(Color.parseColor("#ffffff"));


                    View view52 = tabHost.getTabWidget().getChildTabViewAt(2);
                    TextView tv52 = (TextView) view52.findViewById(R.id.textView);
                    tv52.setText("Covered");
                    tv52.setTextColor(Color.parseColor("#636363"));



                    View view1 = tabHost.getTabWidget().getChildTabViewAt(3);
                    TextView tv1 = (TextView) view1.findViewById(R.id.textView);
                    tv1.setText("Additional");
                    tv1.setTextColor(Color.parseColor("#636363"));



                } else if (tab == 2) {
                    View view0 = tabHost.getTabWidget().getChildTabViewAt(0);
                    TextView tv0 = (TextView) view0.findViewById(R.id.textView);
                    tv0.setText("Route plan");
                    tv0.setTextColor(Color.parseColor("#636363"));



                    View view88 = tabHost.getTabWidget().getChildTabViewAt(1);
                    TextView tv88 = (TextView) view88.findViewById(R.id.textView);
                    tv88.setText("Pending");
                    tv88.setTextColor(Color.parseColor("#636363"));


                    View view52 = tabHost.getTabWidget().getChildTabViewAt(2);
                    TextView tv52 = (TextView) view52.findViewById(R.id.textView);
                    tv52.setText("Covered");
                    tv52.setTextColor(Color.parseColor("#ffffff"));



                    View view1 = tabHost.getTabWidget().getChildTabViewAt(3);
                    TextView tv1 = (TextView) view1.findViewById(R.id.textView);
                    tv1.setText("Additional");
                    tv1.setTextColor(Color.parseColor("#636363"));


                } else if (tab == 3) {

                    View view0 = tabHost.getTabWidget().getChildTabViewAt(0);
                    TextView tv0 = (TextView) view0.findViewById(R.id.textView);
                    tv0.setText("Route plan");
                    tv0.setTextColor(Color.parseColor("#636363"));



                    View view88 = tabHost.getTabWidget().getChildTabViewAt(1);
                    TextView tv88 = (TextView) view88.findViewById(R.id.textView);
                    tv88.setText("Pending");
                    tv88.setTextColor(Color.parseColor("#636363"));


                    View view52 = tabHost.getTabWidget().getChildTabViewAt(2);
                    TextView tv52 = (TextView) view52.findViewById(R.id.textView);
                    tv52.setText("Covered");
                    tv52.setTextColor(Color.parseColor("#636363"));



                    View view1 = tabHost.getTabWidget().getChildTabViewAt(3);
                    TextView tv1 = (TextView) view1.findViewById(R.id.textView);
                    tv1.setText("Additional");
                    tv1.setTextColor(Color.parseColor("#ffffff"));

                }
            }
        });
    }
    private View getTabIndicator(Context context, String title,String color) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_tab, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        tv.setTextColor(Color.parseColor(color));
        return view;
    }




}
