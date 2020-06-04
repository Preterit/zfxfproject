package com.zfxf.network;

import android.content.Context;

import com.zfxf.network.net.NetworkConfig;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public class NetworkSession {
    private NetworkConfig mNetworkConfig;
    private static boolean isAppDebug;
    private Context mContext;

    private NetworkSession() {
    }

    public static NetworkSession get() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final NetworkSession INSTANCE = new NetworkSession();
    }

    public void init(Context context, boolean isDebug, NetworkConfig networkConfig) {
        mContext = context.getApplicationContext();
        isAppDebug = isDebug;
        mNetworkConfig = networkConfig;
    }

    public NetworkConfig getNetworkConfig() {
        return mNetworkConfig;
    }

    public Context getContext() {
        return mContext;
    }

    public static boolean isDebug() {
        return isAppDebug;
    }

}
