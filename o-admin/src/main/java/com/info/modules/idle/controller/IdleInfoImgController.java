package com.info.modules.idle.controller;

import com.info.date.DateUtils;
import com.info.modules.idle.entity.IdleInfoImgEntity;
import com.info.modules.idle.service.IdleInfoImgService;
import com.info.modules.sys.controller.AbstractController;
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
 * 闲置信息图片表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-07 17:17:37
 */
@RestController
@RequestMapping("idle/img")
public class IdleInfoImgController extends AbstractController {

    @Autowired
    private IdleInfoImgService idleInfoImgService;


    /**
     * @Author: Gaosx
     * @Description: 列表查询
     * @Date: 2019-06-07 17:17:37
     */
    @GetMapping("/list")
    @RequiresPermissions("idleImg:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = idleInfoImgService.queryPage(params);
        return ResultMessage.ok( page);
    }

    /**
     * @Author: Gaosx
     * @Description: 获取详细信息
     * @Date: 2019-06-07 17:17:37
     */
    @RequestMapping("/info/{imgId}")
    @RequiresPermissions("idleImg:info")
    public ResultMessage info(@PathVariable("imgId") Integer imgId) {
        Assert.isNull(imgId,"ID不能为空", ConfigConstant.ERROR);
        IdleInfoImgEntity idleInfoImg = idleInfoImgService.getById(imgId);
        return ResultMessage.ok(idleInfoImg);
    }

    /**
     * @Author: Gaosx
     * @Description: 保存信息
     * @Date: 2019-06-07 17:17:37
     */
    @RequestMapping("/save")
    @RequiresPermissions("idleImg:save")
    public ResultMessage save(@RequestBody IdleInfoImgEntity idleInfoImg) {
        ValidatorUtils.validateEntity(idleInfoImg, ConfigConstant.ERROR, AddGroup.class);
        idleInfoImg.setCreatorTime(DateUtils.now());
        idleInfoImgService.save(idleInfoImg);
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 修改信息
     * @Date: 2019-06-07 17:17:37
     */
    @RequestMapping("/update")
    @RequiresPermissions("idleImg:update")
    public ResultMessage update(@RequestBody IdleInfoImgEntity idleInfoImg) {
        ValidatorUtils.validateEntity(idleInfoImg, ConfigConstant.ERROR, UpdateGroup.class);
        idleInfoImg.setEditorTime(DateUtils.now());
        idleInfoImgService.updateById(idleInfoImg);//全部更新
        return ResultMessage.ok();
    }

    /**
     * @Author: Gaosx
     * @Description: 删除信息
     * @Date: 2019-06-07 17:17:37
     */
    @RequestMapping("/del")
    @RequiresPermissions("idleImg:del")
    public ResultMessage del(@RequestBody Integer[] imgIds) {
        idleInfoImgService.removeByIds(Arrays.asList(imgIds));
        return ResultMessage.ok();
    }

}
