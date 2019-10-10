package com.info.modules.access.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 门禁开锁信息记录表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 13:58:10
 */
@Data
@TableName("access_info_log")
public class AccessInfoLogEntity extends BaseEntity {


    /**
     * 社区门禁开锁信息ID主键
     */
    @TableId
    private Integer accId;
    /**
     * 社区小区ID主键
     */
    private Integer infoId;
    /**
     * 开锁业主
     */
    private Integer userId;
    /**
     * 开锁状态 0 失败 1 成功
     */
    private Integer isStatus;
    /**
     * 开锁时间
     */
    private Date creatorTime;
    /**
     * 备注
     */
    private String memo;
    /**
     *
     */
    private Date editorTime;
    /**
     *
     */
    private Integer editor;

}
