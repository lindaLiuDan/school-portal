package com.info.modules.repair.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.modules.repair.dao.ICommunityRepairTypeDao;
import com.info.modules.repair.entity.CommunityRepairTypeEntity;
import com.info.modules.repair.manager.IRepairTypeRedisManager;
import com.info.modules.repair.service.ICommunityRepairTypeService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisKeyUtils;
import com.info.redis.RedisUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 社区物业报修类型信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-14 14:11:07
 */
@Service("communityRepairTypeService")
public class CommunityRepairTypeServiceImpl extends ServiceImpl<ICommunityRepairTypeDao, CommunityRepairTypeEntity> implements ICommunityRepairTypeService {


    /**
     * 功能描述: 日志方法调用
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 12:00
     * @Return:
     */
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    /**
     * 功能描述: 缓存层实现
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/15 12:00
     * @Return:
     */
    @Autowired
    private IRepairTypeRedisManager repairTypeRedisManager;


    /**
     * @Author: Gaosx
     * @Description: 社区物业报修类型信息表
     * @Date: 2019-06-14 14:11:07
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityRepairTypeEntity> page = this.page(
                new Query<CommunityRepairTypeEntity>().getPage(params),
                new QueryWrapper<CommunityRepairTypeEntity>()
                        .select("")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页查询所有的社区物业报修类型
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/14 14:20
     * @Return:
     */
    @Override
    public List<CommunityRepairTypeEntity> all(Map<String, Object> params) {
        List<CommunityRepairTypeEntity> list = repairTypeRedisManager.RepairType(RedisKeyUtils.OwnerKeys.REPAIR_LIST);
        if (list != null) {
            return list;
        }
        list = this.list(new QueryWrapper<CommunityRepairTypeEntity>()
                .select("type_id,info")
                .eq("is_del", ConfigConstant.NOTDEL)
        );
        if (list != null) {
            repairTypeRedisManager.setRepairType(RedisKeyUtils.OwnerKeys.REPAIR_LIST, list);
        }
        return list;
    }


    /**
     * 功能描述: 根据ID查询详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 10:29
     * @Return:
     */
    @Override
    public CommunityRepairTypeEntity getCommunityRepairType(Integer typeId) {
        CommunityRepairTypeEntity communityRepairTypeEntity = repairTypeRedisManager.hget(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, typeId.toString(), CommunityRepairTypeEntity.class, "获取OwnerKeys.REPAIR_INFO：" + typeId + "，异常：");
        if (communityRepairTypeEntity == null) {
            communityRepairTypeEntity = this.getById(typeId);
            if (communityRepairTypeEntity != null) {
                repairTypeRedisManager.hset(RedisKeyUtils.OwnerKeys.REPAIR_TYPE, typeId.toString(), JSON.toJSONString(communityRepairTypeEntity), "存储OwnerKeys.REPAIR_INFO：" + typeId + "，异常：");
            }
        }
        return communityRepairTypeEntity;
    }

}
