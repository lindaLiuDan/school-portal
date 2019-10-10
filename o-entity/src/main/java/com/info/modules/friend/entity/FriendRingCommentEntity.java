package com.info.modules.friend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.modules.friend.vo.FriendRingCommentVo;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 社区朋友圈评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:25
 */
@Data
@TableName("friend_ring_comment")
public class FriendRingCommentEntity extends BaseEntity {


    /**
     * 社区朋友圈评论ID主键
     */
    @TableId
    private Integer commentId;
    /**
     * 社区朋友圈ID主键
     */
    @NotNull(message = "朋友圈ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer ringId;
    /**
     * 评论内容
     */
    @NotNull(message = "评论内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String content;
    /**
     * 评论人
     */
    @NotNull(message = "评论人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 上级父类ID
     */
    @NotNull(message = "上级父类ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer parentId;

    //评价人姓名
    @TableField(exist = false)
    private String userName;


    //评价人头像
    @TableField(exist = false)
    private String img;


    //是否还有评论
    @TableField(exist = false)
    private Integer more;


    //回复信息
    @TableField(exist = false)
    private List<FriendRingCommentVo> newsList;
}
