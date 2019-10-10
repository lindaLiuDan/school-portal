package com.info.modules.provider.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
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
@JsonIgnoreProperties(value = {"creatorTime", "editorTime", "gname"})
public class ProviderInfoEntityVO implements Serializable {


    /**
     * 商家信息ID主键
     */
    @TableId
    private Integer providerId;
    /**
     * 所属社区小区ID主键
     */
    @NotNull(message = "社区ID不能为空", groups = {AddGroup.class})
    private Integer infoId;
    /**
     * 店铺等级ID主键
     */
    private Integer gradeId;
    /**
     * 店铺等级信息名称
     */
    @TableField(exist = false)
    private String gname;
    /**
     * 商家分类信息ID主键
     */
    @TableField(exist = false)
    private Integer typeId;
    /**
     * 商家分类信息名称
     */
    @TableField(exist = false)
    private String typeName;
    /**
     * 商家名称
     */
    @NotNull(message = "商家名称不能为空", groups = {AddGroup.class, UpdateGroup.class})
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
    @NotNull(message = "商家店铺LOGO不能为空", groups = {AddGroup.class, UpdateGroup.class})
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
     * 维度
     */
    private String dimension;
    /**
     * 起送价
     */
    private String startPrice;
    /**
     * 经营范围
     */
    private String manageExtent;
    /**
     * 配送费
     */
    private String shippFee;
    /**
     * 经度
     */
    private String longitude;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;


    private String mobile;

    //是否删除
    private Integer isDel;

    //密码
    private String pwd;

    //商家余额
    private BigDecimal balence;

    //商家名称
    private String userName;

    //商家头像
    private String img;

    private String smallImg;
}
