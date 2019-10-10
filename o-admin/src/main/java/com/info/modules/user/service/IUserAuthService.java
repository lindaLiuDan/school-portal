package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
public interface IUserAuthService extends IService<UserAuthEntity> {

    /**
     * 功能描述: 会员业主身份认证表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 17:59
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

