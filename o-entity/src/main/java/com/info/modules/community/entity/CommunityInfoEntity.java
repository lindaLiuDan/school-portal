package com.info.modules.community.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区小区信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@Data
@TableName("community_info")
public class CommunityInfoEntity extends BaseEntity {


    /**
     * 社区小区ID主键
     */
    @TableId
    private Integer infoId;
    /**
     * 社区编号
     */
    private String infoNo;
    /**
     * 社区小区名称
     */
    private String infoName;
    /**
     * 社区小区简介
     */
    private String introduction;
    /**
     * 社区物业电话
     */
    private String mobile;
    /**
     * 社区所在城市
     */
    private Integer openId;
    /**
     * 维度
     */
    private String dimension;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 社区备注
     */
    private String memo;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
    /**
     * 排序数
     */
    private Integer sort;
    /**
     * 删除标志 0 已删除 1 正常
     */
    private Integer isDel;

}
