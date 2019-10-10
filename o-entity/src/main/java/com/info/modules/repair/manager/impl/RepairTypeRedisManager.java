package com.info.modules.repair.manager.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.modules.repair.manager.IRepairTypeRedisManager;
import com.info.manager.CrudRedisManager;
import com.info.redis.RedisKeyUtils;
import com.info.redis.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 高山溪
 * @Date: 2019/6/14 19:54
 * @Description:
 */
@Component
public class RepairTypeRedisManager extends CrudRedisManager<CommunityRepairTypeEntity> implements IRepairTypeRedisManager {


    /**
     * 功能描述: 日志方法调用
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:33
     * @Return:
     */
    private static final Logger logger = LoggerFactory.getLogger(CrudRedisManager.class);

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 10:36
     * @Return:
     */
    @Autowired
    private RedisUtils redisUtils;


    /**
     * 功能描述: 获取所有类别的集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:25
     * @Return:
     */
    @Override
    public List<CommunityRepairTypeEntity> RepairType(String key) {
        try {
            String jsons = redisUtils.get(key);
            if (jsons != null) {
                return JSON.parseArray(jsons, CommunityRepairTypeEntity.class);
            }
        } catch (Exception e) {
            logger.error("RepairType获取报修类型,Redis异常,Exception{},异常信息为:", e);
        }
        return null;
    }

    /**
     * 功能描述: 循环向list中存储集合对象
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/16 18:50
     * @Return:
     */
    @Override
    public void setRepairType(String key, List<CommunityRepairTypeEntity> list) {
        try {
            redisUtils.set(key, JSON.toJSON(list));
            list.stream().forEach(info -> {
                redisUtils.hset(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, info.getTypeId().toString(), JSON.toJSONString(info));
            });
        } catch (Exception e) {
            logger.error("循环存储报修类型,Redis异常,Exception{},异常信息为:", e);
        }
    }
}
