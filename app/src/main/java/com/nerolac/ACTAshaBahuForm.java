package com.nerolac;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTAshaBahuForm extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form_asha_bahu);
        setTranceprent(ACTAshaBahuForm.this,R.color.appblue);

    }

}

