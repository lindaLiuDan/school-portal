package com.info.modules.user.controller;

import com.info.date.DateUtils;
import com.info.modules.sys.controller.AbstractController;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.service.IUserInfoService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@RestController
@RequestMapping("user/Info")
public class UserInfoController extends AbstractController {

    @Autowired
    private IUserInfoService userInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = userInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @GetMapping("/info/{userId}")
    @RequiresPermissions("user:info")
    public ResultMessage info(@PathVariable("userId") Integer userId) {
        UserInfoEntity userInfo = userInfoService.getById(userId);
        return ResultMessage.ok(userInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:save")
    public ResultMessage save(@RequestBody UserInfoEntity userInfo) {
        ValidatorUtils.validateEntity(userInfo, AddGroup.class);
        userInfo.setCreator(getUserId().intValue());
        userInfo.setCreatorTime(DateUtils.now());
        userInfoService.save(userInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:update")
    public ResultMessage update(@RequestBody UserInfoEntity userInfo) {
        ValidatorUtils.validateEntity(userInfo, UpdateGroup.class);
        userInfo.setEditor(getUserId().intValue());
        userInfo.setEditorTime(DateUtils.now());
        userInfoService.updateById(userInfo);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @RequestMapping("/del")
    @RequiresPermissions("user:del")
    public ResultMessage del(@RequestBody Integer[] userIds) {
        userInfoService.removeByIds(Arrays.asList(userIds));
        return ResultMessage.ok();
    }

}
