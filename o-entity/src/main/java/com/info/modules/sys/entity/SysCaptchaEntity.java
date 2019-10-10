package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 功能描述: 系统验证码
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:38
 * @Return:
 */
@Data
@TableName("sys_captcha")
public class SysCaptchaEntity {

    @TableId(type = IdType.AUTO)
    private Integer uId;

    private String uuid;
    /**
     * 验证码
     */
    private String code;
    /**
     * 过期时间
     */
    private Date expireTime;


}
