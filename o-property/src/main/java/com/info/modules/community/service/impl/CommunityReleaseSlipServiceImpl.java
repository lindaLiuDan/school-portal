package com.info.modules.community.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.manager.ICrudRedisManager;
import com.info.modules.community.dao.ICommunityReleaseSlipDao;
import com.info.modules.community.entity.CommunityReleaseSlipEntity;
import com.info.modules.community.service.ICommunityReleaseSlipService;
import com.info.modules.community.vo.CommunityRedisVo;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-25 18:19:11
 */
@Service("communityReleaseSlipService")
public class CommunityReleaseSlipServiceImpl extends ServiceImpl<ICommunityReleaseSlipDao, CommunityReleaseSlipEntity> implements ICommunityReleaseSlipService {


    @Autowired
    private ICrudRedisManager<CommunityReleaseSlipEntity> crudRedisManager;


    /**
     * @Description 物业查询电子放行单的验证码是否可用
     * @Author LiuDan
     * @Date 2019/7/8 22:58
     * @Param
     * @Return
     * @Exception
     */
    @Override
    public ResultMessage getCheckCode(String checkCode) {
        String key = RedisKeyUtils.CommunityReleaseSlip.MOBILE + checkCode;
        CommunityRedisVo vo = crudRedisManager.get(key, CommunityRedisVo.class, "获取redis中短信验证码是否正确，Redis异常,Exception{},异常信息为");
        if (vo == null) {
            CommunityReleaseSlipEntity entity = this.getOne(new QueryWrapper<CommunityReleaseSlipEntity>().eq("slip_code", checkCode));
            if (entity != null) {
                long expireTime = entity.getEndTime().getTime();
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
