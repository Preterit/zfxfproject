package com.zfxf.app_network.api;

import android.database.Observable;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public interface ApiService {
    String BASE_URL = "www.baidu.com";

    @FormUrlEncoded
    @POST("article/allarticle")
    Observable<RequestBean<List<String>>> getAllArticle(@Field("data") String data);

    @FormUrlEncoded
    @POST("article/article_info")
    Observable<RequestBean> getAllArticleInfo(@Field("data") String data);
}
