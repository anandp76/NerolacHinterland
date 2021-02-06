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
import android.util.Base64;
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
import com.bumptech.glide.Glide;
import com.nerolac.DataBase.Database;
import com.nerolac.Utils.FlowLayout;
import com.nerolac.Utils.GPSTracker;
import com.nerolac.Utils.ImageUtil;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_ASSETS;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_COVERAGE_RETAILERS;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_COVERAGE_VILLAGES;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_TERRITORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_TYPE;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_MONTHLY_TURNOVER;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_PRODUCT_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_WILLINGNESS_TO_INVEST;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_OUTLET_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_BLOCK;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;
import static com.nerolac.Utils.CommonData.getTimeformat;
import static com.nerolac.Utils.CommonData.getTimeformatCurrent;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTDistributorForm extends Activity {

    Database database;
    FlowLayout mLayoutBusinessCat;
    FlowLayout mLayoutBusinessType;
    FlowLayout mLayoutBusinessTerritory;
    FlowLayout mLayoutProductCate;
    FlowLayout mLayoutAssest;
    public static String FOLDER = ".mtkw19";

    ArrayList<String> mListBusinessCat = new ArrayList<>();
    ArrayList<String> mListBusinessType = new ArrayList<>();
    ArrayList<String> mListBusinessTerritory = new ArrayList<>();
    ArrayList<String> mListProductCate = new ArrayList<>();
    ArrayList<String> mListAssest = new ArrayList<>();

    ArrayList<String> mListReAssest = new ArrayList<>();
    ArrayList<String> mListReProductCate = new ArrayList<>();
    ArrayList<String> mListReBusinessTerritory = new ArrayList<>();
    ArrayList<String> mListReBusinessType = new ArrayList<>();
    ArrayList<String> mListReBusinessCat = new ArrayList<>();

    ArrayList<String> mListTehsil = new ArrayList<String>();
    ArrayList<String> mListBlock = new ArrayList<String>();
    ArrayList<String> mListVillage = new ArrayList<String>();


    ArrayList<String> mListWillingness = new ArrayList<String>();
    ArrayList<String> mListRetailerCovrage = new ArrayList<String>();
    ArrayList<String> mListVillageCovrage = new ArrayList<String>();
    ArrayList<String> mListYearInBusiness = new ArrayList<String>();
    ArrayList<String> mListMonthlyTrunover = new ArrayList<String>();


    AutoCompleteTextView mEditVilaage;
    Spinner mSpinnerTehsil;
    Spinner mSpinnerBlock;
    Spinner mSpinnerGSTNumber;
    Spinner mSpinnerTimeToStart;
    Spinner mSpinnerWillingness;

    Spinner mSpinnerBusinessTurnover;
    Spinner mSpinnerBussinessYear;
    Spinner mSpinnerBrand1;
    Spinner mSpinnerBrand5;
    Spinner mSpinnerBrand4;
    Spinner mSpinnerBrand3;
    Spinner mSpinnerBrand2;
    Spinner mSpinnerVillage;
    Spinner mSpinnerRetailers;

    String mStrBlock[] = {"Block"};
    String mStrTime[] = {"Time To Start","Within a month","Less then 3 months ","Less then 6 months"};
    String mStrGst[] = {"GST Available","Yes","No"};
    String mStrYears[] = {"Years","1","2","3","4","5","5+"};


    EditText mEditFirstName;
    EditText mEditLastName;
    EditText mEditBusinessName;
    EditText mEditGstNumber;
    EditText mEditMobileNumber;
    EditText mEditWhatsNumber;
    EditText mEditLandlineNumber;
    EditText mEditOutletSize;
    EditText mEditAddOne;
    EditText mEditAddTwo;
    EditText mEditPinCode;
    EditText mEditProspect;
    EditText mEditRemark;

    EditText mEditBrand5;
    EditText mEditBrand4;
    EditText mEditBrand3;
    EditText mEditBrand2;
    EditText mEditBrand1;


    RadioGroup radioGrpFamilyMembers;
    RadioGroup radioGrpInterestLevel;

    RelativeLayout mLayoutSubmit;


    String mStrFirstname;
    String mStrLastname;
    String mStrBusinessName;
    String mStrMobile;
    String mStrWhatsapp;
    String mStrLandline;
    String mStrOutletSize;
    String mStrGstAvailable;
    String mStrGstNumber;
    String mStrAddressOne;
    String mStrAddressTwo;
    String mStrPincode;
    String mStrVillages;
    String mStrBlocks;
    String mStrTehsil;
    String mStrDistrict;
    String mStrLatitude;
    String mStrLongitude;
    String mStrBuisnessCategory;
    String mStrBuisnessTerritory;
    String mStrProductCategory;
    String mStrBusinessType;
    String mStrBusinessInYear;
    String mStrTurnOver;
    String mStrCoverageVillage;
    String mStrCoverageRetailer;
    String mStrWillingnessInvest;
    String mStrAssets;
    String mStrProspect;
    String mStrRemark;
    String mStrBrandName1;
    String mStrBrandName2;
    String mStrBrandName3;
    String mStrBrandName4;
    String mStrBrandName5;
    String mStrBrandYear1;
    String mStrBrandYear2;
    String mStrBrandYear3;
    String mStrBrandYear4;
    String mStrBrandYear5;
    String mStrFamilyMember;
    String mStrInterestedLevel;
    RequestQueue queue;
    Handler handler;
    ImageView mImgOne;
    ImageView mImgTwo;
    ImageView mImgThree;
    ImageView mImgView;
    String mStrImgOne;
    String mStrImgTwo;
    String mStrImgThree;

    Bitmap bitmap1;
    Bitmap bitmap2;
    Bitmap bitmap3;
    //String mStrMail

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form_distributor);
        database = new Database(ACTDistributorForm.this);
        queue = Volley.newRequestQueue(ACTDistributorForm.this);
        setTranceprent(ACTDistributorForm.this,R.color.appblue);

        mSpinnerBrand1 = (Spinner)findViewById(R.id.mSpinnerBrand1);
        mSpinnerBrand2 = (Spinner)findViewById(R.id.mSpinnerBrand2);
        mSpinnerBrand3 = (Spinner)findViewById(R.id.mSpinnerBrand3);
        mSpinnerBrand4 = (Spinner)findViewById(R.id.mSpinnerBrand4);
        mSpinnerBrand5 = (Spinner)findViewById(R.id.mSpinnerBrand5);
        mSpinnerVillage = (Spinner)findViewById(R.id.mSpinnerVillage);
        mSpinnerRetailers = (Spinner)findViewById(R.id.mSpinnerRetailers);
        mSpinnerTimeToStart = (Spinner)findViewById(R.id.mSpinnerTimeToStart);
        mSpinnerWillingness = (Spinner)findViewById(R.id.mSpinnerWillingness);
        mLayoutBusinessCat = (FlowLayout)findViewById(R.id.mLayoutBusinessCat);
        mEditVilaage = (AutoCompleteTextView) findViewById(R.id.mEditVilaage);

        radioGrpFamilyMembers = (RadioGroup) findViewById(R.id.radioGrpFamilyMembers);
        radioGrpInterestLevel = (RadioGroup) findViewById(R.id.radioGrpInterestLevel);

        mEditRemark = (EditText) findViewById(R.id.mEditRemark);
        mEditProspect = (EditText) findViewById(R.id.mEditProspect);
        mEditPinCode = (EditText) findViewById(R.id.mEditPinCode);
        mEditAddTwo = (EditText) findViewById(R.id.mEditAddTwo);
        mEditAddOne = (EditText) findViewById(R.id.mEditAddOne);
        mEditOutletSize = (EditText) findViewById(R.id.mEditOutletSize);
        mEditLandlineNumber = (EditText) findViewById(R.id.mEditLandlineNumber);
        mEditWhatsNumber = (EditText) findViewById(R.id.mEditWhatsNumber);
        mEditMobileNumber = (EditText) findViewById(R.id.mEditMobileNumber);
        mEditGstNumber = (EditText) findViewById(R.id.mEditGstNumber);
        mEditBusinessName = (EditText) findViewById(R.id.mEditBusinessName);
        mEditLastName = (EditText) findViewById(R.id.mEditLastName);
        mEditFirstName = (EditText) findViewById(R.id.mEditFirstName);

        mEditBrand1 = (EditText) findViewById(R.id.mEditBrand1);
        mEditBrand2 = (EditText) findViewById(R.id.mEditBrand2);
        mEditBrand3 = (EditText) findViewById(R.id.mEditBrand3);
        mEditBrand4 = (EditText) findViewById(R.id.mEditBrand4);
        mEditBrand5 = (EditText) findViewById(R.id.mEditBrand5);

        mLayoutSubmit = (RelativeLayout) findViewById(R.id.mLayoutSubmit);

        mLayoutBusinessType = (FlowLayout)findViewById(R.id.mLayoutBusinessType);
        mLayoutBusinessTerritory = (FlowLayout)findViewById(R.id.mLayoutBusinessTerritory);
        mLayoutProductCate = (FlowLayout)findViewById(R.id.mLayoutProductCate);
        mLayoutAssest = (FlowLayout)findViewById(R.id.mLayoutAssest);

        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)findViewById(R.id.mSpinnerBlock);
        mSpinnerGSTNumber = (Spinner)findViewById(R.id.mSpinnerGSTNumber);
        mSpinnerBusinessTurnover = (Spinner)findViewById(R.id.mSpinnerBussinessTurnover);
        mSpinnerBussinessYear = (Spinner)findViewById(R.id.mSpinnerBussinessYear);
        mImgOne = (ImageView) findViewById(R.id.mImgOne);
        mImgTwo = (ImageView) findViewById(R.id.mImgTwo);
        mImgThree = (ImageView) findViewById(R.id.mImgThree);

        mFunLoadMataData();

        ArrayAdapter mStrTimeToStart  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrTime);
        mStrTimeToStart.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTimeToStart.setAdapter(mStrTimeToStart);


        ArrayAdapter mStrWillin  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListWillingness.toArray(new String[mListWillingness.size()]));
        mStrWillin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerWillingness.setAdapter(mStrWillin);


        ArrayAdapter mStrVilage  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListVillageCovrage.toArray(new String[mListVillageCovrage.size()]));
        mStrVilage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerVillage.setAdapter(mStrVilage);

        ArrayAdapter mStrRetailersdf  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListRetailerCovrage.toArray(new String[mListRetailerCovrage.size()]));
        mStrRetailersdf.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerRetailers.setAdapter(mStrRetailersdf);



        ArrayAdapter Brand1  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrYears);
        Brand1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBrand1.setAdapter(Brand1);

        ArrayAdapter Brand2  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrYears);
        Brand2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBrand2.setAdapter(Brand2);

        ArrayAdapter Brand3  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrYears);
        Brand3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBrand3.setAdapter(Brand3);

        ArrayAdapter Brand4  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrYears);
        Brand4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBrand4.setAdapter(Brand4);

        ArrayAdapter Brand5  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrYears);
        Brand5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBrand5.setAdapter(Brand5);


        ArrayAdapter arrayAdapterTehsil  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBlock);
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayAdapterTehsil);

        ArrayAdapter arrayAdapterGST  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrGst);
        arrayAdapterGST.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerGSTNumber.setAdapter(arrayAdapterGST);




        ArrayAdapter arrayAdapterDistrict  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListTehsil.toArray(new String[mListTehsil.size()]));
        arrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterDistrict);





        ArrayAdapter arrayAdapterBusinessTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListMonthlyTrunover.toArray(new String[mListMonthlyTrunover.size()]));
        arrayAdapterBusinessTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBusinessTurnover.setAdapter(arrayAdapterBusinessTurnover);


        ArrayAdapter arrayAdapterPaintContribution  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListYearInBusiness.toArray(new String[mListYearInBusiness.size()]));
        arrayAdapterPaintContribution.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBussinessYear.setAdapter(arrayAdapterPaintContribution);

        mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerTehsil.getSelectedItem().toString().equals("BLOCK")){

                }else {
                    mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
                    mListBlock.add(0,"BLOCK");
                    ArrayAdapter<String> arrayAdapterTehsil  = new ArrayAdapter(ACTDistributorForm.this,android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
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
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(ACTDistributorForm.this,android.R.layout.simple_list_item_1,mListVillage.toArray(new String[mListVillage.size()]));
                    mEditVilaage.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void mFunLoadMataData(){
        mListBusinessCat = database.GT_DAW_DATA(TBL_DMD_BNS_CATEGORY, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListBusinessType =  database.GT_DAW_DATA(TBL_DMD_BNS_TYPE, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListBusinessTerritory =   database.GT_DAW_DATA(TBL_DMD_BNS_TERRITORY, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListProductCate =   database.GT_DAW_DATA(TBL_DMD_PRODUCT_CATEGORY, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListAssest =    database.GT_DAW_DATA(TBL_DMD_ASSETS, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));

        mListWillingness = database.GT_DAW_DATA(TBL_DMD_WILLINGNESS_TO_INVEST, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListWillingness.add(0,"Willingness To Invest");

        mListRetailerCovrage = database.GT_DAW_DATA(TBL_DMD_BNS_COVERAGE_RETAILERS, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListRetailerCovrage.add(0,"Number of Retailers");

        mListVillageCovrage = database.GT_DAW_DATA(TBL_DMD_BNS_COVERAGE_VILLAGES, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListVillageCovrage.add(0,"Number of Villages");

        mListYearInBusiness = database.GT_DAW_DATA(TBL_DMD_BNS_IN_YEAR, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListYearInBusiness.add(0,"Years In Business");

        mListMonthlyTrunover = database.GT_DAW_DATA(TBL_DMD_MONTHLY_TURNOVER, PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListMonthlyTrunover.add(0,"Monthly Turnover");

        mListTehsil = database.GT_RAW_LOCATION_TEHSIL(TBL_USER_ID,PreferenceManager.getNEROUSERID(ACTDistributorForm.this));
        mListTehsil.add(0,"TEHSIL");

        for (int i = 0; i<mListBusinessCat.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutBusinessCat, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListBusinessCat.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListReBusinessCat.add(mCheckBox.getText().toString());
                    }else {
                        mListReBusinessCat.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutBusinessCat.addView(mViewItemCheckBox);
        }


        for (int i = 0; i<mListBusinessType.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutBusinessType, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListBusinessType.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListReBusinessType.add(mCheckBox.getText().toString());
                    }else {
                        mListReBusinessType.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutBusinessType.addView(mViewItemCheckBox);
        }

        for (int i = 0; i<mListBusinessTerritory.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutBusinessTerritory, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListBusinessTerritory.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListReBusinessTerritory.add(mCheckBox.getText().toString());
                    }else {
                        mListReBusinessTerritory.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutBusinessTerritory.addView(mViewItemCheckBox);
        }


        for (int i = 0; i<mListProductCate.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutProductCate, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListProductCate.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListReProductCate.add(mCheckBox.getText().toString());
                    }else {
                        mListReProductCate.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutProductCate.addView(mViewItemCheckBox);
        }



        for (int i = 0; i<mListAssest.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.item_checkbox, mLayoutAssest, false);
            final CheckBox mCheckBox = (CheckBox)mViewItemCheckBox.findViewById(R.id.mCheckBox);
            mCheckBox.setText(mListAssest.get(i));
            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(mCheckBox.isChecked()){
                        mListReAssest.add(mCheckBox.getText().toString());
                    }else {
                        mListReAssest.remove(mCheckBox.getText().toString());
                    }
                }
            });
            mLayoutAssest.addView(mViewItemCheckBox);
        }

        mEditMobileNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,int before, int count) {
                if(s.length()== 0){
                    mEditWhatsNumber.setText("");
                }else {
                    mEditWhatsNumber.setText(mEditMobileNumber.getText().toString());
                }

            }
        });


        mImgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgView = mImgOne;
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 200);
            }
        });

        mImgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgView = mImgTwo;
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 300);
            }
        });

        mImgThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImgView = mImgThree;
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 400);
            }
        });


        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrFirstname = mEditFirstName.getText().toString();
                mStrLastname = mEditLastName.getText().toString();
                mStrBusinessName = mEditBusinessName.getText().toString();
                mStrMobile = mEditMobileNumber.getText().toString();
                mStrWhatsapp = mEditWhatsNumber.getText().toString();
                mStrLandline = mEditLandlineNumber.getText().toString();
                mStrOutletSize = mEditOutletSize.getText().toString();
                mStrGstAvailable = mSpinnerGSTNumber.getSelectedItem().toString();
                mStrGstNumber = mEditGstNumber.getText().toString();
                mStrAddressOne = mEditAddOne.getText().toString();
                mStrAddressTwo = mEditAddTwo.getText().toString();
                mStrPincode = mEditPinCode.getText().toString();
                mStrVillages = mEditVilaage.getText().toString();
                mStrBlocks = mSpinnerBlock.getSelectedItem().toString();
                mStrTehsil = mSpinnerTehsil.getSelectedItem().toString();
                if(database.GT_RAW_LOCATION_DISTRICT(mStrBlocks,mStrTehsil).size()>0){
                mStrDistrict = database.GT_RAW_LOCATION_DISTRICT(mStrBlocks,mStrTehsil).get(0);
                }
                mStrBuisnessCategory = TextUtils.join(",", mListReBusinessCat);
                mStrBuisnessTerritory = TextUtils.join(",", mListReBusinessTerritory);
                mStrProductCategory = TextUtils.join(",", mListReProductCate);
                mStrBusinessType = TextUtils.join(",", mListReBusinessType);
                mStrBusinessInYear = mSpinnerBussinessYear.getSelectedItem().toString();
                mStrTurnOver = mSpinnerBusinessTurnover.getSelectedItem().toString();
                mStrCoverageVillage = mSpinnerVillage.getSelectedItem().toString();
                mStrCoverageRetailer = mSpinnerRetailers.getSelectedItem().toString();
                mStrWillingnessInvest = mSpinnerWillingness.getSelectedItem().toString();
                mStrAssets = TextUtils.join(",", mListReAssest);
                mStrProspect = mEditProspect.getText().toString();
                mStrRemark = mEditRemark.getText().toString();
                mStrBrandName1 = mEditBrand1.getText().toString();
                mStrBrandName2 = mEditBrand2.getText().toString();
                mStrBrandName3 = mEditBrand3.getText().toString();
                mStrBrandName4 = mEditBrand4.getText().toString();
                mStrBrandName5 = mEditBrand5.getText().toString();
                mStrBrandYear1 = mSpinnerBrand1.getSelectedItem().toString();
                mStrBrandYear2 = mSpinnerBrand2.getSelectedItem().toString();
                mStrBrandYear3 = mSpinnerBrand3.getSelectedItem().toString();
                mStrBrandYear4 = mSpinnerBrand4.getSelectedItem().toString();
                mStrBrandYear5 = mSpinnerBrand5.getSelectedItem().toString();
                int selectedIdFM = radioGrpFamilyMembers.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) findViewById(selectedIdFM);
                mStrFamilyMember = radioSexButton.getText().toString();
                int selectedIdIL = radioGrpInterestLevel.getCheckedRadioButtonId();
                RadioButton radioILButton = (RadioButton) findViewById(selectedIdIL);
                mStrInterestedLevel = radioILButton.getText().toString();

                if(mStrFirstname.length()<=0){
                    mShowAlert("Please enter first name!",ACTDistributorForm.this);
                    return;
                }else if(mStrLastname.length()<=0){
                    mShowAlert("Please enter last name!",ACTDistributorForm.this);
                    return;
                }else if(mStrBusinessName.length()<=0){
                    mShowAlert("Please enter business name!",ACTDistributorForm.this);
                    return;
                }else if(mStrGstAvailable.equals("GST Available")){
                    mShowAlert("Please select gst Available or Not!",ACTDistributorForm.this);
                    return;
                }else if(mStrGstAvailable.equals("Yes") && mStrGstNumber.length()<=0){
                    mShowAlert("Please enter gst number!",ACTDistributorForm.this);
                    return;
                }else if(mStrMobile.length()<=0){
                    mShowAlert("Please enter mobile number!",ACTDistributorForm.this);
                    return;
                }else if(mStrWhatsapp.length()<=0){
                    mShowAlert("Please enter whatsapp number!",ACTDistributorForm.this);
                    return;
                }else if(mStrOutletSize.length()<=0){
                    mShowAlert("Please enter outlet size!",ACTDistributorForm.this);
                    return;
                }else if(mStrAddressOne.length()<=0){
                    mShowAlert("Please enter address one!",ACTDistributorForm.this);
                    return;
                }else if(mStrPincode.length()<=0){
                    mShowAlert("Please enter pin code!",ACTDistributorForm.this);
                    return;
                }else if(mStrTehsil.equals("TEHSIL")){
                    mShowAlert("Please select tehsil!",ACTDistributorForm.this);
                    return;
                }else if(mStrBlocks.equals("BLOCK")){
                    mShowAlert("Please select blocks!",ACTDistributorForm.this);
                    return;
                }else if(mStrVillages.length()<=0){
                    mShowAlert("Please enter village name!",ACTDistributorForm.this);
                    return;
                }else if(mStrBlocks.equals("")){
                    mShowAlert("Please select block!",ACTDistributorForm.this);
                    return;
                }else if(mStrBuisnessCategory.length()<=0){
                    mShowAlert("Please select at lest one business category!",ACTDistributorForm.this);
                    return;
                }else if(mStrBusinessType.length()<=0){
                    mShowAlert("Please select at lest one business type!",ACTDistributorForm.this);
                    return;
                }else if(mStrBuisnessTerritory.length()<=0){
                    mShowAlert("Please select at least one territory!",ACTDistributorForm.this);
                    return;
                }else if(mStrProductCategory.length()<=0){
                    mShowAlert("Please select at least one product category!",ACTDistributorForm.this);
                    return;
                }else if(mStrCoverageVillage.equals("Number of Villages")){
                    mShowAlert("Please select village coverage!",ACTDistributorForm.this);
                    return;
                }else if(mStrCoverageRetailer.equals("Number of Retailers")){
                    mShowAlert("Please select retailers coverage!",ACTDistributorForm.this);
                    return;
                }else if(mStrAssets.length()<=0){
                    mShowAlert("Please select at lest one assets!",ACTDistributorForm.this);
                    return;
                }else if(mStrWillingnessInvest.equals("Willingness To Invest")){
                    mShowAlert("Please select willingness to invest!",ACTDistributorForm.this);
                    return;
                }else if(mSpinnerTimeToStart.getSelectedItem().toString().equals("Time To Start")){
                    mShowAlert("Please select time to start!",ACTDistributorForm.this);
                    return;
                }else if(mStrProspect.length()<=0){
                    mShowAlert("Please enter prospect!",ACTDistributorForm.this);
                    return;
                }
                showProgress(ACTDistributorForm.this);
                mFunEnterForm();
            }
        });

    }

    void mFunEnterForm() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/addDistributor",
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
                                mShowAlert(mStrMessage, ACTDistributorForm.this);
                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        finish();
                                    }
                                }, 1000);

                            } else {
                                mShowAlert("Something went wrong!!", ACTDistributorForm.this);
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
                        mShowAlert(getString(R.string.Something), ACTDistributorForm.this);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(ACTDistributorForm.this));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("distributor_lastname",mStrLastname);
                params.put("distributor_firstname", mStrFirstname);
                params.put("appId", "12");
                params.put("business_name", mStrBusinessName);
                params.put("mobile", mStrMobile);
                params.put("whatsapp", mStrWhatsapp);
                params.put("landline", mStrLandline);
                params.put("outlet_size", mStrOutletSize);
                params.put("gst_available", mStrGstAvailable);
                params.put("gst_number", mStrGstNumber);
                params.put("address1", mStrAddressOne);
                params.put("address2", mStrAddressTwo);
                params.put("pincode", mStrPincode);
                params.put("villages", mStrVillages);
                params.put("block", mStrBlocks);
                params.put("district", mStrDistrict);
                params.put("tehsil", mStrTehsil);
                params.put("latitude", PreferenceManager.getNEROLAT(ACTDistributorForm.this));
                params.put("longitude", PreferenceManager.getNEROLONG(ACTDistributorForm.this));
                params.put("buisness_category", mStrBuisnessCategory);
                params.put("buisness_territory", mStrBuisnessTerritory);
                params.put("product_category", mStrProductCategory);
                params.put("business_type", mStrBuisnessCategory);
                params.put("business_in_year", mStrBusinessInYear);
                params.put("turn_over", mStrTurnOver);
                params.put("coverage_no_of_village",mStrCoverageVillage);
                params.put("coverage_no_of_retailer",mStrCoverageRetailer);
                params.put("willingness_to_invest", mStrWillingnessInvest);
                params.put("family_member_involved", mStrFamilyMember);
                params.put("interested_level", mStrInterestedLevel);
                params.put("prospect", mStrProspect);
                params.put("remark", mStrRemark);
                params.put("brand1name", mStrBrandName1);
                params.put("brand1year", mStrBrandYear1);
                params.put("brand2name", mStrBrandName2);
                params.put("brand2year", mStrBrandYear2);
                params.put("brand3name", mStrBrandName3);
                params.put("brand3year", mStrBrandYear3);
                params.put("brand4name", mStrBrandName4);
                params.put("brand4year", mStrBrandYear4);
                params.put("brand5name", mStrBrandName5);
                params.put("brand5year", mStrBrandYear5);
                params.put("assets", mStrAssets);
                params.put("image1", "data:image/png;base64,"+mStrImgOne);
                params.put("image2", "data:image/png;base64,"+mStrImgTwo);
                params.put("image3", "data:image/png;base64,"+mStrImgThree);
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
            bitmap1 = bitmap;
            mImgView.setImageBitmap(bitmap);
            mStrImgOne = ImageUtil.convert(bitmap);
         }else if (resultCode == RESULT_OK && requestCode == 300) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            bitmap1 = bitmap;
            mImgView.setImageBitmap(bitmap);
            mStrImgTwo = ImageUtil.convert(bitmap);

        }else if (resultCode == RESULT_OK && requestCode == 400) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            bitmap1 = bitmap;
            mImgView.setImageBitmap(bitmap);
            mStrImgThree = ImageUtil.convert(bitmap);
        }

    }

    public String SaveImage(Bitmap finalBitmap) {
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        File myDir = new File(root + "/kwalls");
        myDir.mkdirs();
        String fname = getTimeformat()+".jpg";
        File file = new File (myDir, fname);
        if (file.exists ()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return root+"/kwalls/"+fname;
    }

    private Bitmap writeTextOnDrawable(Bitmap bm, String mStrDate) {
        bm = bm.copy(Bitmap.Config.ARGB_8888, true);
        int width = 400;
        int height = 600;//Math.round(width / aspectRatio);
        bm = Bitmap.createScaledBitmap(bm, width, height, false);
        Canvas canvas = new Canvas(bm);
        canvas.drawBitmap(bm, new Matrix(), null);
        LinearLayout layout = new LinearLayout(ACTDistributorForm.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        layout.setBackgroundColor(Color.parseColor("#4D000000"));
        layout.setLayoutParams(params);
        TextView textView = new TextView(ACTDistributorForm.this);
        textView.setVisibility(View.VISIBLE);
        textView.setText(" " + mEditBusinessName.getText().toString() + " \n " +mEditVilaage.getText().toString()+", "+mSpinnerBlock.getSelectedItem().toString() +", "+mSpinnerTehsil.getSelectedItem().toString() + " \n " + "Lat - "+PreferenceManager.getNEROLAT(ACTDistributorForm.this)+", Long - "+PreferenceManager.getNEROLONG(ACTDistributorForm.this)+ " \n " + getTimeformatCurrent() );
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
}

