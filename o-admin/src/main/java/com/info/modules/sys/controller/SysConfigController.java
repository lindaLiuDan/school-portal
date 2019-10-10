package com.info.modules.sys.controller;

import com.info.common.annotation.SysLog;
import com.info.modules.sys.entity.ConfigEntity;
import com.info.modules.sys.service.SysConfigService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 系统配置信息
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("sys/config")
public class SysConfigController extends AbstractController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:config:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysConfigService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }

    /**
     * 配置信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:config:info")
    public ResultMessage info(@PathVariable("id") Long id) {
        ConfigEntity config = sysConfigService.getById(id);
        return ResultMessage.ok().put("config", config);
    }

    /**
     * 保存配置
     */
    @SysLog("保存配置")
    @RequestMapping("/save")
    @RequiresPermissions("sys:config:save")
    public ResultMessage save(@RequestBody ConfigEntity config) {
        ValidatorUtils.validateEntity(config, ConfigConstant.ERROR, AddGroup.class);
        sysConfigService.saveConfig(config);
        return ResultMessage.ok();
    }

    /**
     * 修改配置
     */
    @SysLog("修改配置")
    @RequestMapping("/update")
    @RequiresPermissions("sys:config:update")
    public ResultMessage update(@RequestBody ConfigEntity config) {
        ValidatorUtils.validateEntity(config, ConfigConstant.ERROR, UpdateGroup.class);
        sysConfigService.update(config);
        return ResultMessage.ok();
    }

    /**
     * 删除配置
     */
    @SysLog("删除配置")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:config:delete")
    public ResultMessage delete(@RequestBody Long[] ids) {
        sysConfigService.deleteBatch(ids);
        return ResultMessage.ok();
    }

}
