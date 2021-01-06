package com.nerolac;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTSarpanchForm extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_form_sarpanch);
        setTranceprent(ACTSarpanchForm.this,R.color.appblue);

    }

}

