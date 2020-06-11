package com.zfxf.zfxfproject.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.zfxf.network.NetworkSession;
import com.zfxf.network.bean.TokenBean;
import com.zfxf.network.net.BaseResponse;
import com.zfxf.network.net.DefaultRetrofitClient;
import com.zfxf.zfxfproject.R;

import java.io.IOException;

public class NetTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_test);
    }

    public void onRequestClicked(View view){

    }

    //用同步方法获取新的Token
    private String getNewToken() throws IOException {
        // 通过获取token的接口，同步请求接口
        String token = null;
        retrofit2.Response<BaseResponse<TokenBean>> response = DefaultRetrofitClient.getInstance()
                .getHttpApi()
                .refreshToken("all-walking/v1/user/login")
                .execute();
        BaseResponse<TokenBean> body = response.body();
        if (body != null) {
            TokenBean data = body.getData();
            if (data != null) {
                token = data.getToken();
                //这里进行接口 回调出去，让外部知道刷新好的token
                if (!TextUtils.isEmpty(token)) {
//                    NetworkSession.get().getNetworkConfig().setNewToken(token);
                }
            }
        }
        return token;
    }
}