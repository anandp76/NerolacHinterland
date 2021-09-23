package com.nerolac;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.Painteradapter;
import com.nerolac.Adpter.Visiteradapter;
import com.nerolac.Modal.PainterModal;
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
import static com.nerolac.Utils.CommonData.setTranceprent;

public class PainterActivity extends Activity {
    public static Activity mActivity;
    static ListView mListView;
    public static RequestQueue queue;
    static String retailerId;
    private static String long_shot_base64;
    ImageView mImgAddNew;
    AlertDialog alertDialog;
    Uri imageUri;
    Bitmap UserBitmap;
    static String retailename;
    static String vilage;
    static String tehsil;
    public static final int RequestPermissionCode = 3, REQUEST_IMAGE_CAPTURE = 101, PICK_FROM_FILE = 6;
    public static ArrayList<PainterModal> mListItem = new ArrayList<>();
    private String filedata = "";
    static String commentvalue = "";
    TextView pagetitel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);
       // setContentView(R.layout.activity_retailer_visit_list);
        setTranceprent(PainterActivity.this,R.color.white);
        mListView = (ListView)findViewById(R.id.mListView);
        mImgAddNew = findViewById(R.id.mImgAddNew);
        pagetitel  = findViewById(R.id.mpagetitel);

        queue = Volley.newRequestQueue(PainterActivity.this);
        mActivity = PainterActivity.this;
        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        retailename = bundle.getString("retailername");
        vilage = bundle.getString("vilage");
        tehsil = bundle.getString("tehsil");
        String titel = bundle.getString("shopname");
        pagetitel.setText(titel);
       // mFunGetMataData1();

        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // alertmessage();
                Intent intent = new Intent(mActivity, AddPainterActivity.class);
                intent.putExtra("retailerId", retailerId);
                intent.putExtra("retailername", retailename);

               startActivity(intent);
            }
        });

    }
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of LoginFragment");
        mListItem.clear();
        //showProgress(getActivity());
        mFunGetMataData1();
        super.onResume();
    }

    public static void mFunGetMataData1() {
        mListItem = new ArrayList<>();
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"getRetailerPainter",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                if(response.has("data")){
                                    JSONArray jsonArrayProducts = response.getJSONArray("data");
                                    for (int j = 0; j<jsonArrayProducts.length();j++) {
                                        JSONObject jsonObject = jsonArrayProducts.getJSONObject(j);
                                        String fld_experience = jsonObject.getString("fld_experience");
                                        String fld_education = jsonObject.getString("fld_education");
                                        String fld_painter_name = jsonObject.getString("fld_painter_name");
                                        String fld_rpid = jsonObject.getString("fld_rpid");
                                        String mStrComment = jsonObject.getString("fld_phone");
                                        //String mStrDate = jsonObject.getString("fld_created");
                                        PainterModal attendence = new PainterModal();
                                        attendence.setmfld_rpid(fld_rpid);
                                        attendence.setmStrfld_painter_name(fld_painter_name);
                                        attendence.setmStrfld_education(fld_education);
                                        attendence.setmStrfld_experience(fld_experience);

                                        mListItem.add(attendence);

                                    }
                                    Painteradapter retailerListAdapter = new Painteradapter(mActivity,mListItem);
                                    mListView.setAdapter(retailerListAdapter);
                                }else {
                                    System.out.println("<><><>## calllllll");
                                    mShowAlert(response.getString("message"),mActivity);

                                    //  mLayoutDayOut.setBackgroundColor(Color.parseColor("#B3B3B3"));
                                }


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