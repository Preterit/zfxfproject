package com.zfxf.douniu_network;

import android.content.Context;
import android.util.Log;

import com.zfxf.douniu_network.entry.ChartInfoBean;
import com.zfxf.douniu_network.util.CommonUtils;
import com.zfxf.zfxfproject.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * @author :  lwb
 * Date: 2020/6/9
 * Desc:
 */
public class CharDataRequest {

    private final String TAG = "CharDataRequest";
    private Context mContext;
    private OnChartDataChange mListener;

    public CharDataRequest(Context context) {
        WeakReference contextWeakReference = new WeakReference<Context>(context);
        if (contextWeakReference.get() != null) {
            mContext = (Context) contextWeakReference.get();
        }
    }

    public void requestChartData(
            String statisticsType,
            int queryType,
            int timeType,
            String customizeFrom,
            String customizeTo,
            OnChartDataChange listener
    ) {
        this.mListener = listener;
        Map<String, Object> map = new HashMap<>();
        map.put("statisticsType", statisticsType);
        map.put("queryType", queryType);
        map.put("timeType", timeType);
        map.put("customizeFrom", customizeFrom);
        map.put("customizeTo", customizeTo);
        new BaseInternetRequestNew(mContext, new BaseInternetRequestNew.HttpUtilsListenerNew<ChartInfoBean>() {
            @Override
            public void onResponse(ChartInfoBean bean, int id) {
                if ("10".equals(bean.businessCode)) {
                    if (mListener != null) {
                        mListener.chartData(bean);
                    }
                } else {
                    CommonUtils.toastMessage(bean.businessMessage);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                return false;
            }
        }).postSign(mContext.getString(R.string.gatChartData), true, map, ChartInfoBean.class);
    }

    public void requestYmdData(
            String statisticsType,
            int queryType,
            int timeType,
            String customizeFrom,
            String customizeTo,
            OnChartDataChange listener
    ) {
        this.mListener = listener;
        Map<String, Object> map = new HashMap<>();
        map.put("statisticsType", statisticsType);
        map.put("queryType", queryType);
        map.put("timeType", timeType);
        map.put("customizeFrom", customizeFrom);
        map.put("customizeTo", customizeTo);
        new BaseInternetRequestNew(mContext, new BaseInternetRequestNew.HttpUtilsListenerNew<ChartInfoBean>() {
            @Override
            public void onResponse(ChartInfoBean bean, int id) {
                if ("10".equals(bean.businessCode)) {
                    if (mListener != null) {
                        mListener.ymdDataResult(bean,timeType);
                    }
                } else {
                    CommonUtils.toastMessage(bean.businessMessage);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                return false;
            }
        }).postSign(mContext.getString(R.string.gatChartData), true, map, ChartInfoBean.class);
    }

    public interface OnChartDataChange {
        void chartData(ChartInfoBean bean);
        void ymdDataResult(ChartInfoBean bean,int timeType);
    }
}
