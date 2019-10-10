package com.info.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 物业用户Token
 *
 * @author Gaosx
 * @email
 * @date 2019-07-13 10:58:06
 */
@Data
@TableName("community_user_token")
public class CommunityUserTokenEntity extends BaseEntity {


    /**
     * 物业ID主键非自增
     */
    @TableId(type = IdType.INPUT)
    private Integer communityUserId;
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
