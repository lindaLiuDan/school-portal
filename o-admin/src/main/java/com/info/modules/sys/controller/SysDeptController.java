package com.info.modules.sys.controller;

import com.info.modules.sys.entity.SysDeptEntity;
import com.info.modules.sys.service.SysDeptService;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 部门管理
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("sys/dept")
public class SysDeptController extends AbstractController {


    @Autowired
    private SysDeptService sysDeptService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dept:list")
    public ResultMessage list() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
        return ResultMessage.ok(deptList);
    }

    /**
     * 选择部门(添加、修改菜单)
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:dept:select")
    public ResultMessage select() {
        List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
        //添加一级部门
        if (getUserId() == Constant.SUPER_ADMIN) {
            SysDeptEntity root = new SysDeptEntity();
            root.setDeptId(0L);
            root.setName("一级部门");
            root.setParentId(-1L);
            root.setOpen(true);
            deptList.add(root);
        }
        return ResultMessage.ok(deptList);
    }

    /**
     * 上级部门Id(管理员则为0)
     */
    @RequestMapping("/info")
    @RequiresPermissions("sys:dept:list")
    public ResultMessage info() {
        long deptId = 0;
        if (getUserId() != Constant.SUPER_ADMIN) {
            List<SysDeptEntity> deptList = sysDeptService.queryList(new HashMap<String, Object>());
            Long parentId = null;
            for (SysDeptEntity sysDeptEntity : deptList) {
                if (parentId == null) {
                    parentId = sysDeptEntity.getParentId();
                    continue;
                }
                if (parentId > sysDeptEntity.getParentId().longValue()) {
                    parentId = sysDeptEntity.getParentId();
                }
            }
            deptId = parentId;
        }
        return ResultMessage.ok(deptId);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{deptId}")
    @RequiresPermissions("sys:dept:info")
    public ResultMessage info(@PathVariable("deptId") Long deptId) {
        Assert.isNull(deptId, "部门ID不能为空", ConfigConstant.ERROR);
        SysDeptEntity dept = sysDeptService.getById(deptId);
        return ResultMessage.ok(dept);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dept:save")
    public ResultMessage save(@RequestBody SysDeptEntity dept) {
        ValidatorUtils.validateEntity(dept, ConfigConstant.ERROR, AddGroup.class);
        sysDeptService.save(dept);
        return ResultMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dept:update")
    public ResultMessage update(@RequestBody SysDeptEntity dept) {
        ValidatorUtils.validateEntity(dept, ConfigConstant.ERROR, UpdateGroup.class);
        sysDeptService.updateById(dept);
        return ResultMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{deptId}")
    @RequiresPermissions("sys:dept:delete")
    public ResultMessage delete(@PathVariable("deptId") long deptId) {
        Assert.isNull(deptId, "部门ID不能为空", ConfigConstant.ERROR);
        //判断是否有子部门
        List<Long> deptList = sysDeptService.queryDetpIdList(deptId);
        if (deptList.size() > 0) {
            return ResultMessage.error("请先删除子部门");
        }
        sysDeptService.removeById(deptId);
        return ResultMessage.ok();
    }

}
