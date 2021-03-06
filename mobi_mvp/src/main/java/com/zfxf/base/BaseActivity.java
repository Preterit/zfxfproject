package com.zfxf.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.zfxf.dialog.LoadingDialog;
import com.zfxf.util.StatusBarPaddingUtil;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lwb
 * @version 1.0
 * @date 2020/5/18 14:54
 * @Dec 略
 */
public abstract class BaseActivity extends RxAppCompatActivity {
    //butterKnife 绑定
    private Unbinder bind;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //去掉标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏、隐藏状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        super.onCreate(savedInstanceState);
        if (isFitWindow()) {
            fitWindow();
        }

        setContentView(getLayoutId());

        //设置状态栏的颜色
        setDarkStatusIcon(false);

        bind = ButterKnife.bind(this);

        if (getIntent() != null) {
            initVariables(getIntent());
        }

        initView();
        initView(savedInstanceState);
        initEvent();
        initData();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            initVariables(intent);
        }
    }

    /**
     * 布局文件id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 处理传过来的intent数据
     *
     * @param intent
     */
    protected void initVariables(@NonNull Intent intent) {

    }

    protected void initView() {

    }

    /**
     * 一般的用不上
     *
     * @param savedInstanceState
     */
    protected void initView(Bundle savedInstanceState) {

    }

    protected void initData() {

    }

    protected void initEvent() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bind != null) {
            bind.unbind();
        }
        bind = null;
    }

    /**
     * 默认不打开
     *
     * @return
     */
    protected boolean isFitWindow() {
        return false;
    }

    private void fitWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            // Translucent status bar
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void setDarkStatusIcon(boolean bDark) {
        StatusBarPaddingUtil.statusBar(this, bDark);
    }

    protected void showProgressDialog() {
        showProgressDialog(true, null);
    }

    protected void showProgressDialog(boolean isCancel) {
        showProgressDialog(isCancel, null);
    }

    protected void showProgressDialog(boolean isCancel, String message) {
        if (!isFinishing()) {
            if (mLoadingDialog == null) {
                mLoadingDialog = new LoadingDialog(this);
            }
            mLoadingDialog.setCancelable(isCancel);
            mLoadingDialog.show();
            mLoadingDialog.setMessage(message);
        }
    }

    protected void closeProgressDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }
}
