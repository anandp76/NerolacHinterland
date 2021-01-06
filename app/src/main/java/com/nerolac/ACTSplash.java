package com.nerolac;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTSplash extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTranceprent(ACTSplash.this,R.color.white);
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(ACTSplash.this,ACTLogin.class);
                startActivity(intent);
                finish();

            }
        }, 3000);


    }

}

