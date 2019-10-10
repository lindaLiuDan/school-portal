package com.info.modules.advertis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区小区广告信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@Data
@TableName("advertis_info")
public class AdvertisInfoEntity extends BaseEntity {


    /**
     * 社区广告信息ID主键
     */
    @TableId
    private Integer advertisId;
    /**
     * 社区广告类型ID主键
     */
    private Integer adId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 广告编号
     */
    private String infoNo;
    /**
     * 广告连接
     */
    private String link;
    /**
     * 广告名称
     */
    private String infoName;
    /**
     * 广告图片
     */
    private String img;
    /**
     * 广告图片 缩略图
     */
    private String smallImg;
    /**
     * 长
     */
    private String width;
    /**
     * 宽
     */
    private String height;
    /**
     * 状态 0 禁用 1 正常使用
     */
    private Integer statues;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 所属终端 0 pc端 1 移动端
     */
    private Integer isType;
    /**
     * 是否删除 0 已删除的 1 正常的
     */
    private Integer isDel;
    /**
     * 排序 默认 0
     */
    private Integer sort;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;

}
