package com.info.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Data
@TableName("order_info_detail")
public class OrderInfoDetailEntity extends BaseEntity {


    /**
     * 订单详表ID主键
     */
    @TableId
    private Integer itemId;
    /**
     * 订单主键ID编号
     */
    private Integer orderId;
    /**
     * 商品ID主键
     */
    private Integer proId;
    /**
     * 优惠后的单价 就是销售价格
     */
    private BigDecimal proPrice;
    /**
     * 商品数量
     */
    private Integer numbers;
    /**
     * SKU_ID 如果SKUID=0的话则表示没有sku单元，否则表示有库存单元
     */
    private Integer skuId;
    /**
     * 商品重量
     */
    private String weight;
    /**
     * 购买方式 1 表示正常 2 表示团购 3 表示换购商品 4 表示抢购 5 表示赠品
     */
    private Integer buyType;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     *
     */
    private Integer editor;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 是否删除 0 已删除 1 正常
     */
    private Integer isDel;

}
