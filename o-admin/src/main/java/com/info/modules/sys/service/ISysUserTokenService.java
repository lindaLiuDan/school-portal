package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysUserTokenEntity;
import com.info.utils.ResultMessage;

/**
 * 功能描述: 用户Token
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/6/26 13:34
 * @Return:
 */
public interface ISysUserTokenService extends IService<SysUserTokenEntity> {

    /**
     * 生成token
     *
     * @param userId 用户ID
     */
    ResultMessage createToken(long userId);

    /**
     * 退出，修改token值
     *
     * @param userId 用户ID
     */
    void logout(long userId);

}
