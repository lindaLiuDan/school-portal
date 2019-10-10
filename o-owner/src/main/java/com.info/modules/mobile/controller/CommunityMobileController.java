package com.info.modules.mobile.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.mobile.entity.CommunityMobileEntity;
import com.info.modules.mobile.service.ICommunityMobileService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 社区便民生活电话
 *
 * @author Gaosx
 * @email
 * @date 2019-06-08 11:59:37
 */
@RestController
@RequestMapping("api/mobile")
public class CommunityMobileController extends AbstractController {

    @Autowired
    private ICommunityMobileService communityMobileService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-08 11:59:37
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityMobileService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-08 11:59:37
     * @Return:
     */
    @Login
    @GetMapping("/info/{mobileId}")
    public ResultMessage info(@PathVariable("mobileId") Integer mobileId) {
        CommunityMobileEntity communityMobile = communityMobileService.getById(mobileId);
        return ResultMessage.ok(communityMobile);
    }

}
