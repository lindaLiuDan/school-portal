package com.info.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.sys.dao.ISysUserTokenDao;
import com.info.modules.sys.entity.SysUserTokenEntity;
import com.info.modules.sys.oauth2.TokenGenerator;
import com.info.modules.sys.service.ISysUserTokenService;
import com.info.utils.ResultMessage;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/5/27 18:54
 * @Return:
 */
@Service("sysUserTokenService")
public class SysUserTokenServiceImpl extends ServiceImpl<ISysUserTokenDao, SysUserTokenEntity> implements ISysUserTokenService {


    //12小时后过期
    private final static int EXPIRE = 3600 * 12;


    /**
     * 功能描述: 生成token
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/26 13:38
     * @Return:
     */
    @Override
    public ResultMessage createToken(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();

        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //判断是否生成过token
        SysUserTokenEntity tokenEntity = this.getById(userId);
        if (tokenEntity == null) {
            tokenEntity = new SysUserTokenEntity();
            tokenEntity.setUserId(userId);
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //保存token
            this.save(tokenEntity);
        } else {
            tokenEntity.setToken(token);
            tokenEntity.setUpdateTime(now);
            tokenEntity.setExpireTime(expireTime);
            //更新token
            this.updateById(tokenEntity);
        }
        ResultMessage resultMessage = ResultMessage.ok().put("token", token).put("expire", EXPIRE);
        return resultMessage;
    }

    /**
     * 功能描述: 退出，修改token值
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/26 13:38
     * @Return:
     */
    @Override
    public void logout(long userId) {
        //生成一个token
        String token = TokenGenerator.generateValue();
        //修改token
        SysUserTokenEntity tokenEntity = new SysUserTokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        this.updateById(tokenEntity);
    }
}
