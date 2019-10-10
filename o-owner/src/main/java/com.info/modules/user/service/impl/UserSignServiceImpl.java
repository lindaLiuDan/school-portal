package com.info.modules.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.user.entity.UserSignEntity;
import com.info.modules.user.dao.IUserSignDao;
import com.info.modules.user.service.IUserSignService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 会员业主签到信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@Service("userSignService")
public class UserSignServiceImpl extends ServiceImpl<IUserSignDao, UserSignEntity> implements IUserSignService {


    @Autowired
    private ICrudRedisManager<UserSignEntity> crudRedisManager;


    /**
     * @Author: Gaosx
     * @Description: 会员业主签到信息表
     * @Date: 2019-06-13 14:47:56
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserSignEntity> page = this.page(
                new Query<UserSignEntity>().getPage(params),
                new QueryWrapper<UserSignEntity>()
                        .select("sign_id,user_id,integral,creator_time")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 用户签到接口
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/15 17:55
     * @Return:
     */
    @Override
    public ResultMessage saveSignInfo(UserSignEntity userSignEntity) {
        UserSignEntity signEntity = crudRedisManager.hget(RedisKeyUtils.UserInfoKyes.SIGN_INFO, DateUtils.timeStimpmer().toString() + userSignEntity.getUserId(), UserSignEntity.class, "获取业主签到信息,Redis异常,Exception{},异常信息为:");
        if (signEntity == null) {
            signEntity = this.getOne(new QueryWrapper<UserSignEntity>()
                    .eq("user_id", signEntity.getUserId())
                    .eq("", signEntity.getCreatorTime())
            );
            Boolean flag = baseMapper.saveSignInfo(userSignEntity);
            if (flag) {
                crudRedisManager.hset(RedisKeyUtils.UserInfoKyes.SIGN_INFO, DateUtils.timeStimpmer().toString() + userSignEntity.getUserId(), JSON.toJSONString(userSignEntity), "获取业主签到信息,Redis异常,Exception{},异常信息为:", Constant.RedisKeyExpire.SIGN_INFO.getValue());
                return ResultMessage.ok("签到成功");
            }
            return ResultMessage.error(ConfigConstant.ERROR, "签到失败");
        }
        return ResultMessage.error(ConfigConstant.ERRORS, "今天已经签到过啦...");
    }

}
