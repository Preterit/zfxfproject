package com.zfxf.zfxfproject.weight;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
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
import com.zfxf.douniu_network.entry.ChartInfoBean;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.util.DateUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CustomLineChartView extends LinearLayout {

    private final String TAG = "CustomLineChartView";

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
    public List monthDaysList = new ArrayList<String>();
    private List<String> xValues = new ArrayList<>();

    private MyWeekFormat xValueFormat = new MyWeekFormat();
    private MyCustomTimeFormat myCustomTimeFormat = new MyCustomTimeFormat();
    private CustomXAxisRenderer customXAxisRenderer;

    private List<String> xValuesMap = new ArrayList<>();

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

        customXAxisRenderer = new CustomXAxisRenderer(
                lineChart.getViewPortHandler(),
                lineChart.getXAxis(),
                lineChart.getTransformer(YAxis.AxisDependency.LEFT));
        lineChart.setXAxisRenderer(customXAxisRenderer);

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
        }
        pushData(values, status);
    }

    public void setData(List<ChartInfoBean.ChartValueBean> data, int status) {
        try {
            if (data == null) {
                setNoData();
                return;
            }
            if (data.size() == 0) {
                setNoData();
                return;
            }
            ArrayList<Entry> values = new ArrayList<>();
            for (int i = 0; i < data.size(); i++) {
                ChartInfoBean.ChartValueBean item = data.get(i);
                float val = Float.parseFloat(item.value);
                values.add(new Entry(i, val));
            }
            pushData(values, status);
        } catch (Exception e) {
            e.printStackTrace();
            setNoData();
        }
    }

    private void setNoData() {
        lineChart.setData(null);
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    private void pushData(ArrayList<Entry> values, int status) {
        LineDataSet set1;

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

    /**
     * type:0 周
     * type:1 月
     * type:2 年
     *
     * @param type
     * @param xValues
     */
    public void setFormat(int type, List<String> xValues) {
        XAxis xAxis = lineChart.getXAxis();
        lineChart.removeAllViews();
        lineChart.resetViewPortOffsets();
        switch (type) {
            case 0:
                this.xValues = xValues;
                xAxis.setAxisMaximum(xValues.size() - 1);
                xAxis.setAxisMinimum(0f);
                lineChart.setExtraBottomOffset(14f);
                xAxis.setValueFormatter(myCustomTimeFormat);

                xValuesMap.clear();

                lineChart.getData().notifyDataChanged();
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

                if (xValues.size() < 6) {
                    xAxis.setLabelCount(xValues.size() - 1, false);
                } else if (xValues.size() >= 6 && xValues.size() <= 10) {
                    xAxis.setLabelCount(4, false);
                } else {
                    xAxis.setLabelCount(5, false);
                }

                customXAxisRenderer.setFistData("");
                customXAxisRenderer.setLaseData("");
                if (xValues.size() > 1) {
                    customXAxisRenderer.setFistData(xValues.get(0));
                    String lastLabel = getLastLableStr();
                    customXAxisRenderer.setLaseData(lastLabel);
                }

                break;
            case 1:
                this.xValues = Arrays.asList(weekStr);
                xAxis.setAxisMaximum(6f);
                xAxis.setAxisMinimum(0f);
                xAxis.setLabelCount(6, false);
                xAxis.setLabelRotationAngle(0);
                xAxis.setValueFormatter(xValueFormat);
                lineChart.setExtraBottomOffset(0);
                break;
            case 2:
                xAxis.setAxisMaximum(xValues.size() - 1);
                xAxis.setAxisMinimum(0f);
                xAxis.setLabelCount(6, false);
                this.xValues = getXValues();
                xAxis.setLabelRotationAngle(0);
                xAxis.setValueFormatter(xValueFormat);
                lineChart.setExtraBottomOffset(0);
                break;
            case 3:
                xAxis.setAxisMaximum(11f);
                xAxis.setAxisMinimum(0f);
                xAxis.setLabelRotationAngle(0);
                xAxis.setLabelCount(6, false);
                this.xValues = Arrays.asList(yearStr);
                xAxis.setValueFormatter(xValueFormat);
                lineChart.setExtraBottomOffset(0);
                break;
        }
        lineChart.getData().notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.invalidate();
    }

    private String getLastLableStr() {
        if (xValuesMap.size() == 0) {
            return "";
        }
        int position = 0;
        int i = 0;
        for (int j = 0; j < xValuesMap.size(); j++) {
            String str = xValuesMap.get(j);
            String[] split = str.split(",");
            int item = Integer.parseInt(split[0].split("/")[1]);
            if (item > i) {
                i = item;
                position = j;
            }
        }
        return xValuesMap.get(position);
    }

    /**
     * 获取月的总条目数量
     */
    private List<String> getXValues() {
        int days = DateUtil.getCurrentMonthDays();
        if (monthDaysList.size() != days) {
            for (int i = 0; i < days; i++) {
                monthDaysList.add((i + 1) + "号");
            }
        }
        return monthDaysList;
    }

    class MyCustomTimeFormat extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            if (xValues.size() == 0) return "";
            if (xValues.size() == 2) {
                if (value == 0) {
                    if (!xValuesMap.contains(xValues.get(0))) {
                        xValuesMap.add(xValues.get(0));
                    }
                    return xValues.get(0);
                } else if (value == 1) {
                    if (!xValuesMap.contains(xValues.get(1))) {
                        xValuesMap.add(xValues.get(1));
                    }
                    return xValues.get(1);
                }
            } else if (xValues.size() == 1) {
                if (value == 0) {
                    return xValues.get(0);
                }
            } else {
//                return xValues.get((int) Math.abs(value) % xValues.size());
                Log.e(TAG, "getFormattedValue: " + xValues.get((int) Math.abs(value) % xValues.size()) + "--" + value);
                String result = xValues.get((int) Math.abs(value) % xValues.size());
                if (!xValuesMap.contains(result)) {
                    xValuesMap.add(result);
                }
                return result;
            }
            return "";
        }
    }

    class MyWeekFormat extends ValueFormatter {
        @Override
        public String getFormattedValue(float value) {
            if (xValues.size() == 0) return "";
            return xValues.get((int) Math.abs(value) % xValues.size());
        }
    }

    public void setTitleValue(int type) {
        if (tvTitle != null) {
            switch (type) {
                case 1:
                    tvTitle.setText("APP线上购买金额统计（单位：元）");
                    break;
                case 2:
                    tvTitle.setText("包年包月服务购买金额统计（单位：元）");
                    break;
                case 3:
                    tvTitle.setText("app线上购买数量统计（单位：元）");
                    break;
                case 4:
                    tvTitle.setText("包年包月服务购买数量统计");
                    break;
            }
        }
    }
}
