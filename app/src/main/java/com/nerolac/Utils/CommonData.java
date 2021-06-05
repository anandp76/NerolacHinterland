package com.nerolac.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidadvance.topsnackbar.TSnackbar;
import com.nerolac.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.core.content.ContextCompat;

public class CommonData {
    //public static String BaseUrl = "http://hinterland.nerolachub.com/Api/";
    public static String BaseUrl = "https://anmolratan.nerolachub.com/Api/";
    public static ProgressDialog pDialog;
    public static ProgressDialog pDialog22;
    public static void setTranceprent(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity, color));
        }
    }

    public static void showProgress(Context context) {
            pDialog = new ProgressDialog(context);
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            pDialog.setContentView(R.layout.progress_box);
            ImageView imgProgress = (ImageView) pDialog.findViewById(R.id.imgProgress);
            Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.rotating);
            imgProgress.startAnimation(animation1);
      }

    public static void showProgress22(Context context) {
        pDialog22 = new ProgressDialog(context);
        pDialog22.setIndeterminate(true);
        pDialog22.setCancelable(false);
        pDialog22.show();
        pDialog22.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        pDialog22.setContentView(R.layout.progress_box);
        ImageView imgProgress = (ImageView) pDialog22.findViewById(R.id.imgProgress);
        Animation animation1 = AnimationUtils.loadAnimation(context, R.anim.rotating);
        imgProgress.startAnimation(animation1);
    }

    public static void hidePDialog22() {
        if (pDialog22 != null) {
            pDialog22.dismiss();
            pDialog22 = null;
        }
    }

    public static void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    public static void mShowAlert(String str, Activity activity) {
        TSnackbar snackbar = TSnackbar.make(activity.findViewById(android.R.id.content), str, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(activity.getResources().getColor(R.color.colorPrimary));
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(activity.getResources().getColor(R.color.colorPrimary));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        snackbar.show();
    }

    public static String getDay(String mStrDate){
        String mStrResult="";
        try {
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=format1.parse(mStrDate);
        mStrResult = (String) DateFormat.format("dd",   date); // 20
        // String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mStrResult;
    }

    public static String getTime(String mStrDate){
        String mStrResult="";
        try {
        SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=format1.parse(mStrDate);
        mStrResult = (String) DateFormat.format("HH:mm a",   date); // 20
        // String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mStrResult;
    }

    public static String ChangeFormate(String mStrDate){
        String mStrResult="";
        try {
            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=format1.parse(mStrDate);
            mStrResult = (String) DateFormat.format("MMM dd, yyyy HH:mm a",   date); // 20
            // String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mStrResult;
    }

    public static String getTimeformat() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getTimeformatWithoutTime() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static String getTimeformatCurrent() {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, yyyy HH:mm a");
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public static boolean CheckCurrentDate(String mStrDate) {
        boolean mresult = false;
        try {
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        Date date=df.parse(mStrDate);
        String mStrResult = (String) DateFormat.format("yyyy-MM-dd",   date);
        if(mStrResult.equals(formattedDate)){
            mresult=true;
        }else {
            mresult = false;
        }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mresult;
    }

    public static String getMonth(String mStrDate){
        String mStrResult="";
        try {
            SimpleDateFormat format1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date=format1.parse(mStrDate);
            mStrResult  = (String) DateFormat.format("MMM",  date); // Jun
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mStrResult;
    }


}
