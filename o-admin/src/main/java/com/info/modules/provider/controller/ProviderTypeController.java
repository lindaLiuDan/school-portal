package com.info.modules.provider.controller;

import com.info.date.DateUtils;
import com.info.modules.provider.entity.ProviderTypeEntity;
import com.info.modules.provider.service.IProviderTypeService;
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
 * 商家分类信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
@RestController
@RequestMapping("provider/type")
public class ProviderTypeController extends AbstractController {

    @Autowired
    private IProviderTypeService providerTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("providerType:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerTypeService.queryPage(params);
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
    @GetMapping("/info/{typeId}")
    @RequiresPermissions("providerType:info")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        ProviderTypeEntity providerType = providerTypeService.getById(typeId);
        return ResultMessage.ok(providerType);
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
    @RequiresPermissions("providerType:save")
    public ResultMessage save(@RequestBody ProviderTypeEntity providerType) {
        ValidatorUtils.validateEntity(providerType, AddGroup.class);
        providerType.setCreator(getUserId().intValue());
        providerType.setCreatorTime(DateUtils.now());
        providerTypeService.save(providerType);
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
    @RequiresPermissions("providerType:update")
    public ResultMessage update(@RequestBody ProviderTypeEntity providerType) {
        ValidatorUtils.validateEntity(providerType, UpdateGroup.class);
        providerType.setEditor(getUserId().intValue());
        providerType.setEditorTime(DateUtils.now());
        providerTypeService.updateById(providerType);//全部更新
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
    @RequiresPermissions("providerType:del")
    public ResultMessage del(@RequestBody Integer[] typeIds) {
        providerTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
