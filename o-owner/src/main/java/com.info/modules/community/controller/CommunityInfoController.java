package com.info.modules.community.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.service.ICommunityInfoService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 社区小区信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@RestController
@RequestMapping("api/community")
public class CommunityInfoController extends AbstractController {

    @Autowired
    private ICommunityInfoService communityInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页获取所有的信息列表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/11 15:44
     * @Return:
     */
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        List<CommunityInfoEntity> list = communityInfoService.all(params);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 15:08:22
     * @Return:
     */
    @Login
    @GetMapping("/info/{infoId}")
    public ResultMessage info(@PathVariable("infoId") Integer infoId) {
        CommunityInfoEntity communityInfo = communityInfoService.getById(infoId);
        return ResultMessage.ok(communityInfo);
    }




}
