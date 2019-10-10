package com.info.modules.community.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
public class CommunityReleaseForm  implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区小区不能空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 会员业主信息ID主键
     */
    @NotNull(message = "userId不能空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 业主会员姓名
     */
    @NotBlank(message = "需要放行的人的姓名不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;
    /**
     * 业主身份证号码
     */
    @NotBlank(message = "需要放行的人的身份证不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String card;

    /**
     * 放行开始时间
     */
    @NotNull(message = "放行开始时间不能空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 放行结束时间
     */
    @NotNull(message = "结束时间不能空", groups = {AddGroup.class, UpdateGroup.class})
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;


    @NotBlank(message = "电话号码不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    /**
     * 楼层和房间号
     */
    @NotNull(message = "房间号不能空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer buildId;

    //申请放行单原因
    @NotBlank(message = "申请放行单原因时间戳不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String slipCause;

    @NotBlank(message = "时间戳不能空", groups = {AddGroup.class, UpdateGroup.class})
    private String times;

    @NotBlank(message = "秘钥不允许为空", groups = {AddGroup.class, UpdateGroup.class})
    private String md;


}
