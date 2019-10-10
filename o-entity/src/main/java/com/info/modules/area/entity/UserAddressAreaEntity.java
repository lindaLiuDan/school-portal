package com.info.modules.area.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 会员收货地址区域表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-10-13 19:03:36
 */
@Data
@TableName("sys_country")
public class UserAddressAreaEntity extends BaseEntity {


    /**
     * 收货地址区域ID主键
     */
    @TableId
    private Integer aId;

    /**
     * 收货地址区域ID主键
     */
    private Integer areaId;
    /**
     * 收货地址区域ID主键
     */
    private String parentId;

    /**
     * 收货地址区域ID主键
     */
    private String parentIds;
    /**
     * 收货区域名称
     */
    @Size(min = 1, max = 10, message = "区域名称只允许1-10个汉字之间", groups = {AddGroup.class, UpdateGroup.class})
    @NotNull(message = "区域名称不允许为空", groups = {AddGroup.class, UpdateGroup.class})
    private String names;
    /**
     * 收货区域名称
     */
    private String sort;
    /**
     * 收货区域名称
     */
    private String code;
    /**
     * 收货区域名称
     */
    private String type;
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
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;

    /**
     * 管理员名称
     */
    @TableField(exist = false)
    private String username;

}
