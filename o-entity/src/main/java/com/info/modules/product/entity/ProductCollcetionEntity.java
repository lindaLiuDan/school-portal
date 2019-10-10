package com.info.modules.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商品收藏信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Data
@TableName("product_collcetion")
public class ProductCollcetionEntity extends BaseEntity {


    /**
     * 用户收藏ID主键
     */
    @TableId
    private Integer collId;
    /**
     * 会员ID主键
     */
    private Integer userId;
    /**
     * 收藏关注类型 1 手机APP，2 微信端，3 手机邮箱, 4 PC端
     */
    private Integer type;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;

}
