package com.info.modules.friend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区朋友圈图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Data
@TableName("friend_ring_img")
public class FriendRingImgEntity extends BaseEntity {


    /**
     * 图片ID主键
     */
    @TableId
    private Integer imgId;
    /**
     * 社区朋友圈ID主键
     */
    private Integer ringId;
    /**
     * 图片img
     */
    private String img;
    /**
     * 图片缩略图
     */
    private String smallImg;
    /**
     * 创建时间
     */
    private Date creatorTime;


}
