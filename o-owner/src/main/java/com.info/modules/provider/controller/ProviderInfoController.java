package com.info.modules.provider.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.provider.service.IProviderInfoService;
import com.info.modules.provider.vo.ProviderInfoEntityVO;
import com.info.modules.sys.service.ICodeService;
import com.info.modules.user.form.*;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
@RestController
@RequestMapping("api/providerInfo")
public class ProviderInfoController extends AbstractController {

    @Autowired
    private IProviderInfoService providerInfoService;

    @Autowired
    private ICodeService sysCodeService;


    /**
     * 功能描述: 分页查询所有的店铺信息--支持多个参数和类型查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = providerInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @GetMapping("/info/{providerId}")
    public ResultMessage info(@PathVariable("providerId") Integer providerId) {
        Assert.isNull(providerId, "店铺ID不能为空", ConfigConstant.ERROR);
        ProviderInfoEntityVO providerInfo = providerInfoService.getProviderInfoById(providerId);
        return ResultMessage.ok(providerInfo);
    }

    /**
     * 功能描述: 根据店铺类型主键查询店铺信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @GetMapping("/typeList/{typeId}")
    public ResultMessage typeList(@PathVariable("typeId") Integer typeId) {
        Assert.isNull(typeId, "店铺类型ID不能为空", ConfigConstant.ERROR);
        ProviderInfoEntityVO providerInfo = providerInfoService.getProviderInfoById(typeId);
        return ResultMessage.ok(providerInfo);
    }

    /**
     * 功能描述: 商家申请入住的接口
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody ProviderInfoEntityVO providerInfo, MultipartFile[] file) {
        ValidatorUtils.validateEntity(providerInfo, ConfigConstant.ERROR, AddGroup.class);

        providerInfo.setCreatorTime(DateUtils.now());
        providerInfoService.save(providerInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 商家修改自己入住填写信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody ProviderInfoEntityVO providerInfo) {
        ValidatorUtils.validateEntity(providerInfo, ConfigConstant.ERROR, UpdateGroup.class);
        providerInfo.setEditorTime(DateUtils.now());
        providerInfoService.updateById(providerInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] providerIds) {
        Assert.isNull(providerIds, "店铺ID不能为空", ConfigConstant.ERROR);
        providerInfoService.removeByIds(Arrays.asList(providerIds));
        return ResultMessage.ok();
    }


    /**
     * @Description 根据验证码登录————商户端
     * @Author LiuDan
     * @Date 2019/7/12 21:07
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/loginCode")
    public ResultMessage loginCode(UserRegistForm registForm) {
        logger.info("商户登录的手机号码为：", registForm.getMobile() + "===============验证码为：" + registForm.getCheckCode());
        return providerInfoService.loginCode(registForm);
    }

    /**
     * @Description 商户密码登录
     * @Author LiuDan
     * @Date 2019/7/13 14:36
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/login")
    public ResultMessage login(@ApiIgnore LoginForm form) {
        ValidatorUtils.validateEntity(form, 0, AddGroup.class);
        logger.info("商户登录------信息是：" + form.getMobile() + "------------" + form.getPassword());
        return providerInfoService.login(form);
    }

    /**
     * @Description  退出登录
     * @Author LiuDan
     * @Date 2019/7/13 15:16
     * @Param
     * @Return
     * @Exception
     */
    @Login
    @PostMapping("/logout")
    public ResultMessage logOut(Integer userId, HttpServletRequest request) {
        return providerInfoService.logout(userId, request);
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
        return providerInfoService.addPsw(userId, password);
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
        return providerInfoService.updatePassword(form);
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
        return providerInfoService.updateMobile(updateMobileForm);
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
        return providerInfoService.findUserInfoById(userId);
    }

    /**
     * @param
     * @return
     * @description: 更改用户信息
     * @author liudan
     * @date 2019/4/8 19:04
     */
    @Login
    @RequestMapping("/updateUser")
    public ResultMessage updateUserName(UserUpdateForm form) {
        Boolean flage = providerInfoService.updateUser(form);
        if (flage) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }
}
