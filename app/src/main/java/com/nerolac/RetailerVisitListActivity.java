package com.nerolac;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.AttendenceListAdapter;
import com.nerolac.Adpter.Visiteradapter;
import com.nerolac.Modal.Attendence;
import com.nerolac.Modal.Distributor;
import com.nerolac.Modal.VisitelogModal;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.getTimeformatCurrent;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;
import static com.nerolac.Utils.FileUtils.getPath;
public class RetailerVisitListActivity extends Activity {
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
    public static final int RequestPermissionCode = 3, REQUEST_IMAGE_CAPTURE = 101, PICK_FROM_FILE = 6;
    public static ArrayList<VisitelogModal> mListItem = new ArrayList<>();
    private String filedata = "";
    static String commentvalue = "";
    TextView pagetitel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_visit_list);
        setTranceprent(RetailerVisitListActivity.this,R.color.white);
        mListView = (ListView)findViewById(R.id.mListView);
        mImgAddNew = findViewById(R.id.mImgAddNew);
        pagetitel  = findViewById(R.id.mpagetitel);
        queue = Volley.newRequestQueue(RetailerVisitListActivity.this);
        mActivity = RetailerVisitListActivity.this;
        Bundle bundle = getIntent().getExtras();
        retailerId = bundle.getString("retailerId");
        retailename = bundle.getString("retailername");
        String titel = bundle.getString("shopname");
        pagetitel.setText(titel);
        mFunGetMataData1();
        mImgAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertmessage();
            }
        });
    }
    private void CheakPermissions() {
        if (!(ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) ||
                !(ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(((Activity) mActivity),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, RequestPermissionCode);


        }
    }
    private void cameraIntent() {


        ContentValues values = new ContentValues();
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
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
                    UserBitmap = MediaStore.Images.Media.getBitmap(
                            mActivity.getContentResolver(), imageUri);


                    String path = getPath(mActivity, imageUri); // From Gallery

                    if (path == null) {
                        path = imageUri.getPath(); // From File Manager
                    }
                    filedata = path;
                    Log.e("Activity", "PathHolder22= " + path);


                    String filename = path.substring(path.lastIndexOf("/") + 1);
                    String file;
                    if (filename.indexOf(".") > 0) {
                        file = filename.substring(0, filename.lastIndexOf("."));
                    } else {
                        file = "";
                    }
                    if (TextUtils.isEmpty(file)) {
                        mShowAlert("File not valid", RetailerVisitListActivity.this);
                    } else {

                        long_shot_base64 = getStringImage(UserBitmap);

                    }
                    alertmessage();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("error======>", e.toString());
                }

                break;
        }
    }
    public String getStringImage(Bitmap bm) {
        bm = bm.copy(Bitmap.Config.ARGB_8888, true);
        int width = 400;
        int height = 600;//Math.round(width / aspectRatio);
        bm = Bitmap.createScaledBitmap(bm, width, height, false);
        Canvas canvas = new Canvas(bm);
        canvas.drawBitmap(bm, new Matrix(), null);
        LinearLayout layout = new LinearLayout(mActivity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        layout.setBackgroundColor(Color.parseColor("#4D000000"));
        layout.setLayoutParams(params);
        TextView textView = new TextView(mActivity);
        textView.setVisibility(View.VISIBLE);
        textView.setText(" "+retailename + "\nLat - " + PreferenceManager.getNEROLAT(mActivity) + ", Long - " + PreferenceManager.getNEROLONG(mActivity) + " \n " + getTimeformatCurrent());
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encoded_image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.e("encoded_image", encoded_image);
        return encoded_image;
    }



    public static void mFunGetMataData1() {
        mListItem = new ArrayList<>();
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"RetailerVisitList",
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
                                        String fld_ufname = jsonObject.getString("fld_ufname");
                                        String fld_ulname = jsonObject.getString("fld_ulname");
                                        String fld_file_path = jsonObject.getString("fld_file_path");
                                        String fld_visit_date = jsonObject.getString("fld_visit_date");
                                        String mStrComment = jsonObject.getString("fld_comment");
                                        //String mStrDate = jsonObject.getString("fld_created");
                                        VisitelogModal attendence = new VisitelogModal();
                                        attendence.setfld_ufname(fld_ufname);
                                        attendence.setfld_file_path("https://anmolratan.nerolachub.com/" +fld_file_path);
                                        attendence.setfld_ulname(fld_ulname);
                                        attendence.setfld_visit_date(fld_visit_date);

                                        mListItem.add(attendence);
                                        commentvalue = "";
                                        long_shot_base64 ="";

                                    }
                                    Visiteradapter retailerListAdapter = new Visiteradapter(mActivity,mListItem);
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
                params.put("fld_rid",retailerId);
                return params;
            }
        };

        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }
    private void alertmessage() {

        LayoutInflater inflater = (LayoutInflater)
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.addvisitlayout,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mActivity, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();

        TextView title_popup = layout.findViewById(R.id.title_popup);
        EditText comment = layout.findViewById(R.id.mTextComment);
        TextView select_image = layout.findViewById(R.id.select_image);
        TextView no_text_popup = layout.findViewById(R.id.no_text_popup);
        TextView yes_text_popup = layout.findViewById(R.id.yes_text_popup);
        comment.setText(commentvalue);
        select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              commentvalue = comment.getText().toString();
                if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted
                    alertDialog.dismiss();
                    cameraIntent();
                } else {
                    CheakPermissions();

                }
            }
        });
        yes_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                if(long_shot_base64 !=""){
                    showProgress(mActivity);
                    addvisitrecord(comment.getText().toString());

                }
                else{
                    mShowAlert("please take visit farme",mActivity);
                }

            }
        });

        no_text_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
    public static void addvisitrecord(String comment) {
        mListItem = new ArrayList<>();
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
                                mShowAlert(response.getString("message"),mActivity);
                                showProgress(mActivity);

                                mFunGetMataData1();
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
                Date today = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                String dateToStr2 = simpleDateFormat.format(today);

                Map<String, String> params = new HashMap<String, String>();
                params.put("fld_rid",retailerId);
                params.put("fld_uid",PreferenceManager.getNEROUSERID(mActivity));
                params.put("fld_visit_date",dateToStr2);
                params.put("image",long_shot_base64);

                params.put("fld_lat",PreferenceManager.getNEROLAT(mActivity));
                params.put("fld_long",PreferenceManager.getNEROLONG(mActivity));
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
}