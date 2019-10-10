package com.info.modules.move.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 社区活动报名表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:23
 */
@Data
@TableName("move_info_signup")
public class MoveInfoSignupEntity extends BaseEntity {


    /**
     * 社区活动报名ID主键
     */
    @TableId
    private Integer upId;
    /**
     * 社区活动ID主键
     */
    private Integer moveId;

    //用户id
    private Integer userId;
    /**
     * 报名人名称
     */
    private String userName;
    /**
     * 报名人电话
     */
    private String mobile;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 排序数
     */
    private Integer sort;


}
