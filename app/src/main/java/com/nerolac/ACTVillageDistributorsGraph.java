package com.nerolac;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nerolac.Utils.DayAxisValueFormatter;
import com.nerolac.Utils.MyAxisValueFormatter;
import com.nerolac.Utils.MyMarkerView;
import com.nerolac.Utils.XYMarkerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import static com.nerolac.Utils.CommonData.setTranceprent;

public class ACTVillageDistributorsGraph extends AppCompatActivity {
    private BarChart barChart;
    private BarChart chartPaint;
    private BarChart OutletPaint;
    PieChart chartVillage;
    PieChart chartDistributors;
    ImageView mImgBack;

    TextView mTxtViewMore5;
    TextView mTxtViewMore4;
    TextView mTxtViewMore3;
    TextView mTxtViewMore2;
    TextView mTxtViewMore1;


    ArrayList<PieEntry> entriesVillage = new ArrayList<>();
    ArrayList<PieEntry> entriesDistributors = new ArrayList<>();
    public static final int[] COLORFUL_COLORS_VILLAGE = {
            Color.parseColor("#2d6a4f"),Color.parseColor("#2a9d8a")};

    public static final int[] COLORFUL_COLORS_DISTRIBUTORS = {
            Color.parseColor("#f94144"),Color.parseColor("#8ecae6"),Color.parseColor("#f9c74f")};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bar_retailers_distributor);
        setTranceprent(ACTVillageDistributorsGraph.this,R.color.appblue);
        init();
        chartVillage = (PieChart) findViewById(R.id.chartVillage);
        mTxtViewMore5 = (TextView) findViewById(R.id.mTxtViewMore5);
        mTxtViewMore4 = (TextView) findViewById(R.id.mTxtViewMore4);
        mTxtViewMore3 = (TextView) findViewById(R.id.mTxtViewMore3);
        mTxtViewMore2 = (TextView) findViewById(R.id.mTxtViewMore2);
        mTxtViewMore1 = (TextView) findViewById(R.id.mTxtViewMore1);
        chartDistributors = (PieChart) findViewById(R.id.chartDistributors);

        mImgBack = findViewById(R.id.mImgBack);

        barChart = findViewById(R.id.chart1);
        chartPaint = findViewById(R.id.chart2);
        OutletPaint = findViewById(R.id.chart3);

        barChart.setDrawBarShadow(false);
        chartPaint.setDrawBarShadow(false);
        OutletPaint.setDrawBarShadow(false);

        barChart.setDrawValueAboveBar(true);
        chartPaint.setDrawValueAboveBar(true);
        OutletPaint.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);
        chartPaint.getDescription().setEnabled(false);
        OutletPaint.getDescription().setEnabled(false);

        barChart.setMaxVisibleValueCount(60);
        chartPaint.setMaxVisibleValueCount(60);
        OutletPaint.setMaxVisibleValueCount(60);

        barChart.setPinchZoom(false);
        chartPaint.setPinchZoom(false);
        OutletPaint.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        chartPaint.setDrawGridBackground(false);
        OutletPaint.setDrawGridBackground(false);



        setData(10,70);
        setData2(10,70);
        setData3(10,70);

        PieDataSet dataset = new PieDataSet(entriesVillage, "Villages Covered");
        PieData data = new PieData(dataset);
        data.setValueTextSize(15f);

        data.setValueTextColor(Color.WHITE);
        dataset.setColors(COLORFUL_COLORS_VILLAGE);
        chartVillage.setData(data);
        chartVillage.animateY(5000);
        chartVillage.getDescription().setText("5 District Data");



        PieDataSet dataset2 = new PieDataSet(entriesDistributors, "Distributors");
        PieData data2 = new PieData(dataset2);
        data2.setValueTextSize(15f);
        data2.setValueTextColor(Color.WHITE);
        dataset2.setColors(COLORFUL_COLORS_DISTRIBUTORS);
        chartDistributors.setData(data2);
        chartDistributors.animateY(5000);
        chartDistributors.getDescription().setText("5 District Data");


        mImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mTxtViewMore5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTVillageDistributorsGraph.this,ACTViewMoreList.class);
                startActivity(intent);
            }
        });

        mTxtViewMore4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTVillageDistributorsGraph.this,ACTViewMoreList.class);
                startActivity(intent);
            }
        });

        mTxtViewMore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTVillageDistributorsGraph.this,ACTViewMoreList.class);
                startActivity(intent);
            }
        });

        mTxtViewMore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTVillageDistributorsGraph.this,ACTViewMoreList.class);
                startActivity(intent);
            }
        });

        mTxtViewMore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ACTVillageDistributorsGraph.this,ACTViewMoreList.class);
                startActivity(intent);
            }
        });

    }
    void init(){
        entriesVillage.add(new PieEntry(Float.valueOf(65),"Covered"));
        entriesVillage.add(new PieEntry(Float.valueOf(35),"Additional"));

        entriesDistributors.add(new PieEntry(Float.valueOf(30),"Hot"));
        entriesDistributors.add(new PieEntry(Float.valueOf(45),"Cold"));
        entriesDistributors.add(new PieEntry(Float.valueOf(25),"Warm "));
    }

    private void setData3(int count, float range) {
        XAxis xl = OutletPaint.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);
        final ArrayList<String> mListData = new ArrayList<>();
        mListData.add("Agra");
        mListData.add("Aligarh");
        mListData.add("Allahabad");
        mListData.add("Azamgarh");
        mListData.add("Bareilly");
        mListData.add("Basti");
        mListData.add("Chitrakoot");
        mListData.add("Gonda");

        xl.setValueFormatter(new MyAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mListData.get((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        YAxis leftAxis = OutletPaint.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true
        OutletPaint.getAxisRight().setEnabled(false);

//data
        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset
// (0.46 + 0.02) * 2 + 0.04 = 1.00 -> interval per "group"

        int startYear = 1;
        int endYear = 5;
        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();

        for (int i = startYear; i < endYear; i++) {

            yVals1.add(new BarEntry(i, getRandomNumberUsingNextIntAA(0,5),"hello"));
            yVals2.add(new BarEntry(i,getRandomNumberUsingNextIntAA(0,5),"hello"));
            // yVals3.add(new BarEntry(i,getRandomNumberUsingNextIntAA(0,5),"hello"));
        }

        BarDataSet set1, set2;
        if (OutletPaint.getData() != null && OutletPaint.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)OutletPaint.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)OutletPaint.getData().getDataSetByIndex(1);
            //set3 = (BarDataSet)barChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            //set3.setValues(yVals3);
            OutletPaint.getData().notifyDataChanged();
            OutletPaint.notifyDataSetChanged();
        } else {
            // create 2 datasets with different types
            set1 = new BarDataSet(yVals1, "Retailers");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Distributors");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            BarData data = new BarData(dataSets);
            OutletPaint.setData(data);
        }

        OutletPaint.getBarData().setBarWidth(barWidth);
        OutletPaint.getXAxis().setAxisMinValue(startYear);
        OutletPaint.groupBars(startYear, groupSpace, barSpace);
        OutletPaint.invalidate();

    }

    private void setData2(int count, float range) {
        XAxis xl = chartPaint.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);
        final ArrayList<String> mListData = new ArrayList<>();
        mListData.add("Agra");
        mListData.add("Aligarh");
        mListData.add("Allahabad");
        mListData.add("Azamgarh");
        mListData.add("Bareilly");
        mListData.add("Basti");
        mListData.add("Chitrakoot");
        mListData.add("Gonda");

        xl.setValueFormatter(new MyAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mListData.get((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        YAxis leftAxis = chartPaint.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true
        chartPaint.getAxisRight().setEnabled(false);

//data
        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.46f; // x2 dataset
// (0.46 + 0.02) * 2 + 0.04 = 1.00 -> interval per "group"

        int startYear = 1;
        int endYear = 5;
        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();

        for (int i = startYear; i < endYear; i++) {

            yVals1.add(new BarEntry(i, getRandomNumberUsingNextIntAA(0,5),"hello"));
            yVals2.add(new BarEntry(i,getRandomNumberUsingNextIntAA(0,5),"hello"));
           // yVals3.add(new BarEntry(i,getRandomNumberUsingNextIntAA(0,5),"hello"));
        }

        BarDataSet set1, set2;
        if (chartPaint.getData() != null && chartPaint.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)chartPaint.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)chartPaint.getData().getDataSetByIndex(1);
            //set3 = (BarDataSet)barChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            //set3.setValues(yVals3);
            chartPaint.getData().notifyDataChanged();
            chartPaint.notifyDataSetChanged();
        } else {
            // create 2 datasets with different types
            set1 = new BarDataSet(yVals1, "Retailers");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Distributors");
            set2.setColor(Color.rgb(164, 228, 251));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            BarData data = new BarData(dataSets);
            chartPaint.setData(data);
        }

        chartPaint.getBarData().setBarWidth(barWidth);
        chartPaint.getXAxis().setAxisMinValue(startYear);
        chartPaint.groupBars(startYear, groupSpace, barSpace);
        chartPaint.invalidate();

    }
    private void setData(int count, float range) {
        XAxis xl = barChart.getXAxis();
        xl.setGranularity(1f);
        xl.setCenterAxisLabels(true);
        final ArrayList<String> mListData = new ArrayList<>();
        mListData.add("Agra");
        mListData.add("Aligarh");
        mListData.add("Allahabad");
        mListData.add("Azamgarh");
        mListData.add("Bareilly");
        mListData.add("Basti");
        mListData.add("Chitrakoot");
        mListData.add("Gonda");

        xl.setValueFormatter(new MyAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mListData.get((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setValueFormatter(new MyAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return String.valueOf((int) value);
            }

            @Override
            public int getDecimalDigits() {
                return 0;
            }
        });
        leftAxis.setDrawGridLines(false);
        leftAxis.setSpaceTop(30f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true
        barChart.getAxisRight().setEnabled(false);

//data
        float groupSpace = 0.04f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.3f; // x2 dataset
// (0.46 + 0.02) * 2 + 0.04 = 1.00 -> interval per "group"

        int startYear = 1;
        int endYear = 4;
        List<BarEntry> yVals1 = new ArrayList<BarEntry>();
        List<BarEntry> yVals2 = new ArrayList<BarEntry>();
        List<BarEntry> yVals3 = new ArrayList<BarEntry>();

        for (int i = startYear; i < endYear; i++) {

            yVals1.add(new BarEntry(i, getRandomNumberUsingNextInt(25,85),"hello"));
            yVals2.add(new BarEntry(i,getRandomNumberUsingNextInt(25,85),"hello"));
            yVals3.add(new BarEntry(i,getRandomNumberUsingNextInt(25,85),"hello"));
        }

        BarDataSet set1, set2, set3;
        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet)barChart.getData().getDataSetByIndex(0);
            set2 = (BarDataSet)barChart.getData().getDataSetByIndex(1);
            set3 = (BarDataSet)barChart.getData().getDataSetByIndex(2);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            set3.setValues(yVals3);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            // create 2 datasets with different types
            set1 = new BarDataSet(yVals1, "Retailers");
            set1.setColor(Color.rgb(104, 241, 175));
            set2 = new BarDataSet(yVals2, "Distributors");
            set2.setColor(Color.rgb(164, 228, 251));
            set3 = new BarDataSet(yVals3, "Villages");
            set3.setColor(Color.rgb(139, 0, 0));

            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
            dataSets.add(set1);
            dataSets.add(set2);
            dataSets.add(set3);
            BarData data = new BarData(dataSets);
            barChart.setData(data);
        }

        barChart.getBarData().setBarWidth(barWidth);
        barChart.getXAxis().setAxisMinValue(startYear);
        barChart.groupBars(startYear, groupSpace, barSpace);
        barChart.invalidate();

    }
   /* void init(){
    entries.add(new PieEntry(Float.valueOf(30),"Hot"));
    entries.add(new PieEntry(Float.valueOf(45),"Cold"));
    entries.add(new PieEntry(Float.valueOf(25),"Warm "));
    }*/
   public int getRandomNumberUsingNextInt(int min, int max) {
       Random random = new Random();
       return random.nextInt(max - min) + min;
   }

    public int getRandomNumberUsingNextIntAA(int min, int max) {
       ArrayList<Integer> mList= new ArrayList<>();
        mList.add(5000);
        mList.add(10000);
        mList.add(15000);
        mList.add(20000);
        mList.add(25000);
        mList.add(30000);
        mList.add(35000);
        mList.add(40000);
        mList.add(45000);
        mList.add(50000);
        Random random = new Random();
        return mList.get(random.nextInt(max - min) + min);
    }

}

