package com.zfxf.zfxfproject.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.github.mikephil.charting.charts.LineChart;
import com.zfxf.zfxfproject.R;

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
    }

    /**
     * 初始化lineChart
     */
    private void initLineChart() {
        /**
         * 1、刷新
         *    invalidate()：在chart中调用会使其刷新重绘
         *    notifyDataChanged()：让chart知道它依赖的基础数据已经改变，并执行所有必要的重新计算（比如偏移量，lenged，最大值，最小值...）。在动态添加数据时需要用到。
         * 2、打印日志
         *    setLogEnable(boolean enabled)：设置为true将激活chart的logcat输出。但这不利于性能，如果不是必要的，应保持禁用。
         * 3、chart属性
         *    setBackgroundColor(int color)：设置背景颜色，将覆盖整个图表视图。此外，背景颜色可以在布局文件.xml中进行设置。
         *    setDescription(Description desc)：设置图表的描述文字，会显示在图表的右下角。
         *    setDescriptionColor(int color)：设置描述文字的颜色。
         *    setDescriptionPosition(float x，floaty)：自定义描述文字在屏幕上的位置(单位是像素)。
         *    setDescriptionTypeface(Typeface t)：设置描述文字的字体。
         *    setDescriptionTextSize(float size)：设置以像素为单位的描述文字，最小6f，最大16f。
         *    setNoDataTextDescription(String desc)：设置当chart为空时显示的描述文字。
         *    setDrawGridBackground(boolean enabled)：如果启用，chart绘图区后面的背景矩形将绘制。
         *    setGridBackgroundColor(int color)：设置网格背景应与绘制的颜色。
         *    setDrawBorder(boolean enabled)：启用/禁用绘制图表边框（chart周围的线）。
         *    setBorderColor（int color）：设置chart边框线的颜色。
         *    setBorderWidth（float width）：设置chart边界线的宽度，单位dp。
         *    setMaxVisibleValueCount（int count）：设置最大可见绘制的chartcount的数量。只在setDrawValues（）设置为true时有效。
         * 4、启用/禁用  手势交互
         *    setTouchEnabled(boolean enabled)：启用/禁用与图表的所有可能的触摸交互。
         *    setDragEnabled(boolean enabled)：启用/禁用拖动（平移）图表。
         *    setScaleEnabled（boolean enabled）：启用/禁用缩放图表上的两个轴。
         *    setScaleXEnabled(boolean enabled)：启用/禁用缩放在X轴上。
         *    setScaleYEnabled(boolean enabled)：启用/禁用缩放在Y轴上。
         *    setPinchZoom（boolena enabled）：如果设置为true，没缩放功能。如果false，x轴和y轴可分别放大。
         *    setDoubleTapToZoomEnabled（booleanenabled）：设置为false以禁止通过在其上双击缩放图表。
         *    setHighlightPerDragEnabled（booleanenabled）：设置为true，允许每个图表表面拖过，当它完全缩小突出。默认值：true
         *    setHighlightPerTapEnabled(boolean enabled)：设置为false，以防止值由敲击姿态被突出显示。值仍然可以通过拖动或编程方式突出显示。默认值：true。
         * 5、图表的抛掷/减速
         *    setDragDecelerationEnabled(boolean enabled)：如果设置为true，手指滑动抛掷图表后继续减速滚动。默认值：true。
         *     setDragDecelerationFrictionCoef（floatcoef）：减速的摩擦系数在[0；1]区间，数值越高表示速度回缓慢下降，例如，如果将其设置为0，将立即停止。1是一个无效的值，会自动转换至0.9999。
         * 6、高亮
         *     highlightValues（Highlight[] highs）：高亮显示值，高亮显示的点击的位置在数据集中的值。设置null或空数组则撤销所有高亮。
         *     highlightValue（int xIndex，intdataSetIndex）：高亮给定xIndex在数据集的值。设置xIndex或dataSetIndex为-1撤销所有高亮。
         *     getHighlightd（）：返回一个highlight[]其中包含所有高亮对象的信息，xIndex和dataSetIndex。以Java编程方式使得值高亮不会回调onChartValueSelectedListener。
         * 7、选择回调
         */

        lineChart.setScaleXEnabled(false);
        lineChart.setScaleYEnabled(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);

        
    }

}
