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
import com.zfxf.util.UiUtils;
import com.zfxf.zfxfproject.MainActivity;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.util.UIUtils;
import com.zfxf.zfxfproject.weight.TitleView;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    @OnClick({R.id.llLoginEye,R.id.tvLogin})
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

    /**
     * 登录
     */
    private void login() {
        if (TextUtils.isEmpty(etLoginPhone.getText().toString().trim())) {
            UIUtils.showToast("请输入正确手机号");
            return;
        }
        if (TextUtils.isEmpty(etLoginPwd.getText().toString().trim())) {
            UIUtils.showToast("请输入密码");
            return;
        }

        startActivity(new Intent(LoginActivity.this, MainActivity.class));
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


}