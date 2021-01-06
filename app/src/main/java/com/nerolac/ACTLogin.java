package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTLogin extends Activity {
   RelativeLayout mLayoutLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTranceprent(ACTLogin.this,R.color.white);
        mLayoutLogin = (RelativeLayout)findViewById(R.id.mLayoutLogin);

        mLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTLogin.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

}

