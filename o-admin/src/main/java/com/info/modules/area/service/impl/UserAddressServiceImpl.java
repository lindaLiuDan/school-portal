package com.info.modules.area.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.area.dao.IUserAddressDao;
import com.info.modules.area.entity.UserAddressAreaEntity;
import com.info.modules.area.entity.UserAddressEntity;
import com.info.modules.area.service.IUserAddressAreaService;
import com.info.modules.area.service.IUserAddressService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 会员收货地址表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-03-07 11:24:39
 */
@Service("userAddressService")
public class UserAddressServiceImpl extends ServiceImpl<IUserAddressDao, UserAddressEntity> implements IUserAddressService {

    protected Logger logger = LoggerFactory.getLogger(UserAddressServiceImpl.class);

    @Value("${addressNumber}")
    private Integer addressNumber;

    @Autowired
    private IUserAddressAreaService userAddressAreaService;

    @Autowired
    private ICrudRedisManager<UserAddressEntity> crudRedisManager;


    /**
     * 功能描述: 根据条件查询用户收货地址信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:16
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserAddressEntity> page = this.page(
                new Query<UserAddressEntity>().getPage(params),
                new QueryWrapper<UserAddressEntity>()
                        .select("address_id,user_id,mobile,consignee,area_id,address,room_no,email,status,is_def")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(userId), "user_id", userId)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: APP端查询用户有多少个收货地址
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:16
     * @Return:
     */
    @Override
    public Integer selectCount(UserAddressEntity userAddressEntity) {
        return baseMapper.selectCount(new QueryWrapper<UserAddressEntity>()
                .eq("user_id", userAddressEntity.getUserId()));
    }

    /**
     * 功能描述: APP端添加用戶收穫地址
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:16
     * @Return:
     */
    @Override
    public ResultMessage insertAddress(UserAddressEntity userAddressEntity) {
        //判断用户的收货地址是否超过10个
        Integer addressNumbers = this.selectCount(userAddressEntity);
        if (addressNumbers > addressNumber) {
            return ResultMessage.error(ConfigConstant.ERROR, "收货地址最多只能保存10个");
        }
        userAddressEntity.setCreatorTime(DateUtils.now());
        Boolean flag = this.save(userAddressEntity);
        if (flag) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }

    /**
     * 功能描述: APP端用戶修改收貨地址
     *
     * @Params: * @param nu ll
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:17
     * @Return:
     */
    @Override
    public ResultMessage updateAddressById(UserAddressEntity userAddressEntity) {
        crudRedisManager.hdel(RedisKeyUtils.AreaKeys.ADDRESS_INFO, "删除地址详情,Redis异常,Exception{},异常信息为:", userAddressEntity.getAddressId().toString());
        userAddressEntity.setEditor(userAddressEntity.getUserId());
        userAddressEntity.setEditorTime(DateUtils.now());
        Boolean flag = this.updateById(userAddressEntity);
        if (flag) {
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }

    /**
     * 功能描述: 根据收货地址ID用户ID查询收货地址详细信息
     *
     * @auther: 960889426@qq.com By User
     * @param:
     * @date: 2018/11/14 11:54
     */
    @Override
    public UserAddressEntity selectByIdEntity(Integer addressId) {
        UserAddressEntity userAddressEntity = crudRedisManager.hget(RedisKeyUtils.AreaKeys.ADDRESS_INFO, addressId.toString(), UserAddressEntity.class, "获取用户地址详情信息,Redis异常,Exception{},异常信息为:");
        if (userAddressEntity == null) {
            userAddressEntity = this.getById(addressId);
            String[] areaIds = userAddressEntity.getAreaId().split(",");
            String names = "";
            for (int j = 0; j < areaIds.length; j++) {
//                UserAddressAreaEntity userAddressAreaEntity = redisUtils.hget(RedisKeyUtils.AreaKeys.AREA_INFO, areaIds[j], UserAddressAreaEntity.class);
//                if (userAddressAreaEntity == null) {
//                    userAddressAreaEntity = userAddressAreaService.getOne(new QueryWrapper<UserAddressAreaEntity>()
//                            .select("a_id,area_id,names")
//                            .eq("area_id", areaIds[j])
//                    );
//                    names += userAddressAreaEntity.getNames() + " ";
//                    redisUtils.hset(RedisKeyUtils.AreaKeys.AREA_INFO, areaIds[j], JsonUtils.objectToJson(userAddressAreaEntity));
//                }
                UserAddressAreaEntity areaEntity = userAddressAreaService.getAreaById(Integer.parseInt(areaIds[j]));
                names += areaEntity.getNames() + " ";
            }
            userAddressEntity.setNames(names);
            crudRedisManager.hset(RedisKeyUtils.AreaKeys.ADDRESS_INFO, addressId.toString(), JSON.toJSONString(userAddressEntity), "存储地址详情信息,Redis异常,Exception{},异常信息为:");
        } else {
            return userAddressEntity;
        }
        return userAddressEntity;
    }

    /**
     * 功能描述: APP端无分页查询我的收货地址的
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/19 11:17
     * @Return:
     */
    @Override
    public List<UserAddressEntity> all(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String str = crudRedisManager.hget(RedisKeyUtils.AreaKeys.ADDRESS_LIST, userId, "获取用户收货地址集合,Redis异常,Exception{},异常信息为:");
        if (str != null) {
            return JSON.parseArray(str, UserAddressEntity.class);
        }
        List<UserAddressEntity> entityList = this.list(new QueryWrapper<UserAddressEntity>()
                .select("address_id,user_id,mobile,consignee,area_id,room_no,address,is_def,status")
                .eq("user_id", userId)
        );
        entityList.stream().forEach(info -> {
            String[] areaIds = info.getAreaId().split(",");
            String names = "";
            for (int j = 0; j < areaIds.length; j++) {
//                UserAddressAreaEntity areaEntity = crudRedisManager.hget(RedisKeyUtils.AreaKeys.AREA_INFO, areaIds[j], UserAddressAreaEntity.class, "获取省市县区详细信息,Redis异常,Exception{},异常信息为:");
//                if (areaEntity == null) {
//                    areaEntity = userAddressAreaService.getOne(new QueryWrapper<UserAddressAreaEntity>()
//                            .select("a_id,area_id,names")
//                            .eq("area_id", areaIds[j])
//                    );
//                    info.setCountry(areaEntity.getNames());
//                    crudRedisManager.hset(RedisKeyUtils.AreaKeys.AREA_INFO, areaIds[j], JSON.objectToJson(areaEntity), "获取省市县区详细信息,Redis异常,Exception{},异常信息为:");
//                } else {
//                    info.setCountry(areaEntity.getNames());
//                }
                UserAddressAreaEntity areaEntity = userAddressAreaService.getAreaById(Integer.parseInt(areaIds[j]));
                names += areaEntity.getNames() + " ";
            }
            info.setNames(names);

        });
        crudRedisManager.hset(RedisKeyUtils.AreaKeys.ADDRESS_LIST, userId, JSON.toJSONString(entityList), "获取用户收货地址集合,Redis异常,Exception{},异常信息为:");
        return entityList;
    }

    /**
     * 功能描述: 删除用户地址详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/20 19:49
     * @Return:
     */
    @Override
    public ResultMessage del(Integer addressId) {
        Boolean flag = this.removeById(addressId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.AreaKeys.ADDRESS_INFO, "删除地址详情,Redis异常,Exception{},异常信息为:", addressId.toString());
            return ResultMessage.ok();
        } else {
            return ResultMessage.err();
        }
    }

}
