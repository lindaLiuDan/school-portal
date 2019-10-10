package com.info.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商品单位信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Data
@TableName("product_unit")
public class ProductUnitEntity extends BaseEntity {


    /**
     * 商品单位信息ID主键
     */
    @TableId
    private Integer unitId;
    /**
     * 商品单位名称
     */
    private String unitName;
    /**
     * 图标
     */
    private String img;
    /**
     * 图标缩略图
     */
    private String smallImg;
    /**
     *
     */
    private Integer creator;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTme;
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
     * 删除标记 0 删除  1 正常
     */
    private Integer isDel;
    /**
     * 排序数 默认 0
     */
    private Integer sort;

}
