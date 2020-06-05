package com.zfxf.app_network.net;

import com.zfxf.app_network.Constants;
import com.zfxf.app_network.NetWorkSession;
import com.zfxf.app_network.api.ApiService;
import com.zfxf.app_network.net.interceptor.NetInterceptor;
import com.zfxf.app_network.net.interceptor.NoNetInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private static ApiService apiService;

    private static OkHttpClient okHttpLongClient;

    public static ApiService getNewsApi() {
        initOkHttp();
        if (apiService == null) {
            apiService = new Retrofit.Builder()
                    .baseUrl(ApiService.BASE_URL)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return apiService;
    }

    private static void initOkHttp(int second) {

        if (okHttpLongClient != null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (NetWorkSession.DEBUG()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

        builder.addInterceptor(new NoNetInterceptor())
                .addNetworkInterceptor(new NetInterceptor());
        File file = new File(Constants.PATH_CACHE);
        //最多缓存100m
        builder.cache(new Cache(file, 100 * 1024 * 1024));

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(second, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        builder.retryOnConnectionFailure(true);

        okHttpLongClient = builder.build();

    }

    private static void initOkHttp(int connectTime,int readTime,int writeTim) {
        if (okHttpLongClient != null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (NetWorkSession.DEBUG()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

        builder.addInterceptor(new NoNetInterceptor())
                .addNetworkInterceptor(new NetInterceptor());
        File file = new File(Constants.PATH_CACHE);
        //最多缓存100m
        builder.cache(new Cache(file, 100 * 1024 * 1024));

        builder.connectTimeout(connectTime, TimeUnit.SECONDS);
        builder.readTimeout(readTime, TimeUnit.SECONDS);
        builder.writeTimeout(writeTim, TimeUnit.SECONDS);

        builder.retryOnConnectionFailure(true);

        okHttpLongClient = builder.build();

    }


    private static void initOkHttp() {
        if (okHttpClient != null) {
            return;
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (NetWorkSession.DEBUG()) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }

//        Interceptor interceptor = new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                if (!SystemUtil.isNetworkConnect()) {
//                    //只进行缓存中读取
//                    request.newBuilder()
//                            .cacheControl(CacheControl.FORCE_CACHE)
//                            .build();
//                }
//
//                int tryCount = 0;
//                Response response = chain.proceed(request);
//                while (!response.isSuccessful() && tryCount < 3) {
//                    ++tryCount;
//                    response = chain.proceed(request);
//                }
//
//                if (SystemUtil.isNetworkConnect()) {
//                    int maxAge = 0;
//                    response.newBuilder()
//                            .header("Cache-Control", "public, max-age=" + maxAge)
//                            .removeHeader("Pragma").build();
//                } else {
//                    int maxStale = 60 * 60 * 24 * 7;
//                    response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
//                            .removeHeader("Pragma").build();
//                }
//
//                return response;
//            }
//        };

        builder.addInterceptor(new NoNetInterceptor())
                .addNetworkInterceptor(new NetInterceptor());
        File file = new File(Constants.PATH_CACHE);
        //最多缓存100m
        builder.cache(new Cache(file, 100 * 1024 * 1024));

        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(10, TimeUnit.SECONDS);
        builder.writeTimeout(10, TimeUnit.SECONDS);

        builder.retryOnConnectionFailure(true);

        okHttpClient = builder.build();

    }
}
