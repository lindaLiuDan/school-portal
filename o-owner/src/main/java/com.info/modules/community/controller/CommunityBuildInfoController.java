package com.info.modules.community.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.service.ICommunityBuildInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 社区楼房信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 18:36:11
 */
@RestController
@RequestMapping("api/buildInfo")
public class CommunityBuildInfoController extends AbstractController {


    @Autowired
    private ICommunityBuildInfoService communityBuildInfoService;


    /**
     * 功能描述: 根据房号查询对应的 楼号 单元号 楼层--这是一个反向查询的方法 根据ID楼号——》楼层--》单元--》楼号--》社区
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 17:42
     * @Return:
     */
    @Login
    @GetMapping("/getRoomInfo/{buildId}")
    public ResultMessage getRoomInfo(@PathVariable("buildId") Integer buildId) {
        Assert.isNull(buildId, "楼房ID不能为空", ConfigConstant.ERROR);
        CommunityBuildInfoEntity roomInfo = communityBuildInfoService.getRoomInfo(buildId);
        return ResultMessage.ok(roomInfo);
    }

    /**
     * 功能描述: 根据社区ID查询所有的楼号，楼层信息使用redis
     *
     * @Params: * @param null
     * @Author: Gaosx By user
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @Login
    @GetMapping("/getBuildInfo/{infoId}")
    public ResultMessage getBuildInfo(@PathVariable("infoId") Integer infoId) {
        Assert.isNull(infoId, "社区ID不能为空", ConfigConstant.ERROR);
        List<CommunityBuildInfoEntity> list = communityBuildInfoService.getBuildInfo(infoId);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 无分页加载列表
     * Integer infoId 社区ID, Integer buildType　社区楼号楼层单元类型 社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号 buildType, buildId 社区主键ID
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @Login
    @GetMapping("/getInfo")
    public ResultMessage getInfo(@RequestParam Map<String, Object> params) {
        Assert.isNull(params.get("infoId"), "社区ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(params.get("buildType"), "查询类型不能为空", ConfigConstant.ERROR);
        Assert.isNull(params.get("buildId"), "查询房屋主键不能为空", ConfigConstant.ERROR);
        List<CommunityBuildInfoEntity> list = communityBuildInfoService.all(params);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityBuildInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }


}

