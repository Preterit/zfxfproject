package com.zfxf.zfxfproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DateUtil {

    /**
     * Date 转 yyyy-MM-dd 日期
     *
     * @param date
     * @return
     */
    public static String date2String(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取 当前月的 天数,
     *
     * @return
     */
    public static int getCurrentMonthDays() {
        return 31;
    }
}
