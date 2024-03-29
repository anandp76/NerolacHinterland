package com.nerolac;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.nerolac.Adpter.RetailerListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Retailers;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import static com.nerolac.ACTAttendenceList.CallMe;
import static com.nerolac.ACTDistributorList.CallTyu;
import static com.nerolac.ACTInboxList.CallThere;
import static com.nerolac.ACTRetailerList.myDream;
import static com.nerolac.ACTTravelLog.mycall;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class ScreenNerolacHome extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    DrawerLayout mDrawerLayout;
    ImageView imgMenu;
    TextView txtProfileName;
    RelativeLayout profileView;
    int i = 0;
    private boolean doubleBackToExitPressedOnce = false;
    RequestQueue queue;
    Database database;
    RelativeLayout mLayoutRetailer;
    RelativeLayout mLayoutOrder;
    RelativeLayout mLayoutAttendance;
    RelativeLayout mLayoutTravelLog;
    RelativeLayout mLayoutProspects;
    RelativeLayout mLayoutInbox;
    RelativeLayout mLayoutRoutePlan;
    RelativeLayout mLayoutDashbord;

    RelativeLayout mLayoutLogout;
    RelativeLayout mLayoutChangePassword;
    RelativeLayout mLayoutHandbook;
    RelativeLayout mLayoutPendingTask;

    public static int mIntRetailer = 0;
    public static int mIntAttendance = 0;
    public static int mIntTravelLog = 0;
    public static int mIntProspects = 0;
    public static int mIntInbox = 0;
    public static int mIntRoutePlan = 0;

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    Handler handler;
    LocationManager manager;
    Context mContext;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.screen_home);
        queue = Volley.newRequestQueue(ScreenNerolacHome.this);
        database = new Database(ScreenNerolacHome.this);
        manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE );
        mDrawerLayout = (DrawerLayout) findViewById(R.id.mDrawerLayout);
        mLayoutRetailer = (RelativeLayout) findViewById(R.id.mLayoutRetailer);
        mLayoutOrder = (RelativeLayout) findViewById(R.id.mLayoutOrder);
        mLayoutLogout = (RelativeLayout) findViewById(R.id.mLayoutLogout);
        mLayoutChangePassword = (RelativeLayout) findViewById(R.id.mLayoutChangePassword);
        mLayoutInbox = (RelativeLayout) findViewById(R.id.mLayoutInbox);
        mLayoutHandbook = (RelativeLayout) findViewById(R.id.mLayoutHandbook);
        mLayoutAttendance = (RelativeLayout) findViewById(R.id.mLayoutAttendance);
        mLayoutTravelLog = (RelativeLayout) findViewById(R.id.mLayoutTravelLog);
        mLayoutProspects = (RelativeLayout) findViewById(R.id.mLayoutProspects);
        mLayoutRoutePlan = (RelativeLayout) findViewById(R.id.mLayoutRoutePlan);
        mLayoutDashbord = (RelativeLayout) findViewById(R.id.mLayoutDashbord);
        mLayoutPendingTask = (RelativeLayout) findViewById(R.id.mLayoutPendingTask);
