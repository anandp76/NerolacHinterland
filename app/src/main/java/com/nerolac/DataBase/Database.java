package com.nerolac.DataBase;

import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nerolac.Modal.AddcartModal;
import com.nerolac.Modal.RawData;
import com.nerolac.Modal.RawLocation;
import com.nerolac.Modal.Retailers;
import com.nerolac.Modal.productsModal;

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
import static com.nerolac.DataBase.DataBaseStringRetailer.CT_TABLE_RMD_PRODUCTS_raw;
import static com.nerolac.DataBase.DataBaseStringRetailer.*;

public class Database extends SQLiteOpenHelper {

    Context context;

    public Database(Context c) {
        super(c, "HinterlandLm.db", null, 11);
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
        db.execSQL(CT_TABLE_RMD_PRODUCTS_raw);
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
        db.execSQL(CT_TABLE_RMD_Add_to_Cart);


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
        db.execSQL("drop table if exists " + CT_TABLE_RMD_Add_to_Cart);
        db.execSQL("drop table if exists " + TABLE_RMD_PRODUCTS_raw);
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
        values.put(RT_OUTLETTYPE, retailers.getTbOutletType());
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
        values.put(RT_IMGFOUR, retailers.getTbImgFour());
        values.put(RT_IMGFIVE, retailers.getTbImgFive());
        values.put(RT_IMGSIX, retailers.getTbImgSix());
        values.put(RT_PAINTERNAMEONE, retailers.getTbPainterNameOne());
        values.put(RT_PAINTEREXPERIENCEONE, retailers.getTbPainterExperienceOne());
        values.put(RT_PAINTEREDUCATIONONE, retailers.getTbPainterEducationOne());
        values.put(RT_PAINTERPHONEONE, retailers.getTbPainterPhoneOne());
        values.put(RT_PAINTERNAMETWO, retailers.getTbPainterNameTwo());
        values.put(RT_PAINTEREXPERIENCETWO, retailers.getTbPainterExperienceTwo());
        values.put(RT_PAINTEREDUCATIONTWO, retailers.getTbPainterEducationTwo());
        values.put(RT_PAINTERPHONETWO, retailers.getTbPainterPhoneTwo());
        values.put(RT_PAINTERNAMETHREE, retailers.getTbPainterNameThree());
        values.put(RT_PAINTEREXPERIENCETHREE, retailers.getTbPainterExperienceThree());
        values.put(RT_PAINTEREDUCATIONTHREE, retailers.getTbPainterEducationThree());
        values.put(RT_PAINTERPHONETHREE, retailers.getTbPainterPhoneThree());
        values.put(RT_PAINTERNAMEFOUR, retailers.getTbPainterNameFour());
        values.put(RT_PAINTEREXPERIENCEFOUR, retailers.getTbPainterExperienceFour());
        values.put(RT_PAINTEREDUCATIONFOUR, retailers.getTbPainterEducationFour());
        values.put(RT_PAINTERPHONEFOUR, retailers.getTbPainterPhoneFour());
        values.put(RT_PAINTERNAMEFIVE, retailers.getTbPainterNameFive());
        values.put(RT_PAINTEREXPERIENCEFIVE, retailers.getTbPainterExperienceFive());
        values.put(RT_PAINTEREDUCATIONFIVE, retailers.getTbPainterEducationFive());
        values.put(RT_PAINTERPHONEFIVE, retailers.getTbPainterPhoneFive());

        values.put(RT_SOURCENAMEONE, retailers.getTbSourceName1());
        values.put(RT_SOURCECONTACTONE, retailers.getTbSourceContact1());
        values.put(RT_SOURCETYPEONE, retailers.getTbSourceType1());
        values.put(RT_SOURCELOCATIONONE, retailers.getTbSourceLocation1());

        values.put(RT_SOURCENAMETWO, retailers.getTbSourceName2());
        values.put(RT_SOURCECONTACTTWO, retailers.getTbSourceContact2());
        values.put(RT_SOURCETYPETWO, retailers.getTbSourceType2());
        values.put(RT_SOURCELOCATIONTWO, retailers.getTbSourceLocation2());

        values.put(RT_SOURCENAMETHREE, retailers.getTbSourceName3());
        values.put(RT_SOURCECONTACTTHREE, retailers.getTbSourceContact3());
        values.put(RT_SOURCETYPETHREE, retailers.getTbSourceType3());
        values.put(RT_SOURCELOCATIONTHREE, retailers.getTbSourceLocation3());

        values.put(RT_SOURCENAMEFOUR, retailers.getTbSourceName4());
        values.put(RT_SOURCECONTACTFOUR, retailers.getTbSourceContact4());
        values.put(RT_SOURCETYPEFOUR, retailers.getTbSourceType4());
        values.put(RT_SOURCELOCATIONFOUR, retailers.getTbSourceLocation4());

        values.put(RT_PAINTAVAIL, retailers.getTbPaintAvail());
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
                retailers.setTbOutletType(c.getString((c.getColumnIndex(RT_OUTLETTYPE))));
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
                retailers.setTbPainterNameOne(c.getString((c.getColumnIndex(RT_PAINTERNAMEONE))));
                retailers.setTbPainterEducationOne(c.getString((c.getColumnIndex(RT_PAINTEREDUCATIONONE))));
                retailers.setTbPainterExperienceOne(c.getString((c.getColumnIndex(RT_PAINTEREXPERIENCEONE))));
                retailers.setTbPainterPhoneOne(c.getString((c.getColumnIndex(RT_PAINTERPHONEONE))));
                retailers.setTbPainterNameTwo(c.getString((c.getColumnIndex(RT_PAINTERNAMETWO))));
                retailers.setTbPainterEducationTwo(c.getString((c.getColumnIndex(RT_PAINTEREDUCATIONTWO))));
                retailers.setTbPainterExperienceTwo(c.getString((c.getColumnIndex(RT_PAINTEREXPERIENCETWO))));
                retailers.setTbPainterPhoneTwo(c.getString((c.getColumnIndex(RT_PAINTERPHONETWO))));
                retailers.setTbPainterNameThree(c.getString((c.getColumnIndex(RT_PAINTERNAMETHREE))));
                retailers.setTbPainterEducationThree(c.getString((c.getColumnIndex(RT_PAINTEREDUCATIONTHREE))));
                retailers.setTbPainterExperienceThree(c.getString((c.getColumnIndex(RT_PAINTEREXPERIENCETHREE))));
                retailers.setTbPainterPhoneThree(c.getString((c.getColumnIndex(RT_PAINTERPHONETHREE))));
                retailers.setTbPainterNameFour(c.getString((c.getColumnIndex(RT_PAINTERNAMEFOUR))));
                retailers.setTbPainterEducationFour(c.getString((c.getColumnIndex(RT_PAINTEREDUCATIONFOUR))));
                retailers.setTbPainterExperienceFour(c.getString((c.getColumnIndex(RT_PAINTEREXPERIENCEFOUR))));
                retailers.setTbPainterPhoneFour(c.getString((c.getColumnIndex(RT_PAINTERPHONEFOUR))));
                retailers.setTbPainterNameFive(c.getString((c.getColumnIndex(RT_PAINTERNAMEFIVE))));
                retailers.setTbPainterEducationFive(c.getString((c.getColumnIndex(RT_PAINTEREDUCATIONFIVE))));
                retailers.setTbPainterExperienceFive(c.getString((c.getColumnIndex(RT_PAINTEREXPERIENCEFIVE))));
                retailers.setTbPainterPhoneFive(c.getString((c.getColumnIndex(RT_PAINTERPHONEFIVE))));

                retailers.setTbSourceName1(c.getString((c.getColumnIndex(RT_SOURCENAMEONE))));
                retailers.setTbSourceType1(c.getString((c.getColumnIndex(RT_SOURCETYPEONE))));
                retailers.setTbSourceContact1(c.getString((c.getColumnIndex(RT_SOURCECONTACTONE))));
                retailers.setTbSourceLocation1(c.getString((c.getColumnIndex(RT_SOURCELOCATIONONE))));

                retailers.setTbSourceName2(c.getString((c.getColumnIndex(RT_SOURCENAMETWO))));
                retailers.setTbSourceType2(c.getString((c.getColumnIndex(RT_SOURCETYPETWO))));
                retailers.setTbSourceContact2(c.getString((c.getColumnIndex(RT_SOURCECONTACTTWO))));
                retailers.setTbSourceLocation2(c.getString((c.getColumnIndex(RT_SOURCELOCATIONTWO))));

                retailers.setTbSourceName3(c.getString((c.getColumnIndex(RT_SOURCENAMETHREE))));
                retailers.setTbSourceType3(c.getString((c.getColumnIndex(RT_SOURCETYPETHREE))));
                retailers.setTbSourceContact3(c.getString((c.getColumnIndex(RT_SOURCECONTACTTHREE))));
                retailers.setTbSourceLocation3(c.getString((c.getColumnIndex(RT_SOURCELOCATIONTHREE))));

                retailers.setTbSourceName4(c.getString((c.getColumnIndex(RT_SOURCENAMEFOUR))));
                retailers.setTbSourceType4(c.getString((c.getColumnIndex(RT_SOURCETYPEFOUR))));
                retailers.setTbSourceContact4(c.getString((c.getColumnIndex(RT_SOURCECONTACTFOUR))));
                retailers.setTbSourceLocation4(c.getString((c.getColumnIndex(RT_SOURCELOCATIONFOUR))));

                retailers.setTbImgOne(c.getString((c.getColumnIndex(RT_IMGONE))));
                retailers.setTbImgTwo(c.getString((c.getColumnIndex(RT_IMGTWO))));
                retailers.setTbImgThree(c.getString((c.getColumnIndex(RT_IMGTHREE))));
                retailers.setTbImgFour(c.getString((c.getColumnIndex(RT_IMGFOUR))));
                retailers.setTbImgFive(c.getString((c.getColumnIndex(RT_IMGFIVE))));
                retailers.setTbImgSix(c.getString((c.getColumnIndex(RT_IMGSIX))));
                retailers.setTbPaintAvail(c.getString((c.getColumnIndex(RT_PAINTAVAIL))));
                mListLocation.add(retailers);
            } while (c.moveToNext());
        }
        c.close();
        return mListLocation;
    }

    public void DT_Retailers(String mStrUserId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RETAILER, RT_ID + " = " + mStrUserId, null);
        db.close();
    }



    public void DT_LOCATION() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RAW_LOCATION,null, null);
        db.delete(TBL_DMD_INTEREST_LEVEL,null, null);
        db.delete(TBL_DMD_ASSETS,null, null);
        db.delete(TBL_DMD_WILLINGNESS_TO_INVEST,null, null);
        db.delete(TBL_DMD_BNS_COVERAGE_RETAILERS,null, null);
        db.delete(TBL_DMD_BNS_COVERAGE_VILLAGES,null, null);
        db.delete(TBL_DMD_MONTHLY_TURNOVER,null, null);
        db.delete(TBL_DMD_BNS_TYPE,null, null);
        db.delete(TBL_DMD_BNS_IN_YEAR,null, null);
        db.delete(TBL_DMD_BNS_TERRITORY,null, null);
        db.delete(TBL_DMD_BNS_CATEGORY,null, null);
        db.delete(TABLE_RMD_PRODUCTS,null, null);
        db.delete(TABLE_RMD_BRANDS,null, null);
        db.delete(TABLE_RMD_BNS_IN_YEAR,null, null);
        db.delete(TABLE_RMD_OUTLET_SALES,null, null);
        db.delete(TABLE_RMD_PAINT_SALES,null, null);
        db.delete(TABLE_RMD_PAINT_MERGE,null, null);
        db.delete(TABLE_RMD_PAINT_DEL_SOURCE,null, null);
        db.delete(TBL_DMD_PRODUCT_CATEGORY,null, null);
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
    public void IN_RAW_RMD_PRODUCTS_raw(RawData rawData) {
        System.out.println("<><><>## call 1");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_USER_ID,rawData.getmStrUserId());
        values.put(TBL_RMD_STR,rawData.getmStrValue());
        db.insert(TABLE_RMD_PRODUCTS_raw, null, values);
        db.close();
    }
    public void IN_RAW_RMD_PRODUCTS(productsModal rawData) {
        System.out.println("<><><>## call 1");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_RAW_product_id,rawData.getproduct_id());
        values.put(TBL_RAW_pack_size,rawData.getpack());
        values.put(TBL_RAW_category,rawData.getcategory());
        values.put(TBL_RAW_sku,rawData.getsku());
        values.put(TBL_RAW_description,rawData.getdescription());
        values.put(TBL_RAW_amount,rawData.getamount());
        db.insert(TABLE_RMD_PRODUCTS, null, values);
        db.close();
    }

    public void IN_RAW_RMD_ADDtocart(AddcartModal rawData) {
        System.out.println("<><><>## call 1");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_RAW_product_id,rawData.getproduct_id());
        values.put(TBL_RAW_pack_size,rawData.getpack());
        values.put(TBL_RAW_category,rawData.getcategory());
        values.put(TBL_RAW_sku,rawData.getsku());
        values.put(TBL_RAW_description,rawData.getdescription());
        values.put(TBL_RAW_amount,rawData.getamount());
        values.put(TBL_RAW_Retailer_id,rawData.getRetailer_id());
        values.put(TBL_RAW_Retailer_name,rawData.getRetailer_name());
        values.put(TBL_RAW_owner,rawData.getowner());
        values.put(TBL_RAW_Retailer_photo,rawData.getRetailer_photo());
        values.put(TBL_RAW_quntity,String.valueOf(rawData.getquntity()));
        values.put(TBL_RAW_Retailer_price,rawData.getprice());

        db.insert(TABLE_RMD_Add_To_Cart, null, values);
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
    public ArrayList<String> GT_RAW_DATA_Raw_products(String mStrTblName,String mStrValue) {
        ArrayList<String> mListLocation = new ArrayList<String>();
        //String selectQuery = "SELECT * FROM " + mStrTblName  ;
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
    public ArrayList<productsModal> GT_AllProducts(String productIDS) {
        ArrayList<productsModal> mListMetadeta = new ArrayList<productsModal>();
        String selectQuery = "SELECT * FROM " + TABLE_RMD_PRODUCTS + " WHERE " + TBL_RAW_product_id +  " NOT IN ( " + productIDS + ")";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                productsModal retailers = new productsModal();
                retailers.setproduct_id(c.getString((c.getColumnIndex(TBL_RAW_product_id))));
                retailers.setamount(c.getString((c.getColumnIndex(TBL_RAW_amount))));
                retailers.setpack(c.getString((c.getColumnIndex(TBL_RAW_pack_size))));
                retailers.setdescription(c.getString((c.getColumnIndex(TBL_RAW_description))));
                retailers.setsku(c.getString((c.getColumnIndex(TBL_RAW_sku))));
                retailers.setcategory(c.getString((c.getColumnIndex(TBL_RAW_category))));

                mListMetadeta.add(retailers);

            } while (c.moveToNext());
        }
        c.close();
        return mListMetadeta;
    }
    public ArrayList<AddcartModal> GT_Addtocart(String mStrValue) {
        ArrayList<AddcartModal> mListMetadeta = new ArrayList<AddcartModal>();
        String selectQuery = "SELECT * FROM " + TABLE_RMD_Add_To_Cart + " WHERE " + TBL_RAW_Retailer_id +  " = '" + mStrValue + "'";
        System.out.println("<><><>selectQuery " + selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {
            do {
                AddcartModal retailers = new AddcartModal();
                retailers.setproduct_id(c.getString((c.getColumnIndex(TBL_RAW_product_id))));
                retailers.setamount(c.getString((c.getColumnIndex(TBL_RAW_amount))));
                retailers.setpack(c.getString((c.getColumnIndex(TBL_RAW_pack_size))));
                retailers.setdescription(c.getString((c.getColumnIndex(TBL_RAW_description))));
                retailers.setsku(c.getString((c.getColumnIndex(TBL_RAW_sku))));
                retailers.setcategory(c.getString((c.getColumnIndex(TBL_RAW_category))));
                retailers.setRetailer_id(c.getString((c.getColumnIndex(TBL_RAW_Retailer_id))));
                retailers.setRetailer_name(c.getString((c.getColumnIndex(TBL_RAW_Retailer_name))));
                retailers.setowner(c.getString((c.getColumnIndex(TBL_RAW_owner))));
                retailers.setRetailer_photo(c.getString((c.getColumnIndex(TBL_RAW_Retailer_photo))));
                retailers.setprice(c.getString((c.getColumnIndex(TBL_RAW_Retailer_price))));
                retailers.setquntity(Integer.parseInt(c.getString((c.getColumnIndex(TBL_RAW_quntity)))));

                mListMetadeta.add(retailers);

            } while (c.moveToNext());
        }
        c.close();
        return mListMetadeta;
    }
    public void UP_RAW_RMD_ADDtocart(AddcartModal rawData,String retailerid) {
//        db.execSQL("UPDATE DB_TABLE SET YOUR_COLUMN='newValue' WHERE id=6 ");
//        String selectQuery = "UPDATE" + TABLE_RMD_Add_To_Cart +" SET "+TBL_RAW_product_id+" = '"+rawData.getproduct_id()+"',"+ " WHERE " + TBL_RAW_Retailer_id +  " = '" + mStrValue + "'";
//        System.out.println("<><><>selectQuery " + selectQuery);
        System.out.println("<><><>## call 1");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TBL_RAW_product_id,rawData.getproduct_id());
        values.put(TBL_RAW_pack_size,rawData.getpack());
        values.put(TBL_RAW_category,rawData.getcategory());
        values.put(TBL_RAW_sku,rawData.getsku());
        values.put(TBL_RAW_description,rawData.getdescription());
        values.put(TBL_RAW_amount,rawData.getamount());
        values.put(TBL_RAW_Retailer_id,rawData.getRetailer_id());
        values.put(TBL_RAW_Retailer_name,rawData.getRetailer_name());
        values.put(TBL_RAW_owner,rawData.getowner());
        values.put(TBL_RAW_Retailer_photo,rawData.getRetailer_photo());
        values.put(TBL_RAW_Retailer_price,rawData.getprice());
        values.put(TBL_RAW_quntity,rawData.getquntity());
        String[] args = new String[]{retailerid, rawData.getproduct_id()};
db.update(TABLE_RMD_Add_To_Cart,values, "Retailer_id = ? AND product_id = ?", args);
        //db.insert(TABLE_RMD_Add_To_Cart, null, values);
        db.close();
    }
    public void deleteorder(String retelerid){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = new String[]{retelerid};
        db.delete(TABLE_RMD_Add_To_Cart,"Retailer_id = ?",args);
        //db.insert(TABLE_RMD_PAINT_SALES, null, values);
        db.close();
    }
    public void deleteorderItem(String retelerid ,String product_id){
        SQLiteDatabase db = this.getWritableDatabase();

        String[] args = new String[]{retelerid, product_id};
        db.delete(TABLE_RMD_Add_To_Cart,"Retailer_id = ? AND product_id = ?",args);
        //db.insert(TABLE_RMD_PAINT_SALES, null, values);
        db.close();
    }
}
