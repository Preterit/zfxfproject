package com.zfxf.douniu_network;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.zfxf.app.BaseApplication;
import com.zfxf.douniu_network.callback.CallBack;
import com.zfxf.douniu_network.entry.BaseInfoOfResult;
import com.zfxf.douniu_network.entry.BaseResultNew;
import com.zfxf.douniu_network.entry.GetTimeBean;
import com.zfxf.douniu_network.entry.RefreshTokenBean;
import com.zfxf.douniu_network.util.CommonUtils;
import com.zfxf.douniu_network.util.Constant;
import com.zfxf.douniu_network.util.Constants;
import com.zfxf.douniu_network.util.GsonUtil;
import com.zfxf.douniu_network.util.LogUtils;
import com.zfxf.douniu_network.util.SpTools;
import com.zfxf.zfxfproject.R;
import com.zfxf.zfxfproject.ui.activity.LoginActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static com.zfxf.douniu_network.util.CommonUtils.isNetworkAvailable;


/**
 * @author IMXU
 * @time 2017/5/11 16:42
 * @des ${TODO}
 * 邮箱：butterfly_xu@sina.com
 */

public class BaseInternetRequestNew {

    private static final String TAG = BaseInternetRequestNew.class.getSimpleName();
    private static final String POST_RESULT = "post result---";
    private static final String POST_PARAMS = "post params---";
    private static final String GET_RESULT = "get result---";

    private HttpUtilsListenerNew mHttpUtilsListenerNew;
    private Context mContext;
    private Gson gson;
    private int index;
//    public static String secret_key = "abcdefghigklmn";//sign 字符串最后拼接的key
//    public static String secret_key = "1ABC2312A0B37E611035E6";//sign 字符串最后拼接的key

    public BaseInternetRequestNew(Context context, HttpUtilsListenerNew httpUtilsListener) {
        mHttpUtilsListenerNew = httpUtilsListener;
        mContext = context;
        gson = new Gson();
    }


    public interface HttpUtilsListenerNew<T> {
        void onResponse(T t, int id);

        void onError(Call call, Exception e, int id);

        boolean dealErrorCode(String baseCode, String baseMessage);//数据code 处理 若返回true 代表某个接口自己处理了返回的code 网络请求的基类就不在处理了
    }

    /**
     * 普通的post 请求
     *
     * @param url
     * @param isNeedHeader
     * @param params
     * @param tClass
     * @param <T>
     */
    public <T> void post(final String url, Boolean isNeedHeader, Map<String, Object> params, final Class<T> tClass) {
        try {
            if (!isNetworkAvailable(mContext)) {
                CommonUtils.toastMessage("您当前无网络，请联网再试");
                mHttpUtilsListenerNew.onError(null, null, 0);
                return;
            }
            PostFormBuilder builder = OkHttpUtils.post().url(Constant.service_host_address_new.concat(url));
            CommonUtils.logMes("---------url----" + Constant.service_host_address_new.concat(url));

            if (params == null) {
                params = new HashMap<>();
            }
            //是否添加基础请求参数

            addNewBaseParamsNew(params);


            //是否添加请求头
            if (isNeedHeader) {
                builder.addHeader("Authorization", "Bearer " + SpTools.getString(mContext, Constants.token, ""));
                LogUtils.e("0418", "postNewNew: Header:" + "Bearer " + SpTools.getString(mContext, Constants.token, ""));
            }


            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                } else {
                    newMap.put(entry.getKey(), entry.getValue().toString());
                }
            }
            LogUtils.e("0418", "postNewNew: <String,Object> 转<S,S>后：" + newMap.toString());
            builder.params(newMap);
//            CommonUtils.logMes("post params---" + url + "---" + newMap);
            LogUtils.e(TAG, POST_PARAMS + url + "---" + newMap);

