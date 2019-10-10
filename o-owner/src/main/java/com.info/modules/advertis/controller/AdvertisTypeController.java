package com.info.modules.advertis.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.advertis.entity.AdvertisTypeEntity;
import com.info.modules.advertis.service.IAdvertisTypeService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
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
@RequestMapping("api/advertisType")
public class AdvertisTypeController extends AbstractController {

    @Autowired
    private IAdvertisTypeService advertisTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = advertisTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @RequestMapping("/info/{adId}")
    public ResultMessage info(@PathVariable("adId") Integer adId) {
        AdvertisTypeEntity advertisType = advertisTypeService.getById(adId);
        return ResultMessage.ok(advertisType);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody AdvertisTypeEntity advertisType) {
        ValidatorUtils.validateEntity(advertisType, AddGroup.class);
        advertisType.setCreatorTime(DateUtils.now());
        advertisTypeService.save(advertisType);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody AdvertisTypeEntity advertisType) {
        ValidatorUtils.validateEntity(advertisType, UpdateGroup.class);
        advertisType.setEditorTime(DateUtils.now());
        advertisTypeService.updateById(advertisType);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] adIds) {
        advertisTypeService.removeByIds(Arrays.asList(adIds));
        return ResultMessage.ok();
    }

}
