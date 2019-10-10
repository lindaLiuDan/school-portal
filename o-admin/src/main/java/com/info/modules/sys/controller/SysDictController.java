package com.info.modules.sys.controller;

import com.info.modules.sys.entity.DictEntity;
import com.info.modules.sys.service.SysDictService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController extends AbstractController {

    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysDictService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public ResultMessage info(@PathVariable("id") Long id) {
        Assert.isNull(id, "字典ID不能为空", ConfigConstant.ERROR);
        DictEntity dict = sysDictService.getById(id);
        return ResultMessage.ok(dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public ResultMessage save(@RequestBody DictEntity dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict, ConfigConstant.ERROR, AddGroup.class);
        sysDictService.save(dict);
        return ResultMessage.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public ResultMessage update(@RequestBody DictEntity dict) {
        //校验类型
        ValidatorUtils.validateEntity(dict, ConfigConstant.ERROR, UpdateGroup.class);
        sysDictService.updateById(dict);
        return ResultMessage.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public ResultMessage delete(@RequestBody Long[] ids) {
        Assert.isNull(ids, "字典ID不能为空", ConfigConstant.ERROR);
        sysDictService.removeByIds(Arrays.asList(ids));
        return ResultMessage.ok();
    }

}
