package com.info.modules.token.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.token.dao.ITokenDao;
import com.info.modules.user.entity.TokenEntity;
import com.info.modules.token.service.ITokenService;
import com.info.redis.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 功能描述:
 *
 * @Params: * @param null
 * @Author: Gaosx  By User
 * @Date: 2019/5/31 9:47
 * @Return:
 */
@Service("tokenService")
public class TokenServiceImpl extends ServiceImpl<ITokenDao, TokenEntity> implements ITokenService {
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;


    @Autowired
    private ICrudRedisManager<TokenServiceImpl> crudRedisManager;


    @Override
    public TokenEntity queryByToken(String token) {
        return this.getOne(new QueryWrapper<TokenEntity>().eq("token", token));
    }

    @Override
    public TokenEntity createToken(long userId) {
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //生成token
        String token = generateToken();

        //保存或更新用户token
        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(expireTime);
        this.saveOrUpdate(tokenEntity);
        crudRedisManager.delete(RedisKeyUtils.UserInfoKyes.TOKEN + userId, "业主token删除redis失败，Redis异常,Exception{},异常信息为：");
        crudRedisManager.set(RedisKeyUtils.UserInfoKyes.TOKEN + userId, JSON.toJSONString(tokenEntity), expireTime.getTime(), "业主token放入redis失败，Redis异常,Exception{},异常信息为：");
        return tokenEntity;
    }

    @Override
    public void expireToken(long userId) {
        Date now = new Date();

        TokenEntity tokenEntity = new TokenEntity();
        tokenEntity.setUserId(userId);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(now);
        this.saveOrUpdate(tokenEntity);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
