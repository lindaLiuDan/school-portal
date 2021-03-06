package com.info.modules.provider.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商家通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-14 19:42:01
 */
@Data
@TableName("provider_message_type")
public class ProviderMessageTypeEntity extends BaseEntity {


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
    private Date creatorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 修改时间
     */
    private Date editorTime;
    /**
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;

}
