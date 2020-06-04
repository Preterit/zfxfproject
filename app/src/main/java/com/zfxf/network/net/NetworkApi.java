package com.zfxf.network.net;

import com.zfxf.network.bean.TokenBean;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public interface NetworkApi {
    @POST("{tokenPath}")
    Call<BaseResponse<TokenBean>> refreshToken(@Path("tokenPath") String path);
}
