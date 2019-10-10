package com.info.pay.utils;

public class WeixinUrl {
    // 获取access_token的接口地址（GET） 限200（次/天）
    public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    //发送模板消息
    public static String send_template_url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

    public static String jscode_session = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
    //预支付交易单,获取prepay_id
    public static String pay_unifiedorder = "https://api.mch.weixin.qq.com/pay/unifiedorder";
}
