package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Retailers;
import com.nerolac.Utils.CommonData;
import com.nerolac.Utils.FlowLayout;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_BRANDS;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_OUTLET_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_DEL_SOURCE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_MERGE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PRODUCTS;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_BLOCK;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.getTimeformat;
import static com.nerolac.Utils.CommonData.getTimeformatCurrent;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTFormUpdate extends Activity {

    Database database;
    Spinner mSpinnerGSTNumber;
    Spinner mSpinnerBlock;
    EditText mEditGstNum;
    EditText mEditBusinessName;
    EditText mEditBillingName;
    EditText mEditOwnerName;
    EditText mEditMobileNumber;
    EditText mEditAddress1;
    EditText mEditAddress2;
    RelativeLayout mLayoutSubmit;
    String mStrGst[] = {"GST Available","Yes","No"};
   // String mStrBlock[] = {"Block","Yes","No"};
    RequestQueue queue;
    AutoCompleteTextView mEditVilaage;
    Spinner mSpinnerTehsil;
    public  Activity mActivity;
    ArrayList<String> mListTehsil = new ArrayList<String>();
    ArrayList<String> mListBlock = new ArrayList<String>();
    ArrayList<String> mListVillage = new ArrayList<String>();


    String mStrBusinessName;
    String mStrMobile;

    String mstrOwnername;

    String mStrGstAvailable;
    String mStrGstNumber;
    String mStrAddressOne;
    String mStrAddressTwo;
    String mStrbillname;
    String mStrVillages;
    String mStrBlocks;
    String mStrTehsil;
    String mStrDistrict;
    String retailerId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form_update);
        queue = Volley.newRequestQueue(ACTFormUpdate.this);
        setTranceprent(ACTFormUpdate.this,R.color.appblue);
        mSpinnerGSTNumber = (Spinner)findViewById(R.id.mSpinnerGSTNumber);
        mSpinnerBlock = (Spinner)findViewById(R.id.mSpinnerBlock);
        mLayoutSubmit = (RelativeLayout) findViewById(R.id.mLayoutSubmit);
        mEditGstNum = (EditText) findViewById(R.id.mEditGstNum);
        mEditBusinessName = (EditText) findViewById(R.id.mEditBusinessName);
        mEditBillingName = (EditText) findViewById(R.id.mEditBillingName);
        mEditOwnerName = (EditText) findViewById(R.id.mEditOwnerName);
        mEditMobileNumber = (EditText) findViewById(R.id.mEditMobileNumber);
        mEditAddress1 = (EditText) findViewById(R.id.mEditAddress1);
        mEditAddress2 = (EditText) findViewById(R.id.mEditAddress2);
        mEditVilaage = (AutoCompleteTextView) findViewById(R.id.mEditVilaage);
        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        database = new Database(ACTFormUpdate.this);
        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        mStrTehsil = bundle.getString("tehsil");
        mStrBlocks = bundle.getString("block");
        mStrVillages = bundle.getString("village");
        mStrAddressOne = bundle.getString("address1");
        mstrOwnername = bundle.getString("retailerowner");
        mStrBusinessName = bundle.getString("retailerName");
        mStrMobile = bundle.getString("mobile");
        mStrGstAvailable = bundle.getString("gst_available");
        mStrGstNumber = bundle.getString("getfld_gst_number");
        mStrAddressTwo = bundle.getString("address2");
        mEditGstNum.setText(mStrGstNumber);
        mEditMobileNumber.setText(mStrMobile);
        mEditAddress1.setText(mStrAddressOne);
        mEditBusinessName.setText(mStrBusinessName);
        mEditOwnerName.setText(mstrOwnername);
        mEditVilaage.setText(mStrVillages);
        mEditAddress2.setText(mStrAddressTwo);

        mActivity = this;
        ArrayAdapter arrayPainterEducation  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrGst);
        arrayPainterEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGSTNumber.setAdapter(arrayPainterEducation);
        if (mStrGstAvailable != null) {
            int spinnerPosition = arrayPainterEducation.getPosition(mStrGstAvailable);
            mSpinnerGSTNumber.setSelection(spinnerPosition);
        }

        mListTehsil = database.GT_RAW_LOCATION_TEHSIL(TBL_USER_ID,PreferenceManager.getNEROUSERID(ACTFormUpdate.this));
        mListTehsil.add(0,"TEHSIL");


        ArrayAdapter arrayAdapterDistrict  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListTehsil.toArray(new String[mListTehsil.size()]));
        arrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterDistrict);

        if (mStrTehsil != null) {
            int spinnerPosition = arrayAdapterDistrict.getPosition(mStrTehsil);
            mSpinnerTehsil.setSelection(spinnerPosition);
            mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mStrTehsil);
            mListBlock.add(0,"BLOCK");

        }

        mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerTehsil.getSelectedItem().toString().equals("BLOCK")){

                }else {
                    mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
                    mListBlock.add(0,"BLOCK");
                    ArrayAdapter<String> arrayAdapterTehsil  = new ArrayAdapter(ACTFormUpdate.this,android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
                    arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerBlock.setAdapter(arrayAdapterTehsil);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter arrayAdapterTehsil  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListBlock);
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayAdapterTehsil);
        if (mStrBlocks != null) {
            System.out.println(mListBlock.size());
            System.out.println(mStrBlocks);
            int spinnerPosition1 = arrayAdapterTehsil.getPosition(mStrBlocks);
            mSpinnerBlock.setSelection(spinnerPosition1);
        }
        mSpinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerBlock.getSelectedItem().toString().equals("BLOCK")){

                }else{
                    mListVillage = database.GT_RAW_LOCATION_VILLAGE(TBL_RAW_LOCATION_BLOCK,mSpinnerBlock.getSelectedItem().toString());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ACTFormUpdate.this,android.R.layout.simple_list_item_1,mListVillage.toArray(new String[mListVillage.size()]));
                    mEditVilaage.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //System.out.println(selectedDistributor);
