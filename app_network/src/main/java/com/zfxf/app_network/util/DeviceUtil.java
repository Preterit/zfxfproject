package com.zfxf.app_network.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.zfxf.app_network.Constants;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class DeviceUtil {
    @SuppressLint("MissingPermission")
    public static String getDeviceNo(Context context) {
        if (context == null) {
            return "";
        }

        SpUtil.putString(Constants.DEVICE_NAME, Build.MODEL);
        String device_no = SpUtil.getString(Constants.DEVICE_NO);
        if (!TextUtils.isEmpty(device_no)) {
            return device_no;
        }

        try {
            //获取当前设备的IMEI
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            device_no = telephonyManager.getDeviceId();
            SpUtil.putString(Constants.DEVICE_NO, device_no);
        } catch (Exception e) {
            device_no = "";
        }

        return device_no == null ? "" : device_no;
    }
}
