package com.zfxf.network.net;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Date:2020/6/3
 * author:lwb
 * Desc:
 */
public class BaseResponse<T> implements Serializable {
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
