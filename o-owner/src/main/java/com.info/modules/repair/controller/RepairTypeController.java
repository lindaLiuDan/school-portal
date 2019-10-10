package com.info.modules.repair.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.modules.repair.service.ICommunityRepairTypeService;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 社区物业报修类型信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-14 14:11:07
 */
@RestController
@RequestMapping("api/repairType")
public class RepairTypeController extends AbstractController {

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
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        List<CommunityRepairTypeEntity> list = communityRepairTypeService.all(params);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-14 14:11:07
     * @Return:
     */
    @Login
    @GetMapping("/info/{typeId}")
    public ResultMessage info(@PathVariable("typeId") Integer typeId) {
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
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody CommunityRepairTypeEntity communityRepairType) {
        ValidatorUtils.validateEntity(communityRepairType, AddGroup.class);
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
    @Login
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody CommunityRepairTypeEntity communityRepairType) {
        ValidatorUtils.validateEntity(communityRepairType, UpdateGroup.class);
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
    @Login
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] typeIds) {
        communityRepairTypeService.removeByIds(Arrays.asList(typeIds));
        return ResultMessage.ok();
    }

}
