package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
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
import com.nerolac.Utils.GPSTracker;
import com.nerolac.Utils.ImageUtil;
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

import androidx.core.content.FileProvider;

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

public class ACTRetailerForm extends Activity {

    Spinner mSpinnerTehsil;
    Spinner mSpinnerBlock;
    Spinner mSpinnerGSTNumber;
    Spinner mSpinnerBusinessTurnover;
    Spinner mSpinnerBussinessInYears;
    Spinner mSpinnerBussinessMargin;
    Spinner mSpinnerPaintTurnover;
    Spinner mSpinnerOutletType;





    Spinner mSpinnerPainterExp1;
    Spinner mSpinnerPainterExp2;
    Spinner mSpinnerPainterExp3;
    Spinner mSpinnerPainterExp4;
    Spinner mSpinnerPainterExp5;

    Spinner mSpinnerPainterEducation1;
    Spinner mSpinnerPainterEducation2;
    Spinner mSpinnerPainterEducation3;
    Spinner mSpinnerPainterEducation4;
    Spinner mSpinnerPainterEducation5;

    RelativeLayout mLayoutSubmit;
    AutoCompleteTextView mEditVilaage;
    FlowLayout mLayoutProduct;
    FlowLayout mLayoutPaintBrands;
    FlowLayout mLayoutDelivery;
    String mStrBlock[] = {"BLOCK"};
    String mStrGst[] = {"GST Available","Yes","No"};
    String mStrOutletType[] = {"Hardware","Kirana","Paint","Pipe Shop","Plywood","Sanitary","Tiles","Other"};
    String mStrPainterExp[] = {"Experience(Yrs)","0-5","5-10","10-15","15-20"};
    String mStrPainterEducation[] = {"Education","No","10 School","12 High School","Graduate","Post Graduate"};
    Database database;

    ArrayList<String> mListBusinessTurnover = new ArrayList<String>();
    ArrayList<String> mListPaintTurnover = new ArrayList<String>();
    ArrayList<String> mListPaintMargin = new ArrayList<String>();
    ArrayList<String> mListBusinessYears = new ArrayList<String>();

    ArrayList<String> mListTehsil = new ArrayList<String>();
    ArrayList<String> mListBlock = new ArrayList<String>();
    ArrayList<String> mListVillage = new ArrayList<String>();

    ArrayList<String> mListProduct = new ArrayList<String>();
    ArrayList<String> mListPaintBrands = new ArrayList<String>();
    ArrayList<String> mListPaintDelivery = new ArrayList<String>();

    ArrayAdapter arrayAdapterTehsil;


    ArrayList<String> mListResultProduct = new ArrayList<String>();
    ArrayList<String> mListResultBrands = new ArrayList<String>();
    ArrayList<String> mListResultDelivery = new ArrayList<String>();
    EditText mEditShopName;
    EditText mEditGstNum;
    EditText mEditOwnerFirstName;
    EditText mEditOwnerLastName;
    EditText mEditPhone;
    EditText mEditWhatsapp;
    EditText mEditLandline;
    EditText mEditOutLetSize;
    EditText mEditAddress1;
    EditText mEditAddress2;
    EditText mEditRemark;
    EditText mEditPinNum;

    String mStrRemark;
    String mStrAddress2;
    String mStrAddress1;
    String mStrOutletSize;
    String mStrLandline;
    String mStrWhatsNum;
    String mStrPhone;
    String mStrOwnerLast;
    String mStrOwnerFirst;
    String mStrGstNum;
    String mStrShopName;
    String mStrPinNum;
    String mStrYearInBussiness;
    String mStrMonthlySales;
    String mStrPaintSales;
    String mStrPaintMargin;
    String mStrGstAvailble;
    String mStrTehsil;
    String mStrBlocks;
    String mStrOutLetType;
    String mStrDistrict;
    String mStrVillage;
    String mStrReProduct;
    String mStrReBrands;
    String mStrReDelivery;
    RequestQueue queue;

    ImageView mImgOne;
    ImageView mImgTwo;
    ImageView mImgThree;
    ImageView mImgFour;
    ImageView mImgFive;
    ImageView mImgSix;
    RadioGroup radioGrpPaintAvailable;





    String mStrImgOnePath;
    String mStrImgTwoPath;
    String mStrImgThreePath;
    String mStrImgFourPath;
    String mStrImgFivePath;
    String mStrImgSixPath;

    String mStrPainterName1;
    String mStrPainterName2;
    String mStrPainterName3;
    String mStrPainterName4;
    String mStrPainterName5;

    String mStrPainterPhone1;
    String mStrPainterPhone2;
    String mStrPainterPhone3;
    String mStrPainterPhone4;
    String mStrPainterPhone5;

    String mStrPainterExp1;
    String mStrPainterExp2;
    String mStrPainterExp3;
    String mStrPainterExp4;
    String mStrPainterExp5;

    String mStrPainterEdu1;
    String mStrPainterEdu2;
    String mStrPainterEdu3;
    String mStrPainterEdu4;
    String mStrPainterEdu5;
    String mStrPaintAvaible;



    String currenTime;
    Calendar c;
    String path;
    Handler handler;

