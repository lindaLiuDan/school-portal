package com.info.modules.sys.service.impl;

import com.info.modules.sys.dao.ISysUserTokenDao;
import com.info.modules.sys.dao.SysMenuDao;
import com.info.modules.sys.dao.SysUserDao;
import com.info.modules.sys.entity.SysMenuEntity;
import com.info.modules.sys.entity.SysUserEntity;
import com.info.modules.sys.entity.SysUserTokenEntity;
import com.info.modules.sys.service.IShiroService;
import com.info.utils.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 功能描述: ShiroServiceImpl
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:29
 * @Return:
 */
@Service("shiroServiceImpl")
public class ShiroServiceImpl implements IShiroService {

    @Autowired
    private SysMenuDao sysMenuDao;

    @Autowired
    private SysUserDao sysUserDao;

    @Autowired
    private ISysUserTokenDao sysUserTokenDao;


    /**
     * 功能描述: 获取用户权限列表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:14
     * @Return:
     */
    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if (userId == Constant.SUPER_ADMIN) {
            List<SysMenuEntity> menuList = sysMenuDao.selectList(null);
            permsList = new ArrayList<>(menuList.size());
            for (SysMenuEntity menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            permsList = sysUserDao.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for (String perms : permsList) {
            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    /**
     * 功能描述: 根据token查询用户信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:51
     * @Return:
     */
    @Override
    public SysUserTokenEntity queryByToken(String token) {
        return sysUserTokenDao.queryByToken(token);
    }

    /**
     * 功能描述: 根据用户ID，查询用户
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 11:52
     * @Return:
     */
    @Override
    public SysUserEntity queryUser(Long userId) {
        return sysUserDao.selectById(userId);
    }
}
