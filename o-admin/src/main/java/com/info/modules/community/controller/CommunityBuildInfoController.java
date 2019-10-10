package com.info.modules.community.controller;

import com.info.common.annotation.SysLog;
import com.info.date.DateUtils;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.service.ICommunityBuildInfoService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区楼房信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 18:36:11
 */
@RestController
@RequestMapping("build/Info")
public class CommunityBuildInfoController extends AbstractController {


    @Autowired
    private ICommunityBuildInfoService communityBuildInfoService;


    /**
     * 功能描述: 无分页查询所有的楼房信息，根据不同的查询类型
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @SysLog
    @GetMapping("/getBuildInfo")
    @RequiresPermissions("buildInfo:list")
    public ResultMessage allBuildInfo(@RequestParam Map<String, Object> params) {
        return ResultMessage.ok(communityBuildInfoService.all(params));
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @RequestMapping("/info/{buildId}")
    @RequiresPermissions("buildInfo:info")
    public ResultMessage info(@PathVariable("buildId") Integer buildId) {
        Assert.isNull(buildId, "楼房信息ID不能为空", ConfigConstant.ERROR);
        CommunityBuildInfoEntity communityBuildInfo = communityBuildInfoService.getById(buildId);
        return ResultMessage.ok(communityBuildInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @SysLog
    @RequestMapping("/save")
    @RequiresPermissions("buildInfo:save")
    public ResultMessage save(@RequestBody CommunityBuildInfoEntity communityBuildInfo) {
        ValidatorUtils.validateEntity(communityBuildInfo, ConfigConstant.ERROR, AddGroup.class);
        communityBuildInfo.setCreator(getUserId().intValue());
        communityBuildInfo.setCreatorTime(DateUtils.now());
        return communityBuildInfoService.addBuildInfo(communityBuildInfo);
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @SysLog
    @RequestMapping("/update")
    @RequiresPermissions("buildInfo:update")
    public ResultMessage update(@RequestBody CommunityBuildInfoEntity communityBuildInfo) {
        ValidatorUtils.validateEntity(communityBuildInfo,ConfigConstant.ERROR, UpdateGroup.class);
        communityBuildInfo.setEditor(getUserId().intValue());
        communityBuildInfo.setEditorTime(DateUtils.now());
        return communityBuildInfoService.updateBuildInfo(communityBuildInfo);//全部更新
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @SysLog
    @RequestMapping("/del/{buildId}/{buildType}")
    @RequiresPermissions("buildInfo:del")
    public ResultMessage delete(@PathVariable("buildId") Integer buildId, @PathVariable("buildType") Integer buildType) {
        Assert.isNull(buildId, "楼房信息ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(buildType, "楼房信息类型不能为空", ConfigConstant.ERROR);
        return communityBuildInfoService.delBuildInfo(buildId, buildType);
    }

}
