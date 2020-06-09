package com.zfxf.zfxfproject.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.zfxf.zfxfproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomLineChartView extends LinearLayout {

    private Context mContext;
    private LineChart lineChart;
    private TextView tvTitle;
    private Drawable[] drawables = {
            getResources().getDrawable(R.drawable.linechart_fade_blue),
            getResources().getDrawable(R.drawable.linechart_fade_yellow),
            getResources().getDrawable(R.drawable.linechart_fade_pink),
            getResources().getDrawable(R.drawable.linechart_fade_red)
    };
    private int[] colors = {
            Color.parseColor("#2E5BFF"),
            Color.parseColor("#F7C137"),
            Color.parseColor("#8B53FF"),
            Color.parseColor("#D1343B")
    };

    public static final String[] yearStr = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};
    public static final String[] weekStr = {"周一", "周二", "周三", "周四", "周五", "周六", "周日"};
    private List<String> xValues = new ArrayList<>();

    private MyWeekFormat xValueFormat = new MyWeekFormat();
    private List<String> monthXValue = new ArrayList<>();


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
        tvTitle = findViewById(R.id.tvTitle);

        initLineChart();
    }

    private void initLineChart() {
        //是否显示边界
        lineChart.setDrawBorders(false);

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
        xl.setDrawGridLines(true);
        xl.setDrawAxisLine(true);
        xl.setGridColor(Color.parseColor("#DEDEDE"));
        xl.setTextColor(Color.parseColor("#666666"));
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setLabelCount(7, true);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setInverted(false);
        leftAxis.setDrawAxisLine(true);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setDrawGridLines(true);
        leftAxis.setTextColor(Color.parseColor("#DEDEDE"));
        leftAxis.setGridColor(Color.parseColor("#DEDEDE"));
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


    public void setData(int count, float range, int status) {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            float val = (float) (Math.random() * (range + 1)) + 20;
            values.add(new Entry(i, val));
            monthXValue.add((i + 1) + "号");
        }

        LineDataSet set1;
        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "DataSet 1");
            set1.setDrawFilled(true);
            set1.setDrawCircles(true);
            set1.setLineWidth(1.5f);
            set1.setCircleRadius(3f);
            set1.setCircleColor(colors[status]);
            set1.setCircleHoleColor(Color.WHITE);
            set1.setCircleHoleRadius(1.5f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(colors[status]);
            set1.setFillDrawable(drawables[status]);

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

            // set data
            lineChart.setData(data);
            lineChart.invalidate();
        }
    }


    /**
     * type:0 周
     * type:1 月
     * type:2 年
     *
     * @param type
     * @param xValues
     */
    public void setFormat(int type, List<String> xValues) {
        this.xValues = xValues;
        XAxis xAxis = lineChart.getXAxis();
        switch (type) {
            case 0:
            case 1:
                xAxis.setLabelCount(6, false);
                break;
            case 2:
                xAxis.setLabelCount(5, false);
                break;
        }
        xAxis.setValueFormatter(xValueFormat);
        lineChart.getData().notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    class MyWeekFormat extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            return xValues.get((int) Math.abs(value) % xValues.size());
        }
    }

    public List<String> getXValues(int type) {
        List<String> result = new ArrayList<>();
        switch (type) {
            case 0:
                result = Arrays.asList(weekStr);
                break;
            case 1:
                result = monthXValue;
                break;
            case 2:
                result = Arrays.asList(yearStr);
                break;
        }
        return result;
    }

    public void setTitleValue(int type) {
        if (tvTitle != null) {
            switch (type) {
                case 0:
                    tvTitle.setText("APP线上购买金额统计（单位：元）");
                    break;
                case 1:
                    tvTitle.setText("包年包月服务购买金额统计（单位：元）");
                    break;
                case 2:
                    tvTitle.setText("app线上购买数量统计（单位：元）");
                    break;
            }
        }
    }
}
