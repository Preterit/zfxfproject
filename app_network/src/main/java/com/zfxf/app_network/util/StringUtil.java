package com.zfxf.app_network.util;

import android.text.TextUtils;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class StringUtil {
    public static String stringNotNull(String value) {
        String temp = "";
        if (!TextUtils.isEmpty(value)) {
            temp = value;
        }
        return temp;
    }
}
