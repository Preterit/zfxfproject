package com.zfxf.douniu_network.callback;

import com.zhy.http.okhttp.callback.Callback;

import okhttp3.Response;

/**
 * Created by fengtao
 * on 2018/7/20.
 */

public abstract class CallBack extends Callback<String> {
    @Override
    public String parseNetworkResponse(Response response, int id) throws Exception {
        if(response.code() >= 200 && response.code() < 300){
            return response.body().string();
        }else{
            throw new Exception("请求失败，错误码为:" + response.code() + "\n" + response.body().string());
        }
    }

    //需要查找500的具体原因时返回true
    @Override
    public boolean validateReponse(Response response, int id) {
        return response.isSuccessful();
//        return true;
    }
}
