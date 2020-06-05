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
     * @param i
     */
    private void initStatus(int i) {
        
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.conJGC:

                break;
            case R.id.conNRK:
                break;
            case R.id.conNSD:
                break;
            case R.id.conZMYB:
                break;
            case R.id.conShop:
                break;
        }
    }


}
