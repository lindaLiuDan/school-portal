package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: 系统用户Token
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:28
 * @Return:
 */
@Data
@TableName("sys_user_token")
public class SysUserTokenEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //用户ID
    @TableId(type = IdType.INPUT)
    private Long userId;
    //token
    private String token;
    //过期时间
    private Date expireTime;
    //更新时间
    private Date updateTime;

}
