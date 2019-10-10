package com.info.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商品评论信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
@Data
@TableName("product_comment")
public class ProductCommentEntity extends BaseEntity {


    /**
     * 商品评论ID主键
     */
    @TableId
    private Integer commId;
    /**
     * 商品主键
     */
    private Integer productId;
    /**
     * 评论用户ID主键
     */
    private Integer userId;
    /**
     * 评论时间 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 评论标题
     */
    private String title;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论标签 存储的是product_comment_tag 的ID（1,3,4）使用逗号分隔开
     */
    private String tagType;
    /**
     * 是否显示 0不显示 1显示
     */
    private Integer isShow;
    /**
     * 评论星级（1：差评，2,3：中评，4,5：好评）
     */
    private Integer levels;
    /**
     * 排序 数字越大越靠前
     */
    private Integer sort;
    /**
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;

}
