package com.nerolac.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by admin1 on 23/5/18.
 */

public class PreferenceManager {

    private static String NERO_PREF = "com.nero.pref";
    private static String NERO_ISLOGIN = "com.nero.pref.is.login";
    private static String NERO_TOKEN = "com.nero.pref.tokenType";
    private static String NERO_USERNAME = "com.nero.pref.username";
    private static String NERO_USERID = "com.nero.pref.user_id";
    private static String NERO_MOBILE = "com.nero.pref.user_mobile";
    private static String NERO_FULLNAME = "com.nero.pref.full_name";
    private static String NERO_STATUS = "com.nero.pref.status";
    private static String NERO_EMAIL = "com.nero.pref.email";
    private static String NERO_LAT = "com.nero.pref.lat";
    private static String NERO_LONG = "com.nero.pref.long";




    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(NERO_PREF, Context.MODE_PRIVATE);
    }


    public static String getNEROLAT(Context context) {
        return getPrefs(context).getString(NERO_LAT, "0");
    }

    public static void setNEROLAT(Context context, String value) {
        getPrefs(context).edit().putString(NERO_LAT, value).commit();
    }

    public static String getNEROLONG(Context context) {
        return getPrefs(context).getString(NERO_LONG, "0");
    }

    public static void setNEROLONG(Context context, String value) {
        getPrefs(context).edit().putString(NERO_LONG, value).commit();
    }



    public static String getNEROISLOGIN(Context context) {
        return getPrefs(context).getString(NERO_ISLOGIN, "0");
    }

    public static void setNEROISLOGIN(Context context, String value) {
        getPrefs(context).edit().putString(NERO_ISLOGIN, value).commit();
    }


    public static String getNEROTOKEN(Context context) {
        return getPrefs(context).getString(NERO_TOKEN, "0");
    }

    public static void setNEROTOKEN(Context context, String value) {
        getPrefs(context).edit().putString(NERO_TOKEN, value).commit();
    }



    public static String getNEROUSERNAME(Context context) {
        return getPrefs(context).getString(NERO_USERNAME, "0");
    }

    public static void setNEROUSERNAME(Context context, String value) {
        getPrefs(context).edit().putString(NERO_USERNAME, value).commit();
    }


    public static String getNEROUSERID(Context context) {
        return getPrefs(context).getString(NERO_USERID, "0");
    }

    public static void setNEROUSERID(Context context, String value) {
        getPrefs(context).edit().putString(NERO_USERID, value).commit();
    }


    public static String getNEROMOBILE(Context context) {
        return getPrefs(context).getString(NERO_MOBILE, "0");
    }

    public static void setNEROMOBILE(Context context, String value) {
        getPrefs(context).edit().putString(NERO_MOBILE, value).commit();
    }

    public static String getNEROFULLNAME(Context context) {
        return getPrefs(context).getString(NERO_FULLNAME, "0");
    }

    public static void setNEROFULLNAME(Context context, String value) {
        getPrefs(context).edit().putString(NERO_FULLNAME, value).commit();
    }


    public static String getNEROSTATUS(Context context) {
        return getPrefs(context).getString(NERO_STATUS, "0");
    }

    public static void setNEROSTATUS(Context context, String value) {
        getPrefs(context).edit().putString(NERO_STATUS, value).commit();
    }


    public static String getNEROEMAIL(Context context) {
        return getPrefs(context).getString(NERO_EMAIL, "0");
    }

    public static void setNEROEMAIL(Context context, String value) {
        getPrefs(context).edit().putString(NERO_EMAIL, value).commit();
    }






}