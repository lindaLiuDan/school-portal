package com.info.modules.advertis.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.modules.advertis.dao.IAdvertisInfoDao;
import com.info.modules.advertis.manager.IAdvertisInfoRedisManager;
import com.info.modules.advertis.service.IAdvertisInfoService;
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
 * 社区小区广告信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
@Service("advertisInfoService")
public class AdvertisInfoServiceImpl extends ServiceImpl<IAdvertisInfoDao, AdvertisInfoEntity> implements IAdvertisInfoService {


    @Autowired
    private IAdvertisInfoRedisManager advertisInfoRedisManager;




    /**
     * @Author: Gaosx
     * @Description: 社区小区广告信息表
     * @Date: 2019-06-11 15:08:22
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        IPage<AdvertisInfoEntity> page = this.page(
                new Query<AdvertisInfoEntity>().getPage(params),
                new QueryWrapper<AdvertisInfoEntity>()
                        .select("advertis_id,ad_id,info_id,info_no,link,info_name,img,small_img")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq("infoId", infoId)
                        .ge(StringUtils.isNotBlank(startTime), "creator_time", startTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页获取本社区所有的广告信息列表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 16:56
     * @Return:
     */
    @Override
    public List<AdvertisInfoEntity> all(Integer infoId) {
        String listRedis = advertisInfoRedisManager.hget(RedisKeyUtils.AdvertisKeys.ADVERTIS_LIST, infoId.toString(), "获取本社区广告列表，AdvertisKeys.ADVERTIS_LIST：" + infoId + "异常：");
        if (listRedis == null) {
            List<AdvertisInfoEntity> list = this.list(new QueryWrapper<AdvertisInfoEntity>()
                    .select("advertis_id,ad_id,info_id,info_no,link,info_name,img,small_img")
                    .eq("is_del", ConfigConstant.NOTDEL)
                    .eq("infoId", infoId)
            );
            if (list != null) {
                advertisInfoRedisManager.hset(RedisKeyUtils.AdvertisKeys.ADVERTIS_LIST, JSON.toJSONString(list), infoId.toString(), "获取本社区广告列表，AdvertisKeys.ADVERTIS_LIST：" + infoId + "异常：");
            }
            return list;
        }
        return JSON.parseArray(listRedis, AdvertisInfoEntity.class);
    }

    /**
     * 功能描述: 获取广告详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:12
     * @Return:
     */
    @Override
    public AdvertisInfoEntity getAdvertisInfo(Integer advertisId) {
        AdvertisInfoEntity advertisInfoEntity = advertisInfoRedisManager.hget(RedisKeyUtils.AdvertisKeys.ADVERTIS_INFO, advertisId.toString(), AdvertisInfoEntity.class, "获取本社区广告详情，AdvertisKeys.ADVERTIS_INFO：" + advertisId + "异常：");
        if (advertisInfoEntity == null) {
            advertisInfoEntity = this.getById(advertisId);
            if (advertisInfoEntity != null) {
                advertisInfoRedisManager.hset(RedisKeyUtils.AdvertisKeys.ADVERTIS_INFO, advertisId.toString(), JSON.toJSONString(advertisInfoEntity), "获取本社区广告详情，AdvertisKeys.ADVERTIS_INFO：" + advertisId + "异常：");
            }
            return advertisInfoEntity;
        }
        return advertisInfoEntity;
    }


}
