package com.info.modules.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 购物车信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:05
 */
@Data
@TableName("order_cart")
public class OrderCartEntity extends BaseEntity {


    /**
     * 购物车ID主键
     */
    @TableId
    private Integer cartId;
    /**
     * 会员ID
     */
    private Integer userId;
    /**
     * 商品主键ID
     */
    private Integer productId;
    /**
     * 商品no编号
     */
    private String productNo;
    /**
     * 商品数量
     */
    private Integer numbers;
    /**
     * 商品加入购物车的价格
     */
    private BigDecimal price;
    /**
     * 商品加入购物车时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;

}
