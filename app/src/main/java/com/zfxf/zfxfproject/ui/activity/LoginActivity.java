package com.zfxf.zfxfproject.ui.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zfxf.base.BaseActivity;
import com.zfxf.douniu_network.LoginInternetRequest;
import com.zfxf.douniu_network.entry.LoginBean;
import com.zfxf.douniu_network.util.CommonUtils;
import com.zfxf.douniu_network.util.Constants;
import com.zfxf.douniu_network.util.SpTools;
import com.zfxf.zfxfproject.MainActivity;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.weight.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginInternetRequest.ForLoginListener {

    @BindView(R.id.titleView)
    TitleView titleView;
    @BindView(R.id.iconImg)
    ImageView iconImg;
    @BindView(R.id.etLoginPhone)
    EditText etLoginPhone;
    @BindView(R.id.etLoginPwd)
    EditText etLoginPwd;
    @BindView(R.id.tvLogin)
    TextView tvLogin;
    @BindView(R.id.iv_login_eye)
    ImageView ivLoginEye;
    @BindView(R.id.llLoginEye)
    LinearLayout llLoginEye;

    private boolean isEye = false;
    private LoginInternetRequest mRequest;
    private boolean isLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.llLoginEye, R.id.tvLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.llLoginEye:
                loginEyeStatusChange();
                break;
            case R.id.tvLogin:
                login();
                break;
        }
    }

    @Override
    protected void initView() {
        isLogin = getIntent().getIntExtra("isLogin", -1) == 1;  // 等于1 是需要登录
        if (!TextUtils.isEmpty(SpTools.getString(this, Constants.token, ""))) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
        mRequest = new LoginInternetRequest(this);

        titleView.resetBackListener(v -> {
            if (isLogin) {
                return;
            } else {
                finish();
            }
        });
    }

    /**
     * 登录
     */
    private void login() {
        toLogin();
    }

    /**
     * 密码的可见与不可见
     */
    private void loginEyeStatusChange() {
        if (!isEye) {
            isEye = !isEye;
            etLoginPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivLoginEye.setImageResource(R.drawable.icon_eye);
        } else {
            isEye = !isEye;
            etLoginPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivLoginEye.setImageResource(R.drawable.login_pw_show);
        }
        etLoginPwd.setSelection(etLoginPwd.getText().length());
    }

    /**
     * 登录
     */
    private void toLogin() {
        if (mRequest != null) {
            mRequest.loginSign(
                    etLoginPhone.getText().toString().trim(),
                    etLoginPwd.getText().toString().trim(),
                    this
            );
        }
    }


    /**
     * 登录请求接口的回调
     */
    @Override
    public void onResponseMessage(LoginBean bean) {
        CommonUtils.toastMessage("登录成功");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}