package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.vo.ProviderInfoEntityVO;
import com.info.modules.user.form.*;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-24 16:06:30
 */
public interface IProviderInfoService extends IService<ProviderInfoEntityVO> {


    /**
     * 功能描述: 商家信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/24 18:10
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 获取店铺信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/24 18:13
     * @Return:
     */
    ProviderInfoEntityVO getProviderInfoById(Integer providerId);

    /**
     * 功能描述: 根据店铺类型主键查询店铺信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 11:41
     * @Return:
     */
    PageUtils typeList(Integer providerId);

    /**
     * 功能描述: 修改店铺信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/13 11:21
     * @Return:
     */
    ResultMessage saveProviderInfo(ProviderInfoEntityVO providerInfoEntityVO, MultipartFile[] file);


    /**
     * @Description 根据验证码登录————商户端
     * @Author LiuDan
     * @Date 2019/7/12 21:08
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage loginCode(UserRegistForm registForm);

    /**
     * @Description 商户账号密码登录
     * @Author LiuDan
     * @Date 2019/7/13 14:37
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage login(LoginForm form);


    /**
     * @Description 根据mobile查询用户信息
     * @Author LiuDan
     * @Date 2019/6/14 22:50
     * @Param
     * @Return
     * @Exception
     */
    ProviderInfoEntityVO findByMobile(String mobile);

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
     * @Description 用户重置密码
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
     * @Description 查询电话号码是否注册, true 已经注册过, false 未注册
     * @Author LiuDan
     * @Date 2019/7/13 15:31
     * @Param
     * @Return
     * @Exception
     */
    boolean checkMobile(String mobile);


    /**
     * @Description 查询个人信息
     * @Author LiuDan
     * @Date 2019/7/13 15:31
     * @Param
     * @Return
     * @Exception
     */
    ResultMessage findUserInfoById(Integer userId);

    /**
     * @Description 更新用户基本信息 -- 前台
     * @Author LiuDan
     * @Date 2019/7/13 15:31
     * @Param
     * @Return
     * @Exception
     */
    Boolean updateUser(UserUpdateForm form);
}

