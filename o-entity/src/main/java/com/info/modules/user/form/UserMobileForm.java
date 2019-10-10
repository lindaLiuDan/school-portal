package com.info.modules.user.form;

import io.swagger.annotations.ApiModel;

/**
 * @Author: liudan
 * @Description:
 * @Email: 854161062@qq.com
 * @Create: 2019/3/20 16:14
 */
@ApiModel("获取用户电话")
public class UserMobileForm {

    private Integer getMobileId;

    private Integer userId;

    private String mobile;

    private String userName;

    public Integer getGetMobileId() {
        return getMobileId;
    }

    public void setGetMobileId(Integer getMobileId) {
        this.getMobileId = getMobileId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
