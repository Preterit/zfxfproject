package com.zfxf.zfxfproject.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.zfxf.zfxfproject.R;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class TopLabSelectView extends LinearLayout implements View.OnClickListener {
    private Context mContext;
    private OnTopLabSelectListener mListener;

    private ImageView imgJGC, imgNRK, imgNSD, imgZMYB, imgShop;
    private TextView tvJGC, tvNRK, tvNSD, tvZMYB, tvShop;
    private ConstraintLayout conJGC, conNRK, conNSD, conZMYB, conShop;

    public TopLabSelectView(Context context) {
        this(context, null);
    }

    public TopLabSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TopLabSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.weight_top_labselect_view, this);

        imgJGC = findViewById(R.id.imgJGC);
        imgNRK = findViewById(R.id.imgNRK);
        imgNSD = findViewById(R.id.imgNSD);
        imgZMYB = findViewById(R.id.imgZMYB);
        imgShop = findViewById(R.id.imgShop);

        tvJGC = findViewById(R.id.tvJGC);
        tvNRK = findViewById(R.id.tvNRK);
        tvNSD = findViewById(R.id.tvNSD);
        tvZMYB = findViewById(R.id.tvZMYB);
        tvShop = findViewById(R.id.tvShop);

        conJGC = findViewById(R.id.conJGC);
        conNRK = findViewById(R.id.conNRK);
        conNSD = findViewById(R.id.conNSD);
        conZMYB = findViewById(R.id.conZMYB);
        conShop = findViewById(R.id.conShop);

        conJGC.setOnClickListener(this);
        conNRK.setOnClickListener(this);
        conNSD.setOnClickListener(this);
        conZMYB.setOnClickListener(this);
        conShop.setOnClickListener(this);

        initStatus(0);
    }

    /**
     * 初始化状态
     *
     * @param position
     */
    private void initStatus(int position) {
        tvJGC.setEnabled(false);
        tvNRK.setEnabled(false);
        tvNSD.setEnabled(false);
        tvZMYB.setEnabled(false);
        tvShop.setEnabled(false);

        imgJGC.setEnabled(false);
        imgNRK.setEnabled(false);
        imgNSD.setEnabled(false);
        imgZMYB.setEnabled(false);
        imgShop.setEnabled(false);

        switch (position) {
            case 0:  // 金股池
                tvJGC.setEnabled(true);
                imgJGC.setEnabled(true);
                break;
            case 1:  // 牛人课
                tvNRK.setEnabled(true);
                imgNRK.setEnabled(true);
                break;
            case 2:  // 牛视点
                tvNSD.setEnabled(true);
                imgNSD.setEnabled(true);
                break;
            case 3:  // 知码研报
                tvZMYB.setEnabled(true);
                imgZMYB.setEnabled(true);
                break;
            case 4:  // 商城
                tvShop.setEnabled(true);
                imgShop.setEnabled(true);
                break;
        }
        if (mListener != null) {
            mListener.onLabSelected(position);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.conJGC:
                initStatus(0);
                break;
            case R.id.conNRK:
                initStatus(1);
                break;
            case R.id.conNSD:
                initStatus(2);
                break;
            case R.id.conZMYB:
                initStatus(3);
                break;
            case R.id.conShop:
                initStatus(4);
                break;
        }
    }

    public void setOnTopLabSelectListener(OnTopLabSelectListener listener) {
        this.mListener = listener;
    }

    public interface OnTopLabSelectListener {
        void onLabSelected(int position);
    }

}
