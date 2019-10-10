package com.info.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 消息通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-18 12:06:03
 */
@Data
@TableName("message_info")
public class MessageInfoEntity extends BaseEntity {


    /**
     * 消息推送通知ID主键
     */
    @TableId
    private Integer contentId;
    /**
     * 消息推送通知类型ID主键
     */
    private Integer typeId;
    /**
     * 消息标题
     */
    private String title;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 推送者 0代表系统推送
     */
    private Integer accountId;
    /**
     * 接受者
     */
    private Integer userId;
    /**
     * 推送时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 是否已读 0 未读 1 已读
     */
    private Integer isRead;
    /**
     * 是否删除 0 已删除 1 未删除
     */
    private Integer isDel;
    /**
     * 是否删除 0 不是 1 是的
     */
    private Integer isAll;
    /**
     * 社区ID主键
     */
    private Integer infoId;
    /**
     * 类型名称
     */
    @TableField(exist = false)
    private String tname;

}
