package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 验证码信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-13 21:50:17
 */
@Data
@TableName("sys_code")
public class CodeEntity extends BaseEntity {


    /**
     * ID主键
     */
    @TableId
    private Integer codeId;
    /**
     * 验证码key
     */
    private String mobile;
    /**
     * 验证码value
     */
    private String mobileCode;
    /**
     * 过期时间
     */
    private Date expireTime;
    /**
     * 创建时间
     */
    private Date createTime;

    //用户id
    private Integer userId;

    //类型 1 表示注册  2 表示修改绑定手机号码  3 发送访客通行码  4 申请电子放行单号  5 新手机号 6 原手机号
    private Integer isType;

}
