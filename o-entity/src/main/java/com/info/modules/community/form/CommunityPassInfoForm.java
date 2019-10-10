package com.info.modules.community.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommunityPassInfoForm {

    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    @NotNull(message = "所在小区不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;


    @NotNull(message = "业主id不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;

    @NotNull(message = "楼号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer buildId;

    @NotBlank(message = "时间戳不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String times;

    @NotBlank(message = "秘钥不允许为空", groups = {AddGroup.class, UpdateGroup.class})
    private String md;
}
