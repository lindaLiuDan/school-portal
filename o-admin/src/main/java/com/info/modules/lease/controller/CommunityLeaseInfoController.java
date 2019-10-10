package com.info.modules.lease.controller;

import com.info.date.DateUtils;
import com.info.modules.lease.entity.CommunityLeaseInfoEntity;
import com.info.modules.lease.service.ICommunityLeaseInfoService;
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
 * 社区租赁信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
@RestController
@RequestMapping("lease/info")
public class CommunityLeaseInfoController extends AbstractController {

    @Autowired
    private ICommunityLeaseInfoService communityLeaseInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("lease:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityLeaseInfoService.queryPage(params);
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
    @GetMapping("/info/{leaseId}")
    @RequiresPermissions("lease:info")
    public ResultMessage info(@PathVariable("leaseId") Integer leaseId) {
        Assert.isNull(leaseId, "租赁信息ID不能为空", ConfigConstant.ERROR);
        CommunityLeaseInfoEntity communityLeaseInfo = communityLeaseInfoService.getById(leaseId);
        return ResultMessage.ok(communityLeaseInfo);
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
    @RequiresPermissions("lease:save")
    public ResultMessage save(@RequestBody CommunityLeaseInfoEntity communityLeaseInfo) {
        ValidatorUtils.validateEntity(communityLeaseInfo, ConfigConstant.ERROR, AddGroup.class);
        communityLeaseInfo.setCreatorTime(DateUtils.now());
        communityLeaseInfoService.save(communityLeaseInfo);
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
    @RequiresPermissions("lease:update")
    public ResultMessage update(@RequestBody CommunityLeaseInfoEntity communityLeaseInfo) {
        ValidatorUtils.validateEntity(communityLeaseInfo, ConfigConstant.ERROR, UpdateGroup.class);
        communityLeaseInfo.setEditorTime(DateUtils.now());
        communityLeaseInfoService.updateById(communityLeaseInfo);//全部更新
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
    @RequiresPermissions("lease:delete")
    public ResultMessage delete(@RequestBody Integer[] leaseIds) {
        communityLeaseInfoService.removeByIds(Arrays.asList(leaseIds));
        return ResultMessage.ok();
    }

}
