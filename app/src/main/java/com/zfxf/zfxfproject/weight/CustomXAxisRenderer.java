package com.zfxf.zfxfproject.weight;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

/**
 * @author :  lwb
 * Date: 2020/6/11
 * Desc:
 */
public class CustomXAxisRenderer extends XAxisRenderer {

    private Paint mSecondLinePaint;
    private Paint mFirstLinePaint;
    private String mFirstTx = "", mLastTx = "";

    public CustomXAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer trans) {
        super(viewPortHandler, xAxis, trans);
        init();
    }

    private void init() {
        mSecondLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mFirstLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mFirstLinePaint.setColor(Color.BLACK);
        mFirstLinePaint.setTextAlign(Paint.Align.CENTER);
        mFirstLinePaint.setTextSize(Utils.convertDpToPixel(8.5f));
        mFirstLinePaint.setTypeface(mXAxis.getTypeface());

        mSecondLinePaint.setColor(0xFF9b9b9b);
        mSecondLinePaint.setTextAlign(Paint.Align.CENTER);
        mSecondLinePaint.setTextSize(Utils.convertDpToPixel(10f));
        mSecondLinePaint.setTypeface(mXAxis.getTypeface());
    }

    @Override
    protected void drawLabel(Canvas c, String formattedLabel, float x, float y, MPPointF anchor, float angleDegrees) {
        float labelHeight = mXAxis.getTextSize();
        float labelInterval = 0f;
        float firstTxTopOffset = 5f;
        String[] labels = formattedLabel.split(",");
        if (labels.length > 1) {
            mFirstLinePaint.setTextSize(Utils.convertDpToPixel(8.5f));
            mSecondLinePaint.setTextSize(Utils.convertDpToPixel(10f));
            int firstTxOffset = Utils.calcTextWidth(mSecondLinePaint, labels[1]);
            if (mFirstTx.equals(formattedLabel)) {
                Utils.drawXAxisValue(c, labels[0], x - firstTxOffset, y + firstTxTopOffset, mFirstLinePaint, anchor, angleDegrees);
                Utils.drawXAxisValue(c, labels[1], x - firstTxOffset, y + labelHeight + labelInterval, mSecondLinePaint, anchor, angleDegrees);
            } else if (mLastTx.equals(formattedLabel)) {
                Utils.drawXAxisValue(c, labels[0], x + firstTxOffset, y + firstTxTopOffset, mFirstLinePaint, anchor, angleDegrees);
                Utils.drawXAxisValue(c, labels[1], x + firstTxOffset, y + labelHeight + labelInterval, mSecondLinePaint, anchor, angleDegrees);
            } else {
                Utils.drawXAxisValue(c, labels[0], x, y + firstTxTopOffset, mFirstLinePaint, anchor, angleDegrees);
                Utils.drawXAxisValue(c, labels[1], x, y + labelHeight + labelInterval, mSecondLinePaint, anchor, angleDegrees);
            }
        } else {
            mFirstLinePaint.setTextSize(Utils.convertDpToPixel(10f));
            Utils.drawXAxisValue(c, formattedLabel, x, y, mFirstLinePaint, anchor, angleDegrees);
        }
    }

    public void setFistData(String tx) {
        this.mFirstTx = tx;
    }

    public void setLaseData(String tx) {
        this.mLastTx = tx;
    }
}
