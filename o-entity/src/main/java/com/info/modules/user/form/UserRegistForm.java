package com.info.modules.user.form;

import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/21 15:33
 * @Return:
 */
@Data
public class UserRegistForm extends BaseEntity {

    //电话
    @NotNull(message = "电话不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    //验证码
    @NotNull(message = "验证码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String checkCode;

}
