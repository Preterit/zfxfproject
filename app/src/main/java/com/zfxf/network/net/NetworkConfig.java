package com.zfxf.network.net;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Dispatcher;
import okhttp3.Interceptor;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public class NetworkConfig {

    private List<Interceptor> extraInterceptor;

    /**
     * 按照 秒 来实现的
     */
    private int connectTimeout = 10_000;
    private int readTimeout = 10_000;
    private int writeTimeout = 10_000;


    /**
     * loggingInterceptor
     */
    private Interceptor loggingInterceptor;

    /**
     * baseConfigUrl
     * baseUrl
     */
    private INetworkCallback networkCallback;
    private String baseUrl;

    private String mac;
    private String androidId;
    private String imei;
    private String deviceId;
    private String token;

    private NetworkConfig(NetworkConfig.Builder builder) {
        if (builder.extraInterceptor == null) {
            builder.extraInterceptor = new ArrayList<>(0);
        } else {
            this.extraInterceptor = builder.extraInterceptor;
        }

        if (builder.connectTimeout != 0) {
            this.connectTimeout = builder.connectTimeout;
        }

        if (builder.readTimeout != 0) {
            this.readTimeout = builder.readTimeout;
        }

        if (builder.writeTimeout != 0) {
            this.writeTimeout = builder.writeTimeout;
        }

        if (builder.loggingInterceptor != null) {
            this.loggingInterceptor = builder.loggingInterceptor;
        }

        this.networkCallback = builder.networkUrl;
    }

    public List<Interceptor> getExtraInterceptor() {
        return extraInterceptor;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public int getWriteTimeout() {
        return writeTimeout;
    }

    public String getBaseUrl() {
        if (networkCallback != null && TextUtils.isEmpty(baseUrl)) {
            baseUrl = networkCallback.getBaseUrl();
        }
        return baseUrl;
    }

    public Interceptor getLoggingInterceptor() {
        return loggingInterceptor;
    }

    public static class Builder {
        List<Interceptor> extraInterceptor;
        int connectTimeout;
        int readTimeout;
        int writeTimeout;
        Dispatcher dispatcher;

        INetworkCallback networkUrl;

        Interceptor loggingInterceptor;

        public Builder setExtraInterceptor(@Nullable List<Interceptor> extraInterceptor) {
            this.extraInterceptor = extraInterceptor;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        public Builder setWriteTimeout(int writeTimeout) {
            this.writeTimeout = writeTimeout;
            return this;
        }

        public Builder setNetworkCallback(@NonNull INetworkCallback networkCallback) {
            this.networkUrl = networkCallback;
            return this;
        }

        public Builder setDispatcher(@Nullable Dispatcher dispatcher) {
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder setLoggingInterceptor(@Nullable Interceptor loggingInterceptor) {
            this.loggingInterceptor = loggingInterceptor;
            return this;
        }

        public NetworkConfig build() {
            return new NetworkConfig(this);
        }
    }
}
