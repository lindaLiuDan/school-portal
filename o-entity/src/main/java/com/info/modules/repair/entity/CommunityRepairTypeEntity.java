package com.info.modules.repair.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区物业报修类型信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:07
 */
@Data
@TableName("community_repair_type")
public class CommunityRepairTypeEntity extends BaseEntity {


    /**
     * 社区报修类型ID主键
     */
    @TableId
    private Integer typeId;
    /**
     * 报修类型内容
     */
    private String info;
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
    private Integer creator;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     *
     */
    private Integer editor;
    /**
     * 删除状态 0 已删除 1 未删除
     */
    private Integer isDel;
    /**
     * 排序 数字越大越靠前
     */
    private Integer sort;

}
