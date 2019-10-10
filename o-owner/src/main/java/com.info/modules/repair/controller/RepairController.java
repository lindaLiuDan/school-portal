package com.info.modules.repair.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.repair.entity.CommunityRepairEntity;
import com.info.modules.repair.service.ICommunityRepairService;
import com.info.utils.ConfigConstant;
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
 * 社区物业报修表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:06
 */
@RestController
@RequestMapping("api/repair")
public class RepairController extends AbstractController {


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
    @Login
    @GetMapping("/list")
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
    @Login
    @GetMapping("/info/{repairId}")
    public ResultMessage info(@PathVariable("repairId") Integer repairId) {
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
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody CommunityRepairEntity communityRepair) {
        ValidatorUtils.validateEntity(communityRepair, ConfigConstant.ERROR, AddGroup.class);
        communityRepair.setCreatorTime(DateUtils.now());
        return communityRepairService.saveCommunityRepair(communityRepair);
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-14 14:11:06
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody CommunityRepairEntity communityRepair) {
        ValidatorUtils.validateEntity(communityRepair, ConfigConstant.ERROR, UpdateGroup.class);
        communityRepair.setEditorTime(DateUtils.now());
        communityRepairService.updateById(communityRepair);
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
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] repairIds) {
        communityRepairService.removeByIds(Arrays.asList(repairIds));
        return ResultMessage.ok();
    }

}
