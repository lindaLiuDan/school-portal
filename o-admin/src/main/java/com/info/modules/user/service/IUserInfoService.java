package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
public interface IUserInfoService extends IService<UserInfoEntity> {

    /**
     * 功能描述: 会员业主信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 17:51
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

