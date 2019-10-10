package com.info.modules.user.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.modules.user.form.UserAuthForm;
import com.info.modules.user.service.IUserAuthService;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.utils.PageUtils;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-13 14:47:56
 */
@RestController
@RequestMapping("api/auth")
public class UserAuthController extends AbstractController {


    @Autowired
    private IUserAuthService userAuthService;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 列表查询
     * @Date: 2019-06-14 14:03:54
     */
    @Login
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = userAuthService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 提交用户认证信息 __用下边的addAuth方法
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@Valid @RequestBody UserAuthEntity userAuth) {
        ValidatorUtils.validateEntity(userAuth, ConfigConstant.ERROR, AddGroup.class);
        userAuth.setCreatorTime(DateUtils.now());
        userAuthService.save(userAuth);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 获取用户ID主键获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @Login
    @GetMapping("/get/{userId}")
    public ResultMessage get(@PathVariable("userId") Integer userId) {
        Assert.isNull(userId, "用户ID不能为空", ConfigConstant.ERROR);
        UserAuthEntity userAuth = userAuthService.getUserById(userId);
        return ResultMessage.ok(userAuth);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @Login
    @GetMapping("/info/{authId}")
    public ResultMessage info(@PathVariable("authId") Integer authId) {
        Assert.isNull(authId, "认证信息ID不能为空", ConfigConstant.ERROR);
        UserAuthEntity userAuth = userAuthService.getById(authId);
        return ResultMessage.ok(userAuth);
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody UserAuthEntity userAuth) {
        ValidatorUtils.validateEntity(userAuth, ConfigConstant.ERROR, UpdateGroup.class);
        userAuth.setEditorTime(DateUtils.now());
        userAuth.setEditor(userAuth.getUserId());
        //全部更新
        return userAuthService.updateUserById(userAuth);
    }

    /**
     * @Description 房屋认证——app
     * @Author LiuDan
     * @Date 2019/6/16 22:26
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/addAuth")
    public ResultMessage addAuth(UserAuthForm form) {
        ValidatorUtils.validateEntity(form, ConfigConstant.ERROR, AddGroup.class);
        return userAuthService.addAuth(form);
    }

    /**
     * @Description 重新认证_根据id 查询出来信息
     * @Author LiuDan
     * @Date 2019/6/16 22:26
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getAuthById")
    public ResultMessage getAuthById(Integer authId) {
        Assert.isNull(authId, "认证信息ID不能为空", ConfigConstant.ERROR);
        return userAuthService.getAuthById(authId);
    }

    /**
     * @Description 重新认证_
     * @Author LiuDan
     * @Date 2019/6/16 22:34
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/updateAuth")
    public ResultMessage updateAuth(UserAuthForm form) {
        return userAuthService.updateAuth(form);
    }
}
