package com.info.modules.repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.manager.ICommunityRedisManager;
import com.info.modules.repair.entity.CommunityRepairEntity;
import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.modules.repair.manager.IRepairRedisManager;
import com.info.modules.community.service.ICommunityBuildInfoService;
import com.info.modules.community.service.ICommunityInfoService;
import com.info.modules.repair.dao.ICommunityRepairDao;
import com.info.modules.repair.service.ICommunityRepairService;
import com.info.modules.repair.service.ICommunityRepairTypeService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 社区物业报修表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:06
 */
@Service("communityRepairService")
public class CommunityRepairServiceImpl extends ServiceImpl<ICommunityRepairDao, CommunityRepairEntity> implements ICommunityRepairService {

    /**
     * 功能描述:
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 14:42
     * @Return:
     */
    @Autowired
    private ICommunityRepairTypeService repairTypeService;

    /**
     * 功能描述: 缓存层实现
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 13:45
     * @Return:
     */
    @Autowired
    private IRepairRedisManager repairRedisManager;


    /**
     * 功能描述: 社区楼层信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 14:42
     * @Return:
     */
    @Autowired
    private ICommunityBuildInfoService buildInfoService;

    /**
     * 功能描述: 获取所有小区信息业务实现层
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 10:16
     * @Return:
     */
    @Autowired
    private ICommunityInfoService communityInfoService;

    @Autowired
    private ICommunityRedisManager communityRedisManager;


    /**
     * @Author: Gaosx
     * @Description: 社区物业报修表
     * @Date: 2019-06-14 14:11:06
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityRepairEntity> page = this.page(
                new Query<CommunityRepairEntity>().getPage(params),
                new QueryWrapper<CommunityRepairEntity>()
                        .select("repair_id,type_id,creator_time,user_id")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq("user_id", params.get("userId"))
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        page.getRecords().stream().forEach(info -> {
            String str = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, info.getTypeId().toString(), "获取报修类型信息,Redis异常,Exception{},异常信息为:");
            if (str == null) {
                CommunityRepairTypeEntity typeEntity = repairTypeService.getById(info.getTypeId());
                if (typeEntity != null) {
                    repairRedisManager.hset(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, info.getTypeId().toString(), JSON.toJSONString(typeEntity), "存储报修类型,Redis异常,Exception{},异常信息为:");
                    info.setInfo(typeEntity.getInfo());
                }
            }
        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: {repairId}详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 19:37
     * @Return:
     */
    @Override
    public CommunityRepairEntity getCommunityRepairById(Integer repairId) {
        CommunityRepairEntity repair = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.REPAIR_INFO, repairId.toString(), CommunityRepairEntity.class, "获取报修内容详情,Redis异常,Exception{},异常信息为:");
        if (repair == null) {
            //报修单详情查询
            repair = this.getById(repairId);
            //根据报修单详情查询对应小区名称
            CommunityInfoEntity infoName = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, repair.getInfoId().toString(), CommunityInfoEntity.class, "获取小区名称,Redis异常,Exception{},异常信息为:");
            if (infoName == null) {
                CommunityInfoEntity info = communityInfoService.getById(repair.getInfoId());
                if (info != null) {
                    repair.setInfoName(info.getInfoName());
                    communityRedisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, info.getInfoId().toString(), JSON.toJSONString(info), "存储本城市单个社区信息,Redis异常,Exception{},异常信息为:");
                }
            } else {
                repair.setInfoName(infoName.getInfoName());
            }
            //查询报修类型
            CommunityRepairTypeEntity str = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, repair.getTypeId().toString(), CommunityRepairTypeEntity.class, "获取报修类型信息,Redis异常,Exception{},异常信息为:");
            if (str == null) {
                CommunityRepairTypeEntity typeEntity = repairTypeService.getById(repair.getTypeId());
                if (typeEntity != null) {
                    repair.setInfo(typeEntity.getInfo());
                    repairRedisManager.hset(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, typeEntity.getTypeId().toString(), JSON.toJSONString(typeEntity), "获取报修类型信息,Redis异常,Exception{},异常信息为:");
                }
            } else {
                repair.setInfo(str.getInfo());
            }
            //根据报修单详情查询对应的楼层信息
            CommunityBuildInfoEntity levelName = repairRedisManager.hget(RedisKeyUtils.OwnerKeys.LEVEL_INFO, repair.getBuildId().toString(), CommunityBuildInfoEntity.class, "获取楼层号,Redis异常,Exception{},异常信息为:");
            if (levelName == null) {
                CommunityBuildInfoEntity buildInfoEntity = buildInfoService.getById(repair.getBuildId());
                repairRedisManager.hset(RedisKeyUtils.OwnerKeys.LEVEL_INFO, repair.getBuildId().toString(), JSON.toJSONString(buildInfoEntity), "获取楼层号,Redis异常,Exception{},异常信息为:");
                repair.setLevel(buildInfoEntity.getBname());
                //根据查询到的楼层信息的parentId查询对应的单元号
                this.getHuildInfo(buildInfoEntity, repair);
            } else {
                repair.setLevel(levelName.getBname());
                this.getHuildInfo(levelName, repair);
            }
            //将查询到的详细信息存入缓存中去
            repairRedisManager.hset(RedisKeyUtils.OwnerKeys.REPAIR_INFO, repairId.toString(), JSON.toJSONString(repair), "存储报修内容详情,Redis异常,Exception{},异常信息为:");
        }
        return repair;
    }

    /**
     * 功能描述: 配对上面的方法
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 11:49
     * @Return:
     */
    private void getHuildInfo(CommunityBuildInfoEntity buildInfoEntity, CommunityRepairEntity repair) {
        //根据查询到的楼层信息的parentId查询对应的单元
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


    /**
     * 功能描述: 用户提交物业报修信息表单
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 13:44
     * @Return:
     */
    @Override
    public ResultMessage saveCommunityRepair(CommunityRepairEntity communityRepairEntity) {
        Boolean flag = this.save(communityRepairEntity);
        if (flag) {
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

}
