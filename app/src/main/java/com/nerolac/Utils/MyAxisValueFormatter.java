package com.nerolac.Utils;

import android.icu.text.DecimalFormat;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;


public class MyAxisValueFormatter implements IAxisValueFormatter
{

    private final DecimalFormat mFormat;

    public MyAxisValueFormatter() {
        mFormat = new DecimalFormat("###,###,###,##0.0");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return mFormat.format(value) + " $";
    }

    @Override
    public int getDecimalDigits() {
        return 0;
    }
}
