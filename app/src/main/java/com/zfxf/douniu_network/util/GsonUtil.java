package com.zfxf.douniu_network.util;

import com.google.gson.Gson;
import com.zfxf.douniu_network.entry.BaseResultNew;

/**
 * Created by Song on 2019/4/19 10:42
 * Happy Code
 */
public class GsonUtil {
    /**
     * 基础结果Bean
     * @param jsonStr   返回的json字符串
     * @param tClass    对应返回的基础数据中的data的类型
     * @param <T>
     * @return
     */
    public static <T> BaseResultNew<T> fromJsonByType(String jsonStr, Class<T> tClass) {
        ParameterizedTypeImpl parameterizedType = new ParameterizedTypeImpl(BaseResultNew.class,tClass);
        BaseResultNew<T> baseResultNew = new Gson().fromJson(jsonStr, parameterizedType);
        return baseResultNew;
    }
}
