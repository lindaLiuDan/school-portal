package com.info.modules.feedback.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-11-14 17:59:26
 */
@Data
@TableName("app_feedback")
public class AppFeedbackEntity extends BaseEntity {


    /**
     * 反馈ID主键
     */
    @TableId
    private Integer feedblackId;
    /**
     * 反馈人
     */
    private Integer userId;
    /**
     * 反馈问题内容
     */
    private String content;
    /**
     * 反馈时间
     */
    private Date createTime;
    /**
     * 是否处理 1 已处理 0 未处理
     */
    private Integer isProcess;
    /**
     * 处理时间
     */
    private Date processTime;
    /**
     * 处理人
     */
    private Integer processMan;
    /**
     * 反馈人姓名
     */
    @TableField(exist = false)
    private String username;

    //图片
    private String img;

    //小图片
    private String smallImg;

    //电话号码
    private String mobail;

    //处理人姓名
    @TableField(exist = false)
    private String processName;
}
