package com.info.modules.user.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.sys.service.ICodeService;
import com.info.modules.user.form.LoginForm;
import com.info.modules.user.form.SendSMSForm;
import com.info.modules.user.form.UpdateMobileForm;
import com.info.modules.user.form.UserUpdatePwdForm;
import com.info.modules.user.service.ICommunityUserInfoService;
import com.info.modules.user.service.ISendService;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-07-11 19:01:05
 */
@RestController
@RequestMapping("api/communityuserinfo")
public class CommunityUserInfoController extends AbstractController {

    @Autowired
    private ICommunityUserInfoService communityUserInfoService;


    @Autowired
    private ICodeService sysCodeService;


    @Autowired
    private ISendService sendService;


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
        return communityUserInfoService.login(form);
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
        return communityUserInfoService.logout(userId, request);
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
        return communityUserInfoService.updatePassword(form);
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
        return communityUserInfoService.updateMobile(updateMobileForm);
    }


}

