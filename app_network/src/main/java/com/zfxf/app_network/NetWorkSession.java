package com.zfxf.app_network;

import android.app.Application;
import android.content.Context;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class NetWorkSession {
    private static Application application;
    private static boolean isBuildConfig;

    public static void init(Application applicationContext, boolean isBuildConfig) {
        application =  applicationContext;
        NetWorkSession.isBuildConfig = isBuildConfig;
    }

    public static Context getContext() {
        return application;
    }

    public static boolean DEBUG() {
        return isBuildConfig;
    }
}
