package com.zfxf.app_network.callback;

/**
 * @author :  lwb
 * Date: 2020/6/5
 * Desc:
 */
public interface IRequestCallback<T> {
    void onSuccess(T t);

    void onFailure(int code, String error);
}
