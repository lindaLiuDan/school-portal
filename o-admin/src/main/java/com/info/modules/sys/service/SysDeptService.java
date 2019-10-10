package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysDeptEntity;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Gaosx
 */
public interface SysDeptService extends IService<SysDeptEntity> {


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/26 14:29
     * @Return:
     */
    List<SysDeptEntity> queryList(Map<String, Object> map);

    /**
     * 查询子部门ID列表
     *
     * @param parentId 上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

    /**
     * 获取子部门ID，用于数据过滤
     */
    List<Long> getSubDeptIdList(Long deptId);

}
