package com.mobi.mvp;

import android.app.Activity;

import androidx.annotation.Nullable;

/**
 * @author lwb
 * @version 1.0
 * @date 2020/5/18 13:24
 * @Dec 略
 */
public interface IView {
    /**
     * 用于rxjava生命周期监听, dialog显示
     *
     * @return
     */
    @Nullable
    Activity getMvpActivity();
}
