package com.info.modules.provider.controller;

import com.info.date.DateUtils;
import com.info.modules.provider.entity.ProviderInfoEntity;
import com.info.modules.provider.service.IProviderInfoService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
@RestController
@RequestMapping("provider/info")
public class ProviderInfoController extends AbstractController {

    @Autowired
    private IProviderInfoService providerInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("provider:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @GetMapping("/info/{providerId}")
    @RequiresPermissions("provider:info")
    public ResultMessage info(@PathVariable("providerId") Integer providerId) {
        ProviderInfoEntity providerInfo = providerInfoService.getById(providerId);
        return ResultMessage.ok(providerInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("provider:save")
    public ResultMessage save(@RequestBody ProviderInfoEntity providerInfo) {
        ValidatorUtils.validateEntity(providerInfo, AddGroup.class);
        providerInfo.setCreator(getUserId().intValue());
        providerInfo.setCreatorTime(DateUtils.now());
        providerInfoService.save(providerInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("provider:update")
    public ResultMessage update(@RequestBody ProviderInfoEntity providerInfo) {
        ValidatorUtils.validateEntity(providerInfo, UpdateGroup.class);
        providerInfo.setEditor(getUserId().intValue());
        providerInfo.setEditorTime(DateUtils.now());
        providerInfoService.updateById(providerInfo);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("provider:del")
    public ResultMessage del(@RequestBody Integer[] providerIds) {
        providerInfoService.removeByIds(Arrays.asList(providerIds));
        return ResultMessage.ok();
    }

}
