package com.nerolac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    boolean doubleBackToExitPressedOnce = false;
    RelativeLayout mLayoutOther;
    LinearLayout mLayoutFull;
    LinearLayout mLayoutHalf;


    RelativeLayout mLayoutHeatMarket;
    RelativeLayout mLayoutAnajMandi;
    RelativeLayout mLayoutAshaBahu;
    RelativeLayout mLayoutAnganWadi;
    RelativeLayout mLayoutSarpanch;
    RelativeLayout mLayout2Pending;


    RelativeLayout mLayout2Retailer;
    RelativeLayout mLayout2Distributor;
    RelativeLayout mLayout2SalesOrder;
    RelativeLayout mLayout2Inbox;
    int a = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_two);
        mLayoutOther = (RelativeLayout)findViewById(R.id.mLayoutOther);
        mLayoutFull = (LinearLayout) findViewById(R.id.mLayoutFull);
        mLayoutHalf = (LinearLayout) findViewById(R.id.mLayoutHalf);


        mLayoutHeatMarket = (RelativeLayout) findViewById(R.id.mLayoutHeatMarket);
        mLayoutAnajMandi = (RelativeLayout) findViewById(R.id.mLayoutAnajMandi);
        mLayoutAshaBahu = (RelativeLayout) findViewById(R.id.mLayoutAshaBahu);
        mLayoutAnganWadi = (RelativeLayout) findViewById(R.id.mLayoutAnganWadi);
        mLayoutSarpanch = (RelativeLayout) findViewById(R.id.mLayoutSarpanch);

        mLayout2Retailer = (RelativeLayout) findViewById(R.id.mLayout2Retailer);
        mLayout2Distributor = (RelativeLayout) findViewById(R.id.mLayout2Distributor);
        mLayout2SalesOrder = (RelativeLayout) findViewById(R.id.mLayout2SalesOrder);
        mLayout2Inbox = (RelativeLayout) findViewById(R.id.mLayout2Inbox);
        mLayout2Pending = (RelativeLayout) findViewById(R.id.mLayout2Pending);

        mLayout2Retailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTRetailerList.class);
                startActivity(intent);
            }
        });
        mLayout2Distributor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTDistributorList.class);
                startActivity(intent);
            }
        });
        mLayout2SalesOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTRoutPlanList.class);
                startActivity(intent);
            }
        });
        mLayout2Inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTInboxList.class);
                startActivity(intent);
            }
        });

        mLayout2Pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTVillageDistributorsGraph.class);
                startActivity(intent);
            }
        });

        mLayoutOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(a==0){
                    a=1;
                    mLayoutHalf.setVisibility(View.GONE);
                    mLayoutFull.setVisibility(View.VISIBLE);
                }

            }
        });


        mLayoutHeatMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTHaatMarketList.class);
                startActivity(intent);
            }
        });
        mLayoutAnajMandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTAnajMandiList.class);
                startActivity(intent);
            }
        });
        mLayoutAshaBahu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTAshaBahuList.class);
                startActivity(intent);
            }
        });
        mLayoutAnganWadi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTAnganwadiList.class);
                startActivity(intent);
            }
        });
        mLayoutSarpanch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ACTSarpanchList.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

        if(a==1){
            a=0;
            mLayoutHalf.setVisibility(View.VISIBLE);
            mLayoutFull.setVisibility(View.GONE);
        }else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }
}
