package com.info.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.info.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
@Data
@TableName("product_info")
public class ProductInfoEntity extends BaseEntity {


    /**
     * 商品主键
     */
    @TableId
    private Integer productId;
    /**
     * 商品单位信息ID主键
     */
    private Integer unitId;
    /**
     * 商家信息ID主键
     */
    private Integer providerId;
    /**
     * 商品类别信息ID主键
     */
    private Integer cateId;
    /**
     * 商品编号
     */
    private String productNo;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品库存数量
     */
    private Integer stock;
    /**
     * 商品品类(显示无限级分类:1,101,10101)
     */
    private String cateIds;
    /**
     * 商品主图片
     */
    private String img;
    /**
     * 商品主图缩略图
     */
    private String smallImg;
    /**
     * 销售价格
     */
    private BigDecimal salesPrice;
    /**
     * 供货价<进价>
     */
    private BigDecimal tradePrice;
    /**
     * 市场价
     */
    private BigDecimal marketPrice;
    /**
     * 点击数
     */
    private Integer clickNum;
    /**
     * 是否允许积分换购 0 不允许(现金购买) 1 允许(积分换购) 2 积分加现金购买 默认都是不允许
     */
    private Integer isIntegral;
    /**
     * 换购该商品时候所需要的积分数
     */
    private Integer integral;
    /**
     * 正常购买该商品送的积分数 默认是送0积分
     */
    private Integer scoreIntegral;
    /**
     * 积分加现金购买时需要的金额
     */
    private BigDecimal moneyIntegral;
    /**
     * 重量
     */
    private String weight;
    /**
     * 联营/自营：1--自营；2--联营 默认所有的商品都是自营自买卖
     */
    private Integer busModel;
    /**
     * 商品简介
     */
    private String brief;
    /**
     * 物品清单
     */
    private String lists;
    /**
     * 商品详情
     */
    private String detail;
    /**
     * 售后服务
     */
    private String afterSale;
    /**
     * 商品状态  1 下架的 2 未销售  3 在售
     */
    private Integer status;
    /**
     * 上架时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date onlineTime;
    /**
     * 发布人ID
     */
    private Integer creator;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 销售数量
     */
    private Integer saledNum;
    /**
     * 排序   值越大越靠前 默认0
     */
    private Integer sort;
    /**
     * 删除标记 0 删除  1 正常
     */
    private Integer isDel;
    /**
     * meta关键词 作为SEO优化使用
     */
    private String meta;
    /**
     * 产地
     */
    private String world;
    /**
     * 备注
     */
    private String memo;

}
