package com.zfxf.app;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.LogcatLogStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.zfxf.BuildConfig;
import com.zfxf.app_network.NetWorkSession;
import com.zfxf.global.CrashHandler;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class MyApplication extends BaseApplication {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initLogger();
        CrashHandler.getInstance().init(this);
        NetWorkSession.init(this, BuildConfig.DEBUG);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    //初始化logger
    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)      //（可选）是否显示线程信息。 默认值为true
                .methodCount(2)               // （可选）要显示的方法行数。 默认2
                .methodOffset(7)               // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                .logStrategy(new LogcatLogStrategy())  //（可选）更改要打印的日志策略。 默认LogCat
                .tag("zhou-TAG")                  //（可选）每个日志的全局标记。 默认PRETTY_LOGGER（如上图）
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
