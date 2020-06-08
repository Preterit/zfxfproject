package com.zfxf.zfxfproject.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;

import com.zfxf.base.BaseLazyLoadFragment;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.weight.CustomLineChartView;
import com.zfxf.zfxfproject.weight.TimeSelectView;

import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class ChartFragment extends BaseLazyLoadFragment implements TimeSelectView.OnDateChangeListener {

    @BindView(R.id.tsv_view)
    TimeSelectView tsvView;
    @BindView(R.id.clcvView1)
    CustomLineChartView clcvView1;
    @BindView(R.id.clcvView2)
    CustomLineChartView clcvView2;
    @BindView(R.id.clcvView3)
    CustomLineChartView clcvView3;

    public static ChartFragment newInstance() {
        Bundle args = new Bundle();
        ChartFragment fragment = new ChartFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void firstIn() {

    }

    @Override
    protected int getFragmentView() {
        return R.layout.fragment_chart;
    }

    @Override
    protected void initView() {
        super.initView();
        tsvView.setOnDateChangeListener(this);
    }

    /**
     * 切换 周/月/年
     *
     * @param status
     */
    @Override
    public void dateChange(int status) {
        switch (status) {
            case 0:
                clcvView1.setData(7, 100, 0);
                clcvView2.setData(7, 100, 1);
                clcvView3.setData(7, 100, 2);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }
}
