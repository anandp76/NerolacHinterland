package com.nerolac;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Utils.PreferenceManager;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import static com.nerolac.Utils.CommonData.getTimeformat;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.showProgress;

public class AndroidLocationServices extends Service {

    WakeLock wakeLock;
    String CHANNEL_ID = "abcd343fdg";
    private LocationManager locationManager;
    RequestQueue queue;
    private LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            // TODO Auto-generated method stub

            Log.e("Google", "Location Changed");

            if (location == null){
                return;
            }else {
                System.out.println("<><>11 "+location.getLatitude());
                System.out.println("<><>11 "+location.getLongitude());
                mFunDayIn(String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));
            }
         /*

            if (isConnectingToInternet(getApplicationContext())) {

                JSONObject jsonObject = new JSONObject();

                try {

                    //jsonObject.put("secret_key", Constants.SECRET_KEY);
                    jsonObject.put("latitude", location.getLatitude());
                    jsonObject.put("longitude", location.getLongitude());
                    jsonObject.put("deviceID", getDeviceImei(AndroidLocationServices.this));


               *//*     new LocationWebService().execute(new String[]{
                            Constants.TRACK_URL, jsonObject.toString()});*//*
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }*/

        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }
    };

    public AndroidLocationServices() {
        // TODO Auto-generated constructor stub
    }

    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }



    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
        PowerManager pm = (PowerManager) getSystemService(this.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "DoNotSleep");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground(1, new Notification());



    }

    private void startMyOwnForeground(){
        String NOTIFICATION_CHANNEL_ID = "com.nerolac";
        String channelName = "My Background Service";
        NotificationChannel chan = new NotificationChannel(NOTIFICATION_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        assert manager != null;
        manager.createNotificationChannel(chan);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        Notification notification = notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("App is running in background")
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(2, notification);
    }

    @SuppressLint("MissingPermission")
    @Override
    @Deprecated
    public void onStart(Intent intent, int startId) {
        // TODO Auto-generated method stub
        super.onStart(intent, startId);

//		new ToggleGPS(getApplicationContext()).turnGPSOn();

        // Toast.makeText(getApplicationContext(), "Service Started",
        // Toast.LENGTH_SHORT).show();
        Log.e("Google", "Service Started");

        locationManager = (LocationManager) getApplicationContext()
                .getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                15000, 1, listener);

    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        wakeLock.release();

    }

    void mFunDayIn(final String mStrLat,final String mStrLong) {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/addTrack",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        System.out.println("<><>er "+str);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //hidePDialog();
                        //mShowAlert(getString(R.string.Something), getActivity());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", PreferenceManager.getNEROTOKEN(getApplicationContext()));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(getApplicationContext()));
                params.put("datetime",getTimeformat());
                params.put("latitude",mStrLat);
                params.put("longitude",mStrLong);
                return params;
            }
        };
        queue.add(strRequest);
    }



}
