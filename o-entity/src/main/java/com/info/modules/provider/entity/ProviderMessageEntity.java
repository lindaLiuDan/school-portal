package com.info.modules.provider.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商户通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-14 19:42:01
 */
@Data
@TableName("provider_message")
public class ProviderMessageEntity extends BaseEntity {


    /**
     * 商户通告ID主键
     */
    @TableId
    private Integer meId;
    /**
     * 类型id
     */
    private Integer typeId;
    /**
     * 商户id
     */
    private Integer providerId;
    /**
     * 商户通告标题
     */
    private String title;
    /**
     * 商户通告主题内容
     */
    private String content;
    /**
     * 备注
     */
    private String memo;
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
