package com.info.modules.lease.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.lease.entity.CommunityLeaseInfoEntity;
import com.info.modules.lease.service.ICommunityLeaseInfoService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区租赁信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 15:28:11
 */
@RestController
@RequestMapping("api/lease")
public class LeaseInfoController extends AbstractController {


    /**
     * 功能描述: 社区租赁信息业务实现层
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 17:11
     * @Return:
     */
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
    @Login
    @GetMapping("/list")
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
    @Login
    @GetMapping("/info/{leaseId}")
    public ResultMessage info(@PathVariable("leaseId") Integer leaseId) {
        CommunityLeaseInfoEntity communityLeaseInfo = communityLeaseInfoService.getLeaseInfoById(leaseId);
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
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody CommunityLeaseInfoEntity communityLeaseInfo) {
        ValidatorUtils.validateEntity(communityLeaseInfo, 0, AddGroup.class);
        communityLeaseInfo.setStartTime(DateUtils.now());
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
//    @Login
//    @PostMapping("/update")
//    public ResultMessage update(@RequestBody CommunityLeaseInfoEntity communityLeaseInfo) {
//        ValidatorUtils.validateEntity(communityLeaseInfo, UpdateGroup.class);
//        communityLeaseInfo.setEditorTime(DateUtils.now());
//        communityLeaseInfoService.updateById(communityLeaseInfo);//全部更新
//        return ResultMessage.ok();
//    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
//    @Login
//    @PostMapping("/delete")
//    public ResultMessage delete(@RequestBody Integer[] leaseIds) {
//        communityLeaseInfoService.removeByIds(Arrays.asList(leaseIds));
//        return ResultMessage.ok();
//    }

}
