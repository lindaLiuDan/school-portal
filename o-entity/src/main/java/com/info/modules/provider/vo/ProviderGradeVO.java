package com.info.modules.provider.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.info.base.BaseEntity;
import lombok.Data;

import java.io.Serializable;
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
//@JsonIgnoreProperties(value = {"creatorTime", "editorTime", "privilege","creator","memo"})
public class ProviderGradeVO implements Serializable {


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

}
