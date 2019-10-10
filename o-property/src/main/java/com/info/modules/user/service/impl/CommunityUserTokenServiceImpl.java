package com.info.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.user.dao.ICommunityUserTokenDao;
import com.info.modules.user.entity.CommunityUserTokenEntity;
import com.info.modules.user.service.ICommunityUserTokenService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 物业用户Token
 *
 * @author Gaosx
 * @email
 * @date 2019-07-13 10:58:06
 */
@Service("communityUserTokenService")
public class CommunityUserTokenServiceImpl extends ServiceImpl<ICommunityUserTokenDao, CommunityUserTokenEntity> implements ICommunityUserTokenService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(CommunityUserTokenServiceImpl.class);




    @Autowired
    private ICrudRedisManager<CommunityUserTokenServiceImpl> crudRedisManager;
    /**
     * 12小时后过期
     */
    private final static int EXPIRE = 3600 * 12;


    @Override
    public CommunityUserTokenEntity queryByToken(String token) {
        return this.getOne(new QueryWrapper<CommunityUserTokenEntity>().eq("token", token));
    }


    /**
     * @Description 生成token
     * @Author LiuDan
     * @Date 2019/7/13 11:07
     * @Param userId 用户ID
     * @Return 返回token信息
     * @Exception
     */
    @Override
    public CommunityUserTokenEntity createToken(Integer userId) {
        //当前时间
        Date now = new Date();
        //过期时间
        Date expireTime = new Date(now.getTime() + EXPIRE * 1000);

        //生成token
        String token = generateToken();

        //保存或更新用户token
        CommunityUserTokenEntity tokenEntity = new CommunityUserTokenEntity();
        tokenEntity.setCommunityUserId(userId);
        tokenEntity.setToken(token);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(expireTime);
        this.saveOrUpdate(tokenEntity);
        crudRedisManager.delete(RedisKeyUtils.OwnerKeys.TOKEN + userId, "业主token删除redis失败，Redis异常,Exception{},异常信息为：");
        crudRedisManager.set(RedisKeyUtils.OwnerKeys.TOKEN + userId, JSON.toJSONString(tokenEntity), expireTime.getTime(), "业主token放入redis失败，Redis异常,Exception{},异常信息为：");
        return tokenEntity;
    }


    /**
     * @Description token 过期
     * @Author LiuDan
     * @Date 2019/7/13 11:08
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public void expireToken(Integer userId) {
        Date now = new Date();

        CommunityUserTokenEntity tokenEntity = new CommunityUserTokenEntity();
        tokenEntity.setCommunityUserId(userId);
        tokenEntity.setUpdateTime(now);
        tokenEntity.setExpireTime(now);
        this.saveOrUpdate(tokenEntity);
    }

    private String generateToken() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
