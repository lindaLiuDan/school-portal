package com.info.modules.test.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.user.form.LoginForm;
import com.info.modules.user.service.IUserInfoService;
import com.info.modules.token.service.ITokenService;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 登录接口
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("/api")
@Api(tags="登录接口")
public class ApiLoginController extends AbstractController {

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ITokenService ITokenService;


    @PostMapping("login")
    @ApiOperation("登录")
    public ResultMessage login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form, ConfigConstant.NOTDEL);

        //用户登录
        Map<String, Object> map = userInfoService.login(form);

        return ResultMessage.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public ResultMessage logout(@ApiIgnore @RequestAttribute("userId") long userId){
        ITokenService.expireToken(userId);
        return ResultMessage.ok();
    }

}
