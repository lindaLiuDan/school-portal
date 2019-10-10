package com.info.modules.area.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.area.entity.UserAddressAreaEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.area.dao.IUserAddressAreaDao;
import com.info.modules.area.service.IUserAddressAreaService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.PageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.info.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 功能描述: 用户收货地址的区域信息
 *
 * @author 高山溪
 * @auther: Gaosx By User
 * @param:
 * @date: 2019/4/27 16:04
 */
@Service("userAddressAreaServiceImpl")
public class UserAddressAreaServiceImpl extends ServiceImpl<IUserAddressAreaDao, UserAddressAreaEntity> implements IUserAddressAreaService {


    @Autowired
    private ICrudRedisManager<UserAddressAreaEntity> crudRedisManager;


    /**
     * 功能描述: 分页查询用户收货地址的区域信息
     *
     * @auther: Gaosx By User
     * @param:
     * @date: 2019/4/27 16:04
     */
    @Override
    @Transactional(readOnly = true)
    public PageUtils queryPage(Map<String, Object> params) {
        String names = (String) params.get("names");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserAddressAreaEntity> page = this.page(
                new Query<UserAddressAreaEntity>().getPage(params),
                new QueryWrapper<UserAddressAreaEntity>()
                        .select("area_id,names,parent_id,parent_ids,creator_time,creator")
                        .like(StringUtils.isNotBlank(names), "aname", names)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 无分页查询城市列表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/12 19:05
     * @Return:
     */
    @Override
    public List<UserAddressAreaEntity> all(Map<String, Object> params) {
        String str = crudRedisManager.get(RedisKeyUtils.AreaKeys.AREA_ALL, "获取省市县区集合,Redis异常,Exception{},异常信息为:");
        if (str == null) {
            List<UserAddressAreaEntity> list = this.list(new QueryWrapper<UserAddressAreaEntity>()
                    .select("a_id,area_id,names")
            );
            crudRedisManager.set(RedisKeyUtils.AreaKeys.AREA_ALL, JSON.toJSONString(list), "存储省市县区集合,Redis异常,Exception{},异常信息为:");
            return list;
        }
        return JSON.parseArray(str, UserAddressAreaEntity.class);
    }

    /**
     * 功能描述: 根据areaId查询对应的信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 9:48
     * @Return:
     */
    @Override
    public UserAddressAreaEntity getAreaById(Integer areaId) {
        UserAddressAreaEntity areaEntity = crudRedisManager.hget(RedisKeyUtils.AreaKeys.AREA_INFO, areaId.toString(), UserAddressAreaEntity.class, "获取省市县区详情,Redis异常,Exception{},异常信息为:");
        if (areaEntity != null) {
            return areaEntity;
        } else {
            areaEntity = this.getOne(new QueryWrapper<UserAddressAreaEntity>()
                    .select("a_id,area_id,names")
                    .eq("area_id", areaId)
            );
            crudRedisManager.hset(RedisKeyUtils.AreaKeys.AREA_INFO, areaId.toString(),  JSON.toJSONString(areaEntity), "存储省市县区详情,Redis异常,Exception{},异常信息为:");
            return areaEntity;
        }
    }

    /**
     * 功能描述: 根据areaId查询对应的下一级区域的集合
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/21 9:50
     * @Return:
     */
    @Override
    public List<UserAddressAreaEntity> getAreaList(Integer areaId) {
        String str = crudRedisManager.hget(RedisKeyUtils.AreaKeys.AREA_AREA, areaId.toString(),  "获取省市县区集合,Redis异常,Exception{},异常信息为:");
        if (str != null) {
            return JSON.parseArray(str, UserAddressAreaEntity.class);
        } else {
            List<UserAddressAreaEntity> entityList = this.list(new QueryWrapper<UserAddressAreaEntity>()
                    .select("a_id,area_id,names")
                    .eq("parent_id", areaId)
            );
            crudRedisManager.hset(RedisKeyUtils.AreaKeys.AREA_AREA, areaId.toString(),  JSON.toJSONString(entityList), "存储省市县区详情,Redis异常,Exception{},异常信息为:");
            return entityList;
        }
    }


}
