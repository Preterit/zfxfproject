package com.zfxf.network.net;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public class DefaultRetrofitClient extends BaseRetrofitClient<NetworkApi> {
    private DefaultRetrofitClient() {
    }

    public static DefaultRetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DefaultRetrofitClient INSTANCE = new DefaultRetrofitClient();
    }

}
