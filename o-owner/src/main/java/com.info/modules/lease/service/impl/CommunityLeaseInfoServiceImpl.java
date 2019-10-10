package com.info.modules.lease.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.manager.ICommunityRedisManager;
import com.info.modules.lease.entity.CommunityLeaseInfoEntity;
import com.info.modules.repair.manager.IRepairRedisManager;
import com.info.manager.ICrudRedisManager;
import com.info.modules.community.service.ICommunityBuildInfoService;
import com.info.modules.community.service.ICommunityInfoService;
import com.info.modules.lease.dao.ICommunityLeaseInfoDao;
import com.info.modules.lease.service.ICommunityLeaseInfoService;
import com.info.redis.RedisKeyUtils;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 社区租赁信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
@Service("communityLeaseInfoService")
public class CommunityLeaseInfoServiceImpl extends ServiceImpl<ICommunityLeaseInfoDao, CommunityLeaseInfoEntity> implements ICommunityLeaseInfoService {


    /**
     * 功能描述: 日志方法调用
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 17:10
     * @Return:
     */
    private static final Logger logger = LoggerFactory.getLogger(CommunityLeaseInfoServiceImpl.class);

    @Autowired
    private ICrudRedisManager<CommunityLeaseInfoEntity> crudRedisManager;

    @Autowired
    private ICommunityBuildInfoService buildInfoService;

    @Autowired
    private ICommunityInfoService communityInfoService;

    @Autowired
    private ICommunityRedisManager communityRedisManager;

    @Autowired
    private IRepairRedisManager repairRedisManager;

    /**
     * 功能描述: 社区租赁信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityLeaseInfoEntity> page = this.page(
                new Query<CommunityLeaseInfoEntity>().getPage(params),
                new QueryWrapper<CommunityLeaseInfoEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 查看详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/18 9:14
     * @Return:
     */
    @Override
    public CommunityLeaseInfoEntity getLeaseInfoById(Integer leaseId) {
        CommunityLeaseInfoEntity leaseInfoEntity = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.LEASE_INFO, leaseId.toString(), CommunityLeaseInfoEntity.class, "获取业主租赁信息详情,Redis异常,Exception{},异常信息为:");
        if (leaseInfoEntity == null) {
            leaseInfoEntity = this.getById(leaseId);
            //根据根据租赁详情查询对应小区名称
            CommunityInfoEntity infoName = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, leaseInfoEntity.getInfoId().toString(), CommunityInfoEntity.class, "获取小区名称,Redis异常,Exception{},异常信息为:");
            if (infoName == null) {
                CommunityInfoEntity info = communityInfoService.getById(leaseInfoEntity.getInfoId());
                if (info != null) {
                    leaseInfoEntity.setInfoName(info.getInfoName());
                    communityRedisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, info.getInfoId().toString(), JSON.toJSONString(info), "存储本城市单个社区信息,Redis异常,Exception{},异常信息为:");
                }
            } else {
                leaseInfoEntity.setInfoName(infoName.getInfoName());
            }
            //查询出来对应的楼层
            CommunityBuildInfoEntity levelName = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.LEVEL_INFO, leaseInfoEntity.getBuildId().toString(), CommunityBuildInfoEntity.class, "获取楼层号,Redis异常,Exception{},异常信息为:");
            if (levelName == null) {
                CommunityBuildInfoEntity buildInfoEntity = buildInfoService.getById(leaseInfoEntity.getBuildId());
                repairRedisManager.hset(RedisKeyUtils.OwnerKeys.LEVEL_INFO, leaseInfoEntity.getBuildId().toString(), JSON.toJSONString(buildInfoEntity), "获取楼层号,Redis异常,Exception{},异常信息为:");
                leaseInfoEntity.setLevel(buildInfoEntity.getBname());
                //根据查询到的楼层信息的parentId查询对应的单元号
                this.getHuildInfo(buildInfoEntity, leaseInfoEntity);
            } else {
                leaseInfoEntity.setLevel(levelName.getBname());
                this.getHuildInfo(levelName, leaseInfoEntity);
            }
            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.LEASE_INFO, leaseId.toString(), JSON.toJSONString(leaseInfoEntity), "获取业主租赁信息详情,Redis异常,Exception{},异常信息为:");
        }
        return leaseInfoEntity;
    }

    /**
     * 功能描述: 配对上面的方法
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 11:49
     * @Return:
     */
    private void getHuildInfo(CommunityBuildInfoEntity buildInfoEntity, CommunityLeaseInfoEntity repair) {
        //根据查询到的楼层号信息的parentId查询对应的单元
        if (buildInfoEntity != null) {
            CommunityBuildInfoEntity unitName = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.UNIT_INFO, buildInfoEntity.getParentId().toString(), CommunityBuildInfoEntity.class, "获取单元号,Redis异常,Exception{},异常信息为:");
            if (unitName == null) {
                unitName = buildInfoService.getById(buildInfoEntity.getParentId());
                repairRedisManager.hset(RedisKeyUtils.OwnerKeys.UNIT_INFO, unitName.getBuildId().toString(), JSON.toJSONString(unitName), "获取单元号,Redis异常,Exception{},异常信息为:");
                repair.setUnit(unitName.getBname());
            } else {
                repair.setUnit(unitName.getBname());
            }
            //查询到的单元信息的parentID查询对应的楼号
            CommunityBuildInfoEntity floorName = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.FLOOR_INFO, unitName.getParentId().toString(), CommunityBuildInfoEntity.class, "获取楼号,Redis异常,Exception{},异常信息为:");
            if (floorName != null) {
                repair.setFloor(floorName.getBname());
            } else {
                floorName = buildInfoService.getById(unitName.getParentId());
                repairRedisManager.hset(RedisKeyUtils.OwnerKeys.FLOOR_INFO, floorName.getBuildId().toString(), JSON.toJSONString(floorName), "获取楼号,Redis异常,Exception{},异常信息为:");
                repair.setFloor(floorName.getBname());
            }
        }

    }


}




