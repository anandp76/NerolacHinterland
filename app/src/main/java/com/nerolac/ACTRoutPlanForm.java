package com.nerolac;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
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
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Retailers;
import com.nerolac.Utils.FlowLayout;
import com.nerolac.Utils.GPSTracker;
import com.nerolac.Utils.ImageUtil;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_BRANDS;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_OUTLET_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_DEL_SOURCE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_MERGE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PRODUCTS;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_BLOCK;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_DISTRICT;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_STATE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;
import static com.nerolac.Utils.CommonData.getTimeformat;
import static com.nerolac.Utils.CommonData.getTimeformatCurrent;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;

public class ACTRoutPlanForm extends Activity {


    Database database;

    ArrayList<String> mListState = new ArrayList<String>();
    ArrayList<String> mListDistrisct = new ArrayList<String>();
    ArrayList<String> mListTehsil = new ArrayList<String>();
    ArrayList<String> mListBlock = new ArrayList<String>();
    ArrayList<String> mListVillage = new ArrayList<String>();

    RequestQueue queue;

    Spinner mSpinnerState;
    Spinner mSpinnerDistrict;
    Spinner mSpinnerTehsil;
    Spinner mSpinnerBlock;
    AutoCompleteTextView mEditVillage;
    EditText mEditKiloMeter;
    RelativeLayout mLayoutSubmit;

    String mStrARBlock[];
    String mStrARDistrict[];
    String mStrARTehsil[];


    String mStrState;
    String mStrDistrict;
    String mStrTehsil;
    String mStrBlock;
    String mStrVillage;
    String mStrKiloMeter;

    TextView mTextTitlePictures;
    TextView mTextRadioTitle;
    LinearLayout mLayoutPictures;
    RadioGroup radioGrpRetailers;
    ImageView mImgOne;

    String mStrImgOne;
    String mStrSope;

