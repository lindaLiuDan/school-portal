package com.info.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.sys.entity.SysUserEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: 系统用户
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/6/26 11:05
 * @Return:
 */
public interface SysUserService extends IService<SysUserEntity> {


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:04
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 查询用户的所有菜单ID
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:05
     * @Return:
     */
    List<Long> queryAllMenuId(Long userId);

    /**
     * 保存用户
     */
    void saveUser(SysUserEntity user);

    /**
     * 修改用户
     */
    void update(SysUserEntity user);

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);

    /**
     * 功能描述: 根据用户名，查询系统用户
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:06
     * @Return:
     */
    SysUserEntity queryByUserName(String username);
}
