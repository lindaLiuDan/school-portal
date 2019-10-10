package com.info.modules.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.manager.IBuildInfoRedisManager;
import com.info.modules.community.dao.ICommunityBuildInfoDao;
import com.info.modules.community.service.ICommunityBuildInfoService;
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
 * 社区楼房信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 18:36:11
 */
@Service("communityBuildInfoService")
public class CommunityBuildInfoServiceImpl extends ServiceImpl<ICommunityBuildInfoDao, CommunityBuildInfoEntity> implements ICommunityBuildInfoService {


    /**
     * 功能描述: 社区楼房信息表缓存层
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 11:20
     * @Return:
     */
    @Autowired
    private IBuildInfoRedisManager buildInfoRedisManager;

    /**
     * 功能描述: 社区信息业务实现层
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 17:54
     * @Return:
     */
    @Autowired
    private ICommunityInfoService communityInfoService;


    /**
     * @Author: Gaosx
     * @Description: 社区楼房信息表
     * @Date: 2019-06-11 18:36:11
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityBuildInfoEntity> page = this.page(
                new Query<CommunityBuildInfoEntity>().getPage(params),
                new QueryWrapper<CommunityBuildInfoEntity>()
                        .select("build_id,info_id,bname,parent_id,build_type")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        page.getRecords().stream().forEach(info -> {
            CommunityInfoEntity entity = communityInfoService.getCommunityInfoById(info.getInfoId());
            if (entity != null) {

                info.setInfoName(entity.getInfoName());
            }
        });
//        page.getRecords().stream().forEach(info -> {
//            if (info.getBuildType().equals(ConfigConstant.FLOOR)) {
//                this.hset(info, RedisKeyUtils.OwnerKeys.FLOOR);
//            }
//            if (info.getBuildType().equals(ConfigConstant.UNIT)) {
//                this.hset(info, RedisKeyUtils.OwnerKeys.UNIT);
//            }
//            if (info.getBuildType().equals(ConfigConstant.LEVEL)) {
//                this.hset(info, RedisKeyUtils.OwnerKeys.LEVEL);
//            }
//        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: 根据楼层号查询对应的房间号 多类型的查询方法
     * 社区楼号楼层单元类型 1 楼号 2 单元号 3 楼层 4 房号 buildType
     *
     * @Params: * @param null
     * @Author: Gaosx by user
     * @Date: 2019-06-11 18:36:11
     * @Return: infoId 社区ID主键 buildId 楼房信息ID主键 buildType  楼房信息ID主键类型　1 楼号 2 单元号 3 楼层 4 房号
     */
    @Override
    public List<CommunityBuildInfoEntity> all(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        String buildId = (String) params.get("buildId");
        String buildType = (String) params.get("buildType");
        List<CommunityBuildInfoEntity> list = null;
        //这种情况表示根据infoId查询社区所有的楼号信息
        if (infoId != null && buildType.equals(ConfigConstant.FLOOR)) {
            String str = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.FLOOR_LIST, infoId, "获取小区下的所有楼号,Redis异常,Exception{},异常信息为:");
            if (str == null) {
                list = this.get(infoId, buildType, buildId);
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.FLOOR_LIST, infoId, JSON.toJSONString(list), "存储小区下的所有楼号,Redis异常,Exception{},异常信息为:");
                return list;
            }
            return JSON.parseArray(str, CommunityBuildInfoEntity.class);
        } else if (buildId != null && buildType.equals(ConfigConstant.UNIT)) {
            //这里是根据楼号buildId查询所有的下面的单元信息
            String str = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.UNIT_LIST, buildId, "获取本栋楼下的所有单元号,Redis异常,Exception{},异常信息为:");
            if (str == null) {
                list = this.get(infoId, buildType, buildId);
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.UNIT_LIST, buildId, JSON.toJSONString(list), "存储本栋楼下的所有单元号,Redis异常,Exception{},异常信息为:");
                return list;
            }
            return JSON.parseArray(str, CommunityBuildInfoEntity.class);
        } else if (buildId != null && buildType.equals(ConfigConstant.LEVEL)) {
            //这里是根据buildId查询所有的单元下的楼层信息
            String str = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.LEVEL_LIST, buildId, "获取本单元下的所有楼层,Redis异常,Exception{},异常信息为:");
            if (str == null) {
                list = this.get(infoId, buildType, buildId);
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.LEVEL_LIST, buildId, JSON.toJSONString(list), "存储本单元下的所有楼层,Redis异常,Exception{},异常信息为:");
                return list;
            }
            return JSON.parseArray(str, CommunityBuildInfoEntity.class);
        } else if (buildId != null && buildType.equals(ConfigConstant.ROOM)) {
            //这里是根据buildId查询所有的楼层下房间号信息
            String str = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.ROOM_LIST, buildId, "获取本单元下的所有房号,Redis异常,Exception{},异常信息为:");
            if (str == null) {
                list = this.get(infoId, buildType, buildId);
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.ROOM_LIST, buildId, JSON.toJSONString(list), "存储本单元下的所有房号,Redis异常,Exception{},异常信息为:");
                return list;
            }
            return JSON.parseArray(str, CommunityBuildInfoEntity.class);
        }
        return list;
    }

    /**
     * 功能描述: 查询对应的楼号，楼层，单元信息集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 17:52
     * @Return:
     */
    private List<CommunityBuildInfoEntity> get(String infoId, String buildType, String buildId) {
        return this.list(new QueryWrapper<CommunityBuildInfoEntity>()
                .select("build_id,info_id,bname,parent_id,build_type")
                .eq("info_id", infoId)
                .eq(StringUtils.isNotBlank(buildType), "build_type", buildType)
                .eq(StringUtils.isNotBlank(buildId), "build_id", buildId)
        );
    }

    /**
     * 功能描述: 根据社区ID查询所有的楼号，单元，楼层信息使用redis
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-11 18:36:11
     * @Return:
     */
    @Override
    public List<CommunityBuildInfoEntity> getBuildInfo(Integer infoId) {
        String str = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.FLOOR_LIST, infoId.toString(), "获取小区下的所有楼号,Redis异常,Exception{},异常信息为:");
        if (str == null) {
            List<CommunityBuildInfoEntity> list = this.list(new QueryWrapper<CommunityBuildInfoEntity>()
                    .select("build_id,info_id,bname,parent_id,build_type")
                    .eq("info_id", infoId)
                    .eq("build_type", ConfigConstant.FLOOR)
            );
            buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.FLOOR_LIST, infoId.toString(), JSON.toJSONString(list), "存储小区下的所有楼号,Redis异常,Exception{},异常信息为:");
            return list;
        } else {
            return JSON.parseArray(str, CommunityBuildInfoEntity.class);
        }
    }

    /**
     * 功能描述: 根据房号查询对应的 楼号 单元号 楼层--这是一个反向查询的方法 根据ID楼号——》楼层--》单元--》楼号--》社区
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 17:42
     * @Return:
     */
    @Override
    public CommunityBuildInfoEntity getRoomInfo(Integer buildId) {
        CommunityBuildInfoEntity infoEntity = new CommunityBuildInfoEntity();
        CommunityBuildInfoEntity roomInfo = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.ROOM_INFO, buildId.toString(), CommunityBuildInfoEntity.class, "根据房价号查询对应的 楼号 单元号 楼层--这是一个反向查询的方法,Redis异常,Exception{},异常信息为:");
        //根据房间号查询自己的父类ID
        if (roomInfo == null) {
            roomInfo = this.getCommunityBuildInfoEntityById(buildId);
            if (roomInfo != null) {
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.ROOM_INFO, buildId.toString(), JSON.toJSONString(roomInfo), "存储房间详情,Redis异常,Exception{},异常信息为:");
            }
            infoEntity.setRoomName(roomInfo.getBname());
        } else {
            infoEntity.setRoomName(roomInfo.getBname());
        }
        //根据上面查询到的房间号的父类ID,查询对应的楼层信息
        CommunityBuildInfoEntity levelInfo = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.LEVEL_INFO, roomInfo.getParentId().toString(), CommunityBuildInfoEntity.class, "根据房号查询对应的 楼号 单元号 楼层--这是一个反向查询的方法,Redis异常,Exception{},异常信息为:");
        if (levelInfo == null) {
            levelInfo = this.getCommunityBuildInfoEntityById(roomInfo.getParentId());
            if (levelInfo != null) {
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.LEVEL_INFO, roomInfo.getParentId().toString(), JSON.toJSONString(levelInfo), "存储楼层信息信息,Redis异常,Exception{},异常信息为:");
            }
            infoEntity.setLevelName(levelInfo.getBname());
        }else {
            infoEntity.setLevelName(levelInfo.getBname());
        }
        //根据上面查询到的楼层信息父类ID,查询对应的单元信息
        CommunityBuildInfoEntity unitInfo = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.UNIT_INFO, levelInfo.getParentId().toString(), CommunityBuildInfoEntity.class, "根据楼层ID查询单元信息,Redis异常,Exception{},异常信息为:");
        if (unitInfo == null) {
            unitInfo = this.getCommunityBuildInfoEntityById(levelInfo.getParentId());
            if (unitInfo != null) {
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.UNIT_INFO, levelInfo.getParentId().toString(), JSON.toJSONString(unitInfo), "存储单元信息信息,Redis异常,Exception{},异常信息为:");
            }
            infoEntity.setUnitName(unitInfo.getBname());
        }else {
            infoEntity.setUnitName(unitInfo.getBname());
        }
        //根据上面查询到的单元信息父类ID,查询对应的楼号信息
        CommunityBuildInfoEntity floorInfo = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.FLOOR_INFO, unitInfo.getParentId().toString(), CommunityBuildInfoEntity.class, "根据单元信息查询楼号信息,Redis异常,Exception{},异常信息为:");
        if (floorInfo == null) {
            floorInfo = this.getCommunityBuildInfoEntityById(unitInfo.getParentId());
            if (floorInfo != null) {
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.FLOOR_INFO, unitInfo.getParentId().toString(), JSON.toJSONString(floorInfo), "存储楼号信息,Redis异常,Exception{},异常信息为:");
            }
            infoEntity.setFloorName(floorInfo.getBname());
        }else {
            infoEntity.setFloorName(floorInfo.getBname());
        }
        //根据楼号信息查询对应的社区ID信息
        CommunityInfoEntity infoInfo = buildInfoRedisManager.hget(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, floorInfo.getInfoId().toString(), CommunityInfoEntity.class, "根据ID查询社区ID信息,Redis异常,Exception{},异常信息为:");
        if (infoInfo == null) {
           infoInfo= communityInfoService.getCommunityInfoById(floorInfo.getInfoId());
            if (infoInfo != null) {
                buildInfoRedisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_INFO, floorInfo.getInfoId().toString(), JSON.toJSONString(infoInfo), "存储社区ID信息,Redis异常,Exception{},异常信息为:");
            }
            infoEntity.setInfoName(infoInfo.getInfoName());
            infoEntity.setInfoId(infoInfo.getInfoId());
        }else {
            infoEntity.setInfoName(infoInfo.getInfoName());
            infoEntity.setInfoId(infoInfo.getInfoId());
        }
        return infoEntity;
    }

    /**
     * 功能描述: 查询信息详情--配合上面的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 18:20
     * @Return:
     */
    private CommunityBuildInfoEntity getCommunityBuildInfoEntityById(Integer buildId) {
        return this.getOne(new QueryWrapper<CommunityBuildInfoEntity>()
                .select("build_id,info_id,bname,parent_id")
                .eq("build_id", buildId)
        );
    }

}
