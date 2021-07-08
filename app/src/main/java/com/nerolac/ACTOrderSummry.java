package com.nerolac;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.nerolac.Adpter.AddToCartAdapter;
import com.nerolac.Adpter.ProductListAdapter;
import com.nerolac.Adpter.SearchDealerListAdapter;

import com.nerolac.DataBase.Database;
import com.nerolac.Modal.AddcartModal;
import com.nerolac.Modal.RawData;
import com.nerolac.Modal.Retailers;
import com.nerolac.Modal.productsModal;
import com.nerolac.Utils.CommonData;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTOrderSummry extends Activity {

    Spinner mSpinnerScheme;
    Spinner mSpinnerDistributor;
    Spinner mSpinnerBlock;
    public RecyclerView mListView;
    public static ACTOrderSummry instance;
   // String mStrDistributoreee[] = {"JK Distributor","Gandhi Distributor","MP Distributor"};
    String mStrScheme[] = {"6%","7%","8%","9%","10%"};
    String mStrDistributor[] = {"0%","0.5%","1%","1.5%","2%","2.5%","3%","3.5%","4%","4.5%","5%"};
    AlertDialog alertDialog;
    public  Activity mActivity;
   public  ArrayList<RawData> mStrDistributoreee = new ArrayList<RawData>();
    ArrayList<String> ardisstrin = new ArrayList<>();
    public  ArrayList<AddcartModal> mListItem = new ArrayList<AddcartModal>();
    public  RequestQueue queue;
    public   ArrayAdapter arrayDistributorName ;
     Database database;
      AddToCartAdapter retailerListAdapter;
    String retailerId;
    String retailerName;
    String retailerowner;
    String retailerImage;
    TextView retailernametx,retailerownertx;
    TextView totalamount;
    RelativeLayout addmore,placeorder,deleteorder;
    String selectedDistributor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        setTranceprent(ACTOrderSummry.this, R.color.white);
       // mSpinnerScheme = (Spinner)findViewById(R.id.mSpinnerScheme);
        mSpinnerDistributor = (Spinner)findViewById(R.id.mSpinnerDistributor);
         mListView = findViewById(R.id.mListView);
        totalamount = findViewById(R.id.totalamount);
        retailerownertx = findViewById(R.id.mTextAddresssgh);
        retailernametx = findViewById(R.id.mretailername);
        addmore = findViewById(R.id.mLayoutAddMore);
        placeorder = findViewById(R.id.mLayoutPlaceOrder);
        deleteorder = findViewById(R.id.mLayoutdelete);
        instance = ACTOrderSummry.this;
        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        retailerName = bundle.getString("retailerName");
        retailerowner = bundle.getString("retailerowner");
        retailerImage = bundle.getString("retailerImage");
        mActivity = this;
        queue = Volley.newRequestQueue(ACTOrderSummry.this);
        mFunGetMataData1();
         database = new Database(ACTOrderSummry.this);
 mListItem = database.GT_Addtocart(retailerId);
        retailerListAdapter = new AddToCartAdapter(ACTOrderSummry.this,mListItem,retailerId);
       // retailerListAdapter.set_ShopAreaSqFt_Selection(this);

        mListView.setAdapter(retailerListAdapter);


          arrayDistributorName  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ardisstrin);
        arrayDistributorName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistributor.setAdapter(arrayDistributorName);
        mSpinnerDistributor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

selectedDistributor = mStrDistributoreee.get(i).getmStrId();



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

retailernametx.setText(retailerName);
retailerownertx.setText(retailerowner);

