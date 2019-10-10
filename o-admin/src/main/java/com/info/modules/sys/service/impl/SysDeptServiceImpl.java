package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.modules.sys.dao.SysDeptDao;
import com.info.modules.sys.entity.SysDeptEntity;
import com.info.modules.sys.service.SysDeptService;
import com.info.utils.Constant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能描述: 部门信息业务层
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/26 13:37
 * @Return:
 */
@Service("sysDeptService")
public class SysDeptServiceImpl extends ServiceImpl<SysDeptDao, SysDeptEntity> implements SysDeptService {

    @Override
    @DataFilter(subDept = true, user = false, tableAlias = "t1")
    public List<SysDeptEntity> queryList(Map<String, Object> params) {
        List<SysDeptEntity> deptList = baseMapper.queryList(params);
        if (deptList.size() > 0) {
            for (SysDeptEntity sysDeptEntity : deptList) {
                getchildrenList(sysDeptEntity);
            }
        }
        System.out.println(deptList);
        return deptList;
    }

    /**
     * 功能描述: 配对上面的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/26 14:33
     * @Return:
     */
    private void getchildrenList(SysDeptEntity sysDeptEntity) {
        List<SysDeptEntity> child = this.list(new QueryWrapper<SysDeptEntity>().eq("parent_id", sysDeptEntity.getDeptId()));
        if (child.size() > 0) {
            sysDeptEntity.setList(child);
            for (SysDeptEntity sysDept : child) {
                sysDept.setParentName(sysDeptEntity.getName());
                getchildrenList(sysDept);
            }
        }
    }

    @Override
    public List<Long> queryDetpIdList(Long parentId) {
        return baseMapper.queryDetpIdList(parentId);
    }

    @Override
    public List<Long> getSubDeptIdList(Long deptId) {
        //部门及子部门ID列表
        List<Long> deptIdList = new ArrayList<>();

        //获取子部门ID
        List<Long> subIdList = queryDetpIdList(deptId);
        getDeptTreeList(subIdList, deptIdList);

        return deptIdList;
    }

    /**
     * 递归
     */
    private void getDeptTreeList(List<Long> subIdList, List<Long> deptIdList) {
        for (Long deptId : subIdList) {
            List<Long> list = queryDetpIdList(deptId);
            if (list.size() > 0) {
                getDeptTreeList(list, deptIdList);
            }

            deptIdList.add(deptId);
        }
    }
}
