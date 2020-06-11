package com.zfxf.douniu_network.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.zfxf.app.BaseApplication;
import com.zfxf.douniu_network.BaseInternetRequestNew;
import com.zfxf.douniu_network.entry.BaseInfoOfResult;
import com.zfxf.douniu_network.entry.RefreshTokenBean;
import com.zfxf.douniu_network.loadingdialog.LoadingDialog;
import com.zfxf.zfxfproject.R;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;


/**
 * @author Admin
 * @time 2017/3/28 14:01
 * @des ${TODO}
 */

public class CommonUtils {


    private static MediaPlayer mediaPlayer;

    public static boolean isLoging = false;

    public static void logMes(String msg) {
        if (isLoging) {
//           LogUtils.e("-----test----",msg);
            int segmentSize = 3 * 1024;
            long length = msg.length();
            if (length <= segmentSize) {// 长度小于等于限制直接打印
                LogUtils.e("-----test----", msg);
            } else {
                while (msg.length() > segmentSize) {// 循环分段打印日志
                    String logContent = msg.substring(0, segmentSize);
                    msg = msg.replace(logContent, "");
                    LogUtils.e("-----test----", logContent);
                }
                LogUtils.e("-----test----", msg);// 打印剩余日志
            }
        }
    }

    /**
     * 判断是否有网
     */

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        //当前有可用网络
        //当前无可用网络
        return networkInfo != null && networkInfo.isAvailable();
    }

    /**
     * 获取屏幕的宽
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 获取屏幕的高
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    /**
     * 获取通知栏高度
     */
    public static int getStatusBarHeight(Context context) {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 获取手机实际宽度，除去虚拟键盘的宽度
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static int getWidth(Activity context) {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的宽度
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableWidth = metrics.widthPixels;
        //获取当前屏幕的真实宽度
        context.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realWidth = metrics.widthPixels;
        if (realWidth > usableWidth) {
            return usableWidth;
        } else {
            return realWidth;
        }
    }


    /**
     * 判断是否wife
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 判断是否4G
     */
    public static boolean isMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
        return networkINfo != null && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 得到上下文
     */
    public static Context getContext() {
        return BaseApplication.getContext();
    }

    /**
     * 得到Resouce对象
     */
    public static Resources getResource() {
        return getContext().getResources();
    }

    /**
     * 得到应用程序的包名
     */
    public static String getPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 得到主线程id
     */
    public static long getMainThreadid() {
        return android.os.Process.myTid();
    }


    /**
     * 获取当前线程Id
     *
     * @return
     */
    public static int getCurrentThreadId() {
        return android.os.Process.myTid();
    }


    /**
     * 得到主线程Handler
     */
    public static Handler getMainThreadHandler() {
        return  new Handler();
    }

    /**
     * 安全的执行一个任务
     */
    public static void postTaskSafely(Runnable task) {
        int curThreadId = android.os.Process.myTid();
        if (curThreadId == getMainThreadid()) {// 如果当前线程是主线程
            task.run();
        } else {// 如果当前线程不是主线程
            getMainThreadHandler().post(task);
        }
    }

    /**
     * 延迟执行任务
     */
    public static void postTaskDelay(Runnable task, int delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    /**
     * 移除任务
     */
    public static void removeTask(Runnable task) {
        getMainThreadHandler().removeCallbacks(task);
    }



    private static Toast toast;

    public static void toastMessage(String msg) {
//        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        if (TextUtils.isEmpty(msg) || msg == null || msg.equals("null") || msg.contains("null"))
            return;
        int duration;
        if (msg.length() > 15) {
            duration = Toast.LENGTH_LONG;
        } else {
            duration = Toast.LENGTH_SHORT;
        }
        if (toast == null) {
            toast = Toast.makeText(getContext(), msg, duration);
        } else {
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setText(msg);
        }

        toast.show();
    }

    /**
     * 判断手机号的正则表达式
     */
    public static boolean isMobilePhone(String username) {
        String telRegex = "[1][345789]\\d{9}";
//        return username.matches(telRegex);
        if (!TextUtils.isEmpty(username) && username.length() == 11) {
            return true;
        } else {
            return false;
        }
    }


    public static int getColorAccent(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        return typedValue.data;
    }

    public static int getSecondaryColor(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.theme_secondary_color, typedValue, true);
        return typedValue.data;
    }

    /**
     * 保存图片到本地缓存
     */
    public static void saveBitmapFile(Bitmap bitmap, String str) {
        File file = new File(getContext().getExternalCacheDir(), str);//将要保存图片的路径
        try {
            if (file.exists()) {
                file.delete();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static File getBitmapFileOfSaved(String str) {
        File file = new File(getContext().getExternalCacheDir(), str);
        if (!file.exists()) {
            return null;
        }
        return file;

    }


    public static Bitmap getCacheFile(String str) {
        File file = new File(getContext().getFilesDir(), str);
        if (file != null && file.exists()) {
            //文件存在
            //把文件转换成bitmap
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            //再往内存写
            return bitmap;
        } else {
            //不存在
            return null;
        }
    }

    public static void deleteBitmap(String str) {
        File file = new File(getContext().getFilesDir(), str);
        if (file.exists()) {
            file.delete();
        }
    }

    public static Bitmap getBitmap(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时的bitmap为null
        //更改
        options.inJustDecodeBounds = false;
        //计算缩放比
        int be = (int) (options.outHeight / (float) 200);
        if (be < 0) {
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(path, options);//此时的bitmap不为null
        return bitmap;
    }

    public static byte[] getBitMapByteArray(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();

        return bytes;
    }

    private static Dialog progressDialog;

    private static boolean isLiving(Activity activity) {
        if (activity == null) {
            return false;
        }
        return !activity.isFinishing();
    }

    //关闭进度对话框
    public static void dismissProgressDialog() {

        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static synchronized void showProgressDialog(Context comtext, String mes) {
        if (progressDialog == null) {
            progressDialog = LoadingDialog.createLoadingDialog(comtext, mes);
        }
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.setCancelable(true);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
        }
    }


    /**
     * 获取应用版本号
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    /**
     * 获取应用版本名
     */
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    @SuppressLint({"NewApi", "MissingPermission"})
    public static String getMac() {

        Context paramContext = getContext();

        try {
            if (Build.VERSION.SDK_INT >= 23) {
                String str = getMacMoreThanM();
                if (!TextUtils.isEmpty(str))
                    return str;
            }
            // 6.0以下手机直接获取wifi的mac地址即可
            @SuppressLint("WrongConstant") WifiManager wifiManager = (WifiManager) paramContext.getSystemService("wifi");
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                return wifiInfo.getMacAddress();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    private static String getMacMoreThanM() {
        Context paramContext = getContext();
        try {
            //获取本机器所有的网络接口
            Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
            while (enumeration.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) enumeration.nextElement();
                //获取硬件地址，一般是MAC
                byte[] arrayOfByte = networkInterface.getHardwareAddress();
                if (arrayOfByte == null || arrayOfByte.length == 0) {
                    continue;
                }

                StringBuilder stringBuilder = new StringBuilder();
                for (byte b : arrayOfByte) {
                    //格式化为：两位十六进制加冒号的格式，若是不足两位，补0
                    stringBuilder.append(String.format("%02X:", new Object[]{Byte.valueOf(b)}));
                }
                if (stringBuilder.length() > 0) {
                    //删除后面多余的冒号
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                String str = stringBuilder.toString();
                // wlan0:无线网卡 eth0：以太网卡
                if (networkInterface.getName().equals("wlan0")) {
                    return str;
                }
            }
        } catch (SocketException socketException) {
            return null;
        }
        return null;
    }

    /* 获取手机序列号*/
//    @SuppressLint({"NewApi", "MissingPermission"})
//    public static String getMac() {
//        String serial = "";
//        try {
//
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
//
//            } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {//8.0+
//                serial = Build.SERIAL;
//            } else {//8.0-
//                Class<?> c = Class.forName("android.os.SystemProperties");
//                Method get = c.getMethod("get", String.class);
//                serial = (String) get.invoke(c, "ro.serialno");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logMes("读取设备序列号异常：" + e.toString());
//        }
//        return serial;
//    }


    /**
     * 获取mac地址
     */
    public static String getMac_old() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wl/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);

            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (macSerial == null || "".equals(macSerial)) {
            try {
                return loadFileAsString("/sys/class/net/wlan0/address")
                        .toUpperCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();

            }

        }
        return macSerial;
    }

    public static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        return text;
    }

    public static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 获取当前手机系统语言。
     *
     * @return 返回当前系统语言。例如：当前设置的是“中文-中国”，则返回“zh-CN”
     */
    public static String getSystemLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取当前系统上的语言列表(Locale列表)
     *
     * @return 语言列表
     */
    public static Locale[] getSystemLanguageList() {
        return Locale.getAvailableLocales();
    }

    /**
     * 获取当前手机系统版本号
     *
     * @return 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取手机型号
     *
     * @return 手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机运营商
     */
    public static String getOperators(Context context) {
        // 移动设备网络代码（英语：Mobile Network Code，MNC）是与移动设备国家代码（Mobile Country Code，MCC）（也称为“MCC /
        // MNC”）相结合, 例如46000，前三位是MCC，后两位是MNC 获取手机服务商信息
        String OperatorsName = "";
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String IMSI = telManager.getSimOperator();
        // IMSI号前面3位460是国家，紧接着后面2位00 运营商代码
        System.out.println(IMSI);
        if (IMSI.startsWith("46000") || IMSI.startsWith("46002") || IMSI.startsWith("46007")) {
            OperatorsName = "中国移动";
        } else if (IMSI.startsWith("46001") || IMSI.startsWith("46006")) {
            OperatorsName = "中国联通";
        } else if (IMSI.startsWith("46003") || IMSI.startsWith("46005")) {
            OperatorsName = "中国电信";
        }
        return OperatorsName;
    }

    /**
     * 获取IP地址
     */
    public static String getIPAddress(Context context) {
        @SuppressLint("MissingPermission") NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /**
     * MD5 加密
     */
    public static String md5(String str) {
        StringBuilder mess = new StringBuilder();
        try {
            //获取MD5加密器
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = str.getBytes();
            byte[] digest = md.digest(bytes);

            for (byte b : digest) {
                //把每个字节转成16进制数
                int d = b & 0xff;// 0x000000ff
                String hexString = Integer.toHexString(d);
                if (hexString.length() == 1) {//字节的高4位为0
                    hexString = "0" + hexString;
                }
                mess.append(hexString);//把每个字节对应的2位十六进制数当成字符串拼接一起

            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return mess + "";
    }

    public static void webSetting(final WebView webView) {
        WebSettings settings = webView.getSettings();
        //启用支持javascript
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setPluginState(WebSettings.PluginState.ON);
        settings.setAllowFileAccess(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        settings.setBlockNetworkImage(false);
        settings.setDomStorageEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //缩放操作
        settings.setDisplayZoomControls(true); //隐藏原生的缩放控件
//        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //解决webview无限向下滑动 webview大量空白区域 内容无法适屏
        webView.loadUrl("javascript:App.resize(document.getElementsByTagName('div')[0].scrollHeight)");
        webView.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }


        });
    }

    public static int splitBirthday(String date, int i) {
        String[] strs = date.split("-");
        if (i >= strs.length) {
            return 1;
        }
        return Integer.valueOf(strs[i]);
    }

    public static String splitAddress(String address, int i) {
        String[] strs = address.split("-");
        if (i >= strs.length) {
            return "";
        }
        return strs[i];
    }

    public static void splitRegistInfo(String info, TextView tv, String textColor) {
        int color = Color.parseColor(textColor);
        StringBuilder result = new StringBuilder();
        String result1 = "";
        String result2 = "";
        ArrayList<Integer> list = new ArrayList<>();
        while (info.contains("<<<") || info.contains(">>>")) {
            int startIndex2 = info.indexOf("<<<");
            int endIndex2 = info.indexOf(">>>");
            list.add(result.length() + startIndex2);
            list.add(result.length() + endIndex2 - 3);
            result2 = info.substring(startIndex2 + 3, endIndex2);
            if (info.contains("<<<")) {
                int startIndex1 = 0;
                int endIndex1 = info.indexOf("<<<");
                result1 = info.substring(startIndex1, endIndex1);
            }
            result.append(result1).append(result2);
            info = info.substring(endIndex2 + 3, info.length());
        }
        SpannableString spannableString;
        if (!"".equals(info)) {
            spannableString = new SpannableString(result.toString() + info);
        } else {
            spannableString = new SpannableString(result.toString());
        }
        int j = -1;
        for (int i = 0; i < list.size() / 2; i++) {
            ForegroundColorSpan bgColorSpan = new ForegroundColorSpan(color);
            spannableString.setSpan(bgColorSpan, list.get(++j), list.get(++j), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spannableString);
    }

    /**
     * 获取设备唯一标识
     */
    public static String getUnique() {
        String mUnique = "35" + //仿制IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10;
        return mUnique;
    }

    /**
     * 获取用户登录状态
     *
     * @param context 上下文
     */
    public static boolean getUserLoginStatus(Context context) {
        return SpTools.getBoolean(context, Constants.isLogin, false);
    }

    /**
     * 加载圆角图片
     * defaultIv 站位图
     */
    public static void loadFilletImage(Context context, String url, int defaultIv, ImageView iv) {
        Glide.with(context).load(url)
                .apply(RequestOptions.circleCropTransform().placeholder(context.getResources().getDrawable(defaultIv))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)).into(iv);
    }

    /**
     * 加载16:9的图片
     */
    public static void load16Image(final Context context, String url, final ImageView iv, final int width) {
        int height = width / 16 * 9;
        ViewGroup.LayoutParams para = iv.getLayoutParams();
        para.width = width;
        para.height = height;
        Glide.with(context).asBitmap().load(url)
                .apply(new RequestOptions().placeholder(context.getResources().
                        getDrawable(R.drawable.shop_defout_horizontal_msall)).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(iv);
//                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                //宽度固定,然后根据原始宽高比得到此固定宽度需要的高度
//                int height = width / 16 * 9;
//                ViewGroup.LayoutParams para = iv.getLayoutParams();
//                para.width = width;
//                para.height = height;
//                iv.setImageBitmap(resource);
//            }
//        });
    }

    public static void load1bi1(final Context context, String url, final ImageView iv) {
        ViewGroup.LayoutParams para = iv.getLayoutParams();
        para.height = para.width;
        iv.setLayoutParams(para);
        Glide.with(context).asBitmap().load(url)
                .apply(new RequestOptions().placeholder(context.getResources().
                        getDrawable(R.drawable.shop_sunscribe_default)).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(iv);
    }

    /**
     * 加载9:16的图片
     */
    public static void load9Image(final Context context, String url, final ImageView iv, final int height) {
        int width = height / 9 * 16;
        ViewGroup.LayoutParams para = iv.getLayoutParams();
        para.width = width;
        para.height = height;
        Glide.with(context).asBitmap().load(url)
                .apply(new RequestOptions().placeholder(context.getResources().
                        getDrawable(R.drawable.shop_defout_horizontal_msall)).diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(iv);
    }

    /**
     * 改变数字的颜色
     */
    public static SpannableString changeNumberColor(Context context, String number, String str) {
        ForegroundColorSpan bgColorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorTitle2));
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(bgColorSpan, 0, number.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 推送商城类型
     * 切割推送过来的id
     */
    public static ArrayList<String> splitDouhao(String str) {
        ArrayList<String> list = new ArrayList<>();
        while (str.contains(",")) {
            int start = str.indexOf(",");
            String substring = str.substring(0, start);
            list.add(substring);
            if (start + 1 >= str.length()) {
                str = "";
            } else {
                str = str.substring(start + 1, str.length());
            }
            if (!str.contains(",")) {
                list.add(str);
            }
        }
        return list;
    }

    //透明标题栏
    public static void transparentBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //5.x开始需要把颜色设置透明，否则导航栏会呈现系统默认的浅灰色
                Window window = activity.getWindow();
                View decorView = window.getDecorView();
                //两个 flag 要结合使用，表示让应用的主体内容占用系统状态栏的空间
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
                //导航栏颜色也可以正常设置
//                window.setNavigationBarColor(Color.TRANSPARENT);
            } else {
                Window window = activity.getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
                attributes.flags |= flagTranslucentStatus;
//                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /**
     * 判断进程是否存活
     */
    public static boolean isProessRunning(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo info : lists) {
            if (info.processName.equals("com.zfxf.douniu")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从app内跳转到设置页面打开通知提醒
     *
     * @param context
     * @return
     */
    public static void goToOpenNotification(Context context) {
        try {
            Intent localIntent = new Intent();
            localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //直接跳转到应用通知设置的代码：
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                localIntent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
                localIntent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
                context.startActivity(localIntent);
                return;
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
                localIntent.putExtra("app_package", context.getPackageName());
                localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
                context.startActivity(localIntent);
                return;
            }
            if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                localIntent.addCategory(Intent.CATEGORY_DEFAULT);
                localIntent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivity(localIntent);
                return;
            }

            //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面,

            if (Build.VERSION.SDK_INT >= 9) {
                localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
                context.startActivity(localIntent);
                return;
            }

            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" cxx   pushPermission 有问题");
        }

    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 生成签名
     * 签名认证：◆ 参数名ASCII码从小到大排序（字典序）（sign参数不参与签名）；
     * ◆ 如果参数的值为空不参与签名；◆ 参数名区分大小写；
     * 将参数使用&进行拼接 最后拼接上签名key。使用MD5进行加密 赋值给参数sign
     * ◆ stringA="app=douniucaijing&body=thisisbody&timestamp=1555558720&key=mykey";
     * sign = MD5.encode(stringA)
     *
     * @param map
     * @return
     */

//    public static String secret_key = "1ABC2312A0B37E611035E6";
//    public static String secret_key = "abcdefghigklmn";
    public static String getSignToken(Map<String, String> map) {
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }

            //sign字符串最后拼接key
            sb.append("key=" + Constant.secret_key);
            result = sb.toString();
            //进行MD5加密
        } catch (Exception e) {
            return null;
        }
        return result;
    }

    /*将drawable下的图片转换成bitmap*/
    public static Bitmap getBitmapFromDrawable(Context context, int drawable) {

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawable);
        return bitmap;
    }

    /*将图片转换成二进制流*/
    public static byte[] getBitmapByte(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //参数1转换类型，参数2压缩质量，参数3字节流资源
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /*将二进制流转换成图片（Bitmap）*/
    public static Bitmap getBitmapFromByte(byte[] temp) {
        if (temp != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
            return bitmap;
        } else {
            return null;
        }
    }

    /*将Bitmap转换成Drawable*/
    public static Bitmap drawableToBitmap(Drawable drawable) {

        int width = drawable.getIntrinsicWidth();

        int height = drawable.getIntrinsicHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height,

                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888

                        : Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, width, height);

        drawable.draw(canvas);

        return bitmap;

    }

    /**
     * 将base64字符解码保存文件
     *
     * @param base64Code
     * @param targetPath
     */


    public static void decoderBase64File(Context context, final String base64Code, String targetPath, boolean soundClose) {
        try {
            byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);

            File file = new File(targetPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            File tempWav = new File(file, "temp.wav");
            if (!tempWav.exists())
                tempWav.createNewFile();
            FileOutputStream out = new FileOutputStream(tempWav);
            out.write(buffer);
            out.flush();
            out.close();
            FileInputStream fis = new FileInputStream(tempWav);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fis.getFD());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断文件是否存在
     */
    public static boolean fileIsExists(File file) {
        try {
            if (!file.exists()) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 公共的退出登录
     *
     * @param needLogin true为需要退出
     */
    public static void exitLogin(final Context context, final boolean needLogin) {

        if (!SpTools.getBoolean(context, Constants.isLogin, false)) {
            return;
        }

        new BaseInternetRequestNew(context, new BaseInternetRequestNew.HttpUtilsListenerNew<BaseInfoOfResult>() {
            @Override
            public void onResponse(BaseInfoOfResult bean, int id) {
            }

            @Override
            public void onError(Call call, Exception e, int id) {
//                Log.e(TAG, "CommonUtils onError:" + e.getMessage());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                return false;
            }
        }).getSign(context.getResources().getString(R.string.loginOutNew), true, null, BaseInfoOfResult.class);


        SpTools.setBoolean(BaseApplication.getContext(), Constants.isLogin, false);
        SpTools.setBoolean(BaseApplication.getContext(), Constants.alreadyLogout, false);
        SpTools.setString(BaseApplication.getContext(), Constants.userId, null);
        SpTools.setInt(BaseApplication.getContext(), Constants.rvaluateResult, 0);
        SpTools.setString(BaseApplication.getContext(), Constants.token, null);
        SpTools.setString(BaseApplication.getContext(), Constants.refreshToken, null);
        SpTools.setString(BaseApplication.getContext(), "userSign", null);
        CommonUtils.deleteBitmap("myicon.jpg");

      //TODO  没有退出  暂时隐藏
        if (needLogin) {
//            Intent intent = new Intent(context, ActivityLogin.class);
//            context.startActivity(intent);
        }

        new BaseInternetRequestNew(context, new BaseInternetRequestNew.HttpUtilsListenerNew<RefreshTokenBean>() {
            @Override
            public void onResponse(RefreshTokenBean bean, int id) {
                if (bean != null) {
                    SpTools.setString(BaseApplication.getContext(), Constants.token, bean.token);
                    SpTools.setString(BaseApplication.getContext(), Constants.refreshToken, bean.refreshToken);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
//                LogUtils.e(TAG, "MainActivity onError:" + e.getMessage());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                return false;
            }
        }).postSign(context.getResources().getString(R.string.getTouristToken), false, null, RefreshTokenBean.class);
    }
}
