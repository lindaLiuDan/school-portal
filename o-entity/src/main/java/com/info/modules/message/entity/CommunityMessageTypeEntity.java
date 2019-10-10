package com.info.modules.message.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@Data
@TableName("community_message_type")
public class CommunityMessageTypeEntity extends BaseEntity {


    /**
     * 类型名称
     */
    @TableId
    private Integer typeId;
    /**
     * 类型ID主键
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
    /**
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;

}
