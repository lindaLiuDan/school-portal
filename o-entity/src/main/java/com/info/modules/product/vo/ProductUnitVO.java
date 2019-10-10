package com.info.modules.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
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
public class ProductUnitVO implements Serializable {


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

}
