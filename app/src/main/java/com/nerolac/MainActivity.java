package com.nerolac;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.RawData;
import com.nerolac.Modal.RawLocation;
import com.nerolac.Modal.productsModal;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_ASSETS;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_COVERAGE_RETAILERS;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_COVERAGE_VILLAGES;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_TERRITORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_TYPE;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_INTEREST_LEVEL;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_MONTHLY_TURNOVER;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_PRODUCT_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_WILLINGNESS_TO_INVEST;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class MainActivity extends Fragment {

    RequestQueue queue;
    Database database;
    LinearLayout mLayoutFull;
    LinearLayout mLayoutDashbordMenu;
    LinearLayout mLayoutDataMsg;
    RelativeLayout mLayoutHeatMarket;
    RelativeLayout mLayoutAnajMandi;
    RelativeLayout mLayoutAshaBahu;
    RelativeLayout mLayoutAnganWadi;
    RelativeLayout mLayoutSarpanch;
    RelativeLayout mLayout2Pending;
    RelativeLayout mLayout2Attandence;
    RelativeLayout mLayout2Retailer;
    RelativeLayout mLayout2Distributor;
    RelativeLayout mLayout2SalesOrder;
    RelativeLayout mLayout2Inbox;
    RelativeLayout mLayout2Order;
    int a = 0;
    JSONArray jsonArrayProducts;
    JSONArray jsonArrayBrands;
    JSONArray jsonArrayBuisnessInYear;
    JSONArray jsonArrayOutletSales;
    JSONArray jsonArrayPaintSales;
    JSONArray jsonArrayPaintMerge;
    JSONArray jsonArrayPaintDeliverySource;
    int RWA=0,RWB=0,RWC=0,RWD=0,RWE=0,RWF=0,RWG=0;
    ImageView imgProgress;
    LocationManager manager;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dashboard_two, container, false);
        queue = Volley.newRequestQueue(getActivity());
        database = new Database(getActivity());
        manager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE );
        mLayoutFull = (LinearLayout)view. findViewById(R.id.mLayoutFull);
        mLayoutDashbordMenu = (LinearLayout)view. findViewById(R.id.mLayoutDashbordMenu);
        mLayoutHeatMarket = (RelativeLayout)view. findViewById(R.id.mLayoutHeatMarket);
        mLayoutAnajMandi = (RelativeLayout)view. findViewById(R.id.mLayoutAnajMandi);
        mLayoutAshaBahu = (RelativeLayout)view. findViewById(R.id.mLayoutAshaBahu);
        mLayoutAnganWadi = (RelativeLayout)view. findViewById(R.id.mLayoutAnganWadi);
        mLayoutSarpanch = (RelativeLayout)view. findViewById(R.id.mLayoutSarpanch);
        mLayout2Retailer = (RelativeLayout)view. findViewById(R.id.mLayout2Retailer);
        mLayout2Distributor = (RelativeLayout)view. findViewById(R.id.mLayout2Distributor);
        mLayout2SalesOrder = (RelativeLayout)view. findViewById(R.id.mLayout2SalesOrder);
        mLayout2Inbox = (RelativeLayout)view. findViewById(R.id.mLayout2Inbox);
        mLayout2Pending = (RelativeLayout)view. findViewById(R.id.mLayout2Pending);
        mLayout2Attandence = (RelativeLayout)view. findViewById(R.id.mLayout2Attandence);
        mLayout2Order = (RelativeLayout)view. findViewById(R.id.mLayout2Order);
        mLayoutDataMsg = (LinearLayout)view. findViewById(R.id.mLayoutDataMsg);
        imgProgress = (ImageView) view.findViewById(R.id.imgProgress);

        Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.rotating);
        imgProgress.startAnimation(animation1);


        mfunLoadData();
        mLayout2Retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 1;
                        ScreenNerolacHome.mIntAttendance = 0;
                        ScreenNerolacHome.mIntTravelLog = 0;
                        ScreenNerolacHome.mIntProspects = 0;
                        ScreenNerolacHome.mIntInbox = 0;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTRetailerList fragmentDiscover = new ACTRetailerList();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }
                }




            }
        });
        mLayout2Distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 0;
                        ScreenNerolacHome.mIntAttendance = 0;
                        ScreenNerolacHome.mIntTravelLog = 0;
                        ScreenNerolacHome.mIntProspects = 1;
                        ScreenNerolacHome.mIntInbox = 0;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTDistributorList fragmentDiscover = new ACTDistributorList();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }
                }


            }
        });
        mLayout2SalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 0;
                        ScreenNerolacHome.mIntAttendance = 0;
                        ScreenNerolacHome.mIntTravelLog = 1;
                        ScreenNerolacHome.mIntProspects = 0;
                        ScreenNerolacHome.mIntInbox = 0;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTTravelLog fragmentDiscover = new ACTTravelLog();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }
                }


            }
        });
        mLayout2Inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 0;
                        ScreenNerolacHome.mIntAttendance = 0;
                        ScreenNerolacHome.mIntTravelLog = 0;
                        ScreenNerolacHome.mIntProspects = 0;
                        ScreenNerolacHome.mIntInbox = 1;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTInboxList fragmentDiscover = new ACTInboxList();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }
                }


            }
        });

        mLayout2Pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 0;
                        ScreenNerolacHome.mIntAttendance = 0;
                        ScreenNerolacHome.mIntTravelLog = 0;
                        ScreenNerolacHome.mIntProspects = 0;
                        ScreenNerolacHome.mIntInbox = 0;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTPendingUpload fragmentDiscover = new ACTPendingUpload();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC11")
                                .commit();
                    }
                }


            }
        });

        mLayout2Attandence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 0;
                        ScreenNerolacHome.mIntAttendance = 1;
                        ScreenNerolacHome.mIntTravelLog = 0;
                        ScreenNerolacHome.mIntProspects = 0;
                        ScreenNerolacHome.mIntInbox = 0;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTAttendenceList fragmentDiscover = new ACTAttendenceList();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }

                }

            }
        });

        mLayout2Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if(!manager.isProviderEnabled( LocationManager.GPS_PROVIDER )){
                        mShowAlert("Please turn on Gps first!!",getActivity());
                    }else {
                        ScreenNerolacHome.mIntRetailer = 0;
                        ScreenNerolacHome.mIntAttendance = 0;
                        ScreenNerolacHome.mIntTravelLog = 0;
                        ScreenNerolacHome.mIntProspects = 0;
                        ScreenNerolacHome.mIntInbox = 0;
                        ScreenNerolacHome.mIntRoutePlan = 0;
                        ACTOrderList fragmentDiscover = new ACTOrderList();
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }

                }


            }
        });

        mLayoutHeatMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTHaatMarketList.class);
                startActivity(intent);
            }
        });
        mLayoutAnajMandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTAnajMandiList.class);
                startActivity(intent);
            }
        });
        mLayoutAshaBahu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTAshaBahuList.class);
                startActivity(intent);
            }
        });
        mLayoutAnganWadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTAnganwadiList.class);
                startActivity(intent);
            }
        });
        mLayoutSarpanch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ACTSarpanchList.class);
                startActivity(intent);
            }
        });

        return view;
    }




    void mfunLoadData(){
    if(database.GT_RAW_LOCATION(TBL_USER_ID, PreferenceManager.getNEROUSERID(getActivity())).size()<=0){
    mLayoutDataMsg.setVisibility(View.VISIBLE);
    mLayoutDashbordMenu.setVisibility(View.GONE);
    showProgress(getActivity());
    mFunGetMataData1();
    }else {
    mLayoutDataMsg.setVisibility(View.GONE);
    mLayoutDashbordMenu.setVisibility(View.VISIBLE);
    }
    }


        void mFunRawLocation() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"location",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        //hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                JSONArray jsonArray = response.getJSONArray("data");
                                for (int i = 0; i<jsonArray.length();i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String mStrState = jsonObject.getString("state");
                                String mStrDistrict = jsonObject.getString("district");
                                String mStrTehsil = jsonObject.getString("tehsil");
                                String mStrBlock = jsonObject.getString("block");
                                String mStrVillage = jsonObject.getString("village");
                                String mStrPlan = jsonObject.getString("plan");
                                RawLocation rawLocation = new RawLocation();
                                rawLocation.setmStrState(mStrState);
                                rawLocation.setmStrDistrict(mStrDistrict);
                                rawLocation.setmStrBlock(mStrBlock);
                                rawLocation.setmStrTehsil(mStrTehsil);
                                rawLocation.setmStrVillage(mStrVillage);
                                rawLocation.setmStrPlan(mStrPlan);
                                rawLocation.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                database.IN_RAW_LOCATION(rawLocation);
                                if(i==jsonArray.length()-1){
                                hidePDialog();
                                mLayoutDataMsg.setVisibility(View.GONE);
                                mLayoutDashbordMenu.setVisibility(View.VISIBLE);
                                //showProgress(getActivity());
                                //mFunGetMataData1();
                                }
                                }
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
                params.put("user_id", PreferenceManager.getNEROUSERID(getActivity()));
                System.out.println("<><><>## "+params);
                return params;
            }
        };
        queue.add(strRequest);
    }



    void mFunGetMataData1() {
        System.out.println("<><><>## Call1");
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"products_list",
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
                            jsonArrayProducts = jsonObject.getJSONArray("products");
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    JSONObject jsonObject1 = jsonArrayProducts.getJSONObject(j);


                                    String product_id = jsonObject1.getString("product_id");
                                    String category = jsonObject1.getString("category");
                                    String sku = jsonObject1.getString("sku");
                                    String description = jsonObject1.getString("description");
                                    String amount = jsonObject1.getString("amount");
                                    String pack_size = jsonObject1.getString("pack size");

                                    productsModal rawDataProduct = new productsModal();
                                    rawDataProduct.setproduct_id(product_id);
                                    rawDataProduct.setcategory(category);
                                    rawDataProduct.setsku(sku);
                                    rawDataProduct.setdescription(description);
                                    rawDataProduct.setpack(pack_size);
                                    rawDataProduct.setamount(amount);
                                    database.IN_RAW_RMD_PRODUCTS(rawDataProduct);
                                    if(j==jsonArrayProducts.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData2();
                                    }
                            }

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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData2() {
        System.out.println("<><><>## Call2");
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"retailerMetadata",
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
                                jsonArrayProducts = jsonObject.getJSONArray("products");
                                for (int j = 0; j<jsonArrayProducts.length();j++) {
                                    String mStrProduct = jsonArrayProducts.getString(j);
                                    System.out.println("<><><>##11 " + mStrProduct);
                                    RawData rawDataProduct = new RawData();
                                    rawDataProduct.setmStrValue(mStrProduct);
                                    rawDataProduct.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_PRODUCTS_raw(rawDataProduct);

                                }
                                jsonArrayBrands = jsonObject.getJSONArray("brands");
                                for (int b = 0; b<jsonArrayBrands.length();b++) {
                                    RWB = b;
                                    String mStrBrands = jsonArrayBrands.getString(b);
                                    RawData rawDataBrands = new RawData();
                                    rawDataBrands.setmStrValue(mStrBrands);
                                    rawDataBrands.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_BRANDS(rawDataBrands);
                                    if(b==jsonArrayBrands.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData3();
                                    }
                                }

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
                return params;
            }
        };
        queue.add(strRequest);
    }

    void mFunGetMataData3() {
        System.out.println("<><><>## Call3");
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"retailerMetadata",
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
                                jsonArrayBuisnessInYear = jsonObject.getJSONArray("buisness_in_year");
                                for (int c = 0; c<jsonArrayBuisnessInYear.length();c++) {
                                    RWC = c;
                                    String mStrBunsInYear = jsonArrayBuisnessInYear.getString(c);
                                    RawData rawDataBuinsInYear = new RawData();
                                    rawDataBuinsInYear.setmStrValue(mStrBunsInYear);
                                    rawDataBuinsInYear.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_BNS_IN_YEAR(rawDataBuinsInYear);
                                    if(c==jsonArrayBuisnessInYear.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData4();
                                    }
                                }

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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData4() {
        System.out.println("<><><>## Call4");
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"retailerMetadata",
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
                                jsonArrayOutletSales = jsonObject.getJSONArray("outlet_sales");
                                for (int d = 0; d<jsonArrayOutletSales.length();d++) {
                                    RWD = d;
                                    String mStrOutletSales = jsonArrayOutletSales.getString(d);
                                    RawData rawDataOutletSales = new RawData();
                                    rawDataOutletSales.setmStrValue(mStrOutletSales);
                                    rawDataOutletSales.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_OUTLET_SALES(rawDataOutletSales);
                                    if(d==jsonArrayOutletSales.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData5();
                                    }
                                }
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData5() {
        System.out.println("<><><>## Call5");
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"retailerMetadata",
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
                                jsonArrayPaintSales = jsonObject.getJSONArray("paint_sales");
                                for (int e = 0; e<jsonArrayPaintSales.length();e++) {
                                    RWE = e;
                                    String mStrPaintSales = jsonArrayPaintSales.getString(e);
                                    RawData rawDataPaintSales = new RawData();
                                    rawDataPaintSales.setmStrValue(mStrPaintSales);
                                    rawDataPaintSales.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_PAINT_SALES(rawDataPaintSales);
                                    if(e==jsonArrayPaintSales.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData6();
                                    }
                                }

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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData6() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"retailerMetadata",
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
                                jsonArrayPaintMerge = jsonObject.getJSONArray("paint_merge");
                                for (int f = 0; f<jsonArrayPaintMerge.length();f++) {
                                    RWF = f;
                                    String mStrPaintMerge = jsonArrayPaintMerge.getString(f);
                                    RawData rawDataPaintMerge = new RawData();
                                    rawDataPaintMerge.setmStrValue(mStrPaintMerge);
                                    rawDataPaintMerge.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_PAINT_MERGE(rawDataPaintMerge);
                                    if(f==jsonArrayPaintMerge.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData7();
                                    }
                                }
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData7() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"retailerMetadata",
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
                                jsonArrayPaintDeliverySource = jsonObject.getJSONArray("paint_delivery_source");
                                for (int g = 0; g<jsonArrayPaintDeliverySource.length();g++){
                                    RWG=g;
                                    String mStrPaintDeliverySource = jsonArrayPaintDeliverySource.getString(g);
                                    RawData rawDataPaintDeliverySource = new RawData();
                                    rawDataPaintDeliverySource.setmStrValue(mStrPaintDeliverySource);
                                    rawDataPaintDeliverySource.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_PAINT_DEL_SOURCE(rawDataPaintDeliverySource);
                                    if(g==jsonArrayPaintDeliverySource.length()-1){

                                        showProgress(getActivity());
                                        mFunGetMataData8();


                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }

    void mFunGetMataData8() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JABusinessCategory = jsonObject.getJSONArray("business_category");
                                for (int i = 0; i<JABusinessCategory.length();i++){
                                    String mStrData = JABusinessCategory.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_BNS_CATEGORY);
                                    if(i==JABusinessCategory.length()-1){
                                    showProgress(getActivity());
                                        mFunGetMataData9();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData9() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JABusinessTerritory = jsonObject.getJSONArray("business_territory");
                                for (int i = 0; i<JABusinessTerritory.length();i++){
                                    String mStrData = JABusinessTerritory.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_BNS_TERRITORY);
                                    if(i==JABusinessTerritory.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData10();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData10() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JAProductCategory = jsonObject.getJSONArray("product_category");
                                for (int i = 0; i<JAProductCategory.length();i++){
                                    String mStrData = JAProductCategory.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_PRODUCT_CATEGORY);
                                    if(i==JAProductCategory.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData11();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData11() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JABusinessType = jsonObject.getJSONArray("business_type");
                                for (int i = 0; i<JABusinessType.length();i++){
                                    String mStrData = JABusinessType.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_BNS_TYPE);
                                    if(i==JABusinessType.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData12();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData12() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JAYearsInBusiness = jsonObject.getJSONArray("years_in_business");
                                for (int i = 0; i<JAYearsInBusiness.length();i++){
                                    String mStrData = JAYearsInBusiness.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_BNS_IN_YEAR);
                                    if(i==JAYearsInBusiness.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData13();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData13() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JAMonthlyTurnover = jsonObject.getJSONArray("monthly_turnover");
                                for (int i = 0; i<JAMonthlyTurnover.length();i++){
                                    String mStrData = JAMonthlyTurnover.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_MONTHLY_TURNOVER);
                                    if(i==JAMonthlyTurnover.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData14();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData14() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JABusinessCoverageVillages = jsonObject.getJSONArray("business_coverage_villages");
                                for (int i = 0; i<JABusinessCoverageVillages.length();i++){
                                    String mStrData = JABusinessCoverageVillages.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_BNS_COVERAGE_VILLAGES);
                                    if(i==JABusinessCoverageVillages.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData15();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData15() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JABusinessCoverageRetailers = jsonObject.getJSONArray("business_coverage_retailers");
                                for (int i = 0; i<JABusinessCoverageRetailers.length();i++){
                                    String mStrData = JABusinessCoverageRetailers.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_BNS_COVERAGE_RETAILERS);
                                    if(i==JABusinessCoverageRetailers.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData16();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData16() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JAWillingnessToInvest = jsonObject.getJSONArray("willingness_to_invest");
                                for (int i = 0; i<JAWillingnessToInvest.length();i++){
                                    String mStrData = JAWillingnessToInvest.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_WILLINGNESS_TO_INVEST);
                                    if(i==JAWillingnessToInvest.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData17();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData17() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JAWillingnessToInvest = jsonObject.getJSONArray("assets");
                                for (int i = 0; i<JAWillingnessToInvest.length();i++){
                                    String mStrData = JAWillingnessToInvest.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_ASSETS);
                                    if(i==JAWillingnessToInvest.length()-1){
                                        showProgress(getActivity());
                                        mFunGetMataData18();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }
    void mFunGetMataData18() {
        StringRequest strRequest = new StringRequest(Request.Method.GET,BaseUrl+"distributorMetadata",
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
                                JSONArray JALnterestLevel = jsonObject.getJSONArray("interest_level");
                                for (int i = 0; i<JALnterestLevel.length();i++){
                                    String mStrData = JALnterestLevel.getString(i);
                                    RawData rawData = new RawData();
                                    rawData.setmStrValue(mStrData);
                                    rawData.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_DMD_DATA(rawData,TBL_DMD_INTEREST_LEVEL);
                                    if(i==JALnterestLevel.length()-1){
                                        showProgress(getActivity());
                                        mFunRawLocation();
                                    }
                                }
                                //mFunLoadMataData();
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
                return params;
            }
        };
        queue.add(strRequest);
    }

    void mFunLoadMataData(){
        try {
        for (int j = 0; j<jsonArrayProducts.length();j++){
            String mStrProduct = jsonArrayProducts.getString(j);
            System.out.println("<><><>##11 " + mStrProduct);
            RawData rawDataProduct = new RawData();
            rawDataProduct.setmStrValue(mStrProduct);
            rawDataProduct.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
           // database.IN_RAW_RMD_PRODUCTS(rawDataProduct);
            if(j==jsonArrayProducts.length()-1){
                for (int b = 0; b<jsonArrayBrands.length();b++){
                    RWB=b;
                    String mStrBrands = jsonArrayProducts.getString(b);
                    RawData rawDataBrands = new RawData();
                    rawDataBrands.setmStrValue(mStrBrands);
                    rawDataBrands.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                    database.IN_RAW_RMD_BRANDS(rawDataBrands);
                    if(b==jsonArrayBrands.length()-1){
                        for (int c = 0; c<jsonArrayBuisnessInYear.length();c++){
                            RWC=c;
                            String mStrBunsInYear = jsonArrayBuisnessInYear.getString(c);
                            RawData rawDataBuinsInYear = new RawData();
                            rawDataBuinsInYear.setmStrValue(mStrBunsInYear);
                            rawDataBuinsInYear.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                            database.IN_RAW_RMD_BNS_IN_YEAR(rawDataBuinsInYear);
                            if(c==jsonArrayBuisnessInYear.length()-1){
                                for (int d = 0; d<jsonArrayOutletSales.length();d++){
                                    RWD = d;
                                    String mStrOutletSales = jsonArrayOutletSales.getString(d);
                                    RawData rawDataOutletSales = new RawData();
                                    rawDataOutletSales.setmStrValue(mStrOutletSales);
                                    rawDataOutletSales.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                    database.IN_RAW_RMD_OUTLET_SALES(rawDataOutletSales);
                                    if(d==jsonArrayOutletSales.length()-1){

                                        for (int e = 0; e<jsonArrayPaintSales.length();e++){
                                            RWE=e;
                                            String mStrPaintSales = jsonArrayPaintSales.getString(e);
                                            RawData rawDataPaintSales = new RawData();
                                            rawDataPaintSales.setmStrValue(mStrPaintSales);
                                            rawDataPaintSales.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                            database.IN_RAW_RMD_PAINT_SALES(rawDataPaintSales);
                                            if(e==jsonArrayPaintSales.length()-1){
                                                for (int f = 0; f<jsonArrayPaintMerge.length();f++){
                                                    RWF=f;
                                                    String mStrPaintMerge = jsonArrayPaintMerge.getString(f);
                                                    RawData rawDataPaintMerge = new RawData();
                                                    rawDataPaintMerge.setmStrValue(mStrPaintMerge);
                                                    rawDataPaintMerge.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                                    database.IN_RAW_RMD_PAINT_MERGE(rawDataPaintMerge);
                                                    if(f==jsonArrayPaintMerge.length()-1){
                                                        for (int g = 0; g<jsonArrayPaintDeliverySource.length();g++){
                                                            RWG=g;
                                                            String mStrPaintDeliverySource = jsonArrayPaintDeliverySource.getString(g);
                                                            RawData rawDataPaintDeliverySource = new RawData();
                                                            rawDataPaintDeliverySource.setmStrValue(mStrPaintDeliverySource);
                                                            rawDataPaintDeliverySource.setmStrUserId(PreferenceManager.getNEROUSERID(getActivity()));
                                                            database.IN_RAW_RMD_PAINT_DEL_SOURCE(rawDataPaintDeliverySource);
                                                            if(g==jsonArrayPaintDeliverySource.length()-1){
                                                                hidePDialog();
                                                                mLayoutDataMsg.setVisibility(View.GONE);
                                                                mLayoutDashbordMenu.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    }

                                }
                            }

                        }
                    }
                }


            }
        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        int result0 = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CALL_PHONE);

        return result0 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0) {
                    boolean result0 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean result1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean result2 = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean result3 = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (result0 && result1 && result2 && result3){
                    }else{
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
                    }
                }
            }
            break;
        }
    }



}
