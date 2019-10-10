package com.info.modules.advertis.controller;

import com.info.common.annotation.SysLog;
import com.info.date.DateUtils;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.modules.advertis.service.IAdvertisInfoService;
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
 * 社区小区广告信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@RestController
@RequestMapping("advertis/info")
public class AdvertisInfoController extends AbstractController {

    @Autowired
    private IAdvertisInfoService advertisInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("advertis:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = advertisInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页获取本社区所有的广告信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/all/{infoId}")
    @RequiresPermissions("advertis:list")
    public ResultMessage all(@PathVariable("infoId") Integer infoId) {
        Assert.isNull(infoId, "社区ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(advertisInfoService.all(infoId));
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/info/{advertisId}")
    @RequiresPermissions("advertis:info")
    public ResultMessage info(@PathVariable("advertisId") Integer advertisId) {
        Assert.isNull(advertisId, "类型ID不能为空", ConfigConstant.ERROR);
        AdvertisInfoEntity advertisInfo = advertisInfoService.getAdvertisInfo(advertisId);
        return ResultMessage.ok(advertisInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @SysLog
    @RequestMapping("/save")
    @RequiresPermissions("advertis:save")
    public ResultMessage save(@RequestBody AdvertisInfoEntity advertisInfo) {
        ValidatorUtils.validateEntity(advertisInfo, ConfigConstant.ERROR, AddGroup.class);
        advertisInfo.setCreator(getUserId().intValue());
        advertisInfo.setCreatorTime(DateUtils.now());
        return advertisInfoService.addAdvertisInfo(advertisInfo);
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @SysLog
    @RequestMapping("/update")
    @RequiresPermissions("advertis:update")
    public ResultMessage update(@RequestBody AdvertisInfoEntity advertisInfo) {
        ValidatorUtils.validateEntity(advertisInfo, ConfigConstant.ERROR, UpdateGroup.class);
        advertisInfo.setEditor(getUserId().intValue());
        advertisInfo.setEditorTime(DateUtils.now());
        return advertisInfoService.updateAdvertisInfo(advertisInfo);//全部更新
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @SysLog
    @RequestMapping("/del/{infoId}/{advertisId}")
    @RequiresPermissions("advertis:del")
    public ResultMessage del(@PathVariable("infoId") Integer infoId, @PathVariable("infoId") String advertisId) {
        Assert.isNull(infoId, "社区ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(advertisId, "类型ID不能为空", ConfigConstant.ERROR);
        return advertisInfoService.delAdvertisInfo(infoId, advertisId);
    }

}
