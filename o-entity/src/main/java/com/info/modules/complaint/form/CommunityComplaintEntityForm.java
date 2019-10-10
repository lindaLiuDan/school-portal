package com.info.modules.complaint.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 社区投诉建议信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 14:31:58
 */
@Data
public class CommunityComplaintEntityForm extends BaseEntity {


    /**
     * 社区投诉建议ID主键
     */
    @TableId
    private Integer complaintId;
    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区小区ID不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 用户ID主键
     */
    @NotNull(message = "用户ID不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 投诉内容
     */
    @NotBlank(message = "投诉内容不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String content;
    /**
     * 联系电话
     */
    private String mobile;
    /**
     * 是否处理 0 未处理 1 已处理
     */
    private Integer isProcess;
    /**
     * 处理人
     */
    private Integer processMan;
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
    /**
     *
     */
    private Integer editor;

}
