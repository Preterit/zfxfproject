package com.zfxf.douniu_network.entry;

import com.google.gson.annotations.SerializedName;

/**
 * @author :  lwb
 * Date: 2020/6/9
 * Desc:
 */
public class LoginBean extends BaseInfoOfResult {
    /**
     * businessCode : 10
     * businessMessage : 登陆成功
     * isReg : 1
     * openId :
     * refreshToken : eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1OTI4ODI4MTIsInN1YiI6Im15U2VjcmV0MTgxNiIsImlhdCI6MTU5MTY3MzIxMn0.6C28n-1Ewt9UdN-fQE84yGZ0uOvnyPOMYhZStLov_Ja8WbMvSu2CYMfapxqJEVNpE4vIneMaWRFfwyHJj3Vn1Q
     * token : eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE1OTIyNzgwMTIsInN1YiI6IjE4MTYiLCJpYXQiOjE1OTE2NzMyMTJ9.bPwP0nPuQsVwIEl6B2iEwcswks0YZhynjiaM4I9_Fvf4qMyosPPJi6dJuBpKJnZ4aGcJYwfCkQhT9ko6mhQ4wQ
     * ubId : 1816
     * udNickname : 斗牛士_5205
     * udPhotoFileid :
     * userSig :
     */

    @SerializedName("businessCode")
    public String businessCodeX;
    @SerializedName("businessMessage")
    public String businessMessageX;
    public String isReg;
    public String openId;
    public String refreshToken;
    public String token;
    public int ubId;
    public String udNickname;
    public String udPhotoFileid;
    public String userSig;


}
