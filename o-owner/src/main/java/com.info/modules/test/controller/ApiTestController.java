package com.info.modules.test.controller;

import com.info.common.annotation.Login;
import com.info.common.annotation.LoginUser;
import com.info.common.base.AbstractController;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.utils.ResultMessage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 功能描述: 测试接口
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/2 18:04
 * @Return:
 */
@RestController
@RequestMapping("/api")
@Api(tags = "测试接口")
public class ApiTestController extends AbstractController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息", response = UserInfoEntity.class)
    public ResultMessage userInfo(@ApiIgnore @LoginUser UserInfoEntity user) {
        return ResultMessage.ok(user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public ResultMessage userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId) {
        return ResultMessage.ok(userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public ResultMessage notToken() {
        return ResultMessage.ok("无需token也能访问");
    }

    @GetMapping("token")
    public ResultMessage getRequestHeader(@RequestHeader("token") String token) {
        return ResultMessage.ok(token);
    }
}
