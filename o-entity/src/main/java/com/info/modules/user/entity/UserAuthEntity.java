package com.info.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import java.util.Date;
import javax.validation.constraints.NotNull;

/**
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @date 2019-06-13 14:47:56
 * @email 960889426@qq.com
 * @date 2019-06-14 14:03:54
 */
@Data
@TableName("user_auth")
public class UserAuthEntity extends BaseEntity {


    /**
     * 社区小区身份认证ID主键
     */
    @TableId
    @NotNull(message = "认证ID不能为空",groups = {UpdateGroup.class})
    private Integer authId;
    /**
     * 会员ID主键
     */
    @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 社区小区楼层ID主键
     */
    @NotNull(message = "ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer buildId;
    /**
     * 业主身份类型 1 业主 2 租客 3 业主亲戚 4 其他
     */
    @NotNull(message = "业主身份不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer identity;
    /**
     * 业主姓名
     */
    @NotNull(message = "业主姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String realName;
    /**
     * 业主身份证号码
     */
    @NotNull(message = "业主身份身份证号码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String idCard;
    /**
     * 会员所在城市
     */
    @TableField(exist=false)
    private Integer aId;
    /**
     * 房屋以及屋主认证 0 未认证 1 认证中 2 认证失败 3 认证成功
     */
    private Integer isAuth;
    /**
     * 会员房屋结构图
     */
    private String houseStructure;
    /**
     * 二维码图片的路径
     */
    private String qrCode;
    /**
     * 认证时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 认证修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 删除状态 0 已删除 1 未删除
     */
    private Integer isDel;

}
