package com.info.modules.user.form;

import lombok.Data;

@Data
public class UserBaseForm {

    private Integer userId;

    private String userName;

    private String img;

    //房屋以及屋主认证 0 未认证 1 认证中 2 认证失败 3 认证成功
    private Integer isAuth;
}
