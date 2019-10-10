package com.info.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 消息通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-18 11:40:10
 */
@Data
@TableName("message_info_type")
public class MessageInfoTypeEntity extends BaseEntity {


    /**
     * 消息推送通知类型ID主键
     */
    @TableId
    private Integer typeId;
    /**
     * 消息推送通知类型名称
     */
    private String tname;
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

}
