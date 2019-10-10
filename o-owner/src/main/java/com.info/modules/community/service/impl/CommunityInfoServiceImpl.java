package com.info.modules.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.manager.ICommunityRedisManager;
import com.info.modules.community.dao.ICommunityInfoDao;
import com.info.modules.community.service.ICommunityInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 社区小区信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@Service("communityInfoService")
public class CommunityInfoServiceImpl extends ServiceImpl<ICommunityInfoDao, CommunityInfoEntity> implements ICommunityInfoService {


    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 16:22
     * @Return:
     */
    @Autowired
    private ICommunityRedisManager redisManager;


    /**
     * @Author: Gaosx
     * @Description: 社区小区信息表
     * @Date: 2019-06-11 15:08:22
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String openId = (String) params.get("openId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityInfoEntity> page = this.page(
                new Query<CommunityInfoEntity>().getPage(params),
                new QueryWrapper<CommunityInfoEntity>()
                        .select("info_id,info_no,info_name,introduction,mobile,a_id,dimension,longitude")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(openId), "open_id", openId)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页获取所有社区信息列表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/11 15:44
     * @Return:
     */
    @Override
    public List<CommunityInfoEntity> all(Map<String, Object> params) {
        String openId = (String) params.get("openId");
        String str = redisManager.get(RedisKeyUtils.OwnerKeys.COMMUNITY_LIST, "获取本城市社区信息,Redis异常,Exception{},异常信息为:");
        if (str == null) {
            List<CommunityInfoEntity> list = this.list(new QueryWrapper<CommunityInfoEntity>()
                    .select("info_id,info_no,info_name,introduction,mobile,open_id,dimension,longitude")
                    .eq("is_del", ConfigConstant.NOTDEL)
                    .eq("open_id", openId)
            );
            if (list != null) {
                redisManager.set(RedisKeyUtils.OwnerKeys.COMMUNITY_LIST, JSON.toJSONString(list), "存储本城市所有社区信息,Redis异常,Exception{},异常信息为:");
                list.stream().forEach(info -> {
                    redisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, info.getOpenId().toString(), JSON.toJSONString(info), "存储本城市单个社区信息,Redis异常,Exception{},异常信息为:");
                });
            }
            return list;
        } else {
            return JSON.parseArray(str, CommunityInfoEntity.class);
        }
    }

    /**
     * 功能描述: 获取单个社区详情信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 17:45
     * @Return:
     */
    @Override
    public CommunityInfoEntity getCommunityInfoById(Integer infoId) {
        CommunityInfoEntity entity = redisManager.hget(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, infoId.toString(), CommunityInfoEntity.class, "社区名称,Redis异常,Exception{},异常信息为:");
        if (entity == null) {
            entity = this.getById(infoId);
            redisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, infoId.toString(), JSON.toJSONString(entity), "社区名称,Redis异常,Exception{},异常信息为:");
        }
        return entity;
    }

}
