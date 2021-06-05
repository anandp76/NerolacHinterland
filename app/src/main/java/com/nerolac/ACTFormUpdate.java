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
import com.nerolac.Utils.FlowLayout;
import com.nerolac.Utils.PreferenceManager;

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

public class ACTFormUpdate extends Activity {


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
    String mStrBlock[] = {"Block","Yes","No"};
    RequestQueue queue;


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

        ArrayAdapter arrayPainterEducation  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrGst);
        arrayPainterEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGSTNumber.setAdapter(arrayPainterEducation);

        ArrayAdapter arrayBlock  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBlock);
        arrayBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayBlock);



    }



}

