package com.info.modules.provider.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Data
@TableName("provider_info")
public class ProviderInfoEntity extends BaseEntity {


    /**
     * 商家信息ID主键
     */
    @TableId
    private Integer providerId;
    /**
     * 所属社区小区ID主键
     */
    private Integer community;
    /**
     * 店铺等级ID主键
     */
    private Integer gradeId;
    /**
     * 登陆注册账号名称
     */
    private Integer mobile;
    /**
     * 登录注册密码
     */
    private String pwd;
    /**
     * 店铺等级信息名称
     */
    private String gname;
    /**
     * 商家分类信息ID主键
     */
    @TableField(exist = false)
    private Integer typeId;
    /**
     * 商家分类信息名称
     */
    private String typeName;
    /**
     * 商家名称
     */
    private String providerName;
    /**
     * 商家编号
     */
    private String providerNo;
    /**
     * 店铺营业状态 0 下架中 1 正常营业 2 打烊 3 暂停营业
     */
    private Integer status;
    /**
     * 商家店铺LOGO
     */
    private String logo;
    /**
     * 店铺LOGO缩略图
     */
    private String smallLogo;
    /**
     * 店铺简介
     */
    private String memo;
    /**
     * 经营范围
     */
    private String manageExtent;
    /**
     * 经营品牌
     */
    private String manageBrand;
    /**
     * 经营类别
     */
    private String manageCate;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     *
     */
    private Integer editor;
    /**
     * 是否删除 0 删除 1 未删除
     */
    private Integer isDel;
    /**
     * 排序数 默认 0 越打越靠前
     */
    private Integer sort;

    //商家余额
    private BigDecimal balence;

    private String userName;

    private String img;

    private String smallImg;

}
