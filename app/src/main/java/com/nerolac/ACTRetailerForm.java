package com.nerolac;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTRetailerForm extends Activity {

    Spinner mSpinnerTehsil;
    Spinner mSpinnerState;
    Spinner mSpinnerDistrict;
    Spinner mSpinnerBusinessTurnover;
    Spinner mSpinnerBussinessInYears;
    Spinner mSpinnerBussinessMargin;
    Spinner mSpinnerPaintTurnover;
    String mStrTehsil[] = {"Tehsil"};
    String mStrState[] = {"Block"};
    String mStrDistrict[] = {"District"};
    String mStrBusinessTurnover[] = {"Outlet Sales","5K","10K","15K","20K","25K","50+K"};//paint ka turnover
    String mStrPaintTurnover[] = {"Paint Sales","5K","10K","15K","20K","25K","50+K"};//paint ka turnover
    String mStrBusinessMargin[] = {"Paint Margin","5%","6%","7%","8%","9%","10%"};//paint ka turnover
    String mStrBusinessYears[] = {"Business In Years","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","20+"};//paint ka turnover
    //shop turnover busines turn over  phle
    String mStrPaintContribution[] = {"Paint %"};
    String mStrPaintMargin[] = {"Paint %"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form);


        setTranceprent(ACTRetailerForm.this,R.color.appblue);
        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerState = (Spinner)findViewById(R.id.mSpinnerState);
        mSpinnerDistrict = (Spinner)findViewById(R.id.mSpinnerDistrict);
        mSpinnerBusinessTurnover = (Spinner)findViewById(R.id.mSpinnerBussinessTurnover);
        mSpinnerBussinessInYears = (Spinner)findViewById(R.id.mSpinnerBussinessInYears);
        mSpinnerBussinessMargin = (Spinner)findViewById(R.id.mSpinnerBussinessMargin);
        mSpinnerPaintTurnover = (Spinner)findViewById(R.id.mSpinnerPaintTurnover);

        ArrayAdapter arrayPaintTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrPaintTurnover);
        arrayPaintTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPaintTurnover.setAdapter(arrayPaintTurnover);


        ArrayAdapter arrayAdapterTehsil  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrState);
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterTehsil);


        ArrayAdapter arrayAdapterState  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrDistrict);
        arrayAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(arrayAdapterState);


        ArrayAdapter arrayAdapterDistrict  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrTehsil);
        arrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistrict.setAdapter(arrayAdapterDistrict);


        ArrayAdapter arrayAdapterBusinessTurnover  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBusinessTurnover);
        arrayAdapterBusinessTurnover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBusinessTurnover.setAdapter(arrayAdapterBusinessTurnover);


        ArrayAdapter arrayAdapterPaintContribution  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBusinessYears);
        arrayAdapterPaintContribution.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBussinessInYears.setAdapter(arrayAdapterPaintContribution);


        ArrayAdapter arrayAdapterPaintMargin  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrBusinessMargin);
        arrayAdapterPaintMargin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBussinessMargin.setAdapter(arrayAdapterPaintMargin);


    }

}

