package com.info.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单日志信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Data
@TableName("order_info_log")
public class OrderInfoLogEntity extends BaseEntity {


    /**
     * 订单信息日志表
     */
    @TableId
    private Integer logId;
    /**
     * 订单主键ID编号
     */
    @NotNull( message = "订单ID不能为空",groups = {AddGroup.class})
    private Integer orderId;
    /**
     * 订单流程跟踪表ID主键
     */
    private Integer flowId;
    /**
     * 订单编号
     */
    @NotBlank( message = "订单编号不能为空",groups = {AddGroup.class})
    private String orderNo;
    /**
     * 订单金额
     */
//    @NotNull( message = "订单金额不能为空",groups = {AddGroup.class})
    private BigDecimal totalMoney;
    /**
     * 操作描述
     */
    private String memo;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 创建者
     */
    private Integer creator;
    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;

}
