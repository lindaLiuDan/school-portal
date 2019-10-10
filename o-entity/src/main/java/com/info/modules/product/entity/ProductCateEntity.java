package com.info.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Data
@TableName("product_cate")
public class ProductCateEntity extends BaseEntity {


    /**
     * 商品类别信息ID主键
     */
    @TableId
    private Integer cateId;
    /**
     * 商品类别名称
     */
    private String cateName;
    /**
     * 父类编号
     */
    private Integer parentId;
    /**
     * 类别图片
     */
    private String img;
    /**
     * 类别图片缩略图
     */
    private String smallImg;
    /**
     * 是否热门推荐品类 0 否 1 是
     */
    private Integer isHot;
    /**
     *
     */
    private Integer creator;
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
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;
    /**
     * 排序数两位数
     */
    private Integer sort;

}
