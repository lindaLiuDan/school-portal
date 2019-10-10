package com.info.modules.sys.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.sys.entity.SysDeptEntity;
import com.info.modules.sys.entity.SysUserEntity;
import com.info.modules.sys.service.SysDeptService;
import com.info.modules.sys.service.SysUserRoleService;
import com.info.modules.sys.service.SysUserService;
import com.info.modules.sys.shiro.ShiroUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private SysDeptService sysDeptService;


    /**
     * 功能描述: 所有用户列表
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/5/31 10:10
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public ResultMessage info() {
        return ResultMessage.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public ResultMessage password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");
        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());
        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return ResultMessage.error("原密码不正确");
        }
        return ResultMessage.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public ResultMessage info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);
        SysDeptEntity deptEntity = sysDeptService.getById(user.getDeptId());
        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);
        user.setDeptName(deptEntity.getName());
        return ResultMessage.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public ResultMessage save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, ConfigConstant.ERROR, AddGroup.class);
        sysUserService.saveUser(user);
        return ResultMessage.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public ResultMessage update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, ConfigConstant.ERROR, UpdateGroup.class);
        sysUserService.update(user);
        return ResultMessage.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public ResultMessage delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return ResultMessage.error("系统管理员不能删除");
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return ResultMessage.error("当前用户不能删除");
        }
        sysUserService.removeByIds(Arrays.asList(userIds));
        return ResultMessage.ok();
    }
}
