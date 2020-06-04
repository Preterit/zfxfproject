package com.zfxf.network.net;

import androidx.annotation.Nullable;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public interface INetworkCallback {
    /**
     * 获取 一开始配置的时候的 Url
     * @return
     */
    String getBaseConfigUrl();

    /**
     * 获取BaseUrl
     *
     * @return
     */
    String getBaseUrl();

    /**
     * 获取deviceId
     * @return
     */
    String getDeviceId();

    /**
     * 获取外界的token
     *
     * @return
     */
    String getToken();

    /**
     * 刷新外面的token
     * 这个方法会在 TokenInterceptor 中调用到
     * 通过 {@link NetworkConfig#setNewToken} 进行设置
     *
     * @param newToken
     */
    void refreshToken(@Nullable String newToken);
}
