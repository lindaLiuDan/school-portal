package com.info.modules.mobile.controller;

import com.info.modules.mobile.entity.CommunityMobileEntity;
import com.info.modules.mobile.service.ICommunityMobileService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("mobile/info")
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
    @GetMapping("/list")
    @RequiresPermissions("friend:list")
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
    @GetMapping("/info/{mobileId}")
    @RequiresPermissions("friend:info")
    public ResultMessage info(@PathVariable("mobileId") Integer mobileId) {
        Assert.isNull(mobileId, "mobileId不能为空", ConfigConstant.ERROR);
        CommunityMobileEntity communityMobile = communityMobileService.getCommunityMobile(mobileId);
        return ResultMessage.ok(communityMobile);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-08 11:59:37
     * @Return:
     */
    @GetMapping("/save/{mobileId}")
    @RequiresPermissions("friend:info")
    public ResultMessage save(@RequestBody CommunityMobileEntity communityMobileEntity) {
        Boolean flag = communityMobileService.save(communityMobileEntity);
        if (flag) {
            return ResultMessage.ok("添加便民电话成功");
        }
        return ResultMessage.error(ConfigConstant.ERROR, "添加便民电话失败");
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-08 11:59:37
     * @Return:
     */
    @GetMapping("/del/{mobileId}")
    @RequiresPermissions("friend:info")
    public ResultMessage del(@PathVariable("mobileId") Integer mobileId) {
        return communityMobileService.delCommunityMobile(mobileId);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-08 11:59:37
     * @Return:
     */
    @GetMapping("/update/{mobileId}")
    @RequiresPermissions("friend:info")
    public ResultMessage update(@RequestBody CommunityMobileEntity communityMobileEntity) {
        return communityMobileService.updateCommunityMobile(communityMobileEntity);
    }

}
