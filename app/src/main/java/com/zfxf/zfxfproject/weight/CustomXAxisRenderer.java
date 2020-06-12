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

    private final String TAG = "CustomXAxisRenderer";

    private Paint mSecondLinePaint;
    private Paint mFirstLinePaint;

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

            // TODO 第一个值进行 居中处理
//            if (mFirstTx.equals(formattedLabel)) {
//                Utils.drawXAxisValue(c, labels[0], x - firstTxOffset, y + firstTxTopOffset, mFirstLinePaint, anchor, angleDegrees);
//                Utils.drawXAxisValue(c, labels[1], x - firstTxOffset, y + labelHeight + labelInterval, mSecondLinePaint, anchor, angleDegrees);
//            }
//            else if (mLastTx.equals(formattedLabel)) {
//                Utils.drawXAxisValue(c, labels[0], x + firstTxOffset, y + firstTxTopOffset, mFirstLinePaint, anchor, angleDegrees);
//                Utils.drawXAxisValue(c, labels[1], x + firstTxOffset, y + labelHeight + labelInterval, mSecondLinePaint, anchor, angleDegrees);
//            }
//            else {
                Utils.drawXAxisValue(c, labels[0], x, y + firstTxTopOffset, mFirstLinePaint, anchor, angleDegrees);
                Utils.drawXAxisValue(c, labels[1], x, y + labelHeight + labelInterval, mSecondLinePaint, anchor, angleDegrees);
//            }
        } else {
            mFirstLinePaint.setTextSize(Utils.convertDpToPixel(10f));
            Utils.drawXAxisValue(c, formattedLabel, x, y, mFirstLinePaint, anchor, angleDegrees);
        }
    }

    @Override
    protected void drawLabels(Canvas c, float pos, MPPointF anchor) {
        final float labelRotationAngleDegrees = mXAxis.getLabelRotationAngle();
        boolean centeringEnabled = mXAxis.isCenterAxisLabelsEnabled();

        float[] positions = new float[mXAxis.mEntryCount * 2];
        for (int i = 0; i < positions.length; i += 2) {
            // only fill x values
            if (centeringEnabled) {
                positions[i] = mXAxis.mCenteredEntries[i / 2];
            } else {
                positions[i] = mXAxis.mEntries[i / 2];
            }
        }
        mTrans.pointValuesToPixel(positions);

        for (int i = 0; i < positions.length; i += 2) {
            float x = positions[i];
            if (mViewPortHandler.isInBoundsX(x)) {
                String label = mXAxis.getValueFormatter().getFormattedValue(mXAxis.mEntries[i / 2], mXAxis);
                if (mXAxis.isAvoidFirstLastClippingEnabled()) {
                    // avoid clipping of the last
                    float width = Utils.calcTextWidth(mAxisLabelPaint, label);
                    if (i == mXAxis.mEntryCount * 2 - 2 && mXAxis.mEntryCount > 1) {
                        //TODO  x轴label 最后一个值
                        x -= 6f;
                        // avoid clipping of the first
                    } else if (i == 0) {
                        //TODO  x轴label 第一个值
//                        x += width / 2;
                    }
                }

                drawLabel(c, label, x, pos, anchor, labelRotationAngleDegrees);
            }
        }
    }
}