addmore.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String pids = "";
        for (int i = 0; i < retailerListAdapter.ShopAreaSqFt_list.size(); i++) {

            AddcartModal retailers = retailerListAdapter.ShopAreaSqFt_list.get(i);
            if(i == 0){
                pids = retailers.getproduct_id();
            }
            else {
                pids = pids+","+ retailers.getproduct_id();
            }

           database.UP_RAW_RMD_ADDtocart(retailers,retailerId);

        }
        Intent intent = new Intent(ACTOrderSummry.this, ACTProductList.class);
        intent.putExtra("retailerId",retailerId);
        intent.putExtra("retailerName",retailerName);
        intent.putExtra("retailerowner",retailerowner);
        intent.putExtra("retailerImage",retailerImage);
        intent.putExtra("productids",pids);
        startActivity(intent);
        finish();

    }
});


        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               System.out.println(selectedDistributor);
               if(retailerListAdapter.ShopAreaSqFt_list.size()>0){
                   Boolean checkamount = true;
                   for (int i = 0; i < retailerListAdapter.ShopAreaSqFt_list.size(); i++) {
                     int total = retailerListAdapter.ShopAreaSqFt_list.get(i).getquntity() * Integer.parseInt(retailerListAdapter.ShopAreaSqFt_list.get(i).getprice());
                     if(total == 0){
                         checkamount = false;
                     }
                   }
                   if(checkamount){
                       if(!totalamount.getText().toString().trim().equals("Rs. 0")){
                           showProgress(ACTOrderSummry.this);
                           placeorder.setEnabled(false);
                           mpushorder();
                       }
                       else {
                           mShowAlert("Please check amount total amount is 0",mActivity);
                       }

                   }else {
                       mShowAlert("Please update amount of each item",mActivity);
                   }


               }else {
                   mShowAlert("Please add item in order",mActivity);
               }


            }
        });
        deleteorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(selectedDistributor);
                alertcofirmation();

            }
        });

    }
    public void remomevfromcart() {
        Handler handler;
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mListItem.clear();
                mListItem = database.GT_Addtocart(retailerId);
                retailerListAdapter.notifyDataSetChanged();
                //retailerListAdapter.notifyDataSetChanged();

            }
        }, 1000);

    }
    public void changeinTotal(){
        int total = 0;
        for (int i = 0; i < retailerListAdapter.ShopAreaSqFt_list.size(); i++) {

                AddcartModal retailers = retailerListAdapter.ShopAreaSqFt_list.get(i);
            int caltotal = 0;
            try {
                caltotal = retailers.getquntity() * Integer.parseInt(retailers.getprice());
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            total = total + caltotal;

        }
        totalamount.setText("Rs. "+total);

    }
    public void mFunGetMataData1() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getDistributorData",
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
                                    RawData ra = new RawData();
                                    String mStrRetailerName = jsonObject.getString("fld_did");
                                    String fld_business_name = jsonObject.getString("fld_business_name");
ra.setmStrId(mStrRetailerName);
ra.setmStrValue(fld_business_name);
                                    ardisstrin.add(fld_business_name);
//                                    Retailers retailers = new Retailers();
//                                    retailers.setTbFirstName(mStrOwnerName);
//                                    retailers.setTbShopName(mStrRetailerName);
//                                    retailers.setTbAddress1(mStrAddress);
//                                    retailers.setTbVillage(mStrVillage)
//                                    retailers.setTbImgOne(mStrPhoto);
//                                    retailers.setTbMobile(mStrMobile);
//                                    retailers.setTbTehsil(mStrTehsil);
//                                    retailers.setTbBlock(mStrBlock);

                                    mStrDistributoreee.add(ra);


                                }
                                arrayDistributorName.notifyDataSetChanged();

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
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }
    private void alertcofirmation() {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.common_popup_layout,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView title_popup = layout.findViewById(R.id.title_popup);

        TextView message_popup = layout.findViewById(R.id.message_popup);
        message_popup.setText("Are you sure you want to cancel the order?");
        TextView no_text_popup = layout.findViewById(R.id.no_text_popup);
        TextView yes_text_popup = layout.findViewById(R.id.yes_text_popup);

        yes_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                database.deleteorder(retailerId);
                finish();

            }
        });

        no_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();


            }
        });

    }
    private void alertmessage() {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.continuelayout,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView title_popup = layout.findViewById(R.id.title_popup);

        TextView message_popup = layout.findViewById(R.id.message_popup);
        message_popup.setText("Order Placed Successfully!");
        TextView no_text_popup = layout.findViewById(R.id.no_text_popup);
        TextView yes_text_popup = layout.findViewById(R.id.yes_text_popup);


        yes_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                finish();

            }
        });



    }
    public void mpushorder() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"addorder",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {

                                database.deleteorder(retailerId);
                                alertmessage();

                            } else {
                                placeorder.setEnabled(true);
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
                        placeorder.setEnabled(true);
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
                params.put("retailer_id",retailerId);
                params.put("distributor_id",selectedDistributor);
                params.put("order_date", CommonData.getTimeformatWithoutTime());
                params.put("fld_lat", PreferenceManager.getNEROLAT(ACTOrderSummry.this));
                params.put("fld_long", PreferenceManager.getNEROLONG(ACTOrderSummry.this));
                if (retailerListAdapter != null) {
                    JSONArray jsonArray = new JSONArray();

                    for (int i = 0; i < retailerListAdapter.ShopAreaSqFt_list.size(); i++) {

                            JSONObject object = new JSONObject();
                            try {
                                object.put("fld_sku_id", retailerListAdapter.ShopAreaSqFt_list.get(i).getsku());
                                object.put("fld_item_name", retailerListAdapter.ShopAreaSqFt_list.get(i).getdescription());
                                object.put("fld_qty", retailerListAdapter.ShopAreaSqFt_list.get(i).getquntity());
                                object.put("fld_item_rate", retailerListAdapter.ShopAreaSqFt_list.get(i).getprice());
                                object.put("fld_amount", retailerListAdapter.ShopAreaSqFt_list.get(i).getquntity() * Integer.parseInt(retailerListAdapter.ShopAreaSqFt_list.get(i).getprice()));
                                object.put("fld_pack_size",retailerListAdapter.ShopAreaSqFt_list.get(i).getpack());

                                jsonArray.put(object);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

//                        System.out.println("physico_chemical_analysis_value=======>"+ shopAreaSqFtAdapter.ShopAreaSqFt_list.get(i).getCheckbox_height() + System.getProperty("line.separator"));
//
//                        System.out.println("physico_chemical_analysis_value=======>"+shopAreaSqFtAdapter.ShopAreaSqFt_list.get(i).getCheckbox_width() + System.getProperty("line.separator"));

                    }
                    params.put("order_details", jsonArray.toString());

                }
                return params;
            }
        };
        queue.add(strRequest);
    }
}