            builder.build().execute(new CallBack() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    try {
                        mHttpUtilsListenerNew.onError(call, e, id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        LogUtils.e(TAG, POST_RESULT + url + "---" + response);

                        BaseResultNew<T> baseResultNew = GsonUtil.fromJsonByType(response, tClass);
                        //成功
                        if (baseResultNew.code.equals("200")) {
                            mHttpUtilsListenerNew.onResponse(baseResultNew.data, id);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 刷新Token
     *
     * @param listenerNew
     */
    public <T extends BaseInfoOfResult> void refreshToken(final HttpUtilsListenerNew listenerNew, final String url, final Boolean isNeedHeader, final Map<String, Object> params, final Class<T> tClass) {

        new BaseInternetRequestNew(mContext, new HttpUtilsListenerNew<RefreshTokenBean>() {
            @Override
            public void onResponse(RefreshTokenBean bean, int id) {
                if (bean != null && !TextUtils.isEmpty(bean.token) && !TextUtils.isEmpty(bean.refreshToken)) {
                    SpTools.setString(mContext, Constants.token, bean.token);
                    SpTools.setString(mContext, Constants.refreshToken, bean.refreshToken);
                    //listenerNew.onResponse(null,501);

                    BaseInternetRequestNew.this.mHttpUtilsListenerNew = listenerNew;
                    BaseInternetRequestNew.this.postSign(url, isNeedHeader, params, tClass);
                }
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                LogUtils.e(TAG, "BaseInternetRequestNew onError:" + e.getMessage());
            }

            @Override
            public boolean dealErrorCode(String baseCode, String baseMessage) {
                //接口没有处理的这里处理
                CommonUtils.toastMessage("登录失效，请重新登录!");
                CommonUtils.exitLogin(mContext, true);


                return true;
            }
        }).get(mContext.getResources().getString(R.string.refreshToken) + "/" + SpTools.getString(mContext, Constants.refreshToken, ""), false, null, RefreshTokenBean.class);

    }

    /**
     * 带参数签名验证的post 请求
     *
     * @param url
     * @param isNeedHeader
     * @param params
     * @param tClass
     * @param <T>
     */
    public <T extends BaseInfoOfResult> void postSign(final String url, Boolean isNeedHeader, Map<String, Object> params, final Class<T> tClass) {
        postSignAddFiles(url, isNeedHeader, params, null, tClass);
    }

    /**
     * 带参数签名验证的post 请求 附带文件
     *
     * @param url
     * @param isNeedHeader
     * @param filesMap     Map<String, File> String是key File
     * @param <T>
     */
    public <T extends BaseInfoOfResult> void postSignAddFiles(final String url, Boolean isNeedHeader, Map<String, Object> params, Map<String, File> filesMap, final Class<T> tClass) {

        //因为基础参数时间戳需要从后台拿  所以异步等待时间返回在调用接口
        getTimeBeforePost(url, isNeedHeader, params, null, tClass);
    }


    private <T extends BaseInfoOfResult> void finalPost(final String url, Boolean isNeedHeader, Map<String, Object> params, Map<String, File> filesMap, final Class<T> tClass, String timeFromServer) {
        try {
            if (!isNetworkAvailable(mContext)) {
                CommonUtils.toastMessage("您当前无网络，请联网再试");
                mHttpUtilsListenerNew.onError(null, null, 0);
                return;
            }
            PostFormBuilder builder = OkHttpUtils.post().url(Constant.service_host_address_new.concat(url));
            CommonUtils.logMes(TAG + "----url----" + Constant.service_host_address_new.concat(url));

            if (params == null) {
                params = new HashMap<>();
            }

            //需求是每次都要传这六个基础的参数 + 时间戳
            addNewBaseParamsNew(params);

            //因为要传服务器拿过来的时间戳  先把这个key单独提出来
            if (!TextUtils.isEmpty(timeFromServer)) {
                //添加从服务器拿的时间
                params.put("timestamp", timeFromServer);//sign 时候要加一个时间戳
            } else {
                params.put("timestamp", (System.currentTimeMillis() / 1000) + "");//sign 时候要加一个时间戳
            }

            //是否需要添加请求头
            if (isNeedHeader) {
                String token = "";
                if (!TextUtils.isEmpty(SpTools.getString(mContext, Constants.token, ""))) {
                    token = SpTools.getString(mContext, Constants.token, "");
                } else if (!TextUtils.isEmpty(BaseApplication.TOKEN)) {
                    token = BaseApplication.TOKEN;
                }

                if (TextUtils.isEmpty(token)) return;
                builder.addHeader("Authorization", "Bearer " + token);
                LogUtils.e("0425", "post header:" + "Bearer " + token);
            }


            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                } else {
                    if (entry.getValue() != null)
                        newMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            String signStr = getSignToken(newMap);
            newMap.put("sign", CommonUtils.md5(signStr));

            //添加参数
            builder.params(newMap);

            if (filesMap != null) {
                //添加文件
                for (String key : filesMap.keySet()) {
                    if (filesMap.get(key) == null) continue;
                    LogUtils.e("BaseInternetRequestNew0828", "postSignAddFiles:" + filesMap.get(key).getName());
                    builder.addFile(key, filesMap.get(key).getName(), filesMap.get(key));
                }
            }
            //可能要重新请求一次请求失败的接口，保存参数用来重新请求
            final Map<String, Object> reRequestMap = params;


//            CommonUtils.logMes("post params---" + url + "---" + newMap);
            LogUtils.e(TAG, POST_PARAMS + url + "---" + newMap);

            builder.build().execute(new CallBack() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    try {
                        LogUtils.e(TAG, POST_RESULT + url + "---onError" + e.getMessage());
                        mHttpUtilsListenerNew.onError(call, e, id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        LogUtils.e(TAG, POST_RESULT + url + "---" + response);
                        Log.e(TAG, "onResponse: " + response);
                        BaseResultNew<T> baseResultNew = GsonUtil.fromJsonByType(response, tClass);

                        //返回空的时候，可能要交给具体的接口来进行操作：比如 返回数据空的时候页面显示数据空的提示
                        if (baseResultNew == null || TextUtils.isEmpty(baseResultNew.code)) {
                            //这里的错误码就直接返回空就好，在具体接口中先判断是不是空
                            if (!mHttpUtilsListenerNew.dealErrorCode(null, null)) {
                                //返回空时接口共同的可能进行的操作
                            }
                            return;
                        }

                        if (baseResultNew.code.equals("200")) {//成功 : 200
                            mHttpUtilsListenerNew.onResponse(baseResultNew.data, id);

                        } else if (baseResultNew.code.equals("501")) {//Token 过期需要调用刷新Token接口来刷新Token ：501
                            //具体接口如果返回true 就不在基类方法中处理了
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {

                                //接口没有处理的这里处理
                                //刷新Token 并重新调用失败的接口
                                refreshToken(mHttpUtilsListenerNew, url, true, reRequestMap, tClass);
                            }

                        } else if (baseResultNew.code.equals("401")) {//请求被拒绝，需要跳转登录页面重新登录 ： 401
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //接口没有处理的这里处理
                                CommonUtils.toastMessage("登录失效，请重新登录！！");
//                                CommonUtils.exitLogin(mContext, true);
                                SpTools.setString(mContext, Constants.token, "");
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.putExtra("isLogin", 1);
                                mContext.startActivity(intent);
                            }
                        } else if (baseResultNew.code.equals("500")) {
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //接口没有处理的这里处理
                                CommonUtils.toastMessage(baseResultNew.message);
                            }
                        } else if (baseResultNew.code.equals("502")) {
                            CommonUtils.toastMessage("您的账号已被封禁！");

                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage("您的账号已被封禁！");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    SpTools.setBoolean(mContext, Constants.isLogin, false);
                                    SpTools.setBoolean(mContext, Constants.alreadyLogout, false);
                                    SpTools.setString(mContext, Constants.userId, null);
                                    SpTools.setInt(mContext, Constants.rvaluateResult, 0);
                                    SpTools.setString(mContext, Constants.token, null);
                                    SpTools.setString(mContext, Constants.refreshToken, null);
                                    SpTools.setString(CommonUtils.getContext(), "userSign", null);
                                    CommonUtils.deleteBitmap("myicon.jpg");

                                    //TODO  没有相关逻辑  注释代码
//                                    Intent intent = new Intent(mContext, ActivityLogin.class);
//                                    mContext.startActivity(intent);
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //接口没有处理的这里处理

                            }
                        } else { //其他情况
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带参数签名验证的get 请求
     *
     * @param url
     * @param isNeedHeader
     * @param params
     * @param tClass
     * @param <T>
     */
    public <T> void getSign(final String url, Boolean isNeedHeader, Map<String, Object> params, final Class<T> tClass) {
        try {
            if (!isNetworkAvailable(mContext)) {
                CommonUtils.toastMessage("您当前无网络，请联网再试");
                mHttpUtilsListenerNew.onError(null, null, 0);
                return;
            }
            GetBuilder builder = OkHttpUtils.get().url(Constant.service_host_address_new.concat(url));
            CommonUtils.logMes("---------url----" + Constant.service_host_address_new.concat(url));

            if (params == null) {
                params = new HashMap<>();
            }
            //是否添加基础请求参数
            addNewBaseParamsNew(params);


            //是否添加请求头
            if (isNeedHeader) {
                builder.addHeader("Authorization", "Bearer " + SpTools.getString(mContext, Constants.token, ""));
                LogUtils.e(TAG, "get head:" + "Bearer " + SpTools.getString(mContext, Constants.token, ""));
            }

            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                } else {
                    newMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            String signStr = getSignToken(newMap);// 排序好之后拼接的 sign 字符串
            newMap.put("sign", CommonUtils.md5(signStr));

            LogUtils.e(TAG, POST_PARAMS + url + "---" + newMap);

            builder.params(newMap);
            builder.build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    try {
                        mHttpUtilsListenerNew.onError(call, e, id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        LogUtils.e(TAG, GET_RESULT + response);
                        LogUtils.e(TAG, POST_RESULT + url + "---" + response);

                        BaseResultNew<T> baseResultNew = GsonUtil.fromJsonByType(response, tClass);
                        if (baseResultNew.code.equals("200")) {
                            //成功
                            mHttpUtilsListenerNew.onResponse(baseResultNew.data, id);
                        } else {
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //默认非200 的处理
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 带参数签名验证的get 请求
     *
     * @param url
     * @param isNeedHeader
     * @param params
     * @param tClass
     * @param <T>
     */
    public <T> void get(String url, Boolean isNeedHeader, Map<String, Object> params, final Class<T> tClass) {
        try {
            if (!isNetworkAvailable(mContext)) {
                CommonUtils.toastMessage("您当前无网络，请联网再试");
                mHttpUtilsListenerNew.onError(null, null, 0);
                return;
            }
            GetBuilder builder = OkHttpUtils.get().url(Constant.service_host_address_new.concat(url));
            CommonUtils.logMes("---------url----" + Constant.service_host_address_new.concat(url));

            if (params == null) {
                params = new HashMap<>();
            }
            //是否添加基础请求参数

            addNewBaseParamsNew(params);


            //是否添加请求头
            if (isNeedHeader) {
                builder.addHeader("Authorization", "Bearer " + SpTools.getString(mContext, Constants.token, ""));
                LogUtils.e("0418", "get head:" + "Bearer " + SpTools.getString(mContext, Constants.token, ""));
            }

            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                } else {
                    newMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            builder.params(newMap);
            builder.build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    try {
                        mHttpUtilsListenerNew.onError(call, e, id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        LogUtils.e(TAG, GET_RESULT + response);

                        BaseResultNew<T> baseResultNew = GsonUtil.fromJsonByType(response, tClass);
                        if (baseResultNew.code.equals("200")) {
                            //成功
                            mHttpUtilsListenerNew.onResponse(baseResultNew.data, id);
                        } else {
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //默认非200 的处理
                            }
                        }
                    } catch (
                            Exception e) {
                        e.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }
            });
        } catch (
                Exception e) {
            e.printStackTrace();
        }

    }


    private void addNewBaseParamsNew(Map<String, Object> params) {
        params.put("pmIp", SpTools.getString(mContext, Constants.outip, ""));
        params.put("pmMac", CommonUtils.getMac());
        params.put("pmSystem", "Android " + CommonUtils.getSystemVersion());
        params.put("pmAppVersion", CommonUtils.getVersionCode(mContext));
        params.put("pmModel", CommonUtils.getSystemModel());
        params.put("pmOperator", CommonUtils.getOperators(mContext));
//        params.put("timestamp", (System.currentTimeMillis() / 1000) + "");//sign 时候要加一个时间戳
//        params.put("token", SpTools.getString(mContext, Constants.token, null));
    }

    /**
     * 在post 之前先获取后台时间 作为post的入参
     *
     * @param url
     * @param isNeedHeader
     * @param params
     * @param filesMap
     * @param tClass
     * @param <T>
     */
    private <T extends BaseInfoOfResult> void getTimeBeforePost(final String url, final Boolean isNeedHeader, final Map<String, Object> params, final Map<String, File> filesMap, final Class<T> tClass) {


        //在这里之前可以对url过滤下 比如某个接口不用传系统时间戳就直接传null就好

        GetBuilder builder = OkHttpUtils.get().url("https://api.douniu8.com/callback/timeStamp");
        builder.build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                String timeFromServer = (System.currentTimeMillis() / 1000) + "";
                finalPost(url, isNeedHeader, params, null, tClass, timeFromServer);
            }

            @Override
            public void onResponse(String response, int id) {
                String timeFromServer;
                GetTimeBean bean = new Gson().fromJson(response, GetTimeBean.class);
                if (bean != null && "200".equals(bean.code)) {
                    timeFromServer = bean.data;
                } else {
                    timeFromServer = (System.currentTimeMillis() / 1000) + "";
                }
                finalPost(url, isNeedHeader, params, null, tClass, timeFromServer);
            }
        });
    }


    /**
     * 生成签名
     *
     * @param map
     * @return
     */
    public String getSignToken(Map<String, String> map) {
        String result = "";
        try {
            List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(map.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

                public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                    return (o1.getKey()).toString().compareTo(o2.getKey());
                }
            });
            // 构造签名键值对的格式
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> item : infoIds) {
                if (item.getKey() != null || item.getKey() != "") {
                    String key = item.getKey();
                    String val = item.getValue();
                    if (!(val == "" || val == null)) {
                        sb.append(key + "=" + val + "&");
                    }
                }
            }

            //sign字符串最后拼接key
            sb.append("key=" + Constant.secret_key);
            result = sb.toString();
        } catch (Exception e) {
            return null;
        }
        return result;
    }


    /**
     * =========带参数签名验证的post 请求 附带文件
     *
     * @param url
     * @param isNeedHeader
     * @param filesMap     Map<String, File> String是key File
     * @param <T>
     */
    public <T extends BaseInfoOfResult> void postSignAddTimeStamp(final String url, Boolean isNeedHeader, Map<String, Object> params, String timeStamp, Map<String, File> filesMap, final Class<T> tClass) {
        try {
            if (!isNetworkAvailable(mContext)) {
                CommonUtils.toastMessage("您当前无网络，请联网再试");
                mHttpUtilsListenerNew.onError(null, null, 0);
                return;
            }
            PostFormBuilder builder = OkHttpUtils.post().url(Constant.service_host_address_new.concat(url));
            CommonUtils.logMes(TAG + "----url----" + Constant.service_host_address_new.concat(url));

            if (params == null) {
                params = new HashMap<>();
            }

            //需求是每次都要传这六个基础的参数 + 时间戳
            //addNewBaseParamsNew(params);
            params.put("pmIp", SpTools.getString(mContext, Constants.outip, ""));
            params.put("pmMac", CommonUtils.getMac());
            params.put("pmSystem", "Android " + CommonUtils.getSystemVersion());
            params.put("pmAppVersion", CommonUtils.getVersionCode(mContext));
            params.put("pmModel", CommonUtils.getSystemModel());
            params.put("pmOperator", CommonUtils.getOperators(mContext));
            LogUtils.i("TAG", TAG + "----timestamp--- " + timeStamp);

            params.put("timestamp", timeStamp);//sign 时候要加一个时间戳

            //是否需要添加请求头
            if (isNeedHeader) {
                builder.addHeader("Authorization", "Bearer " + SpTools.getString(mContext, Constants.token, ""));
                LogUtils.e("0425", "post header:" + "Bearer " + SpTools.getString(mContext, Constants.token, ""));
            }


            Map<String, String> newMap = new HashMap<String, String>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                if (entry.getValue() instanceof String) {
                    newMap.put(entry.getKey(), (String) entry.getValue());
                } else {
                    if (entry.getValue() != null)
                        newMap.put(entry.getKey(), entry.getValue().toString());
                }
            }

            String signStr = getSignToken(newMap);
            newMap.put("sign", CommonUtils.md5(signStr));

            //添加参数
            builder.params(newMap);

            if (filesMap != null) {
                //添加文件
                for (String key : filesMap.keySet()) {
                    if (filesMap.get(key) == null) continue;
                    LogUtils.e("BaseInternetRequestNew0828", "postSignAddFiles:" + filesMap.get(key).getName());
                    builder.addFile(key, filesMap.get(key).getName(), filesMap.get(key));
                }
            }
            //可能要重新请求一次请求失败的接口，保存参数用来重新请求
            final Map<String, Object> reRequestMap = params;


//            CommonUtils.logMes("post params---" + url + "---" + newMap);
            LogUtils.e(TAG, POST_PARAMS + url + "---" + newMap);

            builder.build().execute(new CallBack() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    try {
                        LogUtils.e(TAG, POST_RESULT + url + "---onError" + e.getMessage());
                        mHttpUtilsListenerNew.onError(call, e, id);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        LogUtils.e(TAG, POST_RESULT + url + "---" + response);
                        BaseResultNew<T> baseResultNew = GsonUtil.fromJsonByType(response, tClass);

                        //返回空的时候，可能要交给具体的接口来进行操作：比如 返回数据空的时候页面显示数据空的提示
                        if (baseResultNew == null || TextUtils.isEmpty(baseResultNew.code)) {
                            //这里的错误码就直接返回空就好，在具体接口中先判断是不是空
                            if (!mHttpUtilsListenerNew.dealErrorCode(null, null)) {
                                //返回空时接口共同的可能进行的操作
                            }
                            return;
                        }

                        if (baseResultNew.code.equals("200")) {//成功 : 200
                            mHttpUtilsListenerNew.onResponse(baseResultNew.data, id);

                        } else if (baseResultNew.code.equals("501")) {//Token 过期需要调用刷新Token接口来刷新Token ：501
                            //具体接口如果返回true 就不在基类方法中处理了
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {

                                //接口没有处理的这里处理
                                //刷新Token 并重新调用失败的接口
                                refreshToken(mHttpUtilsListenerNew, url, true, reRequestMap, tClass);
                            }

                        } else if (baseResultNew.code.equals("401")) {//请求被拒绝，需要跳转登录页面重新登录 ： 401
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //接口没有处理的这里处理
                                CommonUtils.toastMessage("登录失效，请重新登录！！！");
                                CommonUtils.exitLogin(mContext, true);

                            }
                        } else if (baseResultNew.code.equals("500")) {
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {
                                //接口没有处理的这里处理
                                CommonUtils.toastMessage(baseResultNew.message);
                            }
                        } else { //其他情况
                            if (!mHttpUtilsListenerNew.dealErrorCode(baseResultNew.code, baseResultNew.message)) {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    CommonUtils.dismissProgressDialog();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}