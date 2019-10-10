package com.info.modules.idle.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 闲置信息图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@Data
@TableName("idle_info_img")
public class IdleInfoImgEntity extends BaseEntity{


    /**
     * 闲置信息图片ID主键
     */
    @TableId
    private Integer imgId;
    /**
     * 闲置信息ID主键
     */
    private Integer leId;
    /**
     * 图片img
     */
    private String img;
    /**
     * 图片缩略图
     */
    private String smallImg;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;


}
