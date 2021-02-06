package com.nerolac.DataBase;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.nerolac.Modal.RawData;
import com.nerolac.Modal.RawLocation;
import com.nerolac.Modal.Retailers;

import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_ASSETS;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_BNS_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_BNS_COVERAGE_RETAILERS;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_BNS_COVERAGE_VILLAGES;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_BNS_TERRITORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_BNS_TYPE;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_INTEREST_LEVEL;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_MONTHLY_TURNOVER;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_PRODUCT_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.CT_TBL_DMD_WILLINGNESS_TO_INVEST;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_ASSETS;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_COVERAGE_RETAILERS;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_COVERAGE_VILLAGES;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_TERRITORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_BNS_TYPE;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_INTEREST_LEVEL;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_MONTHLY_TURNOVER;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_PRODUCT_CATEGORY;
import static com.nerolac.DataBase.DataBaseStringDistributor.TBL_DMD_WILLINGNESS_TO_INVEST;
import static com.nerolac.DataBase.DataBaseStringDistributor.TD_DATA;
import static com.nerolac.DataBase.DataBaseStringDistributor.TD_USER_ID;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RAW_LOCATION;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RETAILER;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_BRANDS;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_OUTLET_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_PAINT_DEL_SOURCE;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_PAINT_MERGE;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_PAINT_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_PRODUCTS;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_ADDRESS1;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_ADDRESS2;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_BLOCK;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_BRANDS;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_BUSINESSINYEARS;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_DELIVERY;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_DISTRICT;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_FIRSTNAME;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_GSTAVAILABLE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_GST_NUMBER;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_ID;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_IMGONE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_IMGTHREE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_IMGTWO;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_LANDLINE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_LASTNAME;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_LATITUDE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_LONGITUDE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_MOBILE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_OUTLETSALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_OUTLETSIZE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_PAINTMARGIN;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_PAINTSALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_PINCODE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_PRODUCTS;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_REMARK;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_SHOPNAME;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_USERID;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_VILLAGE;
import static com.nerolac.DataBase.DataBaseStringRetailer.RT_WHATSAPP;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RAW_LOCATION;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RETAILER;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_BNS_IN_YEAR;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_BRANDS;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_OUTLET_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_DEL_SOURCE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_MERGE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PAINT_SALES;
import static com.nerolac.DataBase.DataBaseStringRetailer.TABLE_RMD_PRODUCTS;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_BLOCK;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_DISTRICT;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_PLAN;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_STATE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_TEHSIL;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RAW_LOCATION_VILLAGE;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_RMD_STR;
import static com.nerolac.DataBase.DataBaseStringRetailer.TBL_USER_ID;

public class Database extends SQLiteOpenHelper {

    Context context;

