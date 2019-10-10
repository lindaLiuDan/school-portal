package com.info.modules.provider.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户Token
 *
 * @author Gaosx
 * @email
 * @date 2019-07-13 10:58:06
 */
@Data
@TableName("provider_info_token")
public class ProviderInfoTokenEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 商家ID主键非自增
     */
    @TableId(type = IdType.INPUT)
    private Integer providerId;
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
