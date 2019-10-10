package com.info.modules.sys.controller;

import com.google.code.kaptcha.Producer;
import com.info.modules.sys.entity.SysUserEntity;
import com.info.modules.sys.form.SysLoginForm;
import com.info.modules.sys.service.ISysCaptchaService;
import com.info.modules.sys.service.ISysUserTokenService;
import com.info.modules.sys.service.SysUserService;
import com.info.modules.sys.shiro.ShiroUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * 登录相关
 *
 * @author Gaosx
 */
@RestController
public class SysLoginController extends AbstractController {

    @Autowired
    private Producer producer;

    @Autowired
    private ISysCaptchaService sysCaptchaService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ISysUserTokenService sysUserTokenService;


    /**
     * 功能描述: 验证码生成URL
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2019/3/26 15:24
     */
    @GetMapping("checkCode")
    public void captcha(HttpServletResponse response, String uuid) throws IOException {
        Assert.isNull(uuid, "UID不能为空", ConfigConstant.ERROR);
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //获取图片验证码
        BufferedImage image = sysCaptchaService.getCaptcha(uuid);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
        IOUtils.closeQuietly(out);
    }

    /**
     * 功能描述: 登录前后端分离式的登录方式
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/5/27 18:35
     * @Return:
     */
    @PostMapping("/sys/login")
    public Map<String, Object> login(@RequestBody SysLoginForm form) throws IOException {
        ValidatorUtils.validateEntity(form, ConfigConstant.ERROR, AddGroup.class);
        Boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
        if (!captcha) {
            return ResultMessage.error(303, "验证码不正确");
        }

        //用户信息
        SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

        //账号不存在、密码错误
        if (user == null || !user.getPassword().equals(ShiroUtils.sha256(form.getPassword(), user.getSalt()))) {
            return ResultMessage.error(305, "账号或密码不正确");
        }

        //账号锁定
        if (user.getStatus() == 0) {
            return ResultMessage.error(306, "账号已被锁定,请联系管理员");
        }
        //生成token，并保存到数据库
        ResultMessage resultMessage = sysUserTokenService.createToken(user.getUserId());
        user.setSalt("");
        user.setPassword("");
        return resultMessage.put("user", user);
    }


    /**
     * 功能描述: 退出
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 10:50
     * @Return:
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        ShiroUtils.logout();
        return "redirect:login.html";
    }

    /**
     * 功能描述: 验证码---非前后端分离方式
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 10:50
     * @Return:
     */
//    @RequestMapping("checkCode")
//    public void captcha(HttpServletResponse response) throws IOException {
//        response.setHeader("Cache-Control", "no-store, no-cache");
//        response.setContentType("image/jpeg");
//        //生成文字验证码
//        String text = producer.createText();
//        //生成图片验证码
//        BufferedImage image = producer.createImage(text);
//        //保存到shiro session
//        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
//
//        ServletOutputStream out = response.getOutputStream();
//        ImageIO.write(image, "jpg", out);
//    }

    /**
     * 功能描述: 登录--非前后端分离方式
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 10:50
     * @Return:
     */
//    @ResponseBody
//    @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
//    public ResultMessage login(String username, String password, String captcha) {
//        String kaptcha = ShiroUtils.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//        if (!captcha.equalsIgnoreCase(kaptcha)) {
//            return ResultMessage.error("验证码不正确");
//        }
//        try {
//            Subject subject = ShiroUtils.getSubject();
//            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
//            subject.login(token);
//        } catch (UnknownAccountException e) {
//            return ResultMessage.error(e.getMessage());
//        } catch (IncorrectCredentialsException e) {
//            return ResultMessage.error("账号或密码不正确");
//        } catch (LockedAccountException e) {
//            return ResultMessage.error("账号已被锁定,请联系管理员");
//        } catch (AuthenticationException e) {
//            return ResultMessage.error("账户验证失败");
//        }
//        return ResultMessage.ok();
//    }

}
