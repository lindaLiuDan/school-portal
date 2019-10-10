package com.info.modules.lease.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 社区租赁信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
@Data
@TableName("community_lease_info")
public class CommunityLeaseInfoEntity extends BaseEntity {


    /**
     * 社区租赁信息ID组建
     */
    @TableId
    private Integer leaseId;
    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 业主会员ID主键
     */
    @NotNull(message = "业主ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 业主楼号单元号(对应主表主键)
     */
    @NotNull(message = "业主楼号单元号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer buildId;
    /**
     * 业主楼层房号
     */
    @NotNull(message = "业主楼层房号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String room;
    /**
     * 业主姓名
     */
    @NotNull(message = "业主姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String userName;
    /**
     * 业主身份证号码
     */
    @NotNull(message = "业主身份证号码不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String idCard;
    /**
     * 租赁开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 租赁结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 是否归还 0 未归还 1 已归还 2 租界中 3 已逾期
     */
    private Integer isReturn;
    /**
     * 租赁物品清单
     */
    @NotNull(message = "租赁物品清单不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String listItems;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;

    /**
     * 社区小区名称
     */
    @TableField(exist = false)
    private String infoName;
    /**
     * 楼号
     */
    @TableField(exist = false)
    private String floor;
    /**
     * 单元号
     */
    @TableField(exist = false)
    private String unit;
    /**
     * 楼层号
     */
    @TableField(exist = false)
    private String level;

}
