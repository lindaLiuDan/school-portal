package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.CommunityUserTokenEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 物业用户Token
 *
 * @author Gaosx
 * @email
 * @date 2019-07-13 10:58:06
 */
public interface ICommunityUserTokenService extends IService<CommunityUserTokenEntity> {


    /**
     * 功能描述: 根据token查询出来对应的用户
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 14:15
     * @Return:
     */
    CommunityUserTokenEntity queryByToken(String token);


    /**
     * @Description 生成token
     * @Author LiuDan
     * @Date 2019/7/13 11:07
     * @Param userId 用户ID
     * @Return 返回token信息
     * @Exception
     */
    CommunityUserTokenEntity createToken(Integer userId);

    /**
     * @Description token 过期
     * @Author LiuDan
     * @Date 2019/7/13 11:08
     * @Param
     * @Return
     * @Exception
     */
    void expireToken(Integer userId);

}

