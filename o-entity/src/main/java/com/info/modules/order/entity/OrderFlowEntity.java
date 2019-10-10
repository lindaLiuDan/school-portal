package com.info.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 订单流程跟踪表:已支付，未支付，配送中等
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Data
@TableName("order_flow")
public class OrderFlowEntity extends BaseEntity {


    /**
     * 订单流程跟踪表ID主键
     */
    @TableId
    private Integer flowId;
    /**
     * 流程类型编号
     */
    private String typeNum;
    /**
     * 订单流程名称
     */
    private String flowName;
    /**
     * 详细描述
     */
    private String detail;
    /**
     * 创建日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 修改日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date edtiorTime;
    /**
     * 修改人
     */
    private Integer edtior;
    /**
     * 是否可删除 0 已删除 1 正常
     */
    private Integer isDel;
    /**
     * 排序值
     */
    private Integer sort;

}
