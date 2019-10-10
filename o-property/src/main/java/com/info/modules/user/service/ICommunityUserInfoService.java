package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.CommunityUserInfoEntity;
import com.info.modules.user.form.LoginForm;
import com.info.modules.user.form.UpdateMobileForm;
import com.info.modules.user.form.UserUpdatePwdForm;
import com.info.utils.ResultMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-11 19:01:05
 */
public interface ICommunityUserInfoService extends IService<CommunityUserInfoEntity> {



    /**
     * @Description 用户登录
     * @Author LiuDan
     * @Date 2019/6/14 22:49
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage login(LoginForm form);

    /**
     * @Description 退出登录
     * @Author LiuDan
     * @Date 2019/6/15 10:35
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage logout(Integer userId, HttpServletRequest request);



    /**
     * @Description用户重置密码
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage updatePassword(UserUpdatePwdForm form);

    /**
     * @Description 修改绑定手机号
     * @Author LiuDan
     * @Date 2019/6/14 22:51
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage updateMobile(UpdateMobileForm updateMobileForm);

    /**
     * @Description根据mobile 查询用户信息以及判断用户是否存在
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    CommunityUserInfoEntity findByMobile(String mobile);

    /**
     * @Description查询电话号码是否注册,true 已经注册过, false 未注册
     * @Author LiuDan
     * @Date 2019/6/15 9:46
     * @Param
     * @Return
     * @Exception
     */
    boolean checkMobile(String mobile);

}

