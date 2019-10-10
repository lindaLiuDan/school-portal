package com.info.modules.advertis.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.modules.advertis.dao.IAdvertisInfoDao;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.modules.advertis.manager.IAdvertisInfoRedisManager;
import com.info.modules.advertis.service.IAdvertisInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 功能描述: 社区小区广告信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 16:51
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<AdvertisInfoEntity> page = this.page(
                new Query<AdvertisInfoEntity>().getPage(params),
                new QueryWrapper<AdvertisInfoEntity>()
                        .select("advertis_id,ad_id,info_id,info_no,link,info_name,img,small_img")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
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
                advertisInfoRedisManager.hset(RedisKeyUtils.AdvertisKeys.ADVERTIS_LIST, JSON.toJSONString(list), infoId.toString(), "存储社区广告列表，AdvertisKeys.ADVERTIS_LIST：" + infoId + "异常：");
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

    /**
     * 功能描述: 添加广告信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:20
     * @Return:
     */
    @Override
    public ResultMessage addAdvertisInfo(AdvertisInfoEntity advertisInfoEntity) {
        Boolean flag = this.save(advertisInfoEntity);
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "添加失败...");
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:23
     * @Return:
     */
    @Override
    public ResultMessage delAdvertisInfo(Integer advertisId, String InfoId) {
        Boolean flag = this.removeById(advertisId);
        if (flag) {
            advertisInfoRedisManager.hdel(RedisKeyUtils.AdvertisKeys.ADVERTIS_INFO, "删除广告详情，AdvertisKeys.ADVERTIS_INFO：" + advertisId + "异常：", advertisId.toString());
            advertisInfoRedisManager.hdel(RedisKeyUtils.AdvertisKeys.ADVERTIS_LIST, "删除广告列表，AdvertisKeys.ADVERTIS_LIST：" + advertisId + "异常：", InfoId);
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除失败...");
    }

    /**
     * 功能描述: 更改广告信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:29
     * @Return:
     */
    @Override
    public ResultMessage updateAdvertisInfo(AdvertisInfoEntity advertisInfoEntity) {
        Boolean flag = this.updateById(advertisInfoEntity);
        if (flag) {
            advertisInfoRedisManager.hdel(RedisKeyUtils.AdvertisKeys.ADVERTIS_INFO, "删除广告详情，AdvertisKeys.ADVERTIS_INFO：" + advertisInfoEntity.getAdvertisId() + "异常：", advertisInfoEntity.getAdvertisId().toString());
            advertisInfoRedisManager.hdel(RedisKeyUtils.AdvertisKeys.ADVERTIS_LIST, "删除广告列表，AdvertisKeys.ADVERTIS_LIST：" + advertisInfoEntity.getInfoId() + "异常：", advertisInfoEntity.getInfoId().toString());
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "修改失败...");
    }


}
