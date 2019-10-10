package com.info.modules.area.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * 会员收货地址表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-03-07 11:24:39
 */
@Data
@TableName("user_address")
public class UserAddressEntity extends BaseEntity {


    /**
     * 收货地址ID主键
     */
    @TableId
    @NotNull(message = "用户ID不能为空", groups = {UpdateGroup.class})
    private Integer addressId;
    /**
     * 用户ID主键
     */
    @NotNull(message = "用户ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 收货人姓名
     */
    @Size(message = "收货人姓名过长", max = 10, min = 1, groups = {AddGroup.class, UpdateGroup.class})
    @NotBlank(message = "收货人不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String consignee;
    /**
     * 省市县区编号
     */
    @NotBlank(message = "省市县区编号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String areaId;
    /**
     * 手机号码
     */
    @NotBlank(message = "手机号不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 收货地址详情
     */
    @NotBlank(message = "收货地址详情不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private String address;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 收货状态 1 全天 2 工作日 3 周末
     */
    private Integer status;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
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
     * 是否为默认收货地址 0 否 1 是
     */
    private Integer isDef;
    /**
     * 房号
     */
    private String roomNo;
    /**
     * 集合
     */
    @TableField(exist = false)
    private List<UserAddressEntity> list;

    /**
     * 省
     */
    @TableField(exist = false)
    private String province;

    /**
     * 市
     */
    @TableField(exist = false)
    private String city;

    /**
     * 县区
     */
    @TableField(exist = false)
    private String area;

    /**
     * 国家
     */
    @TableField(exist = false)
    private String country;
    /**
     * 省市县区的集合字符串儿
     */
    @TableField(exist = false)
    private String names;

}

