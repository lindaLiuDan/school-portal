package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.form.*;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-14 14:03:54
 */
public interface IUserInfoService extends IService<UserInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 会员业主信息表
     * @Date: 2019-06-13 14:47:56
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * @Description 用户注册
     * @Author LiuDan
     * @Date 2019/6/13 12:12
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage regUser(UserRegistForm registForm);


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
     * @Description 添加登录密码——app
     * @Author LiuDan
     * @Date 2019/6/21 11:49
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage addPsw(Integer userId, String password);

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
    UserInfoEntity findByMobile(String mobile);

    /**
     * @Description查询电话号码是否注册,true 已经注册过, false 未注册
     * @Author LiuDan
     * @Date 2019/6/15 9:46
     * @Param
     * @Return
     * @Exception
     */
    boolean checkMobile(String mobile);


    /**
     * @param
     * @return
     * @description: 查询个人信息
     * @author liudan
     * @date 2019/3/23 13:54
     */
    ResultMessage findUserInfoById(Integer userId);

    /**
     * @param
     * @return
     * @description: 更新用户昵称和头像 -- 前台
     * @author liudan
     * @date 2019/4/8 19:07
     */
    Boolean updateUser(UserUpdateForm form);


    /**
     * @Description 根据用户查询该用户所属小区
     * @Author LiuDan
     * @Date 2019/6/25 10:30
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage getCommunity(Integer userId);


}