    String mStrRawState = "STATE";
    String mStrRawDistrict = "DISTRICT";
    String mStrRawTehsil = "TEHSIL";
    String mStrRawBlock = "BLOCK";
    String mStrRawVillage;
    String mStrRawKilometer;
    String mStrRawOption;
    String mStrRawTravelId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rout_plan_form);
        queue = Volley.newRequestQueue(ACTRoutPlanForm.this);
        setTranceprent(ACTRoutPlanForm.this,R.color.appblue);

        mSpinnerState = (Spinner)findViewById(R.id.mSpinnerState);
        mSpinnerDistrict = (Spinner)findViewById(R.id.mSpinnerDistrict);
        mSpinnerTehsil = (Spinner)findViewById(R.id.mSpinnerTehsil);
        mSpinnerBlock = (Spinner)findViewById(R.id.mSpinnerBlock);
        mEditVillage = (AutoCompleteTextView) findViewById(R.id.mEditVillage);
        mEditKiloMeter = (EditText) findViewById(R.id.mEditKiloMeter);
        mLayoutSubmit = (RelativeLayout) findViewById(R.id.mLayoutSubmit);
        mTextTitlePictures = (TextView) findViewById(R.id.mTextTitlePictures);
        mTextRadioTitle = (TextView) findViewById(R.id.mTextRadioTitle);
        mLayoutPictures = (LinearLayout) findViewById(R.id.mLayoutPictures);
        radioGrpRetailers = (RadioGroup) findViewById(R.id.radioGrpRetailers);
        mImgOne = (ImageView) findViewById(R.id.mImgOne);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
        mStrRawOption = bundle.getString("mStrRawOption");
        if(mStrRawOption.equals("1")){
        mStrRawState = bundle.getString("mStrRawState");
        mStrRawDistrict = bundle.getString("mStrRawDistrict");
        mStrRawTehsil = bundle.getString("mStrRawTehsil");
        mStrRawBlock = bundle.getString("mStrRawBlock");
        mStrRawVillage = bundle.getString("mStrRawVillage");
        mStrRawKilometer = bundle.getString("mStrRawKilometer");
        mStrRawTravelId = bundle.getString("mStrRawTravelId");
        mEditKiloMeter.setText(mStrRawKilometer);
        mEditVillage.setText(mStrRawVillage);
        mTextRadioTitle.setVisibility(View.GONE);
        radioGrpRetailers.setVisibility(View.GONE);
        }
        mStrARBlock = new String[]{mStrRawBlock};
        mStrARDistrict = new String[]{mStrRawDistrict};
        mStrARTehsil = new String[]{mStrRawTehsil};
        }


        mFunLoadMataData();

        ArrayAdapter arrayState  = new ArrayAdapter(this,android.R.layout.simple_spinner_item,mListState.toArray(new String[mListState.size()]));
        arrayState.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerState.setAdapter(arrayState);

        ArrayAdapter arrayDistrict  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mStrARDistrict);
        arrayDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDistrict.setAdapter(arrayDistrict);

        ArrayAdapter arrayTehsil  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mStrARTehsil);
        arrayTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTehsil.setAdapter(arrayTehsil);

        ArrayAdapter arrayBlock  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mStrARBlock);
        arrayBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerBlock.setAdapter(arrayBlock);

        mSpinnerState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerState.getSelectedItem().toString().equals("STATE")){

                }else {
                    mListDistrisct = database.GT_RAW_LOCATION_DISTRICTLIST(TBL_RAW_LOCATION_STATE,mSpinnerState.getSelectedItem().toString());
                    mListDistrisct.add(0,mStrRawDistrict);
                    ArrayAdapter arrayDistrict  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mListDistrisct.toArray(new String[mListDistrisct.size()]));
                    arrayDistrict.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerDistrict.setAdapter(arrayDistrict);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerDistrict.getSelectedItem().toString().equals("DISTRICT")){

                }else {
                    mListTehsil = database.GT_RAW_LOCATION_TEHSIL(TBL_RAW_LOCATION_DISTRICT,mSpinnerDistrict.getSelectedItem().toString());
                    mListTehsil.add(0,mStrRawTehsil);
                    ArrayAdapter arrayTehsil  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mListTehsil.toArray(new String[mListTehsil.size()]));
                    arrayTehsil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerTehsil.setAdapter(arrayTehsil);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSpinnerTehsil.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerTehsil.getSelectedItem().toString().equals("TEHSIL")){

                }else {
                    mListBlock = database.GT_RAW_LOCATION_BLOCK(TBL_RAW_LOCATION_TEHSIL,mSpinnerTehsil.getSelectedItem().toString());
                    mListBlock.add(0,mStrRawBlock);
                    ArrayAdapter arrayBlock  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mListBlock.toArray(new String[mListBlock.size()]));
                    arrayBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerBlock.setAdapter(arrayBlock);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSpinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mSpinnerBlock.getSelectedItem().toString().equals("BLOCK")){

                }else {
                    mListVillage = database.GT_RAW_LOCATION_VILLAGE(TBL_RAW_LOCATION_BLOCK,mSpinnerBlock.getSelectedItem().toString());
                    ArrayAdapter arrayVillage  = new ArrayAdapter(ACTRoutPlanForm.this,android.R.layout.simple_spinner_item,mListVillage.toArray(new String[mListVillage.size()]));
                    mEditVillage.setAdapter(arrayVillage);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioGrpRetailers.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
                if(checkedRadioButton.getText().toString().equals("Yes")){
                    mTextTitlePictures.setVisibility(View.GONE);
                    mLayoutPictures.setVisibility(View.GONE);
                }else {
                    mTextTitlePictures.setVisibility(View.VISIBLE);
                    mLayoutPictures.setVisibility(View.VISIBLE);
                }
            }
        });

        mImgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 200);
            }
        });

   }

    void mFunLoadMataData(){
        database = new Database(ACTRoutPlanForm.this);
        mListState = database.GT_RAW_LOCATION_STATE(TBL_USER_ID,PreferenceManager.getNEROUSERID(ACTRoutPlanForm.this));
        mListState.add(0,mStrRawState);


        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mStrState = mSpinnerState.getSelectedItem().toString();
                mStrDistrict = mSpinnerDistrict.getSelectedItem().toString();
                mStrTehsil = mSpinnerTehsil.getSelectedItem().toString();
                mStrBlock = mSpinnerBlock.getSelectedItem().toString();
                mStrVillage = mEditVillage.getText().toString();
                mStrKiloMeter = mEditKiloMeter.getText().toString();
                int selectedIdFM = radioGrpRetailers.getCheckedRadioButtonId();
                RadioButton radioSexButton = (RadioButton) findViewById(selectedIdFM);
                mStrSope = radioSexButton.getText().toString();
                if(mStrRawOption.equals("0")){
                    if(mStrState.equals("STATE")){
                        mShowAlert("Please select state!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrDistrict.equals("DISTRICT")){
                        mShowAlert("Please select district!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrTehsil.equals("TEHSIL")){
                        mShowAlert("Please select tehsil!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrBlock.equals("BLOCK")){
                        mShowAlert("Please select block!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrVillage.length()<=0){
                        mShowAlert("Please enter village name!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrKiloMeter.length()<=0){
                        mShowAlert("Please enter kilo meter!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrSope.equals("No") && mStrImgOne.length()<=0){
                        mShowAlert("Please add a picture!", ACTRoutPlanForm.this);
                        return;
                    }
                    showProgress(ACTRoutPlanForm.this);
                    mFunEnterForm();
                }else {
                    if(mStrState.equals("STATE")){
                        mShowAlert("Please select state!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrDistrict.equals("DISTRICT")){
                        mShowAlert("Please select district!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrTehsil.equals("TEHSIL")){
                        mShowAlert("Please select tehsil!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrBlock.equals("BLOCK")){
                        mShowAlert("Please select block!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrVillage.length()<=0){
                        mShowAlert("Please enter village name!", ACTRoutPlanForm.this);
                        return;
                    }else if(mStrKiloMeter.length()<=0){
                        mShowAlert("Please enter kilo meter!", ACTRoutPlanForm.this);
                        return;
                    }
                    showProgress(ACTRoutPlanForm.this);
                    mFunEnterFormEdit();
                }


             /*   mStrShopName = mEditShopName.getText().toString();
                mStrGstAvailble = mSpinnerGSTNumber.getSelectedItem().toString();
                mStrGstNum = mEditGstNum.getText().toString();
                mStrOwnerFirst = mEditOwnerFirstName.getText().toString();
                mStrOwnerLast = mEditOwnerLastName.getText().toString();
                mStrPhone = mEditPhone.getText().toString();
                mStrWhatsNum = mEditWhatsapp.getText().toString();
                mStrLandline = mEditLandline.getText().toString();
                mStrOutletSize = mEditOutLetSize.getText().toString();
                mStrAddress1 = mEditAddress1.getText().toString();
                mStrAddress2 = mEditAddress2.getText().toString();
                mStrPinNum = mEditPinNum.getText().toString();
                mStrTehsil = mSpinnerTehsil.getSelectedItem().toString();
                mStrBlocks = mSpinnerBlock.getSelectedItem().toString();
                mStrDistrict = database.GT_RAW_LOCATION_DISTRICT(mStrBlocks,mStrTehsil).get(0);
                mStrVillage = mEditVilaage.getText().toString();
                mStrReProduct = TextUtils.join(",", mListResultProduct);
                mStrReBrands = TextUtils.join(",", mListResultBrands);
                mStrYearInBussiness = mSpinnerBussinessInYears.getSelectedItem().toString();
                mStrMonthlySales = mSpinnerBusinessTurnover.getSelectedItem().toString();
                mStrPaintSales = mSpinnerPaintTurnover.getSelectedItem().toString();
                mStrPaintMargin = mSpinnerBussinessMargin.getSelectedItem().toString();
                mStrReDelivery = TextUtils.join(",", mListResultDelivery);
                mStrRemark = mEditRemark.getText().toString();
                if(mStrShopName.length()<=0){
                mShowAlert("Please enter shop name!", ACTRoutPlanForm.this);
                return;
                }else if(mStrGstAvailble.equals("GST Available")){
                mShowAlert("Please select gst Available or Not!", ACTRoutPlanForm.this);
                return;
                }else if(mStrGstAvailble.equals("Yes") && mStrGstNum.length()<=0){
                mShowAlert("Please enter gst number!", ACTRoutPlanForm.this);
                return;
                }else if(mStrOwnerFirst.length()<=0){
                    mShowAlert("Please enter owner first name!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrOwnerLast.length()<=0){
                    mShowAlert("Please enter owner last name!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrPhone.length()<=0){
                    mShowAlert("Please enter mobile number!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrWhatsNum.length()<=0){
                    mShowAlert("Please enter whatsapp number!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrLandline.length()<=0){
                    mShowAlert("Please enter landline number!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrOutletSize.length()<=0){
                    mShowAlert("Please enter outlet size!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrAddress1.length()<=0){
                    mShowAlert("Please enter address 1!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrAddress2.length()<=0){
                    mShowAlert("Please enter address 2!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrPinNum.length()<=0){
                    mShowAlert("Please enter pin code!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrTehsil.equals("TEHSIL")){
                    mShowAlert("Please select tehsil!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrBlocks.equals("BLOCK")){
                    mShowAlert("Please select block!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrVillage.length()<=0){
                    mShowAlert("Please enter village!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrReProduct.length()<=0){
                    mShowAlert("Please select at least one product!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrReBrands.length()<=0){
                    mShowAlert("Please select at least one beands!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrYearInBussiness.equals("Years In Business")){
                    mShowAlert("Please select years in business!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrMonthlySales.equals("Monthly Sales")){
                    mShowAlert("Please select monthly sales!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrPaintSales.equals("Paint Sales")){
                    mShowAlert("Please select paint sales!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrPaintSales.equals("Paint Margin")){
                    mShowAlert("Please select paint margin!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrReDelivery.length()<=0){
                    mShowAlert("Please select at least one delivery source!", ACTRoutPlanForm.this);
                    return;
                }else if(mStrRemark.length()<=0){
                    mShowAlert("Please enter remark!", ACTRoutPlanForm.this);
                    return;
                }

                Retailers retailers = new Retailers();
                retailers.setTbAddress1(mStrAddress1);
                retailers.setTbAddress2(mStrAddress2);
                retailers.setTbBlock(mStrBlocks);
                retailers.setTbBrand(mStrReBrands);
                retailers.setTbBusinessInYears(mStrYearInBussiness);
                retailers.setTbDeliverySource(mStrReDelivery);
                retailers.setTbDistrict(mStrDistrict);
                retailers.setTbFirstName(mStrOwnerFirst);
                retailers.setTbGstAvailable(mStrGstAvailble);
                retailers.setTbGstNumber(mStrGstNum);
                retailers.setTbLandline(mStrLandline);
                retailers.setTbLastName(mStrOwnerLast);
                retailers.setTbLatitude("mStr");
                retailers.setTbLongitude("");
                retailers.setTbMobile(mStrPhone);
                retailers.setTbOutletSales(mStrMonthlySales);
                retailers.setTbOutletSize(mStrOutletSize);
                retailers.setTbPaintMargin(mStrPaintMargin);
                retailers.setTbPaintSales(mStrPaintSales);
                retailers.setTbPincode(mStrPinNum);
                retailers.setTbProducts(mStrReProduct);
                retailers.setTbRemark(mStrRemark);
                retailers.setTbShopName(mStrShopName);
                retailers.setTbTehsil(mStrTehsil);
                retailers.setTbUserId(PreferenceManager.getNEROUSERID(ACTRoutPlanForm.this));
                retailers.setTbVillage(mStrVillage);
                retailers.setTbWhatsApp(mStrWhatsNum);
                database.IN_DATA_RETAILERS(retailers);
                mShowAlert("You have successfully submitted!!", ACTRoutPlanForm.this);
                finish();











                //showProgress(ACTRetailerForm.this);
                //mFunEnterForm();*/


            }
        });

    }

    void mFunEnterForm() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/usersTravel",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                mShowAlert("You have successfully submitted!!", ACTRoutPlanForm.this);
                                finish();
                            } else {
                                mShowAlert("Something went wrong!!", ACTRoutPlanForm.this);
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
                        mShowAlert(getString(R.string.Something), ACTRoutPlanForm.this);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(ACTRoutPlanForm.this));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(ACTRoutPlanForm.this));
                params.put("state", mStrState);
                params.put("district",mStrDistrict);
                params.put("tehsil",mStrTehsil);
                params.put("block",mStrBlock);
                params.put("village", mStrVillage);
                params.put("kilometer", mStrKiloMeter);
                params.put("latitude", PreferenceManager.getNEROLAT(ACTRoutPlanForm.this));
                params.put("longitude", PreferenceManager.getNEROLONG(ACTRoutPlanForm.this));
                params.put("shop", mStrSope);
                params.put("image", "data:image/png;base64,"+mStrImgOne);
                System.out.println("<><><>## "+params);
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }
    void mFunEnterFormEdit() {
        StringRequest strRequest = new StringRequest(Request.Method.POST,"http://hinterland.nerolachub.com/Api/editUsersTravel",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                mShowAlert("You have successfully Updated!!", ACTRoutPlanForm.this);
                                finish();
                            } else {
                                mShowAlert("Something went wrong!!", ACTRoutPlanForm.this);
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
                        mShowAlert(getString(R.string.Something), ACTRoutPlanForm.this);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(ACTRoutPlanForm.this));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id",PreferenceManager.getNEROUSERID(ACTRoutPlanForm.this));
                params.put("state", mStrState);
                params.put("district",mStrDistrict);
                params.put("tehsil",mStrTehsil);
                params.put("block",mStrBlock);
                params.put("village", mStrVillage);
                params.put("kilometer", mStrKiloMeter);
                params.put("user_travel_id", mStrRawTravelId);
                System.out.println("<><><>## "+params);
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("<><><>resultcall");
        if (resultCode == RESULT_OK && requestCode == 200) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            bitmap = writeTextOnDrawable(bitmap,getTimeformat());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mImgOne.setImageBitmap(bitmap);
            mStrImgOne = ImageUtil.convert(bitmap);
        }

    }

    private Bitmap writeTextOnDrawable(Bitmap bm, String mStrDate) {
        bm = bm.copy(Bitmap.Config.ARGB_8888, true);
        int width = 400;
        int height = 600;//Math.round(width / aspectRatio);
        bm = Bitmap.createScaledBitmap(bm, width, height, false);
        Canvas canvas = new Canvas(bm);
        canvas.drawBitmap(bm, new Matrix(), null);
        LinearLayout layout = new LinearLayout(ACTRoutPlanForm.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL;
        layout.setBackgroundColor(Color.parseColor("#4D000000"));
        layout.setLayoutParams(params);
        TextView textView = new TextView(ACTRoutPlanForm.this);
        textView.setVisibility(View.VISIBLE);
        textView.setText(" "+mSpinnerBlock.getSelectedItem().toString() +", "+mSpinnerTehsil.getSelectedItem().toString() + " \n " + "Lat - "+PreferenceManager.getNEROLAT(ACTRoutPlanForm.this)+", Long - "+PreferenceManager.getNEROLONG(ACTRoutPlanForm.this)+ " \n " + getTimeformatCurrent() );
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

