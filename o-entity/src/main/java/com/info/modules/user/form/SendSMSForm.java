package com.info.modules.user.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 发送短信的表单
 *
 * @author： Gaosx 960889426@qq.com
 * @since： 1.0.0 2019-01-25
 */
@Data
@ApiModel(value = "注册表单")
public class SendSMSForm implements Serializable {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    @ApiModelProperty(value = "时间戳")
    @NotBlank(message = "时间戳不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String times;

    @ApiModelProperty(value = "加密串")
    @NotBlank(message = "秘钥不允许为空", groups = {AddGroup.class, UpdateGroup.class})
    private String md;

    /**
     * 1 表示注册  2 表示修改绑定手机号码  3 发送访客通行码  4 申请电子放行单号  5 新手机号 6 原手机号
     */
    @ApiModelProperty(value = "类型")
    @NotNull(message = "类型不允许为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer typeId;

    //只有申请电子放行单的时候需要传递这两个参数  开始时间
    private Date startTime;

    //只有申请电子放行单的时候需要传递这两个参数  结束时间
    private Date endTime;

    //用户id
    private Integer userId;


}
