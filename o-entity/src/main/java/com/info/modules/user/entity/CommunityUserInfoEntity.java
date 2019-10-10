package com.info.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-11 19:01:05
 */
@Data
@TableName("community_user_info")
public class CommunityUserInfoEntity extends BaseEntity {


    /**
     * 用户收藏ID主键
     */
    @TableId
    private Integer userId;
    /**
     * 所在社区ID主键
     */
    private Integer infoId;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 登录密码
     */
    private String pwd;
    /**
     * 添加人（管理员ID主键）
     */
    private Integer creator;
    /**
     * 创建时间
     */
    private Date creatorTime;
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

}
