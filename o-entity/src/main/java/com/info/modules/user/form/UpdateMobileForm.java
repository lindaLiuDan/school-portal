package com.info.modules.user.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * 修改登录密码表单
 *
 * @author Gaosx 960889426@qq.com
 * @since 3.1.0 2019-02-25
 */
@Data
@ApiModel(value = "修改登录密码表单")
public class UpdateMobileForm {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String code;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "新密码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String newPwd;

}
