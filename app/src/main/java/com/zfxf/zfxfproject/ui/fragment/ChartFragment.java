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
    private String customizeFrom = "2020-06-09 00:00:00";  //自定义时间从
    private String customizeTo = "2020-06-09 23:59:59";   //自定义时间到

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
        clcvView1.setTitleValue(1);
        clcvView2.setTitleValue(2);
        clcvView3.setTitleValue(3);
        clcvView3.setTitleValue(4);

//        List<ChartInfoBean.ChartValueBean> list = new ArrayList<>();
//        list.add(new ChartInfoBean.ChartValueBean("1号","10"));
//        list.add(new ChartInfoBean.ChartValueBean("2号","30"));
//        list.add(new ChartInfoBean.ChartValueBean("3号","20"));
//        list.add(new ChartInfoBean.ChartValueBean("4号","5"));
//        list.add(new ChartInfoBean.ChartValueBean("5号","15"));
//        list.add(new ChartInfoBean.ChartValueBean("6号","18"));
//        list.add(new ChartInfoBean.ChartValueBean("7号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("8号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("9号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("10号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("11号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("12号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("13号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("14号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        list.add(new ChartInfoBean.ChartValueBean("15号","25"));
//        clcvView1.setData(list, 0);  // APP线上购买金额统计（单位：元）
//        clcvView1.setFormat(0, getXValuesList(list));
    }

    /**
     * 切换 周/月/年
     *
     * @param status
     */
    @Override
    public void dateChange(int status, String[] times) {
        if (status == 0) {
            timeType = 0;
            queryType = 2;
            customizeFrom = times[0] + " 00:00:00";
            customizeTo = times[1] + " 23:59:59";
        } else {
            queryType = 1;
            timeType = status;
        }
        initData();
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
        clcvView2.setData(bean.vipMoneyList, 1);       // 包年包月服务购买金额统计（单位：元）
        clcvView3.setData(bean.appOnlineCountList, 2);    // app线上购买数量统计（单位：元）
        clcvView4.setData(bean.vipCountList, 3);    // 包年包月服务购买数量统计
        if (bean.appOnlineMoneyList != null&&bean.appOnlineMoneyList.size()>0) {
            clcvView1.setFormat(timeType, getXValuesList(bean.appOnlineMoneyList));
        }
        if (bean.vipMoneyList != null&&bean.vipMoneyList.size()>0) {
            clcvView2.setFormat(timeType, getXValuesList(bean.vipMoneyList));
        }
        if (bean.appOnlineCountList != null&&bean.appOnlineCountList.size()>0) {
            clcvView3.setFormat(timeType, getXValuesList(bean.appOnlineCountList));
        }
        if (bean.vipCountList != null&&bean.vipCountList.size()>0) {
            clcvView4.setFormat(timeType, getXValuesList(bean.vipCountList));
        }
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
