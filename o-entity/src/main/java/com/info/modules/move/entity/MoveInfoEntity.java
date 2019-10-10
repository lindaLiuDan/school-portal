package com.info.modules.move.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 社区活动信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-06 18:05:29
 */
@Data
@TableName("move_info")
public class MoveInfoEntity extends BaseEntity {


    /**
     * 社区活动ID主键
     */
    @TableId
    private Integer moveId;
    /**
     * 所在社区小区ID主键
     */
    @NotNull(message = "社区ID主键不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 活动标题
     */
    @NotNull(message = "活动标题不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String title;
    /**
     * 社区活动内容
     */
    @NotNull(message = "社区活动内容不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String content;
    /**
     * 社区活动主图
     */
    private String img;
    /**
     * 主图缩略图
     */
    private String smallImg;
    /**
     * 社区活动报名费用
     */
    private BigDecimal price;
    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;
    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 创建人
     */
    private Integer creator;
    /**
     * 排序数
     */
    private Integer sort;

}
