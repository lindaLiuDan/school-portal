package com.info.modules.repair.controller;

import com.info.date.DateUtils;
import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.modules.repair.service.ICommunityRepairTypeService;
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
 * 社区物业报修类型信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-14 14:11:07
 */
@RestController
@RequestMapping("repair/type")
public class CommunityRepairTypeController extends AbstractController {

    @Autowired
    private ICommunityRepairTypeService communityRepairTypeService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx
     * @Date: 2019-06-14 14:11:07
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("repairType:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityRepairTypeService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-14 14:11:07
     * @Return:
     */
    @GetMapping("/info/{typeId}")
    @RequiresPermissions("repairType:info")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
        Assert.isNull(typeId, "报修类型ID不能为空", ConfigConstant.ERROR);
        CommunityRepairTypeEntity communityRepairType = communityRepairTypeService.getById(typeId);
        return ResultMessage.ok(communityRepairType);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-14 14:11:07
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("repairType:save")
    public ResultMessage save(@RequestBody CommunityRepairTypeEntity communityRepairType) {
        ValidatorUtils.validateEntity(communityRepairType, ConfigConstant.ERROR, AddGroup.class);
        communityRepairType.setCreatorTime(DateUtils.now());
        communityRepairTypeService.save(communityRepairType);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-14 14:11:07
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("repairType:update")
    public ResultMessage update(@RequestBody CommunityRepairTypeEntity communityRepairType) {
        ValidatorUtils.validateEntity(communityRepairType,ConfigConstant.ERROR, UpdateGroup.class);
        communityRepairType.setEditorTime(DateUtils.now());
        communityRepairTypeService.updateById(communityRepairType);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-14 14:11:07
     * @Return:
     */
    @RequestMapping("/delete")
    @RequiresPermissions("repairType:delete")
    public ResultMessage delete(@RequestBody Integer[] typeIds) {
        communityRepairTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
