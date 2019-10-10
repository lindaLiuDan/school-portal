package com.info.modules.user.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 会员业主签到信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@Data
@TableName("user_sign")
public class UserSignEntity extends BaseEntity {


    /**
     * 签到ID主键
     */
    @TableId
    private Integer signId;
    /**
     * 会员ID主键
     */
    @NotNull(message = "会员ID类型不能为空", groups = {AddGroup.class})
    private Integer userId;
    /**
     * 赠送积分数,金币数
     */
    @NotNull(message = "赠送积分不能为空", groups = {AddGroup.class})
    private Integer integral;
    /**
     * 创建时间（签到时间）
     */
    @NotNull(message = "签到时间不能为空", groups = {AddGroup.class})
//    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;

}
