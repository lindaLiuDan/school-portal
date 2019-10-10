package com.info.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 部门管理
 *
 * @author Gaosx
 */
@Data
@TableName("sys_dept")
public class SysDeptEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 部门ID
     */
    @TableId
    private Long deptId;
    /**
     * 上级部门ID，一级部门为0
     */
    private Long parentId;
    /**
     * type=0 顶级部门（一个公司是大部门） 1 一级部门 2 二级部门 3 三级部门 无限极的
     */
    private Long typeId;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 上级部门名称
     */
    @TableField(exist = false)
    private String parentName;
    /**
     *
     * */
    private Integer orderNum;
    /**
     *
     * */
    @TableLogic
    private Integer delFlag;
    /**
     * ztree属性
     */
    @TableField(exist = false)
    private Boolean open;
    /**
     *
     * */
    @TableField(exist = false)
    private List<?> list;
}
