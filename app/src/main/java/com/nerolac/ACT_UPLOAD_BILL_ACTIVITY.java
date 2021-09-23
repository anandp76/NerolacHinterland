package com.nerolac;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.nerolac.Modal.OderRetailerModal;
import com.nerolac.Utils.PreferenceManager;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;
import static com.nerolac.Utils.FileUtils.getPath;

public class ACT_UPLOAD_BILL_ACTIVITY extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {


    public static final int RequestPermissionCode = 3, REQUEST_IMAGE_CAPTURE = 101, PICK_FROM_FILE = 6;
    private  final String TAG = getClass().getSimpleName();
    public String Buttonclick = "";
    Context mContext;
    EditText bill_no_ext, amount_ext;
    TextView image_name_ext,date_ext;
    RelativeLayout submit_btn;
    CardView select_image, Select_Date;
    AlertDialog alertDialog;
    Uri imageUri;
    Bitmap UserBitmap;
    private String filedata = "",ImageBase64,Bill_Date;
    ArrayList<String> mUplaodImageByteList = new ArrayList<>();
    Calendar calendar;
    int Year,Month,Day;
    String myFormat = "yyyy-MM-dd";
    SimpleDateFormat sdf;
    OderRetailerModal retailerModal;
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_upload_bill);
        setTranceprent(ACT_UPLOAD_BILL_ACTIVITY.this,R.color.white);
        mContext = this;
        queue = Volley.newRequestQueue(this);

        bill_no_ext = findViewById(R.id.bill_no_ext);
        amount_ext = findViewById(R.id.amount_ext);
        image_name_ext = findViewById(R.id.image_name_ext);
        select_image = findViewById(R.id.select_image);
        Select_Date = findViewById(R.id.Select_Date);
        date_ext = findViewById(R.id.date_ext);
        submit_btn = findViewById(R.id.submit_btn);

        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        sdf = new SimpleDateFormat(myFormat, Locale.US);

        select_image.setOnClickListener(this);
        submit_btn.setOnClickListener(this);
        Select_Date.setOnClickListener(this);
        retailerModal = (OderRetailerModal) getIntent().getSerializableExtra("OderRetailerModal");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.select_image:
                filedata = "";
                //  openFolder();
                if (ContextCompat.checkSelfPermission(ACT_UPLOAD_BILL_ACTIVITY.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    // Camera permission granted
                    selectImage();
                } else {

                    if (Buttonclick.equals("")) {
                        Buttonclick = "1";
                        CheakPermissions();
                    } else if (Buttonclick.equals("1")) {
                        Buttonclick = "2";
                        AccessCamera();
                    } else {
                        AccessCamera();
                    }
                }

                break;

            case R.id.Select_Date:

                ChooseDate();

                break;

            case R.id.submit_btn:

                if (bill_no_ext.getText().toString().isEmpty()){
                    mShowAlert("Please Enter Bill Number", ACT_UPLOAD_BILL_ACTIVITY.this);

                }else if (date_ext.getText().toString().isEmpty()){
                    mShowAlert("Please Select Date", ACT_UPLOAD_BILL_ACTIVITY.this);

               }else if (amount_ext.getText().toString().isEmpty()){
                    mShowAlert("Please Enter Bill Amount", ACT_UPLOAD_BILL_ACTIVITY.this);

                }else if (image_name_ext.getText().toString().isEmpty()){
                    mShowAlert("Please Select Bill Image", ACT_UPLOAD_BILL_ACTIVITY.this);

                }else {
                    showProgress(ACT_UPLOAD_BILL_ACTIVITY.this);
                    submit_btn.setEnabled(false);
                    SubmitBill();
                }

                break;
        }

    }

    private void SubmitBill() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"uploadOrderBill",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                mShowAlert(response.getString("message"), ACT_UPLOAD_BILL_ACTIVITY.this);

                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        onBackPressed();

                                    }
                                }, 1000);

                            } else {
                                submit_btn.setEnabled(true);
                               mShowAlert(response.getString("message"), ACT_UPLOAD_BILL_ACTIVITY.this);
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
                        submit_btn.setEnabled(true);
                        System.out.println("<><><>$$ "+error.getMessage());
                        mShowAlert(getString(R.string.Something), ACT_UPLOAD_BILL_ACTIVITY.this);

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(ACT_UPLOAD_BILL_ACTIVITY.this));
                System.out.println("<><><>##11   "+params);
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                Date today = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateToStr2 = simpleDateFormat.format(today);

                Log.e("dateToStr2", dateToStr2);
                //String dateToStr = simpleDateFormat.format(today);
                params.put("fld_rorder_id", retailerModal.getfld_rorder_id());
                params.put("fld_bill_no", bill_no_ext.getText().toString().trim());
                params.put("fld_bill_date",Bill_Date);
                params.put("fld_bill_amount", amount_ext.getText().toString().trim());
                params.put("fld_bill_copy","data:image/jpeg;base64,"+ ImageBase64);
                params.put("fld_bill_upload_date", dateToStr2);

                System.out.println("<><><>## "+params);
                //Toast.makeText(getActivity(),params.toString(),Toast.LENGTH_LONG).show();
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                8000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }

    private void selectImage() {

        LayoutInflater inflater = (LayoutInflater) ACT_UPLOAD_BILL_ACTIVITY.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.pick_img_layout,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ACT_UPLOAD_BILL_ACTIVITY.this, R.style.MyDialogTheme);

        builder.setView(layout);
        builder.setCancelable(true);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();

        LinearLayout Gallery = layout.findViewById(R.id.Gallery);
        LinearLayout Camera = layout.findViewById(R.id.Camera);

        Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                galleryIntent();
            }
        });

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                cameraIntent();
            }
        });


    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), PICK_FROM_FILE);
    }

    private void cameraIntent() {


        ContentValues values = new ContentValues();
        imageUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    private void AccessCamera() {

        LayoutInflater inflater = (LayoutInflater) ACT_UPLOAD_BILL_ACTIVITY.this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.cam_permission,
                null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(ACT_UPLOAD_BILL_ACTIVITY.this);

        builder.setView(layout);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        TextView Cancle = layout.findViewById(R.id.btn_cancle);
        TextView btn_Ok = layout.findViewById(R.id.btn_OK);


        alertDialog.show();

        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

        btn_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                CheakPermissions();
            }
        });
    }

    private void CheakPermissions() {
        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) ||
                !(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, RequestPermissionCode);


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        Log.e(TAG, "onActivityResult: " + requestCode);
        mUplaodImageByteList = new ArrayList<>();
        switch (requestCode) {

            case PICK_FROM_FILE:
                try {
                    Uri mImageCaptureUri = data.getData();
                    String path = getPath(mContext, mImageCaptureUri); // From Gallery

                    UserBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), mImageCaptureUri);

                    if (path == null) {
                        path = mImageCaptureUri.getPath(); // From File Manager
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
                        Toast.makeText(mContext, "File not valid",Toast.LENGTH_LONG).show();
                    } else {

                        mUplaodImageByteList.clear();
                        mUplaodImageByteList = new ArrayList<>();
                        mUplaodImageByteList.add(path);


                        ExifInterface ei = new ExifInterface(path);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(UserBitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(UserBitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(UserBitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = UserBitmap;
                        }
                        ImageBase64 = getStringImage(rotatedBitmap);

                        image_name_ext.setText(mUplaodImageByteList.get(0).substring(mUplaodImageByteList.lastIndexOf("/") + 1));


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;


            case REQUEST_IMAGE_CAPTURE:


                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                            getContentResolver(), imageUri);

                    UserBitmap = MediaStore.Images.Media.getBitmap(
                            mContext.getContentResolver(), imageUri);


                    String path = getPath(mContext, imageUri); // From Gallery

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
                        Toast.makeText(mContext, "File not valid",Toast.LENGTH_LONG).show();
                    } else {
                        mUplaodImageByteList.clear();
                        mUplaodImageByteList = new ArrayList<>();
                        mUplaodImageByteList.add(path);


                        ExifInterface ei = new ExifInterface(path);
                        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                ExifInterface.ORIENTATION_UNDEFINED);

                        Bitmap rotatedBitmap = null;
                        switch (orientation) {

                            case ExifInterface.ORIENTATION_ROTATE_90:
                                rotatedBitmap = rotateImage(UserBitmap, 90);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_180:
                                rotatedBitmap = rotateImage(UserBitmap, 180);
                                break;

                            case ExifInterface.ORIENTATION_ROTATE_270:
                                rotatedBitmap = rotateImage(UserBitmap, 270);
                                break;

                            case ExifInterface.ORIENTATION_NORMAL:
                            default:
                                rotatedBitmap = UserBitmap;
                        }

                        ImageBase64 = getStringImage(rotatedBitmap);

                        image_name_ext.setText(mUplaodImageByteList.get(0).substring(mUplaodImageByteList.lastIndexOf("/") + 1));

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
        }
    }
    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encoded_image = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.e("encoded_image",encoded_image);
        return encoded_image;
    }


    private void ChooseDate() {
        try {

            calendar = Calendar.getInstance();
            int mYear = calendar.get(Calendar.YEAR);
            int mMonth = calendar.get(Calendar.MONTH);
            int mDay = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.DAY_OF_MONTH, mDay);

            Locale locale = getResources().getConfiguration().locale;
            Locale.setDefault(locale);
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(ACT_UPLOAD_BILL_ACTIVITY.this, Year, Month, Day);

            datePickerDialog.showYearPickerFirst(true);
            datePickerDialog.setMaxDate(calendar);
            datePickerDialog.setThemeDark(false);
            datePickerDialog.setAccentColor(Color.parseColor("#2b7ab9"));
            datePickerDialog.show(getFragmentManager(), "DatePickerDialog");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        StringBuilder Date2 = new StringBuilder();
        Date2.delete(0, Date2.length());
        Date2.append((monthOfYear + 1) < 10 ? "0" : "")
                .append((monthOfYear + 1))
                .append("-").append((dayOfMonth < 10 ? "0" : "")).append(dayOfMonth).append("-").append(year);
        Bill_Date = convertDate(Date2.toString());
        date_ext.setText(Bill_Date);
        Log.e("date", Bill_Date);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static String convertDate(String indate) {
        String formattedDate = "";
        try {
            DateFormat originalFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
            DateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date date1 = originalFormat.parse(indate);
            formattedDate = targetFormat.format(date1);
            Log.e("", "formattedDate1= " + formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formattedDate;
    }
}