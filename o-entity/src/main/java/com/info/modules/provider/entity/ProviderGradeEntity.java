package com.info.modules.provider.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商家(店铺)等级表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Data
@TableName("provider_grade")
public class ProviderGradeEntity extends BaseEntity {


    /**
     * 店铺等级ID主键
     */
    @TableId
    private Integer gradeId;
    /**
     * 店铺等级名称
     */
    private String gname;
    /**
     * 店铺等级说明
     */
    private String memo;
    /**
     * 店铺等级特权
     */
    private String privilege;
    /**
     * 创建时间
     */
    private Date creatorTime;
    /**
     * 创建人
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Integer creator;
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
     * 排序数 数字越大越靠前
     */
    private Integer sort;

}
