package com.info.modules.sys.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.sys.entity.SysDeptEntity;
import com.info.modules.sys.entity.SysRoleEntity;
import com.info.modules.sys.service.SysDeptService;
import com.info.modules.sys.service.SysRoleDeptService;
import com.info.modules.sys.service.SysRoleMenuService;
import com.info.modules.sys.service.SysRoleService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 角色管理
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    @Autowired
    private SysRoleDeptService sysRoleDeptService;

    @Autowired
    private SysDeptService sysDeptService;


    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysRoleService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 角色列表
     */
    @RequestMapping("/select")
    @RequiresPermissions("sys:role:select")
    public ResultMessage select() {
        List<SysRoleEntity> list = sysRoleService.list();
        list.stream().forEach(info -> {
            SysDeptEntity deptEntity = sysDeptService.getById(info.getDeptId());
            if (deptEntity != null) {
                info.setDeptName(deptEntity.getName());
            }
        });
        return ResultMessage.ok(list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public ResultMessage info(@PathVariable("roleId") Long roleId) {
        SysRoleEntity role = sysRoleService.getById(roleId);
        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);
        //查询角色对应的部门
        List<Long> deptIdList = sysRoleDeptService.queryDeptIdList(new Long[]{roleId});
        role.setDeptIdList(deptIdList);
        SysDeptEntity deptEntity = sysDeptService.getById(role.getDeptId());
        role.setDeptName(deptEntity.getName());
        return ResultMessage.ok(role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public ResultMessage save(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role, ConfigConstant.ERROR, AddGroup.class);
        sysRoleService.saveRole(role);
        return ResultMessage.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public ResultMessage update(@RequestBody SysRoleEntity role) {
        ValidatorUtils.validateEntity(role, ConfigConstant.ERROR, UpdateGroup.class);
        sysRoleService.update(role);
        return ResultMessage.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public ResultMessage delete(@RequestBody Long[] roleIds) {
        sysRoleService.deleteBatch(roleIds);
        return ResultMessage.ok();
    }
}
