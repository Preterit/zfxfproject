package com.zfxf.app_network.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.util.Base64;

import com.orhanobut.logger.Logger;
import com.zfxf.app_network.Constants;
import com.zfxf.app_network.NetWorkSession;

import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class NetUtil {
    public static boolean isNetworkConnect() {
        ConnectivityManager connectivityManager = (ConnectivityManager) NetWorkSession.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            return connectivityManager.getActiveNetworkInfo() != null;
        }
        return false;
    }

    private static String getBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.NO_WRAP), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    public static String getBase64Data(Map<String, String> map) {
        if (map == null || map.size() == 0)
            return "";
        map.put("v", getVersionCode() + "");
        map.put("ts", System.currentTimeMillis() + "");//把时间转成时间戳？
        StringBuilder sb = new StringBuilder();
        Object[] keyArr = map.keySet().toArray();
        Arrays.sort(keyArr);
        for (int i = 0; i < keyArr.length; i++) {
            if (i != 0) {
                sb.append(Constants.STR_SPLICE_SYMBOL);
            }
            Object key = keyArr[i];
            sb.append(key).append(Constants.STR_EQUAL_OPERATION).append(map.get(key));
        }
        map.put("sn", NetUtil.md5(sb.toString() + Constants.APP_KEY).toUpperCase());
        for (String key : map.keySet()) {
            Logger.e("key  " + key + "  value  " + map.get(key));
        }
        JSONObject json = new JSONObject(map);
        String jsonStr = json.toString();
        return NetUtil.getBase64(jsonStr);
    }

    public static String getNormalData(Map<String, String> map) {
        if (map == null || map.size() == 0)
            return "";
        JSONObject json = new JSONObject(map);
        String jsonStr = json.toString();
        return jsonStr;
    }

    //MD5加密算法
    private static String md5(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String getHttpExceptionMsg(Throwable exception, String errorMsg) {
        String defaultMsg = "未知错误";
        if (exception != null) {
            Logger.e("Request Exception:" + exception.getMessage());
            if (exception instanceof UnknownHostException) {
                defaultMsg = "您的网络可能有问题,请确认连接上有效网络后重试";
            } else if (exception instanceof ConnectTimeoutException) {
                defaultMsg = "连接超时,您的网络可能有问题,请确认连接上有效网络后重试";
            } else if (exception instanceof SocketTimeoutException) {
                defaultMsg = "请求超时,您的网络可能有问题,请确认连接上有效网络后重试";
            } else {
                defaultMsg = "未知的网络错误, 请重试";
            }
        } else {
            if (!TextUtils.isEmpty(errorMsg)) {
                Logger.e("Request Exception errorMsg: " + errorMsg);
                String lowerMsg = errorMsg.toLowerCase(Locale.ENGLISH);
                if (lowerMsg.contains("java")
                        || lowerMsg.contains("exception")
                        || lowerMsg.contains(".net")
                        || lowerMsg.contains("java")) {
                    defaultMsg = "未知错误, 请重试";
                } else {
                    defaultMsg = "未知错误, 请重试";
                }
            }
        }
        return defaultMsg;
    }

    private static int getVersionCode() {
        try {
            PackageManager manager = NetWorkSession.getContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(NetWorkSession.getContext().getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            return 10000;
        }
    }
}
