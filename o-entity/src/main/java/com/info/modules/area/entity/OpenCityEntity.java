package com.info.modules.area.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 开放城市区域信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-12 19:31:54
 */
@Data
@TableName("open_city")
public class OpenCityEntity extends BaseEntity {


    /**
     * 开放城市区域ID主键
     */
    @TableId
    private Integer openId;
    /**
     * 城市名称
     */
    private String cityName;
    /**
     * 城市编号
     */
    private String cityNo;
    /**
     * 父类ID编号 0 表示顶级的
     */
    private Integer parentId;
    /**
     * 添加人
     */
    private Integer creator;
    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 是否删除 0删除 1正常
     */
    private Integer isDel;
    /**
     * 排序 默认0
     */
    private Integer sort;
    /**
     * 数据版本号 默认0
     */
    private Integer version;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;

}
