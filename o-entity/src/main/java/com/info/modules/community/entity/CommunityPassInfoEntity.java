package com.info.modules.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区访客通行证信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 14:38:33
 */
@Data
@TableName("community_pass_info")
public class CommunityPassInfoEntity extends BaseEntity {


    /**
     * 访客通行证ID主键
     */
    @TableId
    private Integer passId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 会员业主ID主键
     */
    private Integer userId;

    //楼号id
    private Integer buildId;
    /**
     * 访客手机号码
     */
    private String mobile;
    /**
     * 访客收到的验证码
     */
    private Integer checkCode;
    /**
     * 通行状态 0 未通行 1 已通行
     */
    private Integer status;
    /**
     * 备注
     */
    private String memo;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;

    //过期时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date expireTime;

    /**
     *
     */
    private Integer editor;


}
