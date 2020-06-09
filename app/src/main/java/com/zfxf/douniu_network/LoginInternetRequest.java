package com.zfxf.douniu_network;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.zfxf.douniu_network.entry.LoginBean;
import com.zfxf.douniu_network.util.CommonUtils;
import com.zfxf.douniu_network.util.Constants;
import com.zfxf.douniu_network.util.SpTools;
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
            return;
        }
        if (TextUtils.isEmpty(phonenumber)) {
            CommonUtils.toastMessage("您输入的手机号为空");
            return;
        } else if (TextUtils.isEmpty(password)) {
            CommonUtils.toastMessage("您输入的密码为空");
            return;
        }

        if (!TextUtils.isEmpty(phonenumber)) {
            if (!CommonUtils.isMobilePhone(phonenumber)) {
                CommonUtils.toastMessage("您输入的手机号有误");
                return;
            }
        }
        
        CommonUtils.showProgressDialog(mContext, "加载中……");
        Map<String, Object> map = new HashMap<>();
        map.put("phone", phonenumber);
        map.put("password", CommonUtils.md5(CommonUtils.md5(password)));
        new BaseInternetRequestNew(mContext, new BaseInternetRequestNew.HttpUtilsListenerNew<LoginBean>() {
            @Override
            public void onResponse(LoginBean bean, int id) {
                if ("10".equals(bean.businessCode)) {
                    SpTools.setString(mContext, Constants.token, bean.token);
                    mListener.onResponseMessage(bean);
                } else {
                    CommonUtils.toastMessage(bean.businessMessage);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                Log.e(TAG, "onError: " + e.toString());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                return false;
            }
        }).postSign(mContext.getString(R.string.getLoginNew), false, map, LoginBean.class);
    }


    public interface ForLoginListener {
        void onResponseMessage(LoginBean bean);
    }
}
