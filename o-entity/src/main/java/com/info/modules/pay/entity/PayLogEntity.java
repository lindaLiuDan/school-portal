package com.info.modules.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付日志信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-09-08 13:57:51
 */
@Data
@TableName("pay_log")
public class PayLogEntity extends BaseEntity {


    /**
     * 支付日志ID主键
     */
    @TableId
    private Integer logId;
    /**
     * 类型ID主键
     */
    private Integer typeId;
    /**
     * 用户ID主键
     */
    private Integer userId;
    /**
     * 订单NO
     */
    private String orderNo;
    /**
     * 订单ID主键
     */
    private Integer orderId;
    /**
     * 金额
     */
    private BigDecimal money;
    /**
     * 第三方支付流水号
     */
    private String payNumber;
    /**
     * 创建时间
     */
    private Date creatorTime;
    /**
     * 创建人
     */
    private Integer creator;


}
