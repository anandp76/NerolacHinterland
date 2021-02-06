package com.nerolac.DataBase;

public class DataBaseStringDistributor {

    public static String TBL_DMD_BNS_CATEGORY = "tbl_dmd_bns_category";
    public static String TBL_DMD_BNS_TERRITORY = "tbl_dmd_bns_territory";
    public static String TBL_DMD_BNS_IN_YEAR= "tbl_dmd_bns_in_year";
    public static String TBL_DMD_PRODUCT_CATEGORY = "tbl_dmd_product_category";
    public static String TBL_DMD_BNS_TYPE = "tbl_dmd_bns_type";
    public static String TBL_DMD_MONTHLY_TURNOVER = "tbl_dmd_monthly_turnover";
    public static String TBL_DMD_BNS_COVERAGE_VILLAGES = "tbl_dmd_bns_coverage_villages";
    public static String TBL_DMD_BNS_COVERAGE_RETAILERS = "tbl_dmd_bns_coverage_retailers";
    public static String TBL_DMD_WILLINGNESS_TO_INVEST = "tbl_dmd_willingness_to_invest";
    public static String TBL_DMD_ASSETS = "tbl_dmd_assets";
    public static String TBL_DMD_INTEREST_LEVEL = "tbl_dmd_interest_level";

    public static String TD_ID = "tbRowId";
    public static String TD_DATA = "tbDMDStr";
    public static String TD_USER_ID = "tbUserId";


    public static String CT_TBL_DMD_BNS_CATEGORY = "CREATE TABLE "
            + TBL_DMD_BNS_CATEGORY + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_BNS_TERRITORY = "CREATE TABLE "
            + TBL_DMD_BNS_TERRITORY + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";



    public static String CT_TBL_DMD_BNS_IN_YEAR = "CREATE TABLE "
            + TBL_DMD_BNS_IN_YEAR + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";

    public static String CT_TBL_DMD_PRODUCT_CATEGORY = "CREATE TABLE "
            + TBL_DMD_PRODUCT_CATEGORY + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_BNS_TYPE = "CREATE TABLE "
            + TBL_DMD_BNS_TYPE + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_MONTHLY_TURNOVER = "CREATE TABLE "
            + TBL_DMD_MONTHLY_TURNOVER + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_BNS_COVERAGE_VILLAGES = "CREATE TABLE "
            + TBL_DMD_BNS_COVERAGE_VILLAGES + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_BNS_COVERAGE_RETAILERS = "CREATE TABLE "
            + TBL_DMD_BNS_COVERAGE_RETAILERS + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_WILLINGNESS_TO_INVEST = "CREATE TABLE "
            + TBL_DMD_WILLINGNESS_TO_INVEST + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";


    public static String CT_TBL_DMD_ASSETS = "CREATE TABLE "
            + TBL_DMD_ASSETS + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";

    public static String CT_TBL_DMD_INTEREST_LEVEL = "CREATE TABLE "
            + TBL_DMD_INTEREST_LEVEL + " (" + TD_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + TD_USER_ID + " TEXT NOT NULL, "
            + TD_DATA + " TEXT NOT NULL )";



}
