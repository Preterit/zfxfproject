package com.zfxf.zfxfproject.ui.fragment;

import android.os.Bundle;

import com.zfxf.base.BaseLazyLoadFragment;
import com.zfxf.douniu_network.CharDataRequest;
import com.zfxf.douniu_network.entry.ChartInfoBean;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.weight.CustomLineChartView;
import com.zfxf.zfxfproject.weight.TimeSelectView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class ChartFragment extends BaseLazyLoadFragment implements TimeSelectView.OnDateChangeListener, CharDataRequest.OnChartDataChange {

    @BindView(R.id.tsv_view)
    TimeSelectView tsvView;
    @BindView(R.id.clcvView1)
    CustomLineChartView clcvView1;
    @BindView(R.id.clcvView2)
    CustomLineChartView clcvView2;
    @BindView(R.id.clcvView3)
    CustomLineChartView clcvView3;
    @BindView(R.id.clcvView4)
    CustomLineChartView clcvView4;

    private int queryType = 1;  //查询类型 1 指定时间查询、2 自定义时间查询
    private int timeType = 1;  //指定时间类型 1 本周、2 本月、3 本年
    private String customizeFrom = "2020-06-09";  //自定义时间从
    private String customizeTo = "2020-06-09";   //自定义时间到

    private CharDataRequest mRequest;

    /**
     * 数据统计产品类型 m1001 金股池、m1002 商城、m1004 知码研报、m1007 牛视点、m1017 牛人课
     *
     * @param type
     * @return
     */
    public static ChartFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("statisticsType", type);
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
        mRequest = new CharDataRequest(getContext());
//        clcvView1.setData(7, 100, 0);
//        clcvView2.setData(7, 100, 1);
//        clcvView3.setData(7, 100, 2);
//        clcvView4.setData(7, 100, 3);
//        clcvView1.setFormat(0, clcvView1.getXValues(0));
//        clcvView2.setFormat(0, clcvView1.getXValues(0));
//        clcvView3.setFormat(0, clcvView1.getXValues(0));
    }

    /**
     * 切换 周/月/年
     *
     * @param status
     */
    @Override
    public void dateChange(int status) {
        timeType = status;
        initData();
//        switch (status) {
//            case 0:
//                clcvView1.setData(7, 100, 0);
//                clcvView2.setData(7, 100, 1);
//                clcvView3.setData(7, 100, 2);
//                clcvView4.setData(7, 100, 3);
//                clcvView1.setFormat(0, clcvView1.getXValues(0));
//                clcvView2.setFormat(0, clcvView1.getXValues(0));
//                clcvView3.setFormat(0, clcvView1.getXValues(0));
//                break;
//            case 1:
//                clcvView1.setData(31, 100, 0);
//                clcvView2.setData(31, 100, 1);
//                clcvView3.setData(31, 100, 2);
//                clcvView4.setData(31, 100, 3);
//                clcvView1.setFormat(1, clcvView1.getXValues(1));
//                clcvView2.setFormat(1, clcvView2.getXValues(1));
//                clcvView3.setFormat(1, clcvView3.getXValues(1));
//                break;
//            case 2:
//                clcvView1.setData(12, 100, 0);
//                clcvView2.setData(12, 100, 1);
//                clcvView3.setData(12, 100, 2);
//                clcvView4.setData(12, 100, 3);
//                clcvView1.setFormat(2, clcvView1.getXValues(2));
//                clcvView2.setFormat(2, clcvView1.getXValues(2));
//                clcvView3.setFormat(2, clcvView1.getXValues(2));
//                break;
//        }
    }


    @Override
    protected void initData() {
        String statisticsType = getArguments().getString("statisticsType");
        mRequest.requestChartData(statisticsType, queryType, timeType, customizeFrom, customizeTo, this);
    }

    /**
     * 刷新chartData
     *
     * @param bean
     */
    @Override
    public void chartData(ChartInfoBean bean) {
        clcvView1.setData(bean.appOnlineMoneyList, 0);  // APP线上购买金额统计（单位：元）
//        clcvView2.setData(bean.vipMoneyList, 1);       // 包年包月服务购买金额统计（单位：元）
//        clcvView3.setData(bean.appOnlineCountList, 2);    // app线上购买数量统计（单位：元）
//        clcvView4.setData(bean.vipCountList, 3);    // 包年包月服务购买数量统计
        clcvView1.setFormat(timeType, getXValuesList(bean.appOnlineMoneyList));
//        clcvView2.setFormat(timeType, getXValuesList(bean.vipMoneyList));
//        clcvView3.setFormat(timeType, getXValuesList(bean.appOnlineCountList));
//        clcvView4.setFormat(timeType, getXValuesList(bean.vipCountList));
    }

    public List<String> getXValuesList(List<ChartInfoBean.ChartValueBean> data) {
        List<String> list = new ArrayList<>();
        if (data != null) {
            for (ChartInfoBean.ChartValueBean item : data) {
                list.add(item.abscissa);
            }
        }
        return list;
    }
}
