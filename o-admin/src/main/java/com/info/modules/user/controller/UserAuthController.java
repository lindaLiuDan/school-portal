package com.info.modules.user.controller;

import com.info.date.DateUtils;
import com.info.modules.sys.controller.AbstractController;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.modules.user.service.IUserAuthService;
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
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@RestController
@RequestMapping("user/auth")
public class UserAuthController extends AbstractController {

    @Autowired
    private IUserAuthService userAuthService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("userAuth:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = userAuthService.queryPage(params);
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
    @GetMapping("/info/{authId}")
    @RequiresPermissions("userAuth:info")
    public ResultMessage info(@PathVariable("authId") Integer authId) {
        UserAuthEntity userAuth = userAuthService.getById(authId);
        return ResultMessage.ok(userAuth);
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
    @RequiresPermissions("userAuth:save")
    public ResultMessage save(@RequestBody UserAuthEntity userAuth) {
        ValidatorUtils.validateEntity(userAuth, AddGroup.class);
        userAuth.setCreator(getUserId().intValue());
        userAuth.setCreatorTime(DateUtils.now());
        userAuthService.save(userAuth);
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
    @RequiresPermissions("userAuth:update")
    public ResultMessage update(@RequestBody UserAuthEntity userAuth) {
        ValidatorUtils.validateEntity(userAuth, UpdateGroup.class);
        userAuth.setEditor(getUserId().intValue());
        userAuth.setEditorTime(DateUtils.now());
        userAuthService.updateById(userAuth);//全部更新
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
    @RequiresPermissions("userAuth:del")
    public ResultMessage del(@RequestBody Integer[] authIds) {
        userAuthService.removeByIds(Arrays.asList(authIds));
        return ResultMessage.ok();
    }

}
