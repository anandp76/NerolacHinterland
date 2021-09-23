package com.nerolac.Modal;

public class PainterModal {
    String fld_rpid;
    String fld_painter_name;
    String fld_education;
    String fld_experience;

    public String getmfld_rpid() {
        return fld_rpid;
    }

    public void setmfld_rpid(String mStrUserId) {
        this.fld_rpid = mStrUserId;
    }

    public String getmStrfld_painter_name() {
        return fld_painter_name;
    }

    public void setmStrfld_painter_name(String mStrTitle) {
        this.fld_painter_name = mStrTitle;
    }

    public String getmStrfld_education() {
        return fld_education;
    }

    public void setmStrfld_education(String mStrBody) {
        this.fld_education = mStrBody;
    }

    public String getmStrfld_experience() {
        return fld_experience;
    }

    public void setmStrfld_experience(String mStrCreated) {
        this.fld_experience = mStrCreated;
    }
}
