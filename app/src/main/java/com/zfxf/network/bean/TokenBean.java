package com.zfxf.network.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public class TokenBean {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("token")
    private String token;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
