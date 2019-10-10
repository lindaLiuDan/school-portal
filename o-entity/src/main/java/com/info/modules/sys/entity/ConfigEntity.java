package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.info.base.BaseEntity;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 功能描述: 系统配置信息
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/15 15:45
 * @Return:
 */
@Data
@TableName("sys_config")
public class ConfigEntity extends BaseEntity{

    @TableId
    private Long id;

    @NotBlank(message = "参数名不能为空")
    private String paramKey;

    @NotBlank(message = "参数值不能为空")
    private String paramValue;

    private String remark;

}