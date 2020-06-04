package com.zfxf.zfxfproject.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class TimeSelectView extends LinearLayout implements View.OnClickListener {

    private Context mContext;
    private TextView tvLeftTime, tvRightTime;
    private TextView tvWeek, tvYear;

    private TimePickerView leftTimeSelect;
    private TimePickerView rightTimeSelect;

    private Calendar selectedDate = Calendar.getInstance();
    private Calendar startDate = Calendar.getInstance();
    private Calendar endDate = Calendar.getInstance();

    public TimeSelectView(Context context) {
        this(context, null);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TimeSelectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    /**
     * 初始话
     */
    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.weight_time_select_view, this);
        tvLeftTime = findViewById(R.id.tvLeftTime);
        tvRightTime = findViewById(R.id.tvRightTime);
        tvWeek = findViewById(R.id.tvWeek);
        tvYear = findViewById(R.id.tvYear);

        tvLeftTime.setOnClickListener(this);
        tvRightTime.setOnClickListener(this);
        tvWeek.setOnClickListener(this);
        tvYear.setOnClickListener(this);

        initLeftSelect();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLeftTime:
                // 左边时间选择
                if (leftTimeSelect == null) {
                    initLeftSelect();
                }
                leftTimeSelect.show();
                break;
            case R.id.tvRightTime:
                // 右边时间选择
                if (rightTimeSelect == null) {
                    initRightSelect();
                }
                rightTimeSelect.show();
                break;
            case R.id.tvWeek:
                // 选中 周

                break;
            case R.id.tvYear:
                // 选中 年
                break;
        }
    }


    private void initLeftSelect() {
        startDate.set(1900, 0, 1);
        endDate.set(2020, 11, 31);
        //时间选择器
        leftTimeSelect = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvLeftTime.setText(DateUtil.date2String(date));
            }
        })
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDecorView((ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
    }

    private void initRightSelect() {
        startDate.set(1900, 0, 1);
        endDate.set(2020, 11, 31);
        //时间选择器
        rightTimeSelect = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tvRightTime.setText(DateUtil.date2String(date));
            }
        })
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDecorView((ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content))
                .build();
    }
}
