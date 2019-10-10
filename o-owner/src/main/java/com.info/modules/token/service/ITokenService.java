package com.info.modules.token.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.TokenEntity;

/**
 * 功能描述: 用户Token
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/6/8 14:14
 * @Return:
 */
public interface ITokenService extends IService<TokenEntity> {


    /**
     * 功能描述: 查询token对应的用户
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 14:15
     * @Return:
     */
    TokenEntity queryByToken(String token);

    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token信息
     */
    TokenEntity createToken(long userId);

    /**
     * 设置token过期
     *
     * @param userId 用户ID
     */
    void expireToken(long userId);

}
