package com.info.modules.packag.controller;

import com.info.date.DateUtils;
import com.info.modules.packag.entity.PackageEntity;
import com.info.modules.packag.service.IPackageService;
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
 * 社区快递包裹信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
@RestController
@RequestMapping("package/info")
public class PackageController extends AbstractController {

    @Autowired
    private IPackageService communityPackageService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("package:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityPackageService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @GetMapping("/info/{pakegeId}")
    @RequiresPermissions("package:info")
    public ResultMessage info(@PathVariable("pakegeId") Integer pakegeId) {
        Assert.isNull(pakegeId,"快递包裹ID不能为空", ConfigConstant.ERROR);
        PackageEntity communityPackage = communityPackageService.getById(pakegeId);
        return ResultMessage.ok(communityPackage);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("package:save")
    public ResultMessage save(@RequestBody PackageEntity communityPackage) {
        ValidatorUtils.validateEntity(communityPackage,ConfigConstant.ERROR, AddGroup.class);
        communityPackage.setCreator(getUserId().intValue());
        communityPackage.setCreatorTime(DateUtils.now());
        communityPackageService.save(communityPackage);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("package:update")
    public ResultMessage update(@RequestBody PackageEntity communityPackage) {
        ValidatorUtils.validateEntity(communityPackage, ConfigConstant.ERROR,UpdateGroup.class);
        communityPackage.setEditor(getUserId().intValue());
        communityPackage.setEditorTime(DateUtils.now());
        communityPackageService.updateById(communityPackage);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @PostMapping("/delete")
    @RequiresPermissions("package:delete")
    public ResultMessage delete(@RequestBody Integer[] pakegeIds) {
        communityPackageService.removeByIds(Arrays.asList(pakegeIds));
        return ResultMessage.ok();
    }

}
