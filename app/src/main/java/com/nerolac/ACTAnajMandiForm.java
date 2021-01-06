package com.nerolac;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTAnajMandiForm extends Activity {

    Spinner mSpinnerTehsil;
    Spinner mSpinnerState;
    Spinner mSpinnerDistrict;
    String mStrTehsil[] = {"Tehsil"};
    String mStrState[] = {"State"};
    String mStrDistrict[] = {"District"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form_anaj_mandi);


        setTranceprent(ACTAnajMandiForm.this,R.color.appblue);
        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerState = (Spinner)findViewById(R.id.mSpinnerState);
        mSpinnerDistrict = (Spinner)findViewById(R.id.mSpinnerDistrict);

        ArrayAdapter arrayAdapterTehsil  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrTehsil);
        arrayAdapterTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayAdapterTehsil);


        ArrayAdapter arrayAdapterState  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrState);
        arrayAdapterState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(arrayAdapterState);


        ArrayAdapter arrayAdapterDistrict  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrDistrict);
        arrayAdapterDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistrict.setAdapter(arrayAdapterDistrict);


    }

}

