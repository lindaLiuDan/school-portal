package com.info.modules.community.controller;

import com.info.date.DateUtils;
import com.info.modules.community.entity.CommunityUserInfoEntity;
import com.info.modules.community.service.ICommunityUserInfoService;
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
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-19 11:09:55
 */
@RestController
@RequestMapping("community/user")
public class CommunityUserInfoController extends AbstractController {

    @Autowired
    private ICommunityUserInfoService communityUserInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("community:user:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = communityUserInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("community:user:info")
    public ResultMessage info(@PathVariable("userId") Integer userId) {
        Assert.isNull(userId, "物业管理员ID不能为空", ConfigConstant.ERROR);
        CommunityUserInfoEntity communityUserInfo = communityUserInfoService.getById(userId);
        return ResultMessage.ok(communityUserInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("community:user:save")
    public ResultMessage save(@RequestBody CommunityUserInfoEntity communityUserInfo) {
        ValidatorUtils.validateEntity(communityUserInfo, ConfigConstant.ERROR, AddGroup.class);
        communityUserInfo.setCreator(getUserId().intValue());
        communityUserInfo.setCreatorTime(DateUtils.now());
        Boolean flag = communityUserInfoService.save(communityUserInfo);
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("community:user:update")
    public ResultMessage update(@RequestBody CommunityUserInfoEntity communityUserInfo) {
        ValidatorUtils.validateEntity(communityUserInfo, ConfigConstant.ERROR, UpdateGroup.class);
        communityUserInfo.setEditor(getUserId().intValue());
        communityUserInfo.setEditorTime(DateUtils.now());
        communityUserInfoService.updateById(communityUserInfo);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("community:user:del")
    public ResultMessage del(@RequestBody Integer[] userIds) {
        communityUserInfoService.removeByIds(Arrays.asList(userIds));
        return ResultMessage.ok();
    }

}
