package com.info.modules.community.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.modules.community.dao.ICommunityBuildInfoDao;
import com.info.modules.community.entity.CommunityBuildInfoEntity;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.modules.community.manager.IBuildInfoRedisManager;
import com.info.modules.community.service.ICommunityBuildInfoService;
import com.info.modules.community.service.ICommunityInfoService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import com.info.validator.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityBuildInfoEntity> page = this.page(
                new Query<CommunityBuildInfoEntity>().getPage(params),
                new QueryWrapper<CommunityBuildInfoEntity>()
                        .select("build_id,info_id,bname,parent_id,build_type")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
//        page.getRecords().stream().forEach(info -> {
//            CommunityInfoEntity entity = communityInfoService.getCommunityInfoById(info.getInfoId());
//            if (entity != null) {
//                info.setInfoName(entity.getInfoName());
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
        Assert.isNull(infoId, "社区ID不能为空", ConfigConstant.ERROR);
        Assert.isNull(buildType, "查询类型不能为空", ConfigConstant.ERROR);
        Assert.isNull(buildId, "查询房屋主键不能为空", ConfigConstant.ERROR);
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
     * 功能描述: 添加楼房信息--符复合添加接口
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 15:33
     * @Return:
     */
    @Override
    public ResultMessage addBuildInfo(CommunityBuildInfoEntity communityBuildInfoEntity) {
        Boolean flag = this.save(communityBuildInfoEntity);
        if (flag) {
            if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.FLOOR)) {
                Assert.isNull(communityBuildInfoEntity.getInfoId(), "删除社区楼号信息时社区ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.FLOOR_LIST, "删除OwnerKeys.FLOOR_LIST:" + communityBuildInfoEntity.getInfoId() + "缓存异常：", communityBuildInfoEntity.getInfoId().toString());
            } else if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.UNIT)) {
                Assert.isNull(communityBuildInfoEntity.getBuildId(), "删除单元信息时楼号ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.UNIT_LIST, "删除OwnerKeys.UNIT_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getInfoId().toString());
            } else if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.LEVEL)) {
                Assert.isNull(communityBuildInfoEntity.getBuildId(), "删除楼层信息时单元ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.LEVEL_LIST, "删除OwnerKeys.LEVEL_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getInfoId().toString());
            } else if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.ROOM)) {
                Assert.isNull(communityBuildInfoEntity.getBuildId(), "删除房间号信息时单元ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.ROOM_LIST, "删除OwnerKeys.ROOM_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getInfoId().toString());
            }
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "添加楼房信息失败，请重新添加。");
    }

    /**
     * 功能描述: 修改楼房信息--符复合添加接口
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 15:46
     * @Return:
     */
    @Override
    public ResultMessage updateBuildInfo(CommunityBuildInfoEntity communityBuildInfoEntity) {
        Boolean flag = this.updateById(communityBuildInfoEntity);
        if (flag) {
            if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.FLOOR)) {
                Assert.isNull(communityBuildInfoEntity.getInfoId(), "修改楼号信息时社区ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.FLOOR_INFO, "删除OwnerKeys.FLOOR_INFO:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.FLOOR_LIST, "删除OwnerKeys.FLOOR_LIST:" + communityBuildInfoEntity.getInfoId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
            } else if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.UNIT)) {
                Assert.isNull(communityBuildInfoEntity.getBuildId(), "修改单元信息时楼号ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.UNIT_INFO, "删除OwnerKeys.UNIT_INFO:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.UNIT_LIST, "删除OwnerKeys.UNIT_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
            } else if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.LEVEL)) {
                Assert.isNull(communityBuildInfoEntity.getBuildId(), "修改楼层信息时单元ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.LEVEL_INFO, "删除OwnerKeys.LEVEL_INFO:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.LEVEL_LIST, "删除OwnerKeys.LEVEL_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
            } else if (communityBuildInfoEntity.getBuildType().equals(ConfigConstant.ROOM)) {
                Assert.isNull(communityBuildInfoEntity.getBuildId(), "修改房间号信息时楼层ID不能为空", ConfigConstant.ERROR);
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.ROOM_INFO, "删除OwnerKeys.ROOM_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
                buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.ROOM_LIST, "删除OwnerKeys.ROOM_LIST:" + communityBuildInfoEntity.getBuildId() + "缓存异常：", communityBuildInfoEntity.getBuildId().toString());
            }
        } else {
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "修改楼房信息失败...");
    }

    /**
     * 功能描述: 删除楼房信息--有子类的话不允许删除
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 15:56
     * @Return:
     */
    @Override
    public ResultMessage delBuildInfo(Integer buildId, Integer buildType) {
        List<CommunityBuildInfoEntity> list = this.getParentById(buildId);
        if (list != null) {
            return ResultMessage.error(ConfigConstant.ERROR, "请先删除子类别信息...");
        } else {
            Boolean flag = this.removeById(buildId);
            if (flag) {
                if (buildType.equals(ConfigConstant.FLOOR)) {
                    buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.FLOOR_LIST, "删除OwnerKeys.FLOOR_LIST:" + buildId + "缓存异常：", buildId.toString());
                } else if (buildType.equals(ConfigConstant.UNIT)) {
                    buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.UNIT_LIST, "删除OwnerKeys.UNIT_LIST:" + buildId + "缓存异常：", buildId.toString());
                } else if (buildType.equals(ConfigConstant.LEVEL)) {
                    buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.LEVEL_LIST, "删除OwnerKeys.LEVEL_LIST:" + buildId + "缓存异常：", buildId.toString());
                } else if (buildType.equals(ConfigConstant.ROOM)) {
                    buildInfoRedisManager.hdel(RedisKeyUtils.OwnerKeys.ROOM_LIST, "删除OwnerKeys.ROOM_LIST:" + buildId + "缓存异常：", buildId.toString());
                }
                return ResultMessage.ok();
            } else {
                return ResultMessage.error(ConfigConstant.ERROR, "删除失败...");
            }
        }
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
     * 功能描述: 根据buildId查询自己是否还有子类别
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 15:57
     * @Return:
     */
    private List<CommunityBuildInfoEntity> getParentById(Integer buildId) {
        return this.list(new QueryWrapper<CommunityBuildInfoEntity>()
                .select("build_id,info_id,bname,parent_id,build_type")
                .eq("parent_id", buildId)
        );
    }


}
