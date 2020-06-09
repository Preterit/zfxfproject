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
import androidx.constraintlayout.widget.ConstraintLayout;

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
    private ConstraintLayout clWeekLayout, clMonthLayout, clYearLayout;
    private ConstraintLayout cusTimeLayout; // 自定义时间选择框
    private TextView tvWeek, tvMoth, tvYear, tvTag;
    private TextView tvWeekLine, tvMonthLine, tvYearLine, tvTagLine;
    private int currentItem = 1;  // 当前选中的视图,默认是周.


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
        tvWeek = findViewById(R.id.tvWeek);
        tvWeekLine = findViewById(R.id.tvWeekLine);
        tvMoth = findViewById(R.id.tvMoth);
        tvMonthLine = findViewById(R.id.tvMonthLine);
        tvYear = findViewById(R.id.tvYear);
        tvYearLine = findViewById(R.id.tvYearLine);
        tvTag = findViewById(R.id.tvTag);
        tvTagLine = findViewById(R.id.tvTagLine);

        tvRightTime = findViewById(R.id.tvRightTime);
        clWeekLayout = findViewById(R.id.clWeekLayout);
        clMonthLayout = findViewById(R.id.clMonthLayout);
        clYearLayout = findViewById(R.id.clYearLayout);
        cusTimeLayout = findViewById(R.id.cusTimeLayout);

        clWeekLayout.setOnClickListener(this);
        clMonthLayout.setOnClickListener(this);
        clYearLayout.setOnClickListener(this);
        cusTimeLayout.setOnClickListener(this);

        initLeftSelect();
        refreshTabStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvLeftTime:
                // 左边时间选择
//                if (leftTimeSelect == null) {
//                    initLeftSelect();
//                }
//                leftTimeSelect.show();
                break;
            case R.id.tvRightTime:
                // 右边时间选择
                if (rightTimeSelect == null) {
                    initRightSelect();
                }
                rightTimeSelect.show();
                break;

            case R.id.cusTimeLayout:
                if (currentItem == 0) {
                    return;
                }
                // 自定义时间选择
                if (leftTimeSelect == null) {
                    initLeftSelect();
                }
                leftTimeSelect.show();
                currentItem = 0;
                break;
            case R.id.clWeekLayout:
                if (currentItem == 1) return;
                // 选中 周
                currentItem = 1;
                break;
            case R.id.clMonthLayout:
                if (currentItem == 2) return;
                // 选中 月
                currentItem = 2;
                break;
            case R.id.clYearLayout:
                if (currentItem == 3) return;
                // 选中 年
                currentItem = 3;
                break;
        }
        refreshTabStatus();
    }

    /**
     * 刷新tab的状态
     */
    private void refreshTabStatus() {
        tvWeek.setEnabled(false);
        tvWeekLine.setEnabled(false);
        tvMoth.setEnabled(false);
        tvMonthLine.setEnabled(false);
        tvYear.setEnabled(false);
        tvYearLine.setEnabled(false);
        tvTag.setEnabled(false);
        tvTagLine.setEnabled(false);
        switch (currentItem) {
            case 0:
                tvTag.setEnabled(true);
                tvTagLine.setEnabled(true);
                break;
            case 1:
                tvWeek.setEnabled(true);
                tvWeekLine.setEnabled(true);
                break;
            case 2:
                tvMoth.setEnabled(true);
                tvMonthLine.setEnabled(true);
                break;
            case 3:
                tvYear.setEnabled(true);
                tvYearLine.setEnabled(true);
                break;
        }
        if (mListener != null) {
            mListener.dateChange(currentItem);
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

    private OnDateChangeListener mListener;

    public void setOnDateChangeListener(OnDateChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnDateChangeListener {
        void dateChange(int status);
    }
}
