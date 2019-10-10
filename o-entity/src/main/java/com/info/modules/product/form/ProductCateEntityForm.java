package com.info.modules.product.form;

import com.baomidou.mybatisplus.annotation.TableId;
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
public class ProductCateEntityForm extends BaseEntity {


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
     */
    private Integer creator;
    /**
     *
     */
    private Date creatorTime;
    /**
     *
     */
    private Integer editor;
    /**
     *
     */
    private Date editorTime;


}
