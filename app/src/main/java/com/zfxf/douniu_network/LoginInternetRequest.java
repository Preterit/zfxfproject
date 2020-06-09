package com.zfxf.douniu_network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zfxf.douniu_network.entry.LoginResult;
import com.zfxf.douniu_network.util.CommonUtils;
import com.zfxf.zfxfproject.R;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;

/**
 * @author :  lwb
 * Date: 2020/6/9
 * Desc:
 */
public class LoginInternetRequest {

    private final String TAG = "LoginInternetRequest";

    private Context mContext;
    private ForLoginListener mListener;

    public LoginInternetRequest(Context context) {
        WeakReference contextWeakReference = new WeakReference<Context>(context);
        if (contextWeakReference.get() != null) {
            mContext = (Context) contextWeakReference.get();
        }
    }

    /**
     * 登录
     *
     * @param phonenumber 手机号
     * @param password    密码
     */
    public void loginSign(String phonenumber, String password, ForLoginListener listener) {
        mListener = listener;
        if (!CommonUtils.isNetworkAvailable(CommonUtils.getContext())) {
            CommonUtils.toastMessage("您当前无网络，请联网再试");
            mListener.onResponseMessage("", "");
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            CommonUtils.toastMessage("您输入的手机号为空");
            mListener.onResponseMessage("", "");
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.toastMessage("您输入的密码为空");
            mListener.onResponseMessage("", "");
            return;
        }

        if (!TextUtils.isEmpty(phonenumber)) {
            if (!CommonUtils.isMobilePhone(phonenumber)) {
                CommonUtils.toastMessage("您输入的手机号有误");
                mListener.onResponseMessage("", "");
                return;
            }
        }
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phonenumber);
        map.put("password", CommonUtils.md5(CommonUtils.md5(password)));
        new BaseInternetRequestNew(mContext, new BaseInternetRequestNew.HttpUtilsListenerNew<LoginResult>() {
            @Override
            public void onResponse(LoginResult bean, int id) {
                Log.e(TAG, "onResponse: " + bean.toString());
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                return false;
            }
        }).postSign(mContext.getString(R.string.getLoginNew), false, map, LoginResult.class);
    }


    public interface ForLoginListener {
        void onResponseMessage(String code, String userSign);
    }
}
