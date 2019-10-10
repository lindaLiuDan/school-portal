package com.info.modules.order.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Data
public class UpdateOrderInfoForm extends BaseEntity {


    /**
     * 订单流程跟踪表ID主键
     */
    @NotNull(message = "订单状态ID不能为空", groups = {UpdateGroup.class})
    private Integer flowId;
    /**
     * 收货地址ID主键
     */
    @NotNull(message = "收货地址ID不能为空", groups = {UpdateGroup.class})
    private Integer addressId;
    /**
     * 订单ID主键
     */
    @NotNull(message = "订单ID不能为空", groups = {UpdateGroup.class})
    private Integer orderId;
    /**
     * 订单NO编号
     */
    private String orderNo;
    /**
     * 订单备注信息
     */
    private String memo;
    /**
     * 订单支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date payTime;
    /**
     * 订单服务时间--针对的是服务这块的订单
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date serviceTime;

}
