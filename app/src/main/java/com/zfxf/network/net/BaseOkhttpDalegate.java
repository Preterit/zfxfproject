package com.zfxf.network.net;

import android.text.TextUtils;

import com.zfxf.network.NetworkSession;
import com.zfxf.network.util.LogUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public abstract class BaseOkhttpDalegate {

    //通过静态变量持有，其他类为BaseOkhttpDalegate子类的时候
    //共享这个一个OkhttpClient
    protected static OkHttpClient sOkHttpClient;
    protected OkHttpClient mOkHttpClient;
    protected Retrofit mRetrofit;


    public BaseOkhttpDalegate() {
        if (sOkHttpClient == null) {
            sOkHttpClient = getOkHttpClient();
        }
        //重新获取一个共享的OkhttpClient
        mOkHttpClient = sOkHttpClient;
    }

    protected abstract void initApi();

    /**
     * 配置回来的时候设置上一个数据
     *
     * @return
     */
    protected abstract String baseUrl();


    protected Retrofit createNormalRetrofit(String baseUrl, int type) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .callFactory(mOkHttpClient);

        appendRetrofitBuilder(builder, type);

        return builder.build();
    }

    /**
     * @param builder
     * @param type    用来创建多个 retrofit 的时候进行区分
     */
    protected void appendRetrofitBuilder(Retrofit.Builder builder, int type) {
        //todo 外部创建的时候扩展使用
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        handleBuilder(builder);

        return builder.build();

    }

    private void handleBuilder(OkHttpClient.Builder builder) {
        //超时时间
        builder.connectTimeout(NetworkSession.get().getNetworkConfig().getConnectTimeout(), TimeUnit.SECONDS)
                .readTimeout(NetworkSession.get().getNetworkConfig().getReadTimeout(), TimeUnit.SECONDS)
                .writeTimeout(NetworkSession.get().getNetworkConfig().getWriteTimeout(), TimeUnit.SECONDS);

        //Logging
        Interceptor loggingInterceptor = NetworkSession.get().getNetworkConfig().getLoggingInterceptor();
        if (loggingInterceptor == null) {
            loggingInterceptor = getDefaultLoggingInterceptor();
        }
        builder.addInterceptor(loggingInterceptor);

        //默认的拦截器
//        builder.addInterceptor(new DefaultInterceptor());
        //添加extraInterceptor
        List<Interceptor> interceptors = NetworkSession.get().getNetworkConfig().getExtraInterceptor();
        if (interceptors != null) {
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        appendOkhttpBuilder(builder);
    }

    /**
     * 这个因为建议全局只用一个OkhttpClient，所以建议就调用一次
     *
     * @param builder
     */
    protected void appendOkhttpBuilder(OkHttpClient.Builder builder) {
        //子类需要的话
    }

    /**
     * retrofit的结尾验证 "/"
     *
     * @param baseUrl
     * @return
     */
    protected String invalidBaseUrl(String baseUrl) {
        //容错处理一下，建议baseUrl为 '/' 结尾
        if (!TextUtils.isEmpty(baseUrl) && !baseUrl.endsWith("/")) {
            baseUrl = baseUrl + "/";
        }

        return baseUrl;
    }


    private Interceptor getDefaultLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e(message.toString());
            }
        });

        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }
}
