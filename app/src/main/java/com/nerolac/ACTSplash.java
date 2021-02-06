package com.nerolac;



import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.nerolac.Utils.PreferenceManager;

import androidx.core.app.NotificationCompat;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTSplash extends Activity {
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTranceprent(ACTSplash.this,R.color.white);
        //startForegroundService(new Intent(ACTSplash.this, AndroidLocationServices.class));

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(PreferenceManager.getNEROISLOGIN(ACTSplash.this).equals("1")){
                    Intent intent = new Intent(ACTSplash.this,ScreenNerolacHome.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(ACTSplash.this,ACTLogin.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 3000);


    }



}

