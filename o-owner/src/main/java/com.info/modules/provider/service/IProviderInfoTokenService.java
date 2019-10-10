package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderInfoTokenEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 用户Token
 *
 * @author Gaosx
 * @email
 * @date 2019-07-13 10:58:06
 */
public interface IProviderInfoTokenService extends IService<ProviderInfoTokenEntity> {

    /**
     * 功能描述: 查询token对应的用户
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 14:15
     * @Return:
     */
    ProviderInfoTokenEntity queryByToken(String token);

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token信息
     */
    ProviderInfoTokenEntity createToken(Integer userId);

    /**
     * 设置token过期
     *
     * @param userId 用户ID
     */
    void expireToken(Integer userId);

}

