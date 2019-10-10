package com.info.modules.pay.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 支付类型表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2018-09-08 18:52:52
 */
@Data
@TableName("pay_type")
public class PayTypeEntity extends BaseEntity {


    /**
     * 类型ID主键
     */
    @TableId
    private Integer typeId;
    /**
     * 支付类型名称
     */
    @Size(min = 1, max = 10, message = "支付类型名称长度必须在1-10个汉字内")
    @NotNull(message = "支付类型名称不允许为空")
    private String payName;
    /**
     * 添加时间
     */
    private Date creatorTime;
    /**
     * 添加人
     */
    private Integer creator;
    /**
     * 修改时间
     */
    private Date editorTime;
    /**
     * 修改人
     */
    private Integer editor;
    /**
     * 所属地区
     */
    private String country;

}
