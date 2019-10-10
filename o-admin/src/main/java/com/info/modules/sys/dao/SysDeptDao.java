package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.SysDeptEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 部门管理
 *
 * @author Gaosx
 */
public interface SysDeptDao extends BaseMapper<SysDeptEntity> {


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 13:20
     * @Return:
     */
    List<SysDeptEntity> queryList(Map<String, Object> params);

    /**
     * 查询子部门ID列表
     *
     * @param parentId 上级部门ID
     */
    List<Long> queryDetpIdList(Long parentId);

}
