package com.info.modules.area.form;

import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 添加收货地址表单
 *
 * @author 960889426 960889426@qq.com
 * @since 3.1.0 2018-10-14
 */
@Data
@ApiModel(value = "添加收货地址表单")
public class AddAddressForm {

    @ApiModelProperty(value = "手机号")
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    @ApiModelProperty(value = "收货人")
    @NotBlank(message = "收货人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String consignee;

    @ApiModelProperty(value = "省市县区编号")
    @NotBlank(message = "省市县区编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String areaId;

    @ApiModelProperty(value = "收货地址详情")
    @NotBlank(message = "收货地址详情不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String address;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;

    @ApiModelProperty(value = "是否默认收货地址")
    private Integer isDef;


}
