package com.info.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 功能描述: 系统用户Token
 *
 * @Params: * @param null
 * @Author: Gaosx 960889426@qq.com By User
 * @Date: 2019/5/27 18:30
 * @Return:
 */
public interface ISysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);

}
