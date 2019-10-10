package com.info.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商品评论标签表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
@Data
@TableName("product_comment_tag")
public class ProductCommentTagEntity extends BaseEntity {


    /**
     * 商品评论标签ID主键
     */
    @TableId
    private Integer tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 创建人
     */
    private Integer creator;
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
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;

}
