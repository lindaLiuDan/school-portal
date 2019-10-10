package com.info.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述: BaseEntity 这里面都是公共的数据库字段 集成集成即可使用
 *
 * @Params:  * @param null
 * @Author:  Gaosx  By User
 * @Date: 2019/6/7 17:23
 * @Return:
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 序列化 JVM使用
     */
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     */
    @TableField(exist = false)
    private Integer creator;
    /**
     * 创建时间
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 修改人
     */
    @TableField(exist = false)
    private Integer editor;
    /**
     * 修改时间
     */
    @TableField(exist = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;


}
