package com.info.modules.test.controller;

import com.info.common.base.AbstractController;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.form.RegisterForm;
import com.info.modules.user.service.IUserInfoService;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 注册接口
 *
 * @author Gaosx
 */
@RestController
@RequestMapping("/api")
@Api(tags="注册接口")
public class ApiRegisterController extends AbstractController {

    @Autowired
    private IUserInfoService userInfoService;

    @PostMapping("register")
    @ApiOperation("注册")
    public ResultMessage register(@RequestBody RegisterForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);
        UserInfoEntity user = new UserInfoEntity();
        user.setMobile(form.getMobile());
        user.setUserName(form.getMobile());
        user.setPwd(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreatorTime(new Date());
        userInfoService.save(user);
        return ResultMessage.ok();
    }
}
