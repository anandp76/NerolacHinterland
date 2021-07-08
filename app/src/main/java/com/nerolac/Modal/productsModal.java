package com.nerolac.Modal;

public class productsModal {
    String product_id;
    String category;
    String sku;
    String description;
    String pack;
    String amount;
    boolean Checked = false;
    public String getproduct_id() {
        return product_id;
    }

    public void setproduct_id(String product_id) {
        this.product_id = product_id;
    }
    public String getcategory() {
        return category;
    }

    public void setcategory(String fld_rorder_id) {
        this.category = fld_rorder_id;
    }
    public String getsku() {
        return sku;
    }

    public void setsku(String fld_rorder_id) {
        this.sku = fld_rorder_id;
    }
    public String getdescription() {
        return description;
    }

    public void setdescription(String fld_rorder_id) {
        this.description = fld_rorder_id;
    }
    public String getpack() {
        return pack;
    }

    public void setpack(String fld_rorder_id) {
        this.pack = fld_rorder_id;
    }
    public String getamount() {
        return amount;
    }

    public void setamount(String fld_rorder_id) {
        this.amount = fld_rorder_id;
    }
    public boolean isChecked() {
        return Checked;
    }

    public void setChecked(boolean checked) {
        Checked = checked;
    }


}
