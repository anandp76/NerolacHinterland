package com.nerolac;


import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nerolac.Adpter.MyPlanListAdapter;
import com.nerolac.Adpter.SalesOrderListAdapter;
import com.nerolac.DataBase.Database;
import com.nerolac.Modal.Retailers;
import com.nerolac.Utils.ImageUtil;
import com.nerolac.Utils.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;
import static com.nerolac.Utils.CommonData.BaseUrl;
import static com.nerolac.Utils.CommonData.hidePDialog;
import static com.nerolac.Utils.CommonData.hidePDialog22;
import static com.nerolac.Utils.CommonData.mShowAlert;
import static com.nerolac.Utils.CommonData.setTranceprent;
import static com.nerolac.Utils.CommonData.showProgress;
import static com.nerolac.Utils.CommonData.showProgress22;

public class ACTPendingUpload extends Fragment {

    ArrayList<String> mListItem = new ArrayList<>();
    LinearLayout mLayoutUpload;
    Database database;
    ArrayList<Retailers> mListRetailers = new ArrayList<Retailers>();
    RelativeLayout mLayoutSubmit;
    RequestQueue queue;
    String mStrImgOne = "";
    String mStrImgTwo = "";
    String mStrImgThree = "";
    String mStrImgFour = "";
    String mStrImgFive = "";
    String mStrImgSix = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_pendingupload, container, false);
        queue = Volley.newRequestQueue(getActivity());
        database = new Database(getActivity());
        mLayoutUpload = (LinearLayout)view.findViewById(R.id.mLayoutUpload);
        mLayoutSubmit = (RelativeLayout)view.findViewById(R.id.mLayoutSubmit);

        mListRetailers = database.GT_RETAILERS(PreferenceManager.getNEROUSERID(getActivity()));
        for (int i = 0; i<mListRetailers.size();i++){
            final int ak = i;
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.pending_upload_item, mLayoutUpload, false);
            final TextView mTextTitle = (TextView) mViewItemCheckBox.findViewById(R.id.mTextTitle);
            final ImageView mImgUpload = (ImageView) mViewItemCheckBox.findViewById(R.id.mImgUpload);
            final ImageView mImgDelete = (ImageView) mViewItemCheckBox.findViewById(R.id.mImgDelete);
            mImgUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("<><><>XC "+mListRetailers.get(ak).getTbId());
                    showProgress22(getActivity());
                    mFunEnterFormOneByOne(mListRetailers.get(ak));
                }
            });
            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Delete entry");
                    alert.setMessage("Are you sure you want to delete?");
                    alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            dialog.dismiss();
                            System.out.println("<><><>XC "+mListRetailers.get(ak).getTbId());
                            mShowAlert("You have successfully deleted record!!", getActivity());
                            database.DT_Retailers_By_Id(PreferenceManager.getNEROUSERID(getActivity()),mListRetailers.get(ak).getTbId());
                            reload();

                        }
                    });
                    alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // close dialog
                            dialog.cancel();
                        }
                    });
                    alert.show();







                    //showProgress(getActivity());
                    //mFunEnterFormOneByOne(mListRetailers.get(ak));
                }
            });
            mTextTitle.setText(mListRetailers.get(i).getTbShopName());
            mLayoutUpload.addView(mViewItemCheckBox);
        }

        mLayoutSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListRetailers.size()>0){
                    showProgress(getActivity());
                    for (int i = 0; i<mListRetailers.size();i++){
                        mFunEnterForm(mListRetailers.get(i),i,mListRetailers.size());
                    }
                }

            }
        });
        return view;
    }
    

  
    
    void reload(){
        mLayoutUpload.removeAllViews();
        mListRetailers.clear();
        mListRetailers = database.GT_RETAILERS(PreferenceManager.getNEROUSERID(getActivity()));
        for (int i = 0; i<mListRetailers.size();i++){
            View mViewItemCheckBox = getLayoutInflater().inflate(R.layout.pending_upload_item, mLayoutUpload, false);
            final TextView mTextTitle = (TextView) mViewItemCheckBox.findViewById(R.id.mTextTitle);
            mTextTitle.setText(mListRetailers.get(i).getTbShopName());
            mLayoutUpload.addView(mViewItemCheckBox);
        } 
    }
    
    
    void mFunEnterForm(final Retailers retailers,final int curr,final int size) {
        if(retailers.getTbImgOne() != null && !retailers.getTbImgOne().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgOne(), options);
            mStrImgOne = ImageUtil.convert(bitmap);
        }

        if(retailers.getTbImgTwo() != null && !retailers.getTbImgTwo().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgTwo(), options);
            mStrImgTwo = ImageUtil.convert(bitmap);

        }

        if(retailers.getTbImgThree() != null && !retailers.getTbImgThree().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgThree(), options);
            mStrImgThree = ImageUtil.convert(bitmap);
        }

        if(retailers.getTbImgFour() != null && !retailers.getTbImgFour().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgFour(), options);
            mStrImgFour = ImageUtil.convert(bitmap);
        }
        if(retailers.getTbImgFive() != null && !retailers.getTbImgFive().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgFive(), options);
            mStrImgFive = ImageUtil.convert(bitmap);
        }

        if(retailers.getTbImgSix() != null && !retailers.getTbImgSix().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgSix(), options);
            mStrImgSix = ImageUtil.convert(bitmap);
        }
        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"addRetailer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                database.DT_Retailers_By_Id(PreferenceManager.getNEROUSERID(getActivity()),retailers.getTbId());
                                if(curr==size-1){
                                hidePDialog();
                                mShowAlert("You have successfully submitted all record!!", getActivity());
                                //database.DT_Retailers(PreferenceManager.getNEROUSERID(getActivity()));
                                reload();
                                }

                            } else {

                                //mShowAlert("Something went wrong!!", ACTRetailerForm.this);
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
                        System.out.println("<><><>$$ "+error.getMessage());
                        mShowAlert(getString(R.string.Something), getActivity());
                        reload();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(getActivity()));
                System.out.println("<><><>##11   "+params);
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "2");
                params.put("appId", "12");
                params.put("shopName", retailers.getTbShopName());
                params.put("firstname", retailers.getTbFirstName());
                params.put("lastname", retailers.getTbLastName());
                params.put("mobile", retailers.getTbMobile());
                params.put("whatsapp", retailers.getTbWhatsApp());
                params.put("landline", retailers.getTbLandline());
                params.put("outlet_size", retailers.getTbOutletSize());
                params.put("address1", retailers.getTbAddress1());
                params.put("address2", retailers.getTbAddress2());
                params.put("pincode", retailers.getTbPincode());
                params.put("gst_available", retailers.getTbGstAvailable());
                params.put("gst_number", retailers.getTbGstNumber());
                params.put("village", retailers.getTbVillage());
                params.put("block", retailers.getTbBlock());
                params.put("district", retailers.getTbDistrict());
                params.put("tehsil", retailers.getTbTehsil());
                params.put("products_available", retailers.getTbProducts());
                params.put("brand", retailers.getTbBrand());
                params.put("business_in_year", retailers.getTbBusinessInYears());
                params.put("outlet_sales", retailers.getTbOutletSales());
                params.put("paint_sales", retailers.getTbPaintSales());
                params.put("paint_margin", retailers.getTbPaintMargin());
                params.put("delivery_source", retailers.getTbDeliverySource());
                params.put("latitude", retailers.getTbLatitude());
                params.put("longitude",retailers.getTbLongitude());
                params.put("remark", retailers.getTbRemark());
                params.put("outlet_type", retailers.getTbOutletType());
                params.put("paint_available", retailers.getTbPaintAvail());
                if(retailers.getTbPainterNameOne() != null && !retailers.getTbPainterNameOne().isEmpty()){
                params.put("painter1", retailers.getTbPainterNameOne()+","+retailers.getTbPainterExperienceOne()+","+retailers.getTbPainterEducationOne()+","+retailers.getTbPainterPhoneOne());
                }
                if(retailers.getTbPainterNameTwo() != null && !retailers.getTbPainterNameTwo().isEmpty()){
                params.put("painter2", retailers.getTbPainterNameTwo()+","+retailers.getTbPainterExperienceTwo()+","+retailers.getTbPainterEducationTwo()+","+retailers.getTbPainterPhoneTwo());
                }
                if(retailers.getTbPainterNameThree() != null && !retailers.getTbPainterNameThree().isEmpty()){
                params.put("painter3", retailers.getTbPainterNameThree()+","+retailers.getTbPainterExperienceThree()+","+retailers.getTbPainterEducationThree()+","+retailers.getTbPainterPhoneThree());
                }

                if(retailers.getTbPainterNameFour() != null && !retailers.getTbPainterNameFour().isEmpty()){
                params.put("painter4", retailers.getTbPainterNameFour()+","+retailers.getTbPainterExperienceFour()+","+retailers.getTbPainterEducationFour()+","+retailers.getTbPainterPhoneFour());
                }

                if(retailers.getTbPainterNameFive() != null && !retailers.getTbPainterNameFive().isEmpty()){
                params.put("painter5", retailers.getTbPainterNameFive()+","+retailers.getTbPainterExperienceFive()+","+retailers.getTbPainterEducationFive()+","+retailers.getTbPainterPhoneFive());
                }

                if(retailers.getTbSourceName1() != null && !retailers.getTbSourceName1().isEmpty()){
                params.put("source1", retailers.getTbSourceName1()+","+retailers.getTbSourceType1()+","+retailers.getTbSourceContact1()+","+retailers.getTbSourceLocation1());
                }

                if(retailers.getTbSourceName2() != null && !retailers.getTbSourceName2().isEmpty()){
                params.put("source2", retailers.getTbSourceName2()+","+retailers.getTbSourceType2()+","+retailers.getTbSourceContact2()+","+retailers.getTbSourceLocation2());
                }

                if(retailers.getTbSourceName3() != null && !retailers.getTbSourceName3().isEmpty()){
                params.put("source3", retailers.getTbSourceName3()+","+retailers.getTbSourceType3()+","+retailers.getTbSourceContact3()+","+retailers.getTbSourceLocation3());
                }

                if(retailers.getTbSourceName4() != null && !retailers.getTbSourceName4().isEmpty()){
                params.put("source4", retailers.getTbSourceName4()+","+retailers.getTbSourceType4()+","+retailers.getTbSourceContact4()+","+retailers.getTbSourceLocation4());
                }


                if(retailers.getTbImgOne() != null && !retailers.getTbImgOne().isEmpty()){
                    params.put("image1","data:image/jpeg;base64,"+mStrImgOne);
                }

                if(retailers.getTbImgTwo() != null && !retailers.getTbImgTwo().isEmpty()){
                    params.put("image2","data:image/jpeg;base64,"+mStrImgTwo);

                }

                if(retailers.getTbImgThree() != null && !retailers.getTbImgThree().isEmpty()){
                    params.put("image3","data:image/jpeg;base64,"+mStrImgThree);
                }

                if(retailers.getTbImgFour() != null && !retailers.getTbImgFour().isEmpty()){
                    params.put("image4","data:image/jpeg;base64,"+mStrImgFour);
                }

                if(retailers.getTbImgFive() != null && !retailers.getTbImgFive().isEmpty()){
                    params.put("image5","data:image/jpeg;base64,"+mStrImgFive);
                }

                if(retailers.getTbImgSix() != null && !retailers.getTbImgSix().isEmpty()){
                    params.put("image6","data:image/jpeg;base64,"+mStrImgSix);
                }
                //
                //
                //
                System.out.println("<><><>## "+params);
                //Toast.makeText(getActivity(),params.toString(),Toast.LENGTH_LONG).show();
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }

    void mFunEnterFormOneByOne(final Retailers retailers) {
        if(retailers.getTbImgOne() != null && !retailers.getTbImgOne().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgOne(),options);
            mStrImgOne = ImageUtil.convert(bitmap);
        }

        if(retailers.getTbImgTwo() != null && !retailers.getTbImgTwo().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgTwo(),options);
            mStrImgTwo = ImageUtil.convert(bitmap);

        }

        if(retailers.getTbImgThree() != null && !retailers.getTbImgThree().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgThree(),options);
            mStrImgThree = ImageUtil.convert(bitmap);
        }

        if(retailers.getTbImgFour() != null && !retailers.getTbImgFour().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgFour(), options);
            mStrImgFour = ImageUtil.convert(bitmap);
        }
        if(retailers.getTbImgFive() != null && !retailers.getTbImgFive().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgFive(), options);
            mStrImgFive = ImageUtil.convert(bitmap);
        }

        if(retailers.getTbImgSix() != null && !retailers.getTbImgSix().isEmpty()){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            Bitmap bitmap = BitmapFactory.decodeFile(retailers.getTbImgSix(), options);
            mStrImgSix = ImageUtil.convert(bitmap);
        }

        StringRequest strRequest = new StringRequest(Request.Method.POST,BaseUrl+"addRetailer",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String str) {
                        hidePDialog22();
                        try {
                            System.out.println("<><><>" + str);
                            JSONObject response = new JSONObject(str);
                            String mStrStatus = response.getString("statusCode");
                            if (mStrStatus.equals("200")) {
                                //if(curr==size-1){
                                    mShowAlert("You have successfully submitted all record!!", getActivity());
                                    database.DT_Retailers_By_Id(PreferenceManager.getNEROUSERID(getActivity()),retailers.getTbId());
                                    reload();
                                //}

                            } else {
                                reload();
                                mShowAlert("Something went wrong!!", getActivity());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePDialog22();
                        System.out.println("<><><>$$ "+error.getMessage());
                        mShowAlert(getString(R.string.Something), getActivity());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization",PreferenceManager.getNEROTOKEN(getActivity()));
                return params;
            }
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", "2");
                params.put("appId", "12");
                params.put("shopName", retailers.getTbShopName());
                params.put("firstname", retailers.getTbFirstName());
                params.put("lastname", retailers.getTbLastName());
                params.put("mobile", retailers.getTbMobile());
                params.put("whatsapp", retailers.getTbWhatsApp());
                params.put("landline", retailers.getTbLandline());
                params.put("outlet_size", retailers.getTbOutletSize());
                params.put("address1", retailers.getTbAddress1());
                params.put("address2", retailers.getTbAddress2());
                params.put("pincode", retailers.getTbPincode());
                params.put("gst_available", retailers.getTbGstAvailable());
                params.put("gst_number", retailers.getTbGstNumber());
                params.put("village", retailers.getTbVillage());
                params.put("block", retailers.getTbBlock());
                params.put("district", retailers.getTbDistrict());
                params.put("tehsil", retailers.getTbTehsil());
                params.put("products_available", retailers.getTbProducts());
                params.put("brand", retailers.getTbBrand());
                params.put("business_in_year", retailers.getTbBusinessInYears());
                params.put("outlet_sales", retailers.getTbOutletSales());
                params.put("paint_sales", retailers.getTbPaintSales());
                params.put("paint_margin", retailers.getTbPaintMargin());
                params.put("delivery_source", retailers.getTbDeliverySource());
                params.put("latitude", retailers.getTbLatitude());
                params.put("longitude",retailers.getTbLongitude());
                params.put("remark", retailers.getTbRemark());
                params.put("outlet_type", retailers.getTbOutletType());
                params.put("paint_available", retailers.getTbPaintAvail());
                if(retailers.getTbPainterNameOne() != null && !retailers.getTbPainterNameOne().isEmpty()){
                    params.put("painter1", retailers.getTbPainterNameOne()+","+retailers.getTbPainterExperienceOne()+","+retailers.getTbPainterEducationOne()+","+retailers.getTbPainterPhoneOne());
                }
                if(retailers.getTbPainterNameTwo() != null && !retailers.getTbPainterNameTwo().isEmpty()){
                    params.put("painter2", retailers.getTbPainterNameTwo()+","+retailers.getTbPainterExperienceTwo()+","+retailers.getTbPainterEducationTwo()+","+retailers.getTbPainterPhoneTwo());
                }
                if(retailers.getTbPainterNameThree() != null && !retailers.getTbPainterNameThree().isEmpty()){
                    params.put("painter3", retailers.getTbPainterNameThree()+","+retailers.getTbPainterExperienceThree()+","+retailers.getTbPainterEducationThree()+","+retailers.getTbPainterPhoneThree());
                }

                if(retailers.getTbPainterNameFour() != null && !retailers.getTbPainterNameFour().isEmpty()){
                    params.put("painter4", retailers.getTbPainterNameFour()+","+retailers.getTbPainterExperienceFour()+","+retailers.getTbPainterEducationFour()+","+retailers.getTbPainterPhoneFour());
                }

                if(retailers.getTbPainterNameFive() != null && !retailers.getTbPainterNameFive().isEmpty()){
                    params.put("painter5", retailers.getTbPainterNameFive()+","+retailers.getTbPainterExperienceFive()+","+retailers.getTbPainterEducationFive()+","+retailers.getTbPainterPhoneFive());
                }

                if(retailers.getTbImgOne() != null && !retailers.getTbImgOne().isEmpty()){
                    params.put("image1","data:image/jpeg;base64,"+mStrImgOne);
                }
                if(retailers.getTbImgTwo() != null && !retailers.getTbImgTwo().isEmpty()){
                    params.put("image2","data:image/jpeg;base64,"+mStrImgTwo);

                }
                if(retailers.getTbImgThree() != null && !retailers.getTbImgThree().isEmpty()){
                    params.put("image3","data:image/jpeg;base64,"+mStrImgThree);
                }
                if(retailers.getTbImgFour() != null && !retailers.getTbImgFour().isEmpty()){
                    params.put("image4","data:image/jpeg;base64,"+mStrImgFour);
                }

                if(retailers.getTbImgFive() != null && !retailers.getTbImgFive().isEmpty()){
                    params.put("image5","data:image/jpeg;base64,"+mStrImgFive);
                }

                if(retailers.getTbImgSix() != null && !retailers.getTbImgSix().isEmpty()){
                    params.put("image6","data:image/jpeg;base64,"+mStrImgSix);
                }
                System.out.println("<><><>144  "+params);
                //Toast.makeText(getActivity(),params.toString(),Toast.LENGTH_LONG).show();
                return params;
            }
        };
        strRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(strRequest);
    }
}

