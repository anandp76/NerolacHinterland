package com.nerolac.Modal;

import java.io.Serializable;
import java.util.List;

public class OderRetailerModal implements Serializable {
    String fld_rorder_id;
    String fld_user_id;
    String fld_did;
    String fld_retailer_id;
    String fld_order_date;
    String fld_order_amount;
    String fld_bill_no;
    String fld_bill_date;
    String fld_bill_amount;
    String fld_bill_copy;
    String fld_bill_upload_date;
    String fld_village;
    String fld_district;
    String fld_tehsil;
    String fld_lat;
    String fld_long;
    String fld_estimate_number;
    String fld_updated_at;
    String fld_rid;

    String fld_code;
    String fld_shop_name;
    String fld_app_id;
    String fld_name;
    String fld_mobile;

    String fld_whatsapp;
    String fld_landline;
    String fld_outlet_size;
    String fld_outlet_type;
    String fld_address1;
    String fld_address2;
    String fld_pincode;
    String fld_gst_available;
    String fld_gst_number;
    String fld_tot_p;
    String fld_brand;
    String fld_businees_in_year;
    String fld_img;
    private List<AddcartModal> itemList;
    public List<AddcartModal> getItemList() {
        return itemList;
    }

    public void setItemList(List<AddcartModal> medicineModelList) {
        this.itemList = medicineModelList;
    }
    public String getfld_estimate_number() {
        return fld_estimate_number;
    }

    public void setfld_estimate_number(String fld_rorder_id) {
        this.fld_estimate_number = fld_rorder_id;
    }
    public String getfld_order_amount() {
        return fld_order_amount;
    }

    public void setfld_order_amount(String fld_rorder_id) {
        this.fld_order_amount = fld_rorder_id;
    }
    public String getfld_order_date() {
        return fld_order_date;
    }

    public void setfld_order_date(String fld_rorder_id) {
        this.fld_order_date = fld_rorder_id;
    }
    public String getfld_rorder_id() {
        return fld_rorder_id;
    }

    public void setfld_rorder_id(String fld_rorder_id) {
        this.fld_rorder_id = fld_rorder_id;
    }

    public String getfld_name() {
        return fld_name;
    }

    public void setfld_name(String fld_name) {
        this.fld_name = fld_name;
    }
    public String getfld_shop_name() {
        return fld_shop_name;
    }

    public void setfld_shop_name(String fld_shop_name) {
        this.fld_shop_name = fld_shop_name;
    }

    public String getfld_address1() {
        return fld_address1;
    }

    public void setfld_address1(String fld_address1) {
        this.fld_address1 = fld_address1;
    }

    public String getTbVillage() {
        return fld_village;
    }

    public void setTbVillage(String fld_village) {
        this.fld_village = fld_village;
    }

    public String getTbBlock() {
        return fld_village;
    }

    public void setTbBlock(String fld_village) {
        this.fld_village = fld_village;
    }

    public String getfld_tehsil() {
        return fld_tehsil;
    }

    public void setfld_tehsil(String fld_tehsil) {
        this.fld_tehsil = fld_tehsil;
    }

    public String getfld_img() {
        return fld_img;
    }

    public void setfld_img(String fld_img) {
        this.fld_img = fld_img;
    }

    public String getTbMobile() {
        return fld_mobile;
    }

    public void setTbMobile(String flfld_mobiled_img) {
        this.fld_mobile = fld_mobile;
    }
    public String getfld_bill_copy() {
        return fld_bill_copy;
    }

    public void setfld_bill_copy(String fld_img) {
        this.fld_bill_copy = fld_img;
    }

}
