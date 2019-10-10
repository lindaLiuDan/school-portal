package com.info.modules.sys.service;

import com.info.modules.sys.entity.SysUserEntity;
import com.info.modules.sys.entity.SysUserTokenEntity;

import java.util.Set;

/**
 * 功能描述: shiro相关接口
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:27
 * @Return:
 */
public interface IShiroService {


    /**
     * 功能描述: 获取用户权限列表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:52
     * @Return:
     */
    Set<String> getUserPermissions(long userId);

    /**
     * 功能描述: g根据token查询用户信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:51
     * @Return:
     */
    SysUserTokenEntity queryByToken(String token);

    /**
     * 功能描述: 根据用户ID，查询用户
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:52
     * @Return:
     */
    SysUserEntity queryUser(Long userId);
}
