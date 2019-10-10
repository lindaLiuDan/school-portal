package com.info.modules.community.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.community.dao.ICommunityPassInfoDao;
import com.info.modules.community.entity.CommunityPassInfoEntity;
import com.info.modules.community.service.ICommunityPassInfoService;
import com.info.modules.community.vo.CommunityRedisVo;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 社区访客通行证信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 14:38:33
 */
@Service("communityPassInfoService")
public class CommunityPassInfoServiceImpl extends ServiceImpl<ICommunityPassInfoDao, CommunityPassInfoEntity> implements ICommunityPassInfoService {

    @Autowired
    private ICrudRedisManager<CommunityPassInfoEntity> crudRedisManager;


    @Override
    public ResultMessage findCheckCode(String checkCode) {
        String key = RedisKeyUtils.CommunityReleaseSlip.MOBILE + checkCode;
        CommunityRedisVo vo = crudRedisManager.get(key, CommunityRedisVo.class, "获取redis中短信验证码是否正确，Redis异常,Exception{},异常信息为");
        if (vo == null) {
            CommunityPassInfoEntity entity = this.getOne(new QueryWrapper<CommunityPassInfoEntity>().eq("check_code", checkCode));
            if (entity != null) {
                long expireTime = entity.getExpireTime().getTime();
                long now = DateUtils.now().getTime();
                if (expireTime > now) {
                    return ResultMessage.ok("请立即放行!");
                } else {
                    return ResultMessage.error("次验证已失效!");
                }

            } else {
                return ResultMessage.error("此验证码无效请重新输入！");
            }

        } else {
            return ResultMessage.ok("请立即放行!");
        }

    }
}
