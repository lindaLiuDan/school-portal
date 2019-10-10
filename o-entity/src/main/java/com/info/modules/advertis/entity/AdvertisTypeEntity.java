package com.info.modules.advertis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区广告类型信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@Data
@TableName("advertis_type")
public class AdvertisTypeEntity extends BaseEntity {


    /**
     * 广告类型ID主键
     */
    @TableId
    private Integer adId;
    /**
     * 广告图片类别名称
     */
    private String adName;
    /**
     * 说明
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
    private Integer editor;
    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;

}
