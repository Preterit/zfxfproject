package com.zfxf.zfxfproject.weight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zfxf.zfxfproject.R;

import java.util.ArrayList;

public class CustomLineChartView extends LinearLayout {

    private Context mContext;
    private LineChart lineChart;

    public CustomLineChartView(Context context) {
        this(context, null);
    }

    public CustomLineChartView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomLineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.wight_line_chart_view, this);
        lineChart = findViewById(R.id.lineChart);

        initLineChart();
        setData(45, 100);
    }

    private void initLineChart() {
        /************* 手势操作 *************/
        lineChart.setTouchEnabled(false);//是否开启触摸相关的交互方式
        lineChart.setDragEnabled(false);//是否开启拖拽相关的交互方式
        lineChart.setScaleEnabled(false);//是否开启xy轴的缩放
        lineChart.setScaleXEnabled(false);//是否开启x轴的缩放
        lineChart.setScaleYEnabled(false);//是否开启y轴的缩放
        //是否开启双指捏合缩放:如果关闭了，仍然可以完成x或y一个轴的缩放
        lineChart.setPinchZoom(false);
        /************* 手势操作 *************/

        /************* xy轴设置 *************/
        XAxis xl = lineChart.getXAxis();
        xl.setAvoidFirstLastClipping(true);
        xl.setAxisMinimum(0f);
        xl.setDrawGridLines(false);
        xl.setDrawAxisLine(true);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setLabelCount(7,true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setInverted(false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(false);
        leftAxis.setGridColor(Color.parseColor("#e0e0e0"));
        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        /************* xy轴设置 *************/


        // get the legend (only possible after setting data)
        lineChart.getDescription().setEnabled(false);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.setNoDataText("暂无数据");
        lineChart.invalidate();
    }


    private void setData(int count, float range) {

        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
        }

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

//            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
//            set1.setCubicIntensity(0.2f);
            int color = Color.parseColor("#5abdfc");
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(1.5f);
            set1.setCircleRadius(3f);
            set1.setCircleColor(color);
            set1.setCircleHoleColor(Color.WHITE);
            set1.setCircleHoleRadius(1.5f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(color);
            set1.setFillColor(Color.parseColor("#eb73f6"));
            set1.setFillAlpha(30);

            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setDrawValues(false);
            set1.setDrawCircleHole(true);

            set1.setDrawHorizontalHighlightIndicator(false);
            set1.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return lineChart.getAxisLeft().getAxisMinimum();
                }
            });

            // create a data object with the data sets
            LineData data = new LineData(set1);
            data.setValueTextSize(9f);
            data.setDrawValues(false);

            // set data
            lineChart.setData(data);
            lineChart.invalidate();
        }
    }

}
