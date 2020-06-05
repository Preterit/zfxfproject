package com.zfxf.app_network.net;

import android.content.Context;
import android.text.TextUtils;

import com.zfxf.app_network.Constants;
import com.zfxf.app_network.NetWorkSession;
import com.zfxf.app_network.util.DeviceUtil;
import com.zfxf.app_network.util.NetUtil;
import com.zfxf.app_network.util.SpUtil;
import com.zfxf.app_network.util.StringUtil;

import java.util.HashMap;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public class Params {
    protected Context context;
    protected HashMap<String, String> map;

    public Params() {
        map = new HashMap<>();
        context = NetWorkSession.getContext();
        defaultValue();
    }

    private void defaultValue() {
        put("ui", SpUtil.getInt(Constants.USER_ID, 0));
        put("pi", SpUtil.getInt(Constants.PART_ID, 0));
        put("plid", "1");
    }

    public String getUserId() {
        String ui = map.get("ui");
        if (TextUtils.isEmpty(ui)) {
            ui = String.valueOf(SpUtil.getInt(Constants.USER_ID, 0));
        }
        return ui;
    }

    public String getPartId() {
        String pi = map.get("ci");
        if (TextUtils.isEmpty(pi)) {
            pi = String.valueOf(SpUtil.getInt(Constants.PART_ID, 0));
        }
        return pi;
    }

    public void removeKey(String key) {
        if (map.containsKey(key)) {
            map.remove(key);
        }
    }

    public void put(String key, String value) {
        map.put(key, StringUtil.stringNotNull(value));
    }

    public void put(String key, long value) {
        map.put(key, value + "");
    }

    public void put(String key, int value) {
        map.put(key, value + "");
    }

    public String getData() {
        return NetUtil.getBase64Data(map);
    }

    public String getNormalData() {
        return NetUtil.getNormalData(map);
    }

    public void putDeviceNo() {
        map.put("dn", DeviceUtil.getDeviceNo(context));
    }
}
