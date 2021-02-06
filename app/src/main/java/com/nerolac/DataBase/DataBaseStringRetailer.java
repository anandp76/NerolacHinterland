package com.nerolac.DataBase;

/**
 * Created by admin18 on 16/12/19.
 */

public class DataBaseStringRetailer {

    public static String TABLE_RAW_LOCATION = "tb_raw_location";
    public static String TABLE_RMD_PRODUCTS = "tb_md_products";
    public static String TABLE_RMD_BRANDS = "tb_md_retailer_brands";
    public static String TABLE_RMD_BNS_IN_YEAR= "tb_md_retailer_buisnessinyear";
    public static String TABLE_RMD_OUTLET_SALES = "tb_md_retailer_outlet_sales";
    public static String TABLE_RMD_PAINT_SALES = "tb_md_retailer_paint_sales";
    public static String TABLE_RMD_PAINT_MERGE = "tb_md_retailer_paint_merge";
    public static String TABLE_RMD_PAINT_DEL_SOURCE = "tb_md_retailer_paint_delivery_source";

    public static String TABLE_RETAILER = "tb_data_retailer";
    public static String TBL_ID = "tbRawId";
    public static String TBL_RMD_STR = "tbRMDStr";
    public static String TBL_USER_ID = "tbUserId";
    public static String TBL_RAW_LOCATION_STATE = "tbRawState";
    public static String TBL_RAW_LOCATION_PLAN = "tbRawPlan";
    public static String TBL_RAW_LOCATION_DISTRICT = "tbRawDistrict";
    public static String TBL_RAW_LOCATION_TEHSIL = "tbRawTehsil";
    public static String TBL_RAW_LOCATION_BLOCK = "tbRawBlock";
    public static String TBL_RAW_LOCATION_VILLAGE = "tbRawVillage";

    //tb_data_retailer
    public static String RT_ID = "tbId";
    public static String RT_USERID = "tbUserId";
    public static String RT_GST_NUMBER = "tbGstNumber";
    public static String RT_VILLAGE = "tbVillage";
    public static String RT_BLOCK = "tbBlock";
    public static String RT_DISTRICT = "tbDistrict";
    public static String RT_TEHSIL = "tbTehsil";
    public static String RT_PRODUCTS = "tbProducts";
    public static String RT_BRANDS = "tbBrand";
    public static String RT_BUSINESSINYEARS = "tbBusinessInYears";
    public static String RT_OUTLETSALES = "tbOutletSales";
    public static String RT_PAINTSALES = "tbPaintSales";
    public static String RT_PAINTMARGIN = "tbPaintMargin";
    public static String RT_DELIVERY = "tbDeliverySource";
    public static String RT_LATITUDE = "tbLatitude";
    public static String RT_LONGITUDE = "tbLongitude";
    public static String RT_REMARK = "tbRemark";
    public static String RT_SHOPNAME = "tbShopName";
    public static String RT_FIRSTNAME = "tbFirstName";
    public static String RT_LASTNAME = "tbLastName";
    public static String RT_MOBILE = "tbMobile";
    public static String RT_WHATSAPP = "tbWhatsApp";
    public static String RT_LANDLINE = "tbLandline";
    public static String RT_OUTLETSIZE = "tbOutletSize";
    public static String RT_ADDRESS1 = "tbAddress1";
    public static String RT_ADDRESS2 = "tbAddress2";
    public static String RT_PINCODE = "tbPincode";
    public static String RT_GSTAVAILABLE = "tbGstAvailable";
    public static String RT_IMGONE = "tbImgOne";
    public static String RT_IMGTWO = "tbImgTwo";
    public static String RT_IMGTHREE = "tbImgThree";






    public static String CT_TABLE_RETAILER = "CREATE TABLE "
            + TABLE_RETAILER + " (" + RT_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + RT_USERID + " TEXT NOT NULL, "
            + RT_SHOPNAME + " TEXT NOT NULL, "
            + RT_FIRSTNAME + " TEXT NOT NULL, "
            + RT_LASTNAME + " TEXT NOT NULL, "
            + RT_MOBILE + " TEXT NOT NULL, "
            + RT_WHATSAPP + " TEXT NOT NULL, "
            + RT_LANDLINE + " TEXT, "
            + RT_OUTLETSIZE + " TEXT NOT NULL, "
            + RT_ADDRESS1 + " TEXT NOT NULL, "
            + RT_ADDRESS2 + " TEXT, "
            + RT_PINCODE + " TEXT NOT NULL, "
            + RT_GSTAVAILABLE + " TEXT NOT NULL, "
            + RT_GST_NUMBER + " TEXT, "
            + RT_VILLAGE + " TEXT NOT NULL, "
            + RT_BLOCK + " TEXT NOT NULL, "
            + RT_DISTRICT + " TEXT NOT NULL, "
            + RT_TEHSIL + " TEXT NOT NULL, "
            + RT_PRODUCTS + " TEXT NOT NULL, "
            + RT_BRANDS + " TEXT NOT NULL, "
            + RT_BUSINESSINYEARS + " TEXT NOT NULL, "
            + RT_OUTLETSALES + " TEXT NOT NULL, "
            + RT_PAINTSALES + " TEXT NOT NULL, "
            + RT_PAINTMARGIN + " TEXT NOT NULL, "
            + RT_DELIVERY + " TEXT NOT NULL, "
            + RT_LATITUDE + " TEXT NOT NULL, "
            + RT_LONGITUDE + " TEXT NOT NULL, "
            + RT_IMGONE + " TEXT, "
            + RT_IMGTWO + " TEXT, "
            + RT_IMGTHREE + " TEXT, "
            + RT_REMARK + " TEXT )";




    public static String CT_TABLE_RAW_LOCATION = "CREATE TABLE "
            + TABLE_RAW_LOCATION + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_RAW_LOCATION_STATE + " TEXT NOT NULL, "
            + TBL_RAW_LOCATION_DISTRICT + " TEXT NOT NULL, "
            + TBL_RAW_LOCATION_TEHSIL + " TEXT NOT NULL, "
            + TBL_RAW_LOCATION_BLOCK + " TEXT NOT NULL, "
            + TBL_RAW_LOCATION_PLAN + " TEXT NOT NULL, "
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RAW_LOCATION_VILLAGE + " TEXT NOT NULL )";


    public static String CT_TABLE_RMD_PRODUCTS = "CREATE TABLE "
            + TABLE_RMD_PRODUCTS + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";


    public static String CT_TABLE_RMD_BRANDS = "CREATE TABLE "
            + TABLE_RMD_BRANDS + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";


    public static String CT_TABLE_RMD_BNS_IN_YEAR = "CREATE TABLE "
            + TABLE_RMD_BNS_IN_YEAR + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";



    public static String CT_TABLE_RMD_OUTLET_SALES = "CREATE TABLE "
            + TABLE_RMD_OUTLET_SALES + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";


    public static String CT_TABLE_RMD_PAINT_SALES = "CREATE TABLE "
            + TABLE_RMD_PAINT_SALES + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";


    public static String CT_TABLE_RMD_PAINT_MERGE = "CREATE TABLE "
            + TABLE_RMD_PAINT_MERGE + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";


    public static String CT_TABLE_RMD_PAINT_DEL_SOURCE = "CREATE TABLE "
            + TABLE_RMD_PAINT_DEL_SOURCE + " (" + TBL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TBL_USER_ID + " TEXT NOT NULL, "
            + TBL_RMD_STR + " TEXT NOT NULL )";







}
