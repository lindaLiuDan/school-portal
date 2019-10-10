package com.info.modules.product.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Data
@TableName("product_cate")
public class ProductCateVO implements Serializable {


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
     *
     * */
    @TableField(exist = false)
    private List<ProductCateVO> productCateVOList;

}
