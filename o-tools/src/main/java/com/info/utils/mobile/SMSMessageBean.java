package com.info.utils.mobile;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 发送短信信息存储
 * -------------------------------------------------------------- <br>
 * V1.0 Created by 赵成贵 on 2018/4/28 19:58
 * -------------------------------------------------------------- <br>
 */
public class SMSMessageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 国际区号
     */
    private String areaCode;
    /**
     * 电话号码
     */
    private String mobile;
    /**
     * 模板id
     */
    private int templId;

    /**
     * 签名
     */
    private String sign;

    /**
     * 模板参数
     */
    private ArrayList<String> params = new ArrayList<>(0);

    public SMSMessageBean() {
    }

    public SMSMessageBean(String areaCode, String mobile, int templId, ArrayList<String> params, String sign) {
        this.areaCode = areaCode;
        this.mobile = mobile;
        this.templId = templId;
        this.params = params;
        this.sign = sign;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getTemplId() {
        return templId;
    }

    public void setTemplId(int templId) {
        this.templId = templId;
    }

    public ArrayList<String> getParams() {
        return params;
    }

    public void setParams(ArrayList<String> params) {
        this.params = params;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "SMSMessageBean{" +
                "areaCode='" + areaCode + '\'' +
                ", mobile='" + mobile + '\'' +
                ", templId=" + templId +
                ", params=" + params +
                ", sign=" + sign +
                '}';
    }
}
