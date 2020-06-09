package com.zfxf.douniu_network.entry;


import java.util.List;

/**
 * @author Admin
 * @time 2017/4/7 9:44
 * @des ${TODO}
 */

public class LoginResult extends BaseInfoOfResult{
    public String vc_code;//验证码
    public String ub_id;//用户的ID
    public String ud_nickname;//用户的昵称
    public String ud_photo_fileid;//用户头像的id
    public String ub_phone;//用户的电话
    public List<String> file_ids;//图片信息
    public String qiandao;//用户签到标识
    public String today_jifen;//今日积分
    public String total_niubi;//总牛币
    public String total_jifen;//总积分
    public String role;// 0 普通用户 1 管理员 2 超管 3 主播
    public String is_anchor; //0没有权限 1全部权限都有 2只有回播权限 3只有直播权限
    public String qiandao_reward_niubi;
    public String df_sftg;
    public String df_sfsx;
    public String user_complete;
    public String rongyun_token;//融云token
    public String token;//用户token
    public String contact;//客服电话
    public String reg_status;//1：老用户  0 :新用户
    public String user_sig;//腾讯云Im初始化


    /*新接口字段*/
    public String code;
    public String message;
    public LoginData data;

    public class LoginData {

        public String code;
        public String isReg;
        public String message;
        public String refreshToken;
        public String token;
        public String ubId;
        public String udNickname;
        public String udPhotoFileid;
        public String userSig;
        //签到
        public String businessCode;//返回码 10 成功 30 未查询到数据
        public String businessMessage;
        public String myJiFen;
        public String todayJiFen;
        /*我的页面*/
        public String signStatus;
        public String todayJifen;
        public String totalJifen;
        public String totalNiubi;
        public String dfSfsx;
        public String dfSftg;
        public String isAnchor;
        public String isInviter;
        public String role;
        public String isSingleRoot;//是否展示 会话列表 是否展示 会话列表 0不展示 1展示

        public String imReletionSale; //imReletionSale	普通用户IM是否存在销售好友 存在为销售ID
        public String imCustomerManage; //客服Id，不存在为0
        public String saleName; //销售昵称
        public String customerManageName; //客服昵称
        public String isShowSaleSingleChat; //是否展示我的客户经理 或 我的用户列表 0不展示 1展示我的客户经理 2展示我的用户列表
        //	是否展示我的客户经理 或 我的用户列表 0不展示 1展示我的客户经理 2销售展示我的用户列表 3客服展示我的用户列表

        public UserInfo userInfo;

        public class UserInfo {
            public String avatar;
            public String nickName;
        }
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "vc_code='" + vc_code + '\'' +
                ", ub_id='" + ub_id + '\'' +
                ", ud_nickname='" + ud_nickname + '\'' +
                ", ud_photo_fileid='" + ud_photo_fileid + '\'' +
                ", ub_phone='" + ub_phone + '\'' +
                ", file_ids=" + file_ids +
                ", qiandao='" + qiandao + '\'' +
                ", today_jifen='" + today_jifen + '\'' +
                ", total_niubi='" + total_niubi + '\'' +
                ", total_jifen='" + total_jifen + '\'' +
                ", role='" + role + '\'' +
                ", is_anchor='" + is_anchor + '\'' +
                ", qiandao_reward_niubi='" + qiandao_reward_niubi + '\'' +
                ", df_sftg='" + df_sftg + '\'' +
                ", df_sfsx='" + df_sfsx + '\'' +
                ", user_complete='" + user_complete + '\'' +
                ", rongyun_token='" + rongyun_token + '\'' +
                ", token='" + token + '\'' +
                ", contact='" + contact + '\'' +
                ", reg_status='" + reg_status + '\'' +
                ", user_sig='" + user_sig + '\'' +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
