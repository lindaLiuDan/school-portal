package com.info.modules.user.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: liu
 * @Description:
 * @Date: 2019/1/4 10:21
 */
@Data
@ApiModel("用户重置密码")
public class UserUpdatePwdForm {

    @ApiModelProperty("用户旧密码")
    @NotBlank(message = "旧密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String oldPwd;

    @ApiModelProperty("用户新密码")
    @NotBlank(message = "新密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String pwd;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;

}
