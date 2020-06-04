package com.zfxf.zfxfproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String date2String(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
