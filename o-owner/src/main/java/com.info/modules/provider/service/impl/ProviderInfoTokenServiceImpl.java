package com.info.modules.provider.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.manager.ICrudRedisManager;
import com.info.modules.provider.dao.IProviderInfoTokenDao;
import com.info.modules.provider.entity.ProviderInfoTokenEntity;
import com.info.modules.provider.service.IProviderInfoTokenService;
import com.info.redis.RedisKeyUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * 用户Token
 *
 * @author Gaosx
 * @email
 * @date 2019-07-13 10:58:06
 */
@Service("providerInfoTokenService")
public class ProviderInfoTokenServiceImpl extends ServiceImpl<IProviderInfoTokenDao, ProviderInfoTokenEntity> implements IProviderInfoTokenService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(ProviderInfoTokenServiceImpl.class);


    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;


    @Autowired
    private ICrudRedisManager<ProviderInfoTokenServiceImpl> crudRedisManager;

    /**
     * 功能描述: 查询token对应的用户
     *
     * @Params: * @param null
     * @Author: Gaosx  By User
     * @Date: 2019/6/8 14:15
     * @Return:
     */
    @Override
    public ProviderInfoTokenEntity queryByToken(String token) {
        return this.getOne(new QueryWrapper<ProviderInfoTokenEntity>().eq("token", token));
    }


    /**
     * 生成token
     *
     * @param userId 用户ID
     * @return 返回token信息
     */
    @Override
    public ProviderInfoTokenEntity createToken(Integer userId) {
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //生成token
        String token = generateToken();

        //保存或更新用户token
        ProviderInfoTokenEntity tokenEntity = new ProviderInfoTokenEntity();
        tokenEntity.setProviderId(userId);
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(expireTime);
        this.saveOrUpdate(tokenEntity);
        crudRedisManager.delete(RedisKeyUtils.ProviderKeys.TOKEN + userId, "商家token删除redis失败，Redis异常,Exception{},异常信息为：");
        crudRedisManager.set(RedisKeyUtils.ProviderKeys.TOKEN + userId, JSON.toJSONString(tokenEntity), expireTime.getTime(), "商家token放入redis失败，Redis异常,Exception{},异常信息为：");

        return tokenEntity;
    }


    /**
     * 设置token过期
     *
     * @param userId 用户ID
     */
    @Override
    public void expireToken(Integer userId) {
        Date now = new Date();

        ProviderInfoTokenEntity tokenEntity = new ProviderInfoTokenEntity();
        tokenEntity.setProviderId(userId);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(now);
        this.saveOrUpdate(tokenEntity);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
