package com.info.modules.provider.form;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 商家分类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Data
public class ProviderTypeEntityForm extends BaseEntity {


    /**
     * 商家分类信息ID主键
     */
    @TableId
    private Integer typeId;
    /**
     * 商家分类名称
     */
    private String typeName;
    /**
     * 父类ID主键　默认 0是顶级父类
     */
    private Integer parentId;
    /**
     * 备注
     */
    private String memo;
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
