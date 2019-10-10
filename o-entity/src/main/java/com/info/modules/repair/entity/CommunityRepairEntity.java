package com.info.modules.repair.entity;

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
 * 社区物业报修表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:06
 */
@Data
@TableName("community_repair")
public class CommunityRepairEntity extends BaseEntity {


    /**
     * 社区物业保修ID主键
     */
    @TableId
    private Integer repairId;
    /**
     * 社区报修类型ID主键
     */
    @NotNull(message = "社区报修类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer typeId;
    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 会员ID主键
     */
    private Integer userId;
    /**
     * 联系人
     */
    @NotNull(message = "联系人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String contact;
    /**
     * 联系人电话
     */
    @NotNull(message = "社区报修类型不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 社区楼房信息ID主键
     */
    @NotNull(message = "楼号和单元号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String buildId;
    /**
     * 楼层和房间号
     */
    @NotNull(message = "房间号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String room;
    /**
     * 报修内容
     */
    @NotNull(message = "报修内容不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String content;
    /**
     * 报修图片
     */
    private String img;
    /**
     * 报修图片缩略图
     */
    private String smallImg;
    /**
     * 处理结果
     */
    private Integer processResult;
    /**
     * 处理满意度
     */
    private Integer satisfaction;
    /**
     * 处理时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date processTime;
    /**
     * 处理人
     */
    private String processMan;
    /**
     * 处理方法
     */
    private String process;
    /**
     * 备注
     */
    private String memo;
    /**
     *
     */
    private Integer editor;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 删除状态 0 已删除 1 未删除
     */
    private Integer isDel;
    /**
     * 排序 数字越大越靠前
     */
    private Integer sort;
    /**
     * 报修类型内容
     */
    @TableField(exist = false)
    private String info;
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
