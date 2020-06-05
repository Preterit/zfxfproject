package com.zfxf.app_network;

import java.io.File;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public interface Constants {
    String PART_ID = "part_id";
    String USER_ID = "user_id";
    String PATH_CACHE = NetWorkSession.getContext().getCacheDir().getAbsolutePath() + File.separator + "NetCache";

    //NetUtil处理得到
    String STR_SPLICE_SYMBOL = "&";
    String STR_EQUAL_OPERATION = "=";
    String APP_KEY = "70R84GQ2G90577ED60C4DC56A9EF3DB5";
    String DEVICE_NAME = "device_name";
    String DEVICE_NO = "device_no";
}
