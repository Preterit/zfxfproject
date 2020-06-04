package com.zfxf.network.net;

import android.text.TextUtils;

import com.zfxf.network.NetworkSession;
import com.zfxf.network.util.LogUtils;

import java.lang.reflect.ParameterizedType;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public class BaseRetrofitClient<T> extends BaseOkhttpDalegate {
    public static final String TAG = "RetrofitClient";

    private volatile T mConfigApi;
    private volatile T mHttpApi;

    private Class<T> apiClass;

    private String mBaseUrl;
    private String mBaseConfigUrl;

    @Override
    protected void initConfigApi() {
        if (mConfigRetrofit == null) {
            synchronized (BaseRetrofitClient.class) {
                if (mConfigRetrofit == null) {
                    mConfigRetrofit = createNormalRetrofit(baseConfigUrl(), 0);
                }
            }
        }
    }

    @Override
    protected void initApi() {
        if (mRetrofit == null) {
            synchronized (BaseRetrofitClient.class) {
                if (mRetrofit == null) {
                    mRetrofit = createNormalRetrofit(baseUrl(), 1);
                }
            }
        }
    }

    @Override
    protected String baseConfigUrl() {
        if (TextUtils.isEmpty(mBaseConfigUrl)) {
            mBaseConfigUrl = NetworkSession.get().getNetworkConfig().getBaseConfigUrl();
//            mBaseConfigUrl = invalidBaseUrl(mBaseConfigUrl);
        }
        return mBaseConfigUrl;
    }

    @Override
    protected String baseUrl() {
        //本地存储一个，减少三目运算
        if (TextUtils.isEmpty(mBaseUrl)) {
            mBaseUrl = NetworkSession.get().getNetworkConfig().getBaseUrl();
            mBaseUrl = invalidBaseUrl(mBaseUrl);
        }
        LogUtils.e("mBaseUrl : " + mBaseUrl);
        return mBaseUrl;
    }

    public T getConfigApi() {
        if (mConfigApi == null) {
            initConfigApi();
            mConfigApi = mConfigRetrofit.create(getApiClass());
        }
        return mConfigApi;
    }

    /**
     * 这个只能进行延迟初始化，因为这个时候BaseUrl还没有
     *
     * @return
     */
    public T getHttpApi() {
        if (mHttpApi == null) {
            initApi();
            mHttpApi = mRetrofit.create(getApiClass());
        }

        return mHttpApi;
    }

    private Class<T> getApiClass() {
        if (apiClass == null) {
            apiClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return apiClass;
    }

}
