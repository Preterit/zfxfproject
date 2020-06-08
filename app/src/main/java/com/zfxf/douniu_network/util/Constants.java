package com.zfxf.douniu_network.util;

/**
 * @author Admin
 * @time 2017/2/10 10:49
 * @des ${TODO}
 */

public interface Constants {
    String CONFIGFILE = "douniu";
    String userId = "USERID";
    String token = "TOKEN";
    String refreshToken = "REFRESHTOKEN";
    String rvaluateResult = "RVALUATERESULT";
    String rvaluateAnswer = "RVALUATEANSWER";
    String rvaluateSuccess = "RVALUATESUCCESS";
    String isLogin = "ISLOGIN";//标识登录true为登录  除非退出
    String reLogin = "RELOGIN";//标识重新登录
    String userSig = "userSign";//标识重新登录
    String alreadyLogin = "ALREADYISLOGIN";//标识登录true为登录 一次性
    String alreadyLogout = "ALREADYISLOGOUT";
    String alreadyGuanzhu = "ALREADYISGUANZHU";
    String nickname = "NICKNAME";
    String imgurl = "IMGURL";
    String buy = "BUY";
    String xufei = "XUFEI";//金股池续费
    String shopBuy = "SHOPBUY";
    String niurenOnce = "NIURENONCE";
    String yiyuanbuy = "YIYUNABUY";
    String cankaobuy = "CANKAOBUY";
    String myniubi = "MYNIUBI";//我的页面刷新我的牛币
    String askbuy = "ASKBUY";
    String livebuy = "LIVEBUY";
    String subscribe = "SUBSCRIBE";
    String mengceng = "MENGCENG";
    String publicsubscribe = "PUBLICSUBSCRIBE";
    String read = "READ";
    String goldBottomShow = "GOLDBOTTOMSHOW";//区分从我的页面进入金股池需要显示底部对话框
    int resultCodeLogin = 1;
    int resultCodeEditInfor = 2;
    String APP_ID = "wx086c758d5e01f463";
    String error = "ERROR";
    String payweixin = "PAYWEIXIN";
    String isOpenApp = "isOpenApp";
    String outip = "OUTIP";
    String isattention = "isattention";
    String shouXiId = "SHOUXIID";

    String tuisongId = "TUISONGID";
    String tuisongType = "TUISONGTYPE";
    String tuisongSxId = "TUISONGSXID";
    String tuisongMes = "TUISONGMES";
    String tuisongtitle = "TUISONGTITLE";

    String livingListInformation = "livingListInformation";//主页的直播列表信息
    String referenceListInformation = "referenceListInformation";//主页的大参考列表信息
    String policyListInformation = "policyListInformation";//主页的公开课列表信息
    String secretListInformation = "secretListInformation";//主页的私密课列表信息
    String goldmyselfPondListInformation = "goldMyselfPondListInformation";//我的金股池列表信息
    String answerListInformation = "answerPondListInformation";//主页的微问答列表信息

    String sp_home_banner_buy = "sp_home_banner_buy";//商城首页Banner购买
    String sp_home_bannertext_buy = "sp_home_banner_buy";//商城轮播文字购买
    String sp_home_recommend_buy = "sp_home_recommend_buy";//商城热门推荐新品栏目下的购买
    String sp_home_all_buy = "sp_home_all_buy";//商城全部商品下的内容购买

    String lvId = "LVID";//新版直播 直播间的id
    String lvLength = "LVLENGTH";//新版直播时长
    String isNight = "ISNIGHT";//true为夜间模式
    String stockFullscreen = "STOCKFULLSCREEN";//股票是否是全屏

    String WX_PAY_SUCCESS = "WX_PAY_SUCCESS";//微信支付成功


    //IM chatinfo KEY
    String CHAT_INFO = "chatInfo";

    //SharedPreferences key值
    String patchUUID = "PATCH_UUID";  //本地存储 patchUUID
    String tempUUID = "TEMP_UUID"; //本地临时保持 tempUUID

    String adUrl = "AD_URL"; //启动页广告图url
    String hasNewAd = "HAS_NEW_AD"; // 是否有新广告图
    String adName = "AD_NAME"; //广告图名称


    //隐私政策显示弹框
    String isShowedPrivacyProtection = "isShowedPrivacyProtection";
}
