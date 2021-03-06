package com.zfxf.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * @author lwb
 * @version 1.0
 * @date 2020/4/28 17:01
 * @Dec 出现了2个bug，容易奔溃
 */
public abstract class BaseDialog extends Dialog {
    public BaseDialog(@NonNull Context context) {
        super(context);
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected BaseDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    public void show() {
        if (!isInvalidContext()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (isShowing() && !isInvalidContext()) {
            super.dismiss();
        }
    }

    protected final boolean isInvalidContext() {
        //默认getContext()获取到的是ContextThemeWrapper对象，基本不会走这个方法
        if (getContext() instanceof Activity) {
            Activity context = (Activity) getContext();
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return (context.isFinishing()
                        || context.isDestroyed());
            }
            return context.isFinishing();
        }

        //判断activity是否还在
        if (getActivityContext() instanceof Activity) {
            Activity activityContext = (Activity) getActivityContext();

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return (activityContext.isFinishing()
                        || activityContext.isDestroyed());
            }
            return activityContext.isFinishing();
        }

        return true;
    }

    /**
     * 用于判断Invalid的，主要直接getContext得到的是ContextThemeWrapper对象
     * 并非是Activity对象，这样判断 #isInvalidContext() 这个方法就有问题了，永远进不去了
     * 就一直有效了
     *
     * @return
     */
    protected abstract Context getActivityContext();
}
