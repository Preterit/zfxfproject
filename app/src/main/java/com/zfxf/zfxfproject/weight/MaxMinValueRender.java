package com.zfxf.zfxfproject.weight;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.renderer.LineChartRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.zfxf.zfxfproject.util.ScreenUnit;

import java.text.DecimalFormat;

/**
 * Date:2020/6/4
 * author:lwb
 * Desc:
 */
public class MaxMinValueRender extends LineChartRenderer {

    private int offset_y = 10;
    private int offset_x = 10;
    private int textOffset = 20;


    public MaxMinValueRender(LineDataProvider chart, ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(chart, animator, viewPortHandler);
    }

    @Override
    public void drawValue(Canvas c, String valueText, float x, float y, int color) {
        if (Integer.parseInt(valueText) == (maxValue)) {
            LineDataSet dataSet = (LineDataSet) mChart.getLineData().getDataSetByIndex(0);

            Transformer trans = mChart.getTransformer(dataSet.getAxisDependency());
            MPPointD pointD = trans.getPixelForValues(x, Float.parseFloat(valueText));

            Paint paintDrawPointFill = new Paint(Paint.ANTI_ALIAS_FLAG);
            paintDrawPointFill.setStyle(Paint.Style.FILL);
            paintDrawPointFill.setColor(Color.WHITE);
            c.drawCircle((float) pointD.x, (float) pointD.y, ScreenUnit.dp2px(6), paintDrawPointFill);

            String textTag = "文字内容";
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setTextSize(ScreenUnit.dp2px(12));
            paint.setColor(Color.parseColor("#000000"));
            Rect rectTextBounds = new Rect();
            paint.getTextBounds(textTag, 0, textTag.length(), rectTextBounds);

            int textWidth = (rectTextBounds.right - rectTextBounds.left);
            int textHeight = (rectTextBounds.bottom - rectTextBounds.top);
            RectF rectF = new RectF((int) offset_x - textOffset,
                    (int) offset_y - textHeight - textOffset,
                    (int) offset_x + textWidth + textOffset,
                    (int) offset_y + textOffset);
            c.drawRoundRect(rectF, x, y, paint);
        }
    }

    private int maxValue;

    public void setMaxValue(float maxValue) {
//        Log.e("setMaxValue", "" + maxValue);
//        DecimalFormat fnum = new DecimalFormat("##0.0");
//        fnum.format(maxValue)
        this.maxValue = (int) maxValue;
    }
}
