package com.info.modules.product.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 商品图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
@Data
@TableName("product_img")
public class ProductImgVO implements Serializable {


    /**
     * 商品图片ID主键
     */
    @TableId
    private Integer imgId;
    /**
     * 商品主键
     */
    private Integer productId;
    /**
     * 商品图片URL
     */
    private String img;
    /**
     * 商品图片缩略图
     */
    private String smallImg;

}
