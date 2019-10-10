package com.info.modules.provider.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.modules.provider.entity.ProviderTypeEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 商家分类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@Data
@TableName("provider_type")
public class ProviderTypeEntityVo implements Serializable {

    /**
     * 商家分类信息ID主键
     */
    @TableId
    private Integer typeId;
    /**
     * 分类图片logo
     */
    private String logo;
    /**
     * 商家分类名称
     */
    private String typeName;
    /**
     * 父类ID主键　默认 0是顶级父类
     */
    private Integer parentId;
    /**
     * list集合
     */
    @TableField(exist = false)
    private List<ProviderTypeEntityVo> providerTypeList;

}