//                if(retailerListAdapter.mListItems.size()>0){
//                    if(!totalamount.getText().equals("0")){
//                        mpushorder();
//                    }
//                    else {
//                        mShowAlert("Please select Distributor",mActivity);
//                    }
//
//                }else {
//                    mShowAlert("Please add item in order",mActivity);
//                }
                mStrBusinessName = mEditBusinessName.getText().toString();
                mStrMobile = mEditMobileNumber.getText().toString();

                mStrGstAvailable = mSpinnerGSTNumber.getSelectedItem().toString();
                mStrGstNumber = mEditGstNum.getText().toString();
                mStrbillname = mEditBusinessName.getText().toString();
                mStrAddressOne = mEditAddress1.getText().toString();
                mStrAddressTwo = mEditAddress2.getText().toString();
                mStrVillages = mEditVilaage.getText().toString();
                mStrBlocks = mSpinnerBlock.getSelectedItem().toString();
                mStrTehsil = mSpinnerTehsil.getSelectedItem().toString();
                mstrOwnername = mEditOwnerName.getText().toString();
                if(database.GT_RAW_LOCATION_DISTRICT(mStrBlocks,mStrTehsil).size()>0){
                    mStrDistrict = database.GT_RAW_LOCATION_DISTRICT(mStrBlocks,mStrTehsil).get(0);
                }
                if(mStrBusinessName.length()<=0){
                    mShowAlert("Please enter business name!",ACTFormUpdate.this);
                    return;
                }
                if(mStrbillname.length()<=0){
                    mShowAlert("Please enter billing name!",ACTFormUpdate.this);
                    return;
                }else if(mStrGstAvailable.equals("GST Available")){
                    mShowAlert("Please select gst Available or Not!",ACTFormUpdate.this);
                    return;
                }else if(mStrGstAvailable.equals("Yes") && mStrGstNumber.length()<=0){
                    mShowAlert("Please enter gst number!",ACTFormUpdate.this);
                    return;
                }else if(mStrMobile.length()<=0){
                    mShowAlert("Please enter mobile number!",ACTFormUpdate.this);
                    return;
                }else if(mStrAddressOne.length()<=0){
                    mShowAlert("Please enter address one!",ACTFormUpdate.this);
                    return;
                }else if(mStrAddressTwo.length()<=0){
                    mShowAlert("Please enter address Two!",ACTFormUpdate.this);
                    return;
                }else if(mStrTehsil.equals("TEHSIL")){
                    mShowAlert("Please select tehsil!",ACTFormUpdate.this);
                    return;
                }else if(mStrBlocks.equals("BLOCK")){
                    mShowAlert("Please select blocks!",ACTFormUpdate.this);
                    return;
                }else if(mStrVillages.length()<=0){
                    mShowAlert("Please enter village name!",ACTFormUpdate.this);
                    return;
                }else if(mStrBlocks.equals("")){
                    mShowAlert("Please select block!",ACTFormUpdate.this);
                    return;
                }
                showProgress(ACTFormUpdate.this);
                mpushorder();

            }
        });



    }

    public void mpushorder() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"updateRetailer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                mShowAlert("Update info", mActivity);
                                finish();
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
               // params.put("user_id",PreferenceManager.getNEROUSERID(mActivity));
                params.put("fld_rid",retailerId);
                params.put("business_name",mStrBusinessName);
                params.put("billing_name", mStrbillname);
                params.put("gst_available", mStrGstAvailable);
                params.put("gst_number", mStrGstNumber);
                params.put("owner_name", mstrOwnername);
                params.put("mobile", mStrMobile);
                params.put("tehsil", mStrTehsil);
                params.put("village", mStrVillages);
                params.put("address", mStrAddressOne);
                params.put("address2", mStrAddressTwo);

                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                9000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }

}