mContext = ScreenNerolacHome.this;
        txtProfileName = (TextView) findViewById(R.id.txtProfileName);
        profileView = (RelativeLayout) findViewById(R.id.profileView);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        } else
            Toast.makeText(this, "Not Connected!", Toast.LENGTH_SHORT).show();

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }
            profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });
        imgMenu = (ImageView) findViewById(R.id.imgMenu);
        imgMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (i == 0) {
                    i = 1;
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                    i = 0;
                }
            }
        });
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerStateChanged(int arg0) {
            }

            @Override
            public void onDrawerSlide(View arg0, float arg1) {
            }

            @Override
            public void onDrawerOpened(View arg0) {
                System.out.println("<><><>Open");
                i = 1;
            }

            @Override
            public void onDrawerClosed(View arg0) {
                System.out.println("<><><>Close");
                i = 0;
            }
        });
        txtProfileName.setText(PreferenceManager.getNEROFULLNAME(ScreenNerolacHome.this));
        MainActivity fragmentDiscover = new MainActivity();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                .commit();

        mLayoutRetailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 1;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTRetailerList fragmentDiscover = new ACTRetailerList();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC2")
                                .commit();
                    }
                }




                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mLayoutProspects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 1;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTDistributorList fragmentDiscover = new ACTDistributorList();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC3")
                                .commit();
                    }

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        mLayoutOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTOrderList fragmentDiscover = new ACTOrderList();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC3")
                                .commit();
                    }

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        mLayoutInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 1;
                        mIntRoutePlan = 0;
                        ACTInboxList fragmentDiscover = new ACTInboxList();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC4")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        mLayoutAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 1;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTAttendenceList fragmentDiscover = new ACTAttendenceList();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC5")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mLayoutRoutePlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 1;
                        ACTRoutePlanTabs fragmentDiscover = new ACTRoutePlanTabs();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC6")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });


        mLayoutTravelLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 1;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTTravelLog fragmentDiscover = new ACTTravelLog();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC7")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        mLayoutChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTUpdatePassword fragmentDiscover = new ACTUpdatePassword();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC8")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });


        mLayoutPendingTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTPendingUpload fragmentDiscover = new ACTPendingUpload();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC18")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        mLayoutHandbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        ACTHandbookList fragmentDiscover = new ACTHandbookList();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC9")
                                .commit();
                    }
                }


                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });

        mLayoutDashbord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkPermission()) {
                    requestPermission();
                }else {
                    if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ){
                        mShowAlert("Please turn on Gps first!!",ScreenNerolacHome.this);
                    }else {
                        mIntRetailer = 0;
                        mIntAttendance = 0;
                        mIntTravelLog = 0;
                        mIntProspects = 0;
                        mIntInbox = 0;
                        mIntRoutePlan = 0;
                        MainActivity fragmentDiscover = new MainActivity();
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.content_frame, fragmentDiscover, "ABC10")
                                .commit();
                    }

                }

                mDrawerLayout.closeDrawer(GravityCompat.START);

            }
        });


        mLayoutLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIntRetailer = 0;
                mIntAttendance = 0;
                mIntTravelLog = 0;
                mIntProspects = 0;
                mIntInbox = 0;
                mIntRoutePlan = 0;
                database.DT_LOCATION();
                PreferenceManager.setNEROISLOGIN(ScreenNerolacHome.this,"0");
                Intent intent = new Intent(ScreenNerolacHome.this,ACTLogin.class);
                startActivity(intent);
                finish();

            }
        });
        //showProgress(ScreenNerolacHome.this);
        mFunUserCheck();

        /**/

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mIntInbox==1){
            CallThere();
        }else if (mIntAttendance==1){
            //CallMe();
        }else if (mIntProspects==1){
            CallTyu();
        }else if (mIntRetailer==1){
            myDream();
        }else if (mIntRoutePlan==1){
            //callMyDit();
        }else if (mIntTravelLog==1){
            mycall();
        }
    }



    @Override
    public void onBackPressed() {
        Fragment f = getSupportFragmentManager().findFragmentById(R.id.content_frame);
        if(f instanceof MainActivity){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }else {
            mIntRetailer = 0;
            mIntAttendance = 0;
            mIntTravelLog = 0;
            mIntProspects = 0;
            mIntInbox = 0;
            mIntRoutePlan = 0;
            MainActivity fragmentDiscover = new MainActivity();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_frame, fragmentDiscover, "ABC10")
                    .commit();
        }


    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        settingRequest();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection Suspended!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Connection Failed!", Toast.LENGTH_SHORT).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 90000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("Current Location", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }



    public void settingRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);    // 10 seconds, in milliseconds
        mLocationRequest.setFastestInterval(1000);   // 1 second, in milliseconds
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,
                        builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(@NonNull LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(ScreenNerolacHome.this, 1000);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        break;
                }
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1000:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;
                    case Activity.RESULT_CANCELED:
                        final Dialog dialog = new Dialog(ScreenNerolacHome.this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.popup_dialoge_location);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        RelativeLayout mLayoutSubmit = dialog.findViewById(R.id.mLayoutSubmit);
                        RelativeLayout mLayoutCancel = dialog.findViewById(R.id.mLayoutCancel);
                        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                settingRequest();
                                dialog.dismiss();
                            }
                        });

                        mLayoutCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                finish();
                            }
                        });


                        break;
                    default:
                        break;
                }
                break;
        }
    }

    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else {
            /*Getting the location after aquiring location service*/
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);

            if (mLastLocation != null) {
                System.out.println("<><><>ABC "+String.valueOf(mLastLocation.getLatitude()));
                System.out.println("<><><>ABC "+String.valueOf(mLastLocation.getLongitude()));
                PreferenceManager.setNEROLAT(ScreenNerolacHome.this,String.valueOf(mLastLocation.getLatitude()));
                PreferenceManager.setNEROLONG(ScreenNerolacHome.this,String.valueOf(mLastLocation.getLongitude()));
                //_//progressBar.setVisibility(View.INVISIBLE);
                //_//latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
                //_//longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
            } else {
                /*if there is no last known location. Which means the device has no data for the loction currently.
                 * So we will get the current location.
                 * For this we'll implement Location Listener and override onLocationChanged*/
                Log.i("Current Location", "No data for location found");

                if (!mGoogleApiClient.isConnected())
                    mGoogleApiClient.connect();

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, ScreenNerolacHome.this);
            }
        }
    }

    /*When Location changes, this method get called. */
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        System.out.println("<><><>ABC "+String.valueOf(mLastLocation.getLatitude()));
        System.out.println("<><><>ABC "+String.valueOf(mLastLocation.getLongitude()));
        PreferenceManager.setNEROLAT(ScreenNerolacHome.this,String.valueOf(mLastLocation.getLatitude()));
        PreferenceManager.setNEROLONG(ScreenNerolacHome.this,String.valueOf(mLastLocation.getLongitude()));
       // _progressBar.setVisibility(View.INVISIBLE);
       // _latitude.setText("Latitude: " + String.valueOf(mLastLocation.getLatitude()));
       // _longitude.setText("Longitude: " + String.valueOf(mLastLocation.getLongitude()));
    }


        void mFunUserCheck() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"check_user",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                            String mStrData = response.getString("data");
                            if(!mStrData.equals("A")){
                                mShowAlert("You are deactivate from admin, Please contact to admin!!", ScreenNerolacHome.this);
                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mIntRetailer = 0;
                                        mIntAttendance = 0;
                                        mIntTravelLog = 0;
                                        mIntProspects = 0;
                                        mIntInbox = 0;
                                        mIntRoutePlan = 0;
                                        PreferenceManager.setNEROISLOGIN(ScreenNerolacHome.this,"0");
                                        Intent intent = new Intent(ScreenNerolacHome.this,ACTLogin.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 3000);

                            }
                            } else {
                            mShowAlert(getResources().getString(R.string.Something), ScreenNerolacHome.this);
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
                        mShowAlert(getString(R.string.Something),ScreenNerolacHome.this);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(ScreenNerolacHome.this));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(ScreenNerolacHome.this));
                return params;
            }
        };
        queue.add(strRequest);
    }

    private boolean checkPermission() {
        int result0 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE);
        //int result4 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.FOREGROUND_SERVICE);
        return result0 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:{
                if (grantResults.length > 0) {
                    boolean result0 = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean result1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean result2 = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean result3 = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    if (result0 && result1 && result2 && result3){
                    }else{
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE}, PERMISSION_REQUEST_CODE);
                    }
                }
            }
            break;
        }
    }




}