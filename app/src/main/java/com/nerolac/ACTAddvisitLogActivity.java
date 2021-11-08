package com.nerolac;

import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.getTimeformat;
import static com.nerolac.Utils.CommonData.getTimeformatCurrent;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.nerolac.Utils.ImageUtil;
import com.nerolac.Utils.PreferenceManager;

import androidx.core.content.ContextCompat;


import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class ACTAddvisitLogActivity extends Activity {


    Context mContext;
    EditText comment;
    ImageView mImgview;

    static String retailename;
    static String vilage;
    static String tehsil;
    TextView pagetitel;
    private static String long_shot_base64;
    TextView select_image,no_text_popup,yes_text_popup;
    public static RequestQueue queue;
    static String retailerId;
    public static Activity mActivity;
    public static final int RequestPermissionCode = 3, REQUEST_IMAGE_CAPTURE = 101, PICK_FROM_FILE = 6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actaddvisit_log);
        setTranceprent(ACTAddvisitLogActivity.this,R.color.white);
         comment = findViewById(R.id.mTextComment);
         select_image = findViewById(R.id.select_image);
         no_text_popup = findViewById(R.id.no_text_popup);
         yes_text_popup = findViewById(R.id.yes_text_popup);
        mImgview = findViewById(R.id.mImgTwo);
        pagetitel  = findViewById(R.id.mpagetitel);
        mContext = ACTAddvisitLogActivity.this;
        mActivity = ACTAddvisitLogActivity.this;
        queue = Volley.newRequestQueue(ACTAddvisitLogActivity.this);
        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        retailename = bundle.getString("retailername");
        vilage = bundle.getString("vilage");
        tehsil = bundle.getString("tehsil");
        String titel = bundle.getString("shopname");
        pagetitel.setText(titel);
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }else {
                    cameraIntent();
                }
            }
        });
        mImgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }else {
                    cameraIntent();
                }
            }
        });
        yes_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(long_shot_base64 !=""){

                    if(PreferenceManager.getNEROLAT(mContext) !="0.0" || PreferenceManager.getNEROLAT(mContext) !="0") {
                        //alertDialog.dismiss();
                        showProgress(mActivity);
                        addvisitrecord(comment.getText().toString());
                    }else{
                        mShowAlert("P lease turn on Gps first! Or update your gps location ",ACTAddvisitLogActivity.this);
                    }

                }
                else{
                    mShowAlert("please take visit farme",ACTAddvisitLogActivity.this);
                }

            }
        });

        no_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }
    private void cameraIntent() {
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED  && ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&  ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
        }else{
            Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
//
//        ContentValues values = new ContentValues();
//        imageUri = getContentResolver().insert(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    public void addvisitrecord(String comment) {
       // mListItem = new ArrayList<>();
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"RetailerVisitAdd",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            JSONObject response = new JSONObject(str);
                            System.out.println("<><><>## " + response.toString());
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                hidePDialog();
                                String msg = response.optString("message");
                                if(msg.equals("")){
                                     msg = response.optString("error");
                                }
                                else{
                                    finish();
                                }

                                mShowAlert(msg,mActivity);



                            } else {
                                mShowAlert(response.getString("message"),mActivity);
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
                        mShowAlert(mContext.getString(R.string.Something), ACTAddvisitLogActivity.this);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", PreferenceManager.getNEROTOKEN(mContext));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Date today = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateToStr2 = simpleDateFormat.format(today);

                Map<String, String> params = new HashMap<String, String>();
                params.put("fld_rid",retailerId);
                params.put("fld_uid",PreferenceManager.getNEROUSERID(mContext));
                params.put("fld_visit_date",dateToStr2);
                params.put("image",long_shot_base64);

                params.put("fld_lat",PreferenceManager.getNEROLAT(mContext));
                params.put("fld_long",PreferenceManager.getNEROLONG(mContext));
                params.put("fld_comment",comment);
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        Log.e("TAG", "onActivityResult: " + requestCode);
        switch (requestCode) {



            case REQUEST_IMAGE_CAPTURE:


                try {

                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    bitmap = writeTextOnDrawable(bitmap,getTimeformat());
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    //bitmap3 = bitmap;
                    mImgview.setImageBitmap(bitmap);
                    long_shot_base64 = ImageUtil.convert(bitmap);

//                    UserBitmap = MediaStore.Images.Media.getBitmap(
//                            mActivity.getContentResolver(), imageUri);
//
//
//                    String path = getPath(mActivity, imageUri); // From Gallery
//
//                    if (path == null) {
//                        path = imageUri.getPath(); // From File Manager
//                    }
//                    filedata = path;
//                    Log.e("Activity", "PathHolder22= " + path);
//
//
//                    String filename = path.substring(path.lastIndexOf("/") + 1);
//                    String file;
//                    if (filename.indexOf(".") > 0) {
//                        file = filename.substring(0, filename.lastIndexOf("."));
//                    } else {
//                        file = "";
//                    }
//                    if (TextUtils.isEmpty(file)) {
//                        mShowAlert("File not valid", RetailerVisitListActivity.this);
//                    } else {
//
//                        long_shot_base64 = getStringImage(UserBitmap);
//
//                    }
                    //alertmessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error======>", e.toString());
                }

                break;
        }
    }


    private Bitmap writeTextOnDrawable(Bitmap bm, String mStrDate) {
        bm = bm.copy(Bitmap.Config.ARGB_8888, true);
        int width = 600;
        int height = 800;//Math.round(width / aspectRatio);
        bm = Bitmap.createScaledBitmap(bm, width, height, false);
        Canvas canvas = new Canvas(bm);
        canvas.drawBitmap(bm, new Matrix(), null);
        LinearLayout layout = new LinearLayout(ACTAddvisitLogActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        layout.setBackgroundColor(Color.parseColor("#4D000000"));
        layout.setLayoutParams(params);
        TextView textView = new TextView(ACTAddvisitLogActivity.this);
        textView.setVisibility(View.VISIBLE);
        textView.setText(" "+retailename + "\n"+vilage + tehsil+"\nLat - " + PreferenceManager.getNEROLAT(mContext) + ", Long - " + PreferenceManager.getNEROLONG(mContext) + " \n " + getTimeformatCurrent());
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setPadding(5, 15, 5, 15);
        textView.setTextSize(6);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(params);
        layout.addView(textView);
        layout.measure(bm.getWidth(), 100);
        layout.layout(0, 0, bm.getWidth(), 100);
        canvas.translate(0, bm.getHeight() - 100);
        layout.draw(canvas);
        return bm;
    }
}