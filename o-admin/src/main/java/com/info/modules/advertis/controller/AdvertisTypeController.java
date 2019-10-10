package com.info.modules.advertis.controller;

import com.info.date.DateUtils;
import com.info.modules.advertis.entity.AdvertisTypeEntity;
import com.info.modules.advertis.service.IAdvertisTypeService;
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
 * 社区广告类型信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@RestController
@RequestMapping("advertis/type")
public class AdvertisTypeController extends AbstractController {

    @Autowired
    private IAdvertisTypeService advertisTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/list")
    @RequiresPermissions("advertis:type:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = advertisTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/info/{adId}")
    @RequiresPermissions("advertis:type:info")
    public ResultMessage info(@PathVariable("adId") Integer adId) {
        Assert.isNull(adId, "广告类型ID不能为空", ConfigConstant.ERROR);
        AdvertisTypeEntity advertisType = advertisTypeService.getById(adId);
        return ResultMessage.ok(advertisType);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("advertis:type:save")
    public ResultMessage save(@RequestBody AdvertisTypeEntity advertisType) {
        ValidatorUtils.validateEntity(advertisType, ConfigConstant.ERROR, AddGroup.class);
        advertisType.setCreator(getUserId().intValue());
        advertisType.setCreatorTime(DateUtils.now());
        advertisTypeService.save(advertisType);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("advertis:type:update")
    public ResultMessage update(@RequestBody AdvertisTypeEntity advertisType) {
        ValidatorUtils.validateEntity(advertisType, ConfigConstant.ERROR, UpdateGroup.class);
        advertisType.setEditor(getUserId().intValue());
        advertisType.setEditorTime(DateUtils.now());
        advertisTypeService.updateById(advertisType);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @RequestMapping("/delete")
    @RequiresPermissions("advertis:type:del")
    public ResultMessage del(@RequestBody Integer[] adIds) {
        advertisTypeService.removeByIds(Arrays.asList(adIds));
        return ResultMessage.ok();
    }

}
