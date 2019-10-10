package com.info.utils;

/**
 * 功能描述: 系统参数相关Key
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/14 14:32
 * @Return:
 */
public class ConfigConstant {

    //云存储配置KEY
    public final static String CLOUD_STORAGE_CONFIG_KEY = "CLOUD_STORAGE_CONFIG_KEY";

    //数组长度默认初始值0
    public final static Integer INIT_LIST_SIZE = 0;

    //评论表父id
    public final static Integer PARENT_ID = 0;

    //删除
    public final static Integer DEL = 0;
    // 正常,未删除
    public final static Integer NOTDEL = 1;

    //已读 1
    public final static Integer READ = 0;
    //未读
    public final static Integer NOT_READ = 1;

    //messageCode异常1信息
    public final static Integer ERROR = 0;
    //messageCode异常2信息
    public final static Integer ERRORS = -1;
    // messageCode成功信息
    public final static Integer SUCCESS = 1;
    //网络异常,或是特殊异常
    public final static Integer NET_ERROR = 505;


    //未审核
    public final static Integer VERIFY = 0;
    //未审核通过
    public final static Integer VERIFY_FALSE = -1;
    //审核成功
    public final static Integer VERIFY_SUCCESS = 1;

    //未实名认证
    public final static Integer AUTHNO = -1;
    //实名认证  已审核  审核成功
    public final static Integer AUTH = 1;
    //实名认证中
    public final static Integer AUTHING = 2;
    //实名认证类型 未认证
    public final static Integer AUTHITYPE = 3;

    //未支付
    public final static Integer PAY = 0;
    //支付失败
    public final static Integer PAY_FALSE = -1;
    //支付成功
    public final static Integer PAY_SUCCESS = 1;

    //短信发送类型--注册
    public final static Integer SEND_TYPE_REG = 1;
    //短信发送类型--修改绑定手机号
    public final static Integer SEND_TYPE_MOBILE = 2;
    //短信发送类型---访客通行码
    public final static Integer SEND_TYPE_PASS = 3;
    //短信类型---申请电子放行单
    public final static Integer SEND_TYPE_RELEASESLIP = 4;
    //短信类型---新手机
    public final static Integer SEND_TYPE_NEW_MOBILE = 5;
    //短信类型---原手机号
    public final static Integer SEND_TYPE_OLD_MOBILE = 6;

    //安卓更新
    public final static String APK_VERSION = "APK_VERSION";
    public final static String APK_URL = "APK_URL";
    public final static String APK_DESCRIBE = "APK_DESCRIBE";
    public final static String IOS_REG_MONEY = "IOS_REG_MONEY";
    public final static String IOS_REG_MONEY_NULL = "IOS_REG_MONEY_NULL";

    //这哩是对应的用户楼层，房号，单元号，楼层号的基础配置信息
    //楼号社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号
    public final static Integer FLOOR = 1;
    //单元号社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号
    public final static Integer UNIT = 2;
    //楼层号社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号
    public final static Integer LEVEL = 3;
    //房间号社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号
    public final static Integer ROOM = 4;


    /**
     * 系统设置
     *
     * @author: LiuDan
     * @Date: 2019/6/24 14:27
     * @Description:
     */
    public static class SysConfig {
        //安卓更新
        public final static String APK_VERSION = "APK_VERSION";
        public final static String APK_URL = "APK_URL";
        public final static String APK_DESCRIBE = "APK_DESCRIBE";
    }


}
