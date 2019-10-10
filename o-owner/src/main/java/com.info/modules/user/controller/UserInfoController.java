package com.info.modules.user.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.form.*;
import com.info.modules.user.service.ISendService;
import com.info.modules.user.service.IUserInfoService;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;


import com.info.modules.sys.service.ICodeService;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@RestController
@RequestMapping("api/userinfo")
public class UserInfoController extends AbstractController {


    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private ICodeService sysCodeService;

    @Autowired
    private ISendService sendService;


    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-13 14:47:56
     * @Return:
     */
    @Login
    @GetMapping("/info/{userId}")
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
    @Login
    @RequestMapping("/save")
    public ResultMessage save(@RequestBody UserInfoEntity userInfo) {
        ValidatorUtils.validateEntity(userInfo, 0, AddGroup.class);
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
    @Login
    @RequestMapping("/update")
    public ResultMessage update(@RequestBody UserInfoEntity userInfo) {
        ValidatorUtils.validateEntity(userInfo, 0, UpdateGroup.class);
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
    @Login
    @RequestMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] userIds) {
        userInfoService.removeByIds(Arrays.asList(userIds));
        return ResultMessage.ok();
    }


    /**
     * @Description 发送验证码————  这个需要前端控制 五分钟 之内不能重复发送
     * @Author LiuDan
     * @Date 2019/6/15 9:34
     * @Param
     * @Return
     * @Exception
     */
    @PostMapping("send")
    @ApiOperation("发送手机验证码的方法")
    public ResultMessage sends(SendSMSForm sendSMSForm) {
        //其余的存储在redis的过期时间5分钟
        Date now = DateUtils.now();
        Date date = new Date(now.getTime() + 5 * 60 * 1000);
        sendSMSForm.setStartTime(now);
        sendSMSForm.setEndTime(date);
        ResultMessage send = sendService.send(sendSMSForm);
        return send;
    }



        /**
         * @Description 验证码登录
         * @Author LiuDan
         * @Date 2019/6/14 12:30
         * @Param
         * @Return
         * @Exception
         */
    @RequestMapping("/reg")
    public ResultMessage regist(UserRegistForm registForm) {
        logger.info("验证码登录======" + registForm.getMobile() + "===============" + registForm.getCheckCode());
        return userInfoService.regUser(registForm);
    }

    /**
     * @Description登录
     * @Author LiuDan
     * @Date 2019/6/14 12:30
     * @Param
     * @Return
     * @Exception
     */
    @PostMapping("/login")
    public ResultMessage login(@ApiIgnore LoginForm form) {
        ValidatorUtils.validateEntity(form, 0, AddGroup.class);
        logger.info("登录------信息是：" + form.getMobile() + "------------" + form.getPassword());
        return userInfoService.login(form);
    }


    /**
     * @Description 退出登录
     * @Author LiuDan
     * @Date 2019/6/15 10:35
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/logout")
    public ResultMessage logOut(Integer userId, HttpServletRequest request) {
        return userInfoService.logout(userId, request);
    }


    /**
     * @Description 添加登录密码
     * @Author LiuDan
     * @Date 2019/6/21 11:47
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/addPsw")
    public ResultMessage addPsw(Integer userId, String password) {
        if (!StringUtils.isNotBlank(password)) {
            return ResultMessage.error("参数错误！");
        }
        return userInfoService.addPsw(userId, password);
    }

    /**
     * @Description 用户修改密码  个人中心  设置
     * @Author LiuDan
     * @Date 2019/6/15 9:33
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/updatePassword")
    public ResultMessage updatePassword(UserUpdatePwdForm form) {
        ValidatorUtils.validateEntity(form, AddGroup.class);
        return userInfoService.updatePassword(form);
    }

    /**
     * @Description 修改绑定手机号 个人中心  设置
     * @Author LiuDan
     * @Date 2019/6/15 9:33
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/updateMobile")
    public ResultMessage updateMobile(@ApiIgnore UpdateMobileForm updateMobileForm) {
        ValidatorUtils.validateEntity(updateMobileForm, AddGroup.class);
        boolean b = sysCodeService.checkCode(updateMobileForm.getMobile(), updateMobileForm.getCode());
        if (!b) {
            return ResultMessage.error("验证码错误");
        }
        return userInfoService.updateMobile(updateMobileForm);
    }


    /**
     * @Description 个人信息
     * @Author LiuDan
     * @Date 2019/6/15 10:47
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/findUserInfo")
    public ResultMessage findUserInfo(Integer userId) {
        if (userId == null) {
            return ResultMessage.error("参数错误!");
        }
        return userInfoService.findUserInfoById(userId);
    }

    /**
     * @param
     * @return
     * @description: 更改用户信息  --- 前台
     * @author liudan
     * @date 2019/4/8 19:04
     */
    @Login
    @RequestMapping("/updateUser")
    public ResultMessage updateUserName(UserUpdateForm form) {
        /*if (userName == "" && img == null) {
            ResultMessage.error(0, "参数错误");
        }*/
        Boolean flage = userInfoService.updateUser(form);
        if (flage) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }


    /**
     * @Description 根据用户查询该用户所属小区
     * @Author LiuDan
     * @Date 2019/6/25 10:30
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @RequestMapping("/getCommunity")
    public ResultMessage getCommunity(Integer userId) {
        if (userId == null) {
            return ResultMessage.error("参数错误");
        }
        return userInfoService.getCommunity(userId);
    }

}
