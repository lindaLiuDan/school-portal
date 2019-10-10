package com.info.modules.idle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.info.base.BaseEntity;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 闲置交易信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@Data
@TableName("idle_info")
public class IdleInfoEntity extends BaseEntity {


    /**
     * 闲置信息ID主键
     */
    @TableId
    private Integer leId;
    /**
     * 社区小区ID主键
     */
    @NotNull(message = "社区ID信息不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer infoId;
    /**
     * 闲置信息价格
     */
    @NotNull(message = "发布价格不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Range(max = 999999, message = "输入的价格已超过1或者999999", groups = {AddGroup.class, UpdateGroup.class})
    private BigDecimal price;
    /**
     * 闲置信息简介
     */
    @NotNull(message = "闲置信息简介不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size( max =150,message = "输入文字简介不能高于150个汉字", groups = {AddGroup.class, UpdateGroup.class})
    private String content;
    /**
     * 联系人电话
     */
    @NotNull(message = "联系人电话不能为空", groups = {AddGroup.class, UpdateGroup.class})
    @Size( max =11,message = "输入联系人电话不能大于11个数字", groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;
    /**
     * 发布人会员ID
     */
    @NotNull(message = "发布人ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer userId;
    /**
     * 发布时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date creatorTime;
    /**
     * 交易区域 1 本小区 2 同城
     */
    @NotNull(message = "交易区域ID不能为空", groups = {AddGroup.class, UpdateGroup.class})
    private Integer area;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date editorTime;
    /**
     * 是否删除 0 删除 1 正常
     */
    private Integer isDel;
    /**
     * 排序数
     */
    private Integer sort;

    /**
     * 集合Img的
     */
    @TableField(exist = false)
    private List<IdleInfoImgEntity> imgEntityList;

    /**
     * 集合Img的
     */
    @TableField(exist = false)
    private List<IdleInfoCommentEntity> idleInfoCommentEntityList;

}
