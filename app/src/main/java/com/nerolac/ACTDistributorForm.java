package com.nerolac;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTDistributorForm extends Activity {

    Spinner mSpinnerTehsil;
    Spinner mSpinnerState;
    Spinner mSpinnerDistrict;
    Spinner mSpinnerBusinessTurnover;
    Spinner mSpinnerBussinessYear;
    Spinner mSpinnerBrand1;
    Spinner mSpinnerBrand5;
    Spinner mSpinnerBrand4;
    Spinner mSpinnerBrand3;
    Spinner mSpinnerBrand2;
    Spinner mSpinnerVillage;
    Spinner mSpinnerRetailers;
    String mStrTehsil[] = {"Tehsil"};
    String mStrState[] = {"Block"};
    String mStrDistrict[] = {"District"};
    String mStrBusinessTurnover[] = {"Monthly Turnover","1 Lac","5 Lac","10 Lac","15 Lac"};
    String mStrInterestedInKansai[] = {"Interest Level","High","Medium","Low"};
    String mStrYears[] = {"Years","1","2","3","4","5","5+"};
    String mStrVilages[] = {"Village","5","10","15","20+"};
    String mStrRetailers[] = {"Retailers","50","100","150","200","250+"};
    String mStrBusinessYears[] = {"Business In Years","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","20+"};//paint ka turnover

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form_distributor);


        setTranceprent(ACTDistributorForm.this,R.color.appblue);
        mSpinnerBrand1 = (Spinner)findViewById(R.id.mSpinnerBrand1);
        mSpinnerBrand2 = (Spinner)findViewById(R.id.mSpinnerBrand2);
        mSpinnerBrand3 = (Spinner)findViewById(R.id.mSpinnerBrand3);
        mSpinnerBrand4 = (Spinner)findViewById(R.id.mSpinnerBrand4);
        mSpinnerBrand5 = (Spinner)findViewById(R.id.mSpinnerBrand5);
        mSpinnerVillage = (Spinner)findViewById(R.id.mSpinnerVillage);
        mSpinnerRetailers = (Spinner)findViewById(R.id.mSpinnerRetailers);


        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerState = (Spinner)findViewById(R.id.mSpinnerState);
        mSpinnerDistrict = (Spinner)findViewById(R.id.mSpinnerDistrict);
        mSpinnerBusinessTurnover = (Spinner)findViewById(R.id.mSpinnerBussinessTurnover);
        mSpinnerBussinessYear = (Spinner)findViewById(R.id.mSpinnerBussinessYear);

        ArrayAdapter mStrVilage  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrVilages);
        mStrVilage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerVillage.setAdapter(mStrVilage);

        ArrayAdapter mStrRetailersdf  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrRetailers);
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
        mSpinnerBussinessYear.setAdapter(arrayAdapterPaintContribution);


    }

}

