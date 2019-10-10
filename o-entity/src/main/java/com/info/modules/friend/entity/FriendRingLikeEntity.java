package com.info.modules.friend.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 社区朋友圈点赞表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:24
 */
@Data
@TableName("friend_ring_like")
public class FriendRingLikeEntity extends BaseEntity {


    /**
     * 社区朋友圈点赞ID主键
     */
    @TableId
    private Integer likeId;
    /**
     * 社区朋友圈ID主键
     */
    @NotNull(message = "朋友圈ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer ringId;
    /**
     * 点赞人
     */
    @NotNull(message = "点赞人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 创建时间
     */
    private Date creatorTime;

}
