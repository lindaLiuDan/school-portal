package com.info.modules.idle.form;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 闲置交易评论表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@Data
@TableName("idle_info_comment")
public class IdleInfoCommentSaveForm extends BaseEntity {

    /**
     * 闲置信息ID主键
     */
    @NotNull(message = "闲置信息ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer leId;
    /**
     * 会员名称
     */
    @NotNull(message = "会员ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 评论内容
     */
    @NotBlank(message = "评论内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String comment;
    /**
     * 父类ID
     */
    private Integer parentId;
    /**
     * 评论与回复时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;


}
