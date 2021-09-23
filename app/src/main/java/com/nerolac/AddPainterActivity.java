package com.nerolac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.Visiteradapter;
import com.nerolac.Modal.VisitelogModal;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;

public class AddPainterActivity extends Activity {
    String mStrPainterExp[] = {"Experience(Yrs)","0-5","5-10","10-15","15-20"};
    String mStrPainterEducation[] = {"Education","No","10 School","12 High School","Graduate","Post Graduate"};
    Spinner mSpinnerPainterExp1;
    Spinner mSpinnerPainterEducation1;
    RelativeLayout mLayoutSubmit;
    RelativeLayout mLayoutcancel;
    static String mStrPainterName1;
    static String mStrPainterPhone1;
    static String mStrPainterExp1;
    static String mStrPainterEdu1;
    EditText mEditPainter1;
    EditText mEditPainterNumber1;
    public static Activity mActivity;
    public static RequestQueue queue;
    static String retailerId;
    static String retailename;
    static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_painter2);
        mSpinnerPainterEducation1 = (Spinner)findViewById(R.id.mSpinnerPainterEducation1);
        mSpinnerPainterExp1 = (Spinner)findViewById(R.id.mSpinnerPainterExp1);
        mLayoutSubmit = findViewById(R.id.mLayoutPlace);
        mLayoutcancel = findViewById(R.id.mLayoutdelete);
        mEditPainterNumber1 = (EditText) findViewById(R.id.mEditPainterNumber1);
        mEditPainter1 = (EditText) findViewById(R.id.mEditPainter1);
        queue = Volley.newRequestQueue(AddPainterActivity.this);
        mActivity = AddPainterActivity.this;
        ArrayAdapter arrayPainterEducation  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrPainterEducation);
        arrayPainterEducation.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPainterEducation1.setAdapter(arrayPainterEducation);
        ArrayAdapter arrayPainterExp  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mStrPainterExp);
        arrayPainterExp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPainterExp1.setAdapter(arrayPainterExp);
        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        retailename = bundle.getString("retailername");
        mLayoutcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
finish();
            }
        });
        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStrPainterName1 = mEditPainter1.getText().toString();
                mStrPainterExp1 = mSpinnerPainterExp1.getSelectedItem().toString();
                mStrPainterEdu1 = mSpinnerPainterEducation1.getSelectedItem().toString();
                mStrPainterPhone1 = mEditPainterNumber1.getText().toString();
                if (mStrPainterName1.length()<0){
                    mShowAlert("Please enter painter",AddPainterActivity.this);
                    return;
                }
               else  if( mStrPainterEdu1.equals("Education")){
                    mShowAlert("Please select painter Education Level",AddPainterActivity.this);
                    return;
                }
                else if(mStrPainterPhone1.length()==0 && mStrPainterPhone1.length()<=10){
                    mShowAlert("Please enter painter phone number",AddPainterActivity.this);
                    return;
                }else if( mStrPainterEdu1.equals("Experience(Yrs)")){
                    mShowAlert("Please select painter Experience",AddPainterActivity.this);
                    return;
                }
                mFunGetMataData1();
            }
        });
    }
    public static void mFunGetMataData1() {

        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"addRetailerPainter",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {

                                    System.out.println("<><><>## calllllll");
                                    mShowAlert(response.getString("success"),mActivity);

                                handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mActivity.finish();
                                    }
                                }, 1000);

                            } else {
                                mShowAlert(mActivity.getResources().getString(R.string.Something),mActivity);
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
                        mShowAlert(mActivity.getString(R.string.Something), mActivity);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", PreferenceManager.getNEROTOKEN(mActivity));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("retailer_id",retailerId);
                params.put("painter_name",mStrPainterName1);
                params.put("painter_phone",mStrPainterPhone1);
                params.put("painter_education",mStrPainterEdu1);
                params.put("painters_experience",mStrPainterExp1);
                return params;
            }
        };

        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }
}