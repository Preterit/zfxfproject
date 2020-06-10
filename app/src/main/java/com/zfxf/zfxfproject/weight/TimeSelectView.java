package com.zfxf.zfxfproject.weight;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
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

    private String[] times = new String[2];
    private int[] ymdList = new int[3];

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

        tvLeftTime = findViewById(R.id.tvLeftTime);
        tvRightTime = findViewById(R.id.tvRightTime);

        clWeekLayout = findViewById(R.id.clWeekLayout);
        clMonthLayout = findViewById(R.id.clMonthLayout);
        clYearLayout = findViewById(R.id.clYearLayout);
        cusTimeLayout = findViewById(R.id.cusTimeLayout);

        clWeekLayout.setOnClickListener(this);
        clMonthLayout.setOnClickListener(this);
        clYearLayout.setOnClickListener(this);
        cusTimeLayout.setOnClickListener(this);
//        tvLeftTime.setOnClickListener(this);
//        tvRightTime.setOnClickListener(this);

        initLeftSelect();
        refreshTabStatus();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.tvLeftTime:  // 开始时间
//                if (leftTimeSelect == null) {
//                    initLeftSelect();
//                }
//                leftTimeSelect.show();
//                break;
//            case R.id.tvRightTime:  // 结束时间
//                if (rightTimeSelect == null) {
//                    initRightSelect();
//                }
//                rightTimeSelect.show();
//                break;
            case R.id.cusTimeLayout:
                // 自定义时间选择
                if (leftTimeSelect == null) {
                    initLeftSelect();
                }
                leftTimeSelect.show();
                break;
            case R.id.clWeekLayout:
                if (currentItem == 1) return;
                // 选中 周
                currentItem = 1;
                refreshTabStatus();
                break;
            case R.id.clMonthLayout:
                if (currentItem == 2) return;
                // 选中 月
                currentItem = 2;
                refreshTabStatus();
                break;
            case R.id.clYearLayout:
                if (currentItem == 3) return;
                // 选中 年
                currentItem = 3;
                refreshTabStatus();
                break;
        }
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
            case 0:  // 自定义时间选择
                tvTag.setEnabled(true);
                tvTagLine.setEnabled(true);
                break;
            case 1:  // 周时间选择
                tvWeek.setEnabled(true);
                tvWeekLine.setEnabled(true);
                break;
            case 2:  // 月时间选择
                tvMoth.setEnabled(true);
                tvMonthLine.setEnabled(true);
                break;
            case 3:  // 年时间选择
                tvYear.setEnabled(true);
                tvYearLine.setEnabled(true);
                break;
        }
        if (mListener != null) {
            mListener.dateChange(currentItem, times);
        }
    }


    private void initLeftSelect() {
        startDate.set(1900, 0, 1);
//        endDate.set(2020, 11, 31);
        //时间选择器
        leftTimeSelect = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String startTime = DateUtil.date2String(date);
                tvLeftTime.setText(startTime);
                times[0] = startTime;
                getTimeArray(startTime);
                leftTimeSelect.dismiss();

                // 不复用直接创建 新对象,复用新值
                initRightSelect();
                rightTimeSelect.show();
            }
        })
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDecorView((ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content))
                .setTitleText("选择开始时间")
                .build();
    }

    private void initRightSelect() {
        startDate.set(ymdList[0], ymdList[1] - 1, ymdList[2]);
//        endDate.set(ymdList[0], ymdList[1] - 1, ymdList[2]);
        //时间选择器
        rightTimeSelect = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                String endTime = DateUtil.date2String(date);
                tvRightTime.setText(endTime);
                times[1] = endTime;
                if (times.length == 2) {
                    currentItem = 0;
                    refreshTabStatus();
                }
            }
        })
                .setDate(startDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setDecorView((ViewGroup) ((Activity) mContext).getWindow().getDecorView().findViewById(android.R.id.content))
                .setTitleText("选择结束时间")
                .build();
    }

    private OnDateChangeListener mListener;

    public void setOnDateChangeListener(OnDateChangeListener listener) {
        this.mListener = listener;
    }

    public interface OnDateChangeListener {
        void dateChange(int status, String[] times);
    }

    private void getTimeArray(String time) {
        if (TextUtils.isEmpty(time)) {
            return;
        }
        String[] split = time.split("-");
        for (int i = 0; i < split.length; i++) {
            ymdList[i] = Integer.parseInt(split[i]);
        }
    }
}
