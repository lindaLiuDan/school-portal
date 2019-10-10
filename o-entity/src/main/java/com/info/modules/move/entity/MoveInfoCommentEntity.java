package com.info.modules.move.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区活动留言表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Data
@TableName("move_info_comment")
public class MoveInfoCommentEntity extends BaseEntity {


    /**
     * 社区活动留言表ID主键
     */
    @TableId
    private Integer commentId;
    /**
     * 社区活动ID主键
     */
    private Integer moveId;
    /**
     * 会员ID主键
     */
    private Integer userId;
    /**
     * 留言内容
     */
    private String comment;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 排序数
     */
    private Integer sort;


    @TableField(exist = false)
    private String userName;

    @TableField(exist = false)
    private String img;


}
