package com.info.modules.repair.controller;

import com.info.date.DateUtils;
import com.info.modules.repair.entity.CommunityRepairEntity;
import com.info.modules.repair.service.ICommunityRepairService;
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
 * 社区物业报修表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:06
 */
@RestController
@RequestMapping("repair/info")
public class CommunityRepairController extends AbstractController {

    @Autowired
    private ICommunityRepairService communityRepairService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 14:11:06
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("repair:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityRepairService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 14:11:06
     * @Return:
     */
    @GetMapping("/info/{repairId}")
    @RequiresPermissions("repair:info")
    public ResultMessage info(@PathVariable("repairId") Integer repairId) {
        Assert.isNull(repairId, "报修ID不能为空", ConfigConstant.ERROR);
        CommunityRepairEntity communityRepair = communityRepairService.getCommunityRepairById(repairId);
        return ResultMessage.ok(communityRepair);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 14:11:06
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("repair:save")
    public ResultMessage save(@RequestBody CommunityRepairEntity communityRepair) {
        ValidatorUtils.validateEntity(communityRepair, ConfigConstant.ERROR, AddGroup.class);
        communityRepair.setCreator(getUserId().intValue());
        communityRepair.setCreatorTime(DateUtils.now());
        communityRepairService.save(communityRepair);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 14:11:06
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("repair:update")
    public ResultMessage update(@RequestBody CommunityRepairEntity communityRepair) {
        ValidatorUtils.validateEntity(communityRepair, ConfigConstant.ERROR, UpdateGroup.class);
        communityRepair.setEditor(getUserId().intValue());
        communityRepair.setEditorTime(DateUtils.now());
        communityRepairService.updateById(communityRepair);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 14:11:06
     * @Return:
     */
    @RequestMapping("/del")
    @RequiresPermissions("repair:del")
    public ResultMessage delete(@RequestBody Integer[] repairIds) {
        communityRepairService.removeByIds(Arrays.asList(repairIds));
        return ResultMessage.ok();
    }

}