    String mStrSourceName1;
    String mStrSourceName2;
    String mStrSourceName3;
    String mStrSourceName4;


    String mStrSourceLocation1;
    String mStrSourceLocation2;
    String mStrSourceLocation3;
    String mStrSourceLocation4;


    String mStrSourceContact1;
    String mStrSourceContact2;
    String mStrSourceContact3;
    String mStrSourceContact4;




    EditText mEditSourceLocation4;
    EditText mEditSourceContact4;
    EditText mEditSourceName4;

    EditText mEditSourceLocation3;
    EditText mEditSourceContact3;
    EditText mEditSourceName3;

    EditText mEditSourceLocation2;
    EditText mEditSourceContact2;
    EditText mEditSourceName2;

    EditText mEditSourceLocation1;
    EditText mEditSourceContact1;
    EditText mEditSourceName1;



    Spinner mSpinnerSourceType4;
    Spinner mSpinnerSourceType3;
    Spinner mSpinnerSourceType2;
    Spinner mSpinnerSourceType1;

    String mStrSourceType5;
    String mStrSourceType4;
    String mStrSourceType2;
    String mStrSourceType3;
    String mStrSourceType1;

    EditText mEditPainter1,mEditPainter2,mEditPainter3,mEditPainter4,mEditPainter5;
    EditText mEditPainterNumber1,mEditPainterNumber2,mEditPainterNumber3,mEditPainterNumber4,mEditPainterNumber5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form);
        queue = Volley.newRequestQueue(ACTRetailerForm.this);
        setTranceprent(ACTRetailerForm.this,R.color.appblue);

        path = getExternalFilesDir(null).getAbsolutePath();
        System.out.println("<><><>### "+path);
        c = Calendar.getInstance();
        currenTime = c.get(Calendar.YEAR) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.HOUR_OF_DAY) + "." + c.get(Calendar.MINUTE);
        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)findViewById(R.id.mSpinnerBlock);
        mSpinnerGSTNumber = (Spinner)findViewById(R.id.mSpinnerGSTNumber);
        mSpinnerBusinessTurnover = (Spinner)findViewById(R.id.mSpinnerBussinessTurnover);
        mSpinnerBussinessInYears = (Spinner)findViewById(R.id.mSpinnerBussinessInYears);
        mSpinnerBussinessMargin = (Spinner)findViewById(R.id.mSpinnerBussinessMargin);
        mSpinnerPaintTurnover = (Spinner)findViewById(R.id.mSpinnerPaintTurnover);
        mSpinnerOutletType = (Spinner)findViewById(R.id.mSpinnerOutletType);

        mSpinnerPainterExp1 = (Spinner)findViewById(R.id.mSpinnerPainterExp1);
        mSpinnerPainterExp2 = (Spinner)findViewById(R.id.mSpinnerPainterExp2);
        mSpinnerPainterExp3 = (Spinner)findViewById(R.id.mSpinnerPainterExp3);
        mSpinnerPainterExp4 = (Spinner)findViewById(R.id.mSpinnerPainterExp4);
        mSpinnerPainterExp5 = (Spinner)findViewById(R.id.mSpinnerPainterExp5);

        mSpinnerSourceType1 = (Spinner)findViewById(R.id.mSpinnerSourceType1);
        mSpinnerSourceType2 = (Spinner)findViewById(R.id.mSpinnerSourceType2);
        mSpinnerSourceType3 = (Spinner)findViewById(R.id.mSpinnerSourceType3);
        mSpinnerSourceType4 = (Spinner)findViewById(R.id.mSpinnerSourceType4);

        mSpinnerPainterEducation5 = (Spinner)findViewById(R.id.mSpinnerPainterEducation5);
        mSpinnerPainterEducation4 = (Spinner)findViewById(R.id.mSpinnerPainterEducation4);
        mSpinnerPainterEducation3 = (Spinner)findViewById(R.id.mSpinnerPainterEducation3);
        mSpinnerPainterEducation2 = (Spinner)findViewById(R.id.mSpinnerPainterEducation2);
        mSpinnerPainterEducation1 = (Spinner)findViewById(R.id.mSpinnerPainterEducation1);


        mEditVilaage = (AutoCompleteTextView) findViewById(R.id.mEditVilaage);
        mLayoutProduct = (FlowLayout) findViewById(R.id.mLayoutProduct);
        mLayoutPaintBrands = (FlowLayout) findViewById(R.id.mLayoutPaintBrands);
        mLayoutDelivery = (FlowLayout) findViewById(R.id.mLayoutDelivery);
        mLayoutSubmit = (RelativeLayout) findViewById(R.id.mLayoutSubmit);
        mEditRemark = (EditText) findViewById(R.id.mEditRemark);
        mEditAddress2 = (EditText) findViewById(R.id.mEditAddress2);
        mEditAddress1 = (EditText) findViewById(R.id.mEditAddress1);
        mEditOutLetSize = (EditText) findViewById(R.id.mEditOutLetSize);
        mEditLandline = (EditText) findViewById(R.id.mEditLandline);
        mEditWhatsapp = (EditText) findViewById(R.id.mEditWhatsapp);
        mEditPhone = (EditText) findViewById(R.id.mEditPhone);
        mEditOwnerLastName = (EditText) findViewById(R.id.mEditOwnerLastName);
        mEditOwnerFirstName = (EditText) findViewById(R.id.mEditOwnerFirstName);
        mEditGstNum = (EditText) findViewById(R.id.mEditGstNum);
        mEditShopName = (EditText) findViewById(R.id.mEditShopName);
        mEditPinNum = (EditText) findViewById(R.id.mEditPinNum);

        mEditPainter1 = (EditText) findViewById(R.id.mEditPainter1);
        mEditPainter2 = (EditText) findViewById(R.id.mEditPainter2);
        mEditPainter3 = (EditText) findViewById(R.id.mEditPainter3);
        mEditPainter4 = (EditText) findViewById(R.id.mEditPainter4);
        mEditPainter5 = (EditText) findViewById(R.id.mEditPainter5);



        mEditSourceName4 = (EditText) findViewById(R.id.mEditSourceName4);
        mEditSourceName3 = (EditText) findViewById(R.id.mEditSourceName3);
        mEditSourceName2 = (EditText) findViewById(R.id.mEditSourceName2);
        mEditSourceName1 = (EditText) findViewById(R.id.mEditSourceName1);




        mEditSourceContact4 = (EditText) findViewById(R.id.mEditSourceContact4);
        mEditSourceContact3 = (EditText) findViewById(R.id.mEditSourceContact3);
        mEditSourceContact2 = (EditText) findViewById(R.id.mEditSourceContact2);
        mEditSourceContact1 = (EditText) findViewById(R.id.mEditSourceContact1);



        mEditSourceLocation4 = (EditText) findViewById(R.id.mEditSourceLocation4);
        mEditSourceLocation3 = (EditText) findViewById(R.id.mEditSourceLocation3);
        mEditSourceLocation2 = (EditText) findViewById(R.id.mEditSourceLocation2);
        mEditSourceLocation1 = (EditText) findViewById(R.id.mEditSourceLocation1);

        mEditPainterNumber1 = (EditText) findViewById(R.id.mEditPainterNumber1);
        mEditPainterNumber2 = (EditText) findViewById(R.id.mEditPainterNumber2);
        mEditPainterNumber3 = (EditText) findViewById(R.id.mEditPainterNumber3);
        mEditPainterNumber4 = (EditText) findViewById(R.id.mEditPainterNumber4);
        mEditPainterNumber5 = (EditText) findViewById(R.id.mEditPainterNumber5);

        mImgOne = (ImageView) findViewById(R.id.mImgOne);
        mImgTwo = (ImageView) findViewById(R.id.mImgTwo);
        mImgThree = (ImageView) findViewById(R.id.mImgThree);
        mImgFour = (ImageView) findViewById(R.id.mImgFour);
        mImgFive = (ImageView) findViewById(R.id.mImgFive);
        mImgSix = (ImageView) findViewById(R.id.mImgSix);
        radioGrpPaintAvailable = (RadioGroup) findViewById(R.id.radioGrpPaintAvailable);


        mFunLoadMataData();

        ArrayAdapter arrayPainterEducation  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrPainterEducation);
        arrayPainterEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPainterEducation1.setAdapter(arrayPainterEducation);
        mSpinnerPainterEducation2.setAdapter(arrayPainterEducation);
        mSpinnerPainterEducation3.setAdapter(arrayPainterEducation);
        mSpinnerPainterEducation4.setAdapter(arrayPainterEducation);
        mSpinnerPainterEducation5.setAdapter(arrayPainterEducation);

        ArrayAdapter arrayPainterExp  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrPainterExp);
        arrayPainterExp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPainterExp1.setAdapter(arrayPainterExp);
        mSpinnerPainterExp2.setAdapter(arrayPainterExp);
        mSpinnerPainterExp3.setAdapter(arrayPainterExp);
        mSpinnerPainterExp4.setAdapter(arrayPainterExp);
        mSpinnerPainterExp5.setAdapter(arrayPainterExp);




        ArrayAdapter arrayPaintTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListPaintTurnover.toArray(new String[mListPaintTurnover.size()]));
        arrayPaintTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPaintTurnover.setAdapter(arrayPaintTurnover);



        ArrayAdapter arrayOutletType  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrOutletType);
        arrayOutletType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerOutletType.setAdapter(arrayOutletType);
        mSpinnerSourceType4.setAdapter(arrayOutletType);
        mSpinnerSourceType3.setAdapter(arrayOutletType);
        mSpinnerSourceType2.setAdapter(arrayOutletType);
        mSpinnerSourceType1.setAdapter(arrayOutletType);


        arrayAdapterTehsil  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBlock);
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayAdapterTehsil);

        ArrayAdapter arrayAdapterGST  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrGst);
        arrayAdapterGST.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGSTNumber.setAdapter(arrayAdapterGST);


        ArrayAdapter arrayAdapterDistrict  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListTehsil.toArray(new String[mListTehsil.size()]));
        arrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterDistrict);


        ArrayAdapter arrayAdapterBusinessTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListBusinessTurnover.toArray(new String[mListBusinessTurnover.size()]));
        arrayAdapterBusinessTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBusinessTurnover.setAdapter(arrayAdapterBusinessTurnover);


        ArrayAdapter arrayAdapterPaintContribution  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListBusinessYears.toArray(new String[mListBusinessYears.size()]));
        arrayAdapterPaintContribution.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBussinessInYears.setAdapter(arrayAdapterPaintContribution);


        ArrayAdapter arrayAdapterPaintMargin  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListPaintMargin.toArray(new String[mListPaintMargin.size()]));
        arrayAdapterPaintMargin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBussinessMargin.setAdapter(arrayAdapterPaintMargin);

        mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerTehsil.getSelectedItem().toString().equals("TEHSIL")){

                }else {
                mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
                mListBlock.add(0,"BLOCK");
                arrayAdapterTehsil  = new ArrayAdapter(ACTRetailerForm.this,android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
                arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinnerBlock.setAdapter(arrayAdapterTehsil);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerBlock.getSelectedItem().toString().equals("BLOCK")){

                }else{
                mListVillage = database.GT_RAW_LOCATION_VILLAGE(TBL_RAW_LOCATION_BLOCK,mSpinnerBlock.getSelectedItem().toString());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ACTRetailerForm.this,android.R.layout.simple_list_item_1,mListVillage.toArray(new String[mListVillage.size()]));
                mEditVilaage.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void mFunLoadMataData(){
        database = new Database(ACTRetailerForm.this);
        mListBusinessTurnover = database.GT_RAW_DATA(TABLE_RMD_OUTLET_SALES, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListBusinessTurnover.add(0,"Monthly Sales");
        mListPaintTurnover = database.GT_RAW_DATA(TABLE_RMD_PAINT_SALES, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListPaintTurnover.add(0,"Paint Sales");
        mListPaintMargin = database.GT_RAW_DATA(TABLE_RMD_PAINT_MERGE, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListPaintMargin.add(0,"Paint Margin");
        mListBusinessYears = database.GT_RAW_DATA(TABLE_RMD_BNS_IN_YEAR, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListBusinessYears.add(0,"Years In Business");
        mListTehsil = database.GT_RAW_LOCATION_TEHSIL(TBL_USER_ID,PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListTehsil.add(0,"TEHSIL");

        mListProduct = database.GT_RAW_DATA(TABLE_RMD_PRODUCTS, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListPaintBrands = database.GT_RAW_DATA(TABLE_RMD_BRANDS, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
        mListPaintDelivery = database.GT_RAW_DATA(TABLE_RMD_PAINT_DEL_SOURCE, PreferenceManager.getNEROUSERID(ACTRetailerForm.this));


        for (int i = 0; i<mListProduct.size();i++){
        View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutProduct, false);
        final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
        mCheckBox.setText(mListProduct.get(i));
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(mCheckBox.isChecked()){
                    mListResultProduct.add(mCheckBox.getText().toString());
                }else {
                    mListResultProduct.remove(mCheckBox.getText().toString());
                }
            }
        });
        mLayoutProduct.addView(mViewItemCheckBox);
        }

        for (int i = 0; i<mListPaintBrands.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutPaintBrands, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListPaintBrands.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListResultBrands.add(mCheckBox.getText().toString());
                    }else {
                        mListResultBrands.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutPaintBrands.addView(mViewItemCheckBox);
        }

        for (int i = 0; i<mListPaintDelivery.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutDelivery, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListPaintDelivery.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListResultDelivery.add(mCheckBox.getText().toString());
                    }else {
                        mListResultDelivery.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutDelivery.addView(mViewItemCheckBox);
        }

        mEditPhone.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length()== 0){
                mEditWhatsapp.setText("");
                }else {
                mEditWhatsapp.setText(mEditPhone.getText().toString());
                }

            }
        });

        mImgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 200);
            }
        });

        mImgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 300);
            }
        });

        mImgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 400);
            }
        });
        mImgFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 500);
            }
        });

        mImgFive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 600);
            }
        });

        mImgSix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 700);
            }
        });

        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrShopName = mEditShopName.getText().toString();
                mStrGstAvailble = mSpinnerGSTNumber.getSelectedItem().toString();
                mStrGstNum = mEditGstNum.getText().toString();
                mStrOwnerFirst = mEditOwnerFirstName.getText().toString();
                mStrOwnerLast = mEditOwnerLastName.getText().toString();
                mStrPhone = mEditPhone.getText().toString();
                mStrWhatsNum = mEditWhatsapp.getText().toString();
                mStrLandline = mEditLandline.getText().toString();
                mStrOutletSize = mEditOutLetSize.getText().toString();
                mStrAddress1 = mEditAddress1.getText().toString();
                mStrAddress2 = mEditAddress2.getText().toString();
                mStrPinNum = mEditPinNum.getText().toString();
                mStrTehsil = mSpinnerTehsil.getSelectedItem().toString();
                mStrBlocks = mSpinnerBlock.getSelectedItem().toString();
                mStrOutLetType = mSpinnerOutletType.getSelectedItem().toString();

                mStrVillage = mEditVilaage.getText().toString();
                mStrReProduct = TextUtils.join(",", mListResultProduct);
                mStrReBrands = TextUtils.join(",", mListResultBrands);
                mStrYearInBussiness = mSpinnerBussinessInYears.getSelectedItem().toString();
                mStrMonthlySales = mSpinnerBusinessTurnover.getSelectedItem().toString();
                mStrPaintSales = mSpinnerPaintTurnover.getSelectedItem().toString();
                mStrPaintMargin = mSpinnerBussinessMargin.getSelectedItem().toString();
                mStrReDelivery = TextUtils.join(",", mListResultDelivery);
                mStrRemark = mEditRemark.getText().toString();
                mStrPainterName1 = mEditPainter1.getText().toString();
                mStrPainterName2 = mEditPainter2.getText().toString();
                mStrPainterName3 = mEditPainter3.getText().toString();
                mStrPainterName4 = mEditPainter4.getText().toString();
                mStrPainterName5 = mEditPainter5.getText().toString();
                mStrPainterPhone1 = mEditPainterNumber1.getText().toString();
                mStrPainterPhone2 = mEditPainterNumber2.getText().toString();
                mStrPainterPhone3 = mEditPainterNumber3.getText().toString();
                mStrPainterPhone4 = mEditPainterNumber4.getText().toString();
                mStrPainterPhone5 = mEditPainterNumber5.getText().toString();
                mStrPainterExp1 = mSpinnerPainterExp1.getSelectedItem().toString();
                mStrPainterExp2 = mSpinnerPainterExp2.getSelectedItem().toString();
                mStrPainterExp3 = mSpinnerPainterExp3.getSelectedItem().toString();
                mStrPainterExp4 = mSpinnerPainterExp4.getSelectedItem().toString();
                mStrPainterExp5 = mSpinnerPainterExp5.getSelectedItem().toString();
                mStrPainterEdu1 = mSpinnerPainterEducation1.getSelectedItem().toString();
                mStrPainterEdu2 = mSpinnerPainterEducation2.getSelectedItem().toString();
                mStrPainterEdu3 = mSpinnerPainterEducation3.getSelectedItem().toString();
                mStrPainterEdu4 = mSpinnerPainterEducation4.getSelectedItem().toString();
                mStrPainterEdu5 = mSpinnerPainterEducation5.getSelectedItem().toString();

                mStrSourceName1 = mEditSourceName1.getText().toString();
                mStrSourceLocation1 = mEditSourceLocation1.getText().toString();
                mStrSourceContact1 = mEditSourceContact1.getText().toString();
                mStrSourceType1 = mSpinnerSourceType1.getSelectedItem().toString();


                mStrSourceName2 = mEditSourceName2.getText().toString();
                mStrSourceLocation2 = mEditSourceLocation2.getText().toString();
                mStrSourceContact2 = mEditSourceContact2.getText().toString();
                mStrSourceType2 = mSpinnerSourceType2.getSelectedItem().toString();

                mStrSourceName3 = mEditSourceName3.getText().toString();
                mStrSourceLocation3 = mEditSourceLocation3.getText().toString();
                mStrSourceContact3 = mEditSourceContact3.getText().toString();
                mStrSourceType3 = mSpinnerSourceType3.getSelectedItem().toString();

                mStrSourceName4 = mEditSourceName4.getText().toString();
                mStrSourceLocation4 = mEditSourceLocation4.getText().toString();
                mStrSourceContact4 = mEditSourceContact4.getText().toString();
                mStrSourceType4 = mSpinnerSourceType4.getSelectedItem().toString();




                int selectedIdFM = radioGrpPaintAvailable.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) findViewById(selectedIdFM);
                mStrPaintAvaible = radioSexButton.getText().toString();
                if(mStrPaintAvaible.equals("Yes")){
                mStrPaintAvaible = "1";
                }else {
                mStrPaintAvaible = "2";
                }

                if(mStrShopName.length()<=0){
                mShowAlert("Please enter shop name!",ACTRetailerForm.this);
                return;
                }else if(mStrGstAvailble.equals("GST Available")){
                mShowAlert("Please select gst Available or Not!",ACTRetailerForm.this);
                return;
                }else if(mStrGstAvailble.equals("Yes") && mStrGstNum.length()<=0){
                mShowAlert("Please enter gst number!",ACTRetailerForm.this);
                return;
                }else if(mStrOwnerFirst.length()<=0){
                mShowAlert("Please enter owner first name!",ACTRetailerForm.this);
                return;
                }else if(mStrOwnerLast.length()<=0){
                mShowAlert("Please enter owner last name!",ACTRetailerForm.this);
                return;
                }else if(mStrPhone.length()<=0){
                mShowAlert("Please enter mobile number!",ACTRetailerForm.this);
                return;
                }else if(mStrWhatsNum.length()<=0){
                mShowAlert("Please enter whatsapp number!",ACTRetailerForm.this);
                return;
                }else if(mStrOutletSize.length()<=0){
                mShowAlert("Please enter outlet size!",ACTRetailerForm.this);
                return;
                }else if(mStrAddress1.length()<=0){
                mShowAlert("Please enter address 1!",ACTRetailerForm.this);
                return;
                }else if(mStrPinNum.length()<=0){
                mShowAlert("Please enter pin code!",ACTRetailerForm.this);
                return;
                }else if(mStrTehsil.equals("TEHSIL")){
                mShowAlert("Please select tehsil!",ACTRetailerForm.this);
                return;
                }else if(mStrBlocks.equals("BLOCK")){
                mShowAlert("Please select block!",ACTRetailerForm.this);
                return;
                }else if(mStrVillage.length()<=0){
                mShowAlert("Please enter village!",ACTRetailerForm.this);
                return;
                }else if(mStrReProduct.length()<=0){
                mShowAlert("Please select at least one product!",ACTRetailerForm.this);
                return;
                }else if(mStrReBrands.length()<=0){
                mShowAlert("Please select at least one beands!",ACTRetailerForm.this);
                return;
                }else if(mStrYearInBussiness.equals("Years In Business")){
                mShowAlert("Please select years in business!",ACTRetailerForm.this);
                return;
                }else if(mStrMonthlySales.equals("Monthly Sales")){
                mShowAlert("Please select monthly sales!",ACTRetailerForm.this);
                return;
                }else if(mStrPaintSales.equals("Paint Sales")){
                mShowAlert("Please select paint sales!",ACTRetailerForm.this);
                return;
                }else if(mStrPaintSales.equals("Paint Margin")){
                mShowAlert("Please select paint margin!",ACTRetailerForm.this);
                return;
                }else if(mStrReDelivery.length()<=0){
                mShowAlert("Please select at least one delivery source!",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName1.length()>0 && mStrPainterEdu1.equals("Education")){
                mShowAlert("Please select painter 1 Education Level",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName1.length()>0 && mStrPainterExp1.equals("Experience(Yrs)")){
                mShowAlert("Please select painter 1 Experience",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName1.length()>0 && mStrPainterPhone1.length()<=0){
                mShowAlert("Please enter painter 1 phone number",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName2.length()>0 && mStrPainterEdu2.equals("Education")){
                mShowAlert("Please select painter 2 Education Level",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName2.length()>0 && mStrPainterExp2.equals("Experience(Yrs)")){
                mShowAlert("Please select painter 2 Experience",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName2.length()>0 && mStrPainterPhone2.length()<=0){
                mShowAlert("Please enter painter 2 phone number",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName3.length()>0 && mStrPainterEdu3.equals("Education")){
                mShowAlert("Please select painter 3 Education Level",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName3.length()>0 && mStrPainterExp3.equals("Experience(Yrs)")){
                mShowAlert("Please select painter 3 Experience",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName3.length()>0 && mStrPainterPhone3.length()<=0){
                mShowAlert("Please enter painter 3 phone number",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName4.length()>0 && mStrPainterEdu4.equals("Education")){
                mShowAlert("Please select painter 4 Education Level",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName4.length()>0 && mStrPainterExp4.equals("Experience(Yrs)")){
                mShowAlert("Please select painter 4 Experience",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName4.length()>0 && mStrPainterPhone4.length()<=0){
                mShowAlert("Please enter painter 4 phone number",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName5.length()>0 && mStrPainterEdu5.equals("Education")){
                mShowAlert("Please select painter 5 Education Level",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName5.length()>0 && mStrPainterExp5.equals("Experience(Yrs)")){
                mShowAlert("Please select painter 5 Experience",ACTRetailerForm.this);
                return;
                }else if(mStrPainterName5.length()>0 && mStrPainterPhone5.length()<=0){
                mShowAlert("Please enter painter 5 phone number",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName1.length()>0 && mStrSourceLocation1.length()<=0){
                mShowAlert("Please enter source city name 1",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName1.length()>0 && mStrSourceContact1.length()<=0){
                mShowAlert("Please enter source contact 1",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName2.length()>0 && mStrSourceLocation2.length()<=0){
                mShowAlert("Please enter source city name 2",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName2.length()>0 && mStrSourceContact2.length()<=0){
                mShowAlert("Please enter source contact 2",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName3.length()>0 && mStrSourceLocation3.length()<=0){
                mShowAlert("Please enter source city name 3",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName3.length()>0 && mStrSourceContact3.length()<=0){
                mShowAlert("Please enter source contact 3",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName4.length()>0 && mStrSourceLocation4.length()<=0){
                mShowAlert("Please enter source city name 4",ACTRetailerForm.this);
                return;
                }else if(mStrSourceName4.length()>0 && mStrSourceContact4.length()<=0){
                mShowAlert("Please enter source contact 4",ACTRetailerForm.this);
                return;
                }

                mStrDistrict = database.GT_RAW_LOCATION_DISTRICT(mStrBlocks,mStrTehsil).get(0);
                Retailers retailers = new Retailers();
                retailers.setTbAddress1(mStrAddress1);
                retailers.setTbAddress2(mStrAddress2);
                retailers.setTbBlock(mStrBlocks);
                retailers.setTbBrand(mStrReBrands);
                retailers.setTbBusinessInYears(mStrYearInBussiness);
                retailers.setTbDeliverySource(mStrReDelivery);
                retailers.setTbDistrict(mStrDistrict);
                retailers.setTbFirstName(mStrOwnerFirst);
                retailers.setTbGstAvailable(mStrGstAvailble);
                retailers.setTbGstNumber(mStrGstNum);
                retailers.setTbLandline(mStrLandline);
                retailers.setTbLastName(mStrOwnerLast);
                retailers.setTbLatitude(PreferenceManager.getNEROLAT(ACTRetailerForm.this));
                retailers.setTbLongitude(PreferenceManager.getNEROLONG(ACTRetailerForm.this));
                retailers.setTbMobile(mStrPhone);
                retailers.setTbOutletSales(mStrMonthlySales);
                retailers.setTbOutletSize(mStrOutletSize);
                retailers.setTbPaintMargin(mStrPaintMargin);
                retailers.setTbPaintSales(mStrPaintSales);
                retailers.setTbPincode(mStrPinNum);
                retailers.setTbProducts(mStrReProduct);
                retailers.setTbRemark(mStrRemark);
                retailers.setTbShopName(mStrShopName);
                retailers.setTbTehsil(mStrTehsil);
                retailers.setTbUserId(PreferenceManager.getNEROUSERID(ACTRetailerForm.this));
                retailers.setTbVillage(mStrVillage);
                retailers.setTbWhatsApp(mStrWhatsNum);
                retailers.setTbOutletType(mStrOutLetType);
                retailers.setTbPainterNameOne(mStrPainterName1);
                retailers.setTbPainterExperienceOne(mStrPainterExp1);
                retailers.setTbPainterEducationOne(mStrPainterEdu1);
                retailers.setTbPainterPhoneOne(mStrPainterPhone1);
                retailers.setTbPainterNameTwo(mStrPainterName2);
                retailers.setTbPainterExperienceTwo(mStrPainterExp2);
                retailers.setTbPainterEducationTwo(mStrPainterEdu2);
                retailers.setTbPainterPhoneTwo(mStrPainterPhone2);
                retailers.setTbPainterNameThree(mStrPainterName3);
                retailers.setTbPainterExperienceThree(mStrPainterExp3);
                retailers.setTbPainterEducationThree(mStrPainterEdu3);
                retailers.setTbPainterPhoneThree(mStrPainterPhone3);
                retailers.setTbPainterNameFour(mStrPainterName4);
                retailers.setTbPainterExperienceFour(mStrPainterExp4);
                retailers.setTbPainterEducationFour(mStrPainterEdu4);
                retailers.setTbPainterPhoneFour(mStrPainterPhone4);
                retailers.setTbPainterNameFive(mStrPainterName5);
                retailers.setTbPainterExperienceFive(mStrPainterExp5);
                retailers.setTbPainterEducationFive(mStrPainterEdu5);
                retailers.setTbPainterPhoneFive(mStrPainterPhone5);

                retailers.setTbSourceName1(mStrSourceName1);
                retailers.setTbSourceContact1(mStrSourceContact1);
                retailers.setTbSourceLocation1(mStrSourceLocation1);
                retailers.setTbSourceType1(mStrSourceType1);

                retailers.setTbSourceName2(mStrSourceName2);
                retailers.setTbSourceContact2(mStrSourceContact2);
                retailers.setTbSourceLocation2(mStrSourceLocation2);
                retailers.setTbSourceType2(mStrSourceType2);

                retailers.setTbSourceName3(mStrSourceName3);
                retailers.setTbSourceContact3(mStrSourceContact3);
                retailers.setTbSourceLocation3(mStrSourceLocation3);
                retailers.setTbSourceType3(mStrSourceType3);

                retailers.setTbSourceName4(mStrSourceName4);
                retailers.setTbSourceContact4(mStrSourceContact4);
                retailers.setTbSourceLocation4(mStrSourceLocation4);
                retailers.setTbSourceType4(mStrSourceType4);


                retailers.setTbPaintAvail(mStrPaintAvaible);
                retailers.setTbImgOne(mStrImgOnePath);
                retailers.setTbImgTwo(mStrImgTwoPath);
                retailers.setTbImgThree(mStrImgThreePath);
                retailers.setTbImgFour(mStrImgFourPath);
                retailers.setTbImgFive(mStrImgFivePath);
                retailers.setTbImgSix(mStrImgSixPath);

                database.IN_DATA_RETAILERS(retailers);
                mShowAlert("You have successfully submitted!!", ACTRetailerForm.this);
                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("<><><>resultcall");
        if (resultCode == RESULT_OK && requestCode == 200) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgOne.setImageBitmap(bitmap);
            System.out.println("<><>12 "+storeImage(bitmap));
            //mStrImgOnePath = ImageUtil.convert(bitmap);
            mStrImgOnePath = storeImage(bitmap);
        }else if (resultCode == RESULT_OK && requestCode == 300) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgTwo.setImageBitmap(bitmap);
            System.out.println("<><>123 "+storeImage(bitmap));
            //mStrImgTwoPath = ImageUtil.convert(bitmap);
            mStrImgTwoPath = storeImage(bitmap);
        }else if (resultCode == RESULT_OK && requestCode == 400) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgThree.setImageBitmap(bitmap);
            System.out.println("<><>1234 "+storeImage(bitmap));
            //mStrImgThreePath = ImageUtil.convert(bitmap);
            mStrImgThreePath = storeImage(bitmap);
        }else if (resultCode == RESULT_OK && requestCode == 500) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgFour.setImageBitmap(bitmap);
            System.out.println("<><>1234 "+storeImage(bitmap));
            //mStrImgThreePath = ImageUtil.convert(bitmap);
            mStrImgFourPath = storeImage(bitmap);
        }else if (resultCode == RESULT_OK && requestCode == 600) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgFive.setImageBitmap(bitmap);
            System.out.println("<><>1234 "+storeImage(bitmap));
            //mStrImgThreePath = ImageUtil.convert(bitmap);
            mStrImgFivePath = storeImage(bitmap);
        }else if (resultCode == RESULT_OK && requestCode == 700) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgSix.setImageBitmap(bitmap);
            System.out.println("<><>1234 "+storeImage(bitmap));
            //mStrImgThreePath = ImageUtil.convert(bitmap);
            mStrImgSixPath = storeImage(bitmap);
        }
    }

    private Bitmap writeTextOnDrawable(Bitmap bm, String mStrDate) {
        bm = bm.copy(Bitmap.Config.ARGB_8888, true);
        int width = 400;
        int height = 600;//Math.round(width / aspectRatio);
        bm = Bitmap.createScaledBitmap(bm, width, height, false);
        Canvas canvas = new Canvas(bm);
        canvas.drawBitmap(bm, new Matrix(), null);
        LinearLayout layout = new LinearLayout(ACTRetailerForm.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        layout.setBackgroundColor(Color.parseColor("#4D000000"));
        layout.setLayoutParams(params);
        TextView textView = new TextView(ACTRetailerForm.this);
        textView.setVisibility(View.VISIBLE);
        textView.setText(" " + mEditShopName.getText().toString() + " \n " +mEditVilaage.getText().toString()+", "+mSpinnerBlock.getSelectedItem().toString() +", "+mSpinnerTehsil.getSelectedItem().toString() + " \n " + "Lat - "+PreferenceManager.getNEROLAT(ACTRetailerForm.this)+", Long - "+PreferenceManager.getNEROLONG(ACTRetailerForm.this)+ " \n " + getTimeformatCurrent() );
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setPadding(5, 15, 5, 15);
        textView.setTextSize(6);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(params);
        layout.addView(textView);
        layout.measure(bm.getWidth(), 100);
        layout.layout(0, 0, bm.getWidth(), 100);
        canvas.translate(0, bm.getHeight() - 100);
        layout.draw(canvas);
        return bm;
    }

    public String saveBitmap(Bitmap bitmap) {
        String mStrPath = "noImg";
        //Toast.makeText(PostDetails.this,"click 3",Toast.LENGTH_LONG).show();
        File file = new File(path + "/", currenTime + ".jpg"); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
        try {
            OutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            //Uri uri = FileProvider.getUriForFile(ACTRetailerForm.this, getApplicationContext().getPackageName() + ".provider", file);
            mStrPath = file.getAbsolutePath().toString();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
        return mStrPath;
    }

    void mFunEnterForm() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"addRetailer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            String mStrMessage = response.getString("message");
                            if (mStrStatus.equals("200")) {
                                mShowAlert(mStrMessage, ACTRetailerForm.this);
                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                mShowAlert("Something went wrong!!", ACTRetailerForm.this);
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
                        mShowAlert(getString(R.string.Something), ACTRetailerForm.this);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(ACTRetailerForm.this));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "2");
                params.put("appId", "12");
                params.put("shopName", mStrShopName);
                params.put("firstname", mStrOwnerFirst);
                params.put("lastname", mStrOwnerLast);
                params.put("mobile", mStrPhone);
                params.put("whatsapp", mStrWhatsNum);
                params.put("landline", mStrLandline);
                params.put("outlet_size", mStrOutletSize);
                params.put("address1", mStrAddress1);
                params.put("address2", mStrAddress2);
                params.put("pincode", mStrPinNum);
                params.put("gst_available", mStrGstAvailble);
                params.put("gst_number", mStrGstNum);
                params.put("village", mStrVillage);
                params.put("block", mStrBlocks);
                params.put("district", mStrDistrict);
                params.put("tehsil", mStrTehsil);
                params.put("products_available", mStrReProduct);
                params.put("brand", mStrReBrands);
                params.put("business_in_year", mStrYearInBussiness);
                params.put("outlet_sales", mStrMonthlySales);
                params.put("paint_sales", mStrPaintSales);
                params.put("paint_margin", mStrPaintMargin);
                params.put("delivery_source", mStrReDelivery);
                params.put("latitude", PreferenceManager.getNEROLAT(ACTRetailerForm.this));
                params.put("longitude", PreferenceManager.getNEROLONG(ACTRetailerForm.this));
                params.put("remark", mStrRemark);
                params.put("image1", "data:image/png;base64,"+mStrImgOnePath);
                params.put("image2", "data:image/png;base64,"+mStrImgTwoPath);
                params.put("image3", "data:image/png;base64,"+mStrImgThreePath);
                System.out.println("<><><>## "+params);
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }

    private String SaveImage(Bitmap finalBitmap ) {

        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        String fname = "Image-"+ timeStamp +".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("<><><>## call img"+file.getAbsolutePath());
        return  file.getAbsolutePath();
    }

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return "no img";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return pictureFile.getAbsolutePath();
    }

    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

}