    public Database(Context c) {
        super(c, "HinterlandLm.db", null, 6);
        this.context = c;
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(CT_TABLE_RAW_LOCATION);
        db.execSQL(CT_TABLE_RMD_PRODUCTS);
        db.execSQL(CT_TABLE_RMD_BRANDS);
        db.execSQL(CT_TABLE_RMD_BNS_IN_YEAR);
        db.execSQL(CT_TABLE_RMD_OUTLET_SALES);
        db.execSQL(CT_TABLE_RMD_PAINT_SALES);
        db.execSQL(CT_TABLE_RMD_PAINT_MERGE);
        db.execSQL(CT_TABLE_RMD_PAINT_DEL_SOURCE);
        db.execSQL(CT_TABLE_RETAILER);

        db.execSQL(CT_TBL_DMD_BNS_CATEGORY);
        db.execSQL(CT_TBL_DMD_BNS_TERRITORY);
        db.execSQL(CT_TBL_DMD_BNS_IN_YEAR);
        db.execSQL(CT_TBL_DMD_PRODUCT_CATEGORY);
        db.execSQL(CT_TBL_DMD_BNS_TYPE);
        db.execSQL(CT_TBL_DMD_MONTHLY_TURNOVER);
        db.execSQL(CT_TBL_DMD_BNS_COVERAGE_VILLAGES);
        db.execSQL(CT_TBL_DMD_BNS_COVERAGE_RETAILERS);
        db.execSQL(CT_TBL_DMD_WILLINGNESS_TO_INVEST);
        db.execSQL(CT_TBL_DMD_ASSETS);
        db.execSQL(CT_TBL_DMD_INTEREST_LEVEL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("drop table if exists " + TABLE_RAW_LOCATION);
        db.execSQL("drop table if exists " + TABLE_RMD_PRODUCTS);
        db.execSQL("drop table if exists " + TABLE_RMD_BRANDS);
        db.execSQL("drop table if exists " + TABLE_RMD_BNS_IN_YEAR);
        db.execSQL("drop table if exists " + TABLE_RMD_OUTLET_SALES);
        db.execSQL("drop table if exists " + TABLE_RMD_PAINT_SALES);
        db.execSQL("drop table if exists " + TABLE_RMD_PAINT_SALES);
        db.execSQL("drop table if exists " + TABLE_RMD_PAINT_MERGE);
        db.execSQL("drop table if exists " + TABLE_RMD_PAINT_DEL_SOURCE);
        db.execSQL("drop table if exists " + TABLE_RETAILER);

        db.execSQL("drop table if exists " + TBL_DMD_INTEREST_LEVEL);
        db.execSQL("drop table if exists " + TBL_DMD_ASSETS);
        db.execSQL("drop table if exists " + TBL_DMD_WILLINGNESS_TO_INVEST);
        db.execSQL("drop table if exists " + TBL_DMD_BNS_COVERAGE_RETAILERS);
        db.execSQL("drop table if exists " + TBL_DMD_BNS_COVERAGE_VILLAGES);
        db.execSQL("drop table if exists " + TBL_DMD_MONTHLY_TURNOVER);
        db.execSQL("drop table if exists " + TBL_DMD_BNS_TYPE);
        db.execSQL("drop table if exists " + TBL_DMD_PRODUCT_CATEGORY);
        db.execSQL("drop table if exists " + TBL_DMD_BNS_IN_YEAR);
        db.execSQL("drop table if exists " + TBL_DMD_BNS_TERRITORY);
        db.execSQL("drop table if exists " + TBL_DMD_BNS_CATEGORY);

        onCreate(db);
    }


    public void IN_DATA_RETAILERS(Retailers retailers) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RT_GSTAVAILABLE, retailers.getTbGstAvailable());
        values.put(RT_USERID, retailers.getTbUserId());
        values.put(RT_PINCODE, retailers.getTbPincode());
        values.put(RT_ADDRESS2, retailers.getTbAddress2());
        values.put(RT_ADDRESS1, retailers.getTbAddress1());
        values.put(RT_OUTLETSIZE, retailers.getTbOutletSize());
        values.put(RT_LANDLINE, retailers.getTbLandline());
        values.put(RT_WHATSAPP, retailers.getTbWhatsApp());
        values.put(RT_MOBILE, retailers.getTbMobile());
        values.put(RT_LASTNAME, retailers.getTbLastName());
        values.put(RT_FIRSTNAME, retailers.getTbFirstName());
        values.put(RT_SHOPNAME, retailers.getTbShopName());
        values.put(RT_REMARK, retailers.getTbRemark());
        values.put(RT_LONGITUDE, retailers.getTbLongitude());
        values.put(RT_LATITUDE, retailers.getTbLatitude());
        values.put(RT_DELIVERY, retailers.getTbDeliverySource());
        values.put(RT_PAINTMARGIN, retailers.getTbPaintMargin());
        values.put(RT_PAINTSALES, retailers.getTbPaintSales());
        values.put(RT_OUTLETSALES, retailers.getTbOutletSales());
        values.put(RT_BUSINESSINYEARS, retailers.getTbBusinessInYears());
        values.put(RT_BRANDS, retailers.getTbBrand());
        values.put(RT_PRODUCTS, retailers.getTbProducts());
        values.put(RT_TEHSIL, retailers.getTbTehsil());
        values.put(RT_GST_NUMBER, retailers.getTbGstNumber());
        values.put(RT_VILLAGE, retailers.getTbVillage());
        values.put(RT_BLOCK, retailers.getTbBlock());
        values.put(RT_DISTRICT, retailers.getTbDistrict());
        values.put(RT_IMGONE, retailers.getTbImgOne());
        values.put(RT_IMGTWO, retailers.getTbImgTwo());
        values.put(RT_IMGTHREE, retailers.getTbImgThree());
        db.insert(TABLE_RETAILER, null, values);
        db.close();
    }

    public ArrayList<Retailers> GT_RETAILERS(String mStrUserId) {
        ArrayList<Retailers> mListLocation = new ArrayList<Retailers>();
        String selectQuery = "SELECT * FROM " + TABLE_RETAILER + " WHERE " + RT_USERID +  " = '" + mStrUserId + "'";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                Retailers retailers = new Retailers();
                retailers.setTbId(c.getInt(c.getColumnIndex(RT_ID))+"");
                retailers.setTbAddress1(c.getString((c.getColumnIndex(RT_ADDRESS1))));
                retailers.setTbAddress2(c.getString((c.getColumnIndex(RT_ADDRESS2))));
                retailers.setTbBlock(c.getString((c.getColumnIndex(RT_BLOCK))));
                retailers.setTbBrand(c.getString((c.getColumnIndex(RT_BRANDS))));
                retailers.setTbBusinessInYears(c.getString((c.getColumnIndex(RT_BUSINESSINYEARS))));
                retailers.setTbDeliverySource(c.getString((c.getColumnIndex(RT_DELIVERY))));
                retailers.setTbDistrict(c.getString((c.getColumnIndex(RT_DISTRICT))));
                retailers.setTbFirstName(c.getString((c.getColumnIndex(RT_FIRSTNAME))));
                retailers.setTbGstAvailable(c.getString((c.getColumnIndex(RT_GSTAVAILABLE))));
                retailers.setTbGstNumber(c.getString((c.getColumnIndex(RT_GST_NUMBER))));
                retailers.setTbLandline(c.getString((c.getColumnIndex(RT_LANDLINE))));
                retailers.setTbLastName(c.getString((c.getColumnIndex(RT_LASTNAME))));
                retailers.setTbLatitude(c.getString((c.getColumnIndex(RT_LATITUDE))));
                retailers.setTbLongitude(c.getString((c.getColumnIndex(RT_LONGITUDE))));
                retailers.setTbMobile(c.getString((c.getColumnIndex(RT_MOBILE))));
                retailers.setTbOutletSales(c.getString((c.getColumnIndex(RT_OUTLETSALES))));
                retailers.setTbOutletSize(c.getString((c.getColumnIndex(RT_OUTLETSIZE))));
                retailers.setTbPaintMargin(c.getString((c.getColumnIndex(RT_PAINTMARGIN))));
                retailers.setTbPaintSales(c.getString((c.getColumnIndex(RT_PAINTSALES))));
                retailers.setTbPincode(c.getString((c.getColumnIndex(RT_PINCODE))));
                retailers.setTbProducts(c.getString((c.getColumnIndex(RT_PRODUCTS))));
                retailers.setTbRemark(c.getString((c.getColumnIndex(RT_REMARK))));
                retailers.setTbShopName(c.getString((c.getColumnIndex(RT_SHOPNAME))));
                retailers.setTbTehsil(c.getString((c.getColumnIndex(RT_TEHSIL))));
                retailers.setTbUserId(c.getString((c.getColumnIndex(RT_USERID))));
                retailers.setTbVillage(c.getString((c.getColumnIndex(RT_VILLAGE))));
                retailers.setTbWhatsApp(c.getString((c.getColumnIndex(RT_WHATSAPP))));
                retailers.setTbImgOne(c.getString((c.getColumnIndex(RT_IMGONE))));
                retailers.setTbImgTwo(c.getString((c.getColumnIndex(RT_IMGTWO))));
                retailers.setTbImgThree(c.getString((c.getColumnIndex(RT_IMGTHREE))));
                mListLocation.add(retailers);
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public void DT_Retailers(String mStrUserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RETAILER, RT_USERID + " = " + mStrUserId, null);
        db.close();
    }

    public void DT_Retailers_By_Id(String mStrUserId,String mStrId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RETAILER, RT_USERID + " = " + mStrUserId + " AND "+ RT_ID +" = "+mStrId, null);
        db.close();
    }


    public void IN_RAW_LOCATION(RawLocation rawLocation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_RAW_LOCATION_STATE, rawLocation.getmStrState());
        values.put(TBL_RAW_LOCATION_DISTRICT, rawLocation.getmStrDistrict());
        values.put(TBL_RAW_LOCATION_TEHSIL, rawLocation.getmStrTehsil());
        values.put(TBL_RAW_LOCATION_BLOCK, rawLocation.getmStrBlock());
        values.put(TBL_RAW_LOCATION_VILLAGE, rawLocation.getmStrVillage());
        values.put(TBL_RAW_LOCATION_PLAN, rawLocation.getmStrPlan());
        values.put(TBL_USER_ID, rawLocation.getmStrUserId());
        db.insert(TABLE_RAW_LOCATION, null, values);
        db.close();
    }

    public ArrayList<RawLocation> GT_RAW_LOCATION(String mStrKey,String mStrValue) {
        ArrayList<RawLocation> mListLocation = new ArrayList<RawLocation>();
        String selectQuery = "SELECT * FROM " + TABLE_RAW_LOCATION + " WHERE " + mStrKey +  " = '" + mStrValue + "'";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                RawLocation rawLocation = new RawLocation();
                rawLocation.setmStrState(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_STATE))));
                rawLocation.setmStrDistrict(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_DISTRICT))));
                rawLocation.setmStrTehsil(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_TEHSIL))));
                rawLocation.setmStrBlock(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_BLOCK))));
                rawLocation.setmStrVillage(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_VILLAGE))));
                rawLocation.setmStrPlan(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_PLAN))));
                mListLocation.add(rawLocation);
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }


    public ArrayList<String> GT_RAW_LOCATION_STATE(String mStrKey,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT tbRawState FROM " + TABLE_RAW_LOCATION + " WHERE " + mStrKey +  " = '" + mStrValue + "' ORDER BY "+TBL_RAW_LOCATION_STATE;
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                mListLocation.add(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_STATE))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public ArrayList<String> GT_RAW_LOCATION_DISTRICTLIST(String mStrKey,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT tbRawDistrict FROM " + TABLE_RAW_LOCATION + " WHERE " + mStrKey +  " = '" + mStrValue + "' ORDER BY "+TBL_RAW_LOCATION_DISTRICT;
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                mListLocation.add(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_DISTRICT))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public ArrayList<String> GT_RAW_LOCATION_TEHSIL(String mStrKey,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT tbRawTehsil FROM " + TABLE_RAW_LOCATION + " WHERE " + mStrKey +  " = '" + mStrValue + "' ORDER BY "+TBL_RAW_LOCATION_TEHSIL;
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
               mListLocation.add(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_TEHSIL))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public ArrayList<String> GT_RAW_LOCATION_BLOCK(String mStrKey,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT tbRawBlock FROM " + TABLE_RAW_LOCATION + " WHERE " + mStrKey +  " = '" + mStrValue + "' ORDER BY "+TBL_RAW_LOCATION_BLOCK;
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                mListLocation.add(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_BLOCK))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public ArrayList<String> GT_RAW_LOCATION_VILLAGE(String mStrKey,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_RAW_LOCATION + " WHERE " + mStrKey +  " = '" + mStrValue + "' ORDER BY "+TBL_RAW_LOCATION_VILLAGE;
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                mListLocation.add(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_VILLAGE))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public ArrayList<String> GT_RAW_LOCATION_DISTRICT(String mStrBlock,String mStrTehsil) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT DISTINCT tbRawDistrict FROM " + TABLE_RAW_LOCATION + " WHERE tbRawBlock = '" + mStrBlock + "' AND tbRawTehsil = '" + mStrTehsil + "'";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                mListLocation.add(c.getString((c.getColumnIndex(TBL_RAW_LOCATION_DISTRICT))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public void IN_RAW_DMD_DATA(RawData rawData,String mStrTblName) {
        System.out.println("<><><>## call 1");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TD_USER_ID,rawData.getmStrUserId());
        values.put(TD_DATA,rawData.getmStrValue());
        db.insert(mStrTblName, null, values);
        db.close();
    }

    public ArrayList<String> GT_DAW_DATA(String mStrTblName,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + mStrTblName + " WHERE " + TD_USER_ID +  " = '" + mStrValue + "'";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                mListLocation.add(c.getString((c.getColumnIndex(TD_DATA))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public void IN_RAW_RMD_PRODUCTS(RawData rawData) {
        System.out.println("<><><>## call 1");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID,rawData.getmStrUserId());
        values.put(TBL_RMD_STR,rawData.getmStrValue());
        db.insert(TABLE_RMD_PRODUCTS, null, values);
        db.close();
    }


    public void IN_RAW_RMD_BRANDS(RawData rawData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID, rawData.getmStrUserId());
        values.put(TBL_RMD_STR, rawData.getmStrValue());
        db.insert(TABLE_RMD_BRANDS, null, values);
        db.close();
    }



    public void IN_RAW_RMD_BNS_IN_YEAR(RawData rawData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID, rawData.getmStrUserId());
        values.put(TBL_RMD_STR, rawData.getmStrValue());
        db.insert(TABLE_RMD_BNS_IN_YEAR, null, values);
        db.close();
    }


    public void IN_RAW_RMD_OUTLET_SALES(RawData rawData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID, rawData.getmStrUserId());
        values.put(TBL_RMD_STR, rawData.getmStrValue());
        db.insert(TABLE_RMD_OUTLET_SALES, null, values);
        db.close();
    }


    public void IN_RAW_RMD_PAINT_SALES(RawData rawData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID, rawData.getmStrUserId());
        values.put(TBL_RMD_STR, rawData.getmStrValue());
        db.insert(TABLE_RMD_PAINT_SALES, null, values);
        db.close();
    }

    public void IN_RAW_RMD_PAINT_MERGE(RawData rawData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID, rawData.getmStrUserId());
        values.put(TBL_RMD_STR, rawData.getmStrValue());
        db.insert(TABLE_RMD_PAINT_MERGE, null, values);
        db.close();
    }


    public void IN_RAW_RMD_PAINT_DEL_SOURCE(RawData rawData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID, rawData.getmStrUserId());
        values.put(TBL_RMD_STR, rawData.getmStrValue());
        db.insert(TABLE_RMD_PAINT_DEL_SOURCE, null, values);
        db.close();
    }


    public ArrayList<String> GT_RAW_DATA(String mStrTblName,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + mStrTblName + " WHERE " + TBL_USER_ID +  " = '" + mStrValue + "'";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
               mListLocation.add(c.getString((c.getColumnIndex(TBL_RMD_STR))));
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }
}
