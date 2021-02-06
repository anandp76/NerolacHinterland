package com.nerolac;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTLogin extends Activity {
   RelativeLayout mLayoutLogin;
   EditText mEditPassword;
   EditText mEditEmail;
   String mStrPass;
   String mStrEmail;
   RequestQueue queue;
   private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTranceprent(ACTLogin.this,R.color.white);
        queue = Volley.newRequestQueue(ACTLogin.this);
        mLayoutLogin = (RelativeLayout)findViewById(R.id.mLayoutLogin);
        mEditPassword = (EditText)findViewById(R.id.mEditPassword);
        mEditEmail = (EditText)findViewById(R.id.mEditEmail);
        if (!checkPermission()) {
            requestPermission();
        }

        mLayoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mStrEmail = mEditEmail.getText().toString();
                mStrPass = mEditPassword.getText().toString();
                if(mStrEmail.length()<=0){
                    mShowAlert("Please enter email or mobile number!",ACTLogin.this);
                    return;
                }else if(mStrPass.length()<=0){
                    mShowAlert("Please enter password!",ACTLogin.this);
                    return;
                }
                showProgress(ACTLogin.this);
                mFunSignIn();
            }
        });

    }

    private boolean checkPermission() {
        int result0 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CAMERA);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.FOREGROUND_SERVICE);
        return result0 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.FOREGROUND_SERVICE}, PERMISSION_REQUEST_CODE);
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
                    boolean result4 = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    if (result0 && result1 && result2 && result3 && result4){
                    }else{
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.CALL_PHONE, Manifest.permission.FOREGROUND_SERVICE}, PERMISSION_REQUEST_CODE);
                    }
                }
            }
            break;
        }
    }

    void mFunSignIn() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/login",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                mShowAlert("You have successfully logged in!!", ACTLogin.this);
                                JSONObject jsonObject = response.getJSONObject("data");
                                String mStrId = jsonObject.getString("id");
                                String mStrFullName = jsonObject.getString("full_name");
                                String mStrIsEmail = jsonObject.getString("email");
                                String mStrPhone = jsonObject.getString("phone");
                                String mStrIsStatus = jsonObject.getString("status");
                                String mStrToken = jsonObject.getString("token");

                                PreferenceManager.setNEROISLOGIN(ACTLogin.this,"1");
                                PreferenceManager.setNEROUSERID(ACTLogin.this,mStrId);
                                PreferenceManager.setNEROFULLNAME(ACTLogin.this,mStrFullName);
                                PreferenceManager.setNEROEMAIL(ACTLogin.this,mStrIsEmail);
                                PreferenceManager.setNEROMOBILE(ACTLogin.this,mStrPhone);
                                PreferenceManager.setNEROSTATUS(ACTLogin.this,mStrIsStatus);
                                PreferenceManager.setNEROTOKEN(ACTLogin.this,mStrToken);
                                PreferenceManager.setNEROUSERNAME(ACTLogin.this,mStrEmail);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(ACTLogin.this,ScreenNerolacHome.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                }, 1000);
                            } else {
                                mShowAlert("Wrong email address or password!!", ACTLogin.this);
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
                        mShowAlert(getString(R.string.Something), ACTLogin.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", mStrEmail);
                params.put("password", mStrPass);
                System.out.println("<><><>## "+params);
                return params;
            }
        };
        queue.add(strRequest);
    }

}

