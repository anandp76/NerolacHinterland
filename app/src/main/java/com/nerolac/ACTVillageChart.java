package com.nerolac;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTVillageChart extends Activity {
    PieChart pieChart;
    ImageView mImgBack;
    ArrayList<PieEntry> entries = new ArrayList<>();
    public static final int[] COLORFUL_COLORS = {
            Color.rgb(223, 98, 0),Color.parseColor("#00000000")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pie_village);
        setTranceprent(ACTVillageChart.this,R.color.appblue);
        pieChart = (PieChart) findViewById(R.id.chart);
        mImgBack = (ImageView) findViewById(R.id.mImgBack);
        init();
        PieDataSet dataset = new PieDataSet(entries, "Distributors");
        PieData data = new PieData(dataset);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        ArrayList<Integer> colors = new ArrayList<>();




        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataset.setColors(colors);
        pieChart.setData(data);
        pieChart.animateY(5000);

        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    void init(){
    entries.add(new PieEntry(Float.valueOf(30),"Covered"));
    entries.add(new PieEntry(Float.valueOf(45),"Additional"));

    }


}

