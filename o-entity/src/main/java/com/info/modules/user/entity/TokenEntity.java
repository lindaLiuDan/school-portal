package com.info.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: 用户Token
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/8 14:21
 * @Return:
 */
@Data
@TableName("user_info_token")
public class TokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.INPUT)
    private Long userId;
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
