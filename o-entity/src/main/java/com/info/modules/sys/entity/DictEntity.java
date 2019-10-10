package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author Gaosx 960889426@qq.com
 * @since 3.1.0 2018-01-27
 */
@Data
@TableName("sys_dict")
public class DictEntity implements Serializable {


    private static final long serialVersionUID = 1L;

    @TableId
    private Long dictId;
    /**
     * 字典名称
     */
    @NotBlank(message = "字典名称不能为空")
    private String dictName;
    /**
     * 字典类型
     */
    @NotBlank(message = "字典类型不能为空")
    private String dictType;
    /**
     * 字典码
     */
    @NotBlank(message = "字典码不能为空")
    private String dictCode;
    /**
     * 字典值
     */
    @NotBlank(message = "字典值不能为空")
    private String dictValue;
    /**
     * 排序
     */
    private Integer sor;
    /**
     * 备注
     */
    private String remark;
    /**
     * 删除标记  -1：已删除  0：正常
     */
    private Integer isDel;

}
