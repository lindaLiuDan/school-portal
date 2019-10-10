package com.info.modules.friend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 社区朋友圈信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:30
 */
@Data
@TableName("friend_ring")
public class FriendRingEntity extends BaseEntity {


    /**
     * 社区朋友圈ID主键
     */
    @TableId
    private Integer ringId;
    /**
     * 所在社区小区ID主键
     */
    private Integer infoId;
    /**
     * 发布人
     */
    private Integer userId;
    /**
     * 发布内容
     */
    private String content;
    /**
     * 发布时间
     */
    private Date creatorTime;
    /**
     * 点赞数
     */
    private Integer likeNum;
    /**
     * 评论数
     */
    private Integer commentNum;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 修改时间
     */
    private Date editorTime;
    /**
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;
    /**
     * 排序数 数字越大越靠前
     */
    private Integer sort;


    //发布人姓名
    @TableField(exist = false)
    private String userName;

    //发布人头像
    @TableField(exist = false)
    private String img;

    @TableField(exist = false)
    private List<FriendRingImgEntity> friendRingImgEntities=new ArrayList<FriendRingImgEntity>();
}
