package com.zfxf.zfxfproject.weight;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
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

    /**
     * 初始化lineChart
     */
    private void initLineChart() {
        lineChart.setViewPortOffsets(20, 0, 0, 0);
        lineChart.setBackgroundColor(Color.rgb(104, 241, 175));

        // no description text
        lineChart.getDescription().setEnabled(false);
        XAxis x = lineChart.getXAxis();
        x.setEnabled(false);


        /******************  xy轴初始化  *********************/
        XAxis xAxis = lineChart.getXAxis();

        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setAxisMinimum(1f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);

        YAxis y = lineChart.getAxisLeft();
        y.setAxisMinimum(0f);
        y.setLabelCount(6, false);
        y.setTextColor(Color.BLACK);
        y.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        y.setDrawGridLines(false);
        y.setAxisLineColor(Color.BLACK);
        lineChart.getAxisRight().setEnabled(false);


        /******************  xy轴初始化  *********************/


        // 隐藏描述信息
        lineChart.getLegend().setEnabled(false);

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
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(1.8f);
            set1.setCircleRadius(4f);
            set1.setCircleColor(Color.WHITE);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.WHITE);
            set1.setFillColor(Color.WHITE);
            set1.setFillAlpha(100);
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
        }
    }

}
