package com.info.modules.mobile.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.mobile.dao.ICommunityMobileDao;
import com.info.modules.mobile.entity.CommunityMobileEntity;
import com.info.modules.mobile.service.ICommunityMobileService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 社区便民生活电话
 *
 * @author Gaosx
 * @email
 * @date 2019-06-08 11:59:37
 */
@Service("communityMobileService")
public class CommunityMobileServiceImpl extends ServiceImpl<ICommunityMobileDao, CommunityMobileEntity> implements ICommunityMobileService {

    @Autowired
    private ICrudRedisManager<CommunityMobileEntity> crudRedisManager;


    /**
     * 功能描述: 社区便民生活电话
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:43
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        Assert.isNull(infoId, "社区信息ID不能为空", ConfigConstant.ERROR);
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityMobileEntity> page = this.page(
                new Query<CommunityMobileEntity>().getPage(params),
                new QueryWrapper<CommunityMobileEntity>()
                        .select("mobile_id,info_id,project,mobile")
                        .orderByDesc("sort")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 修改
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:48
     * @Return:
     */
    @Override
    public ResultMessage updateCommunityMobile(CommunityMobileEntity communityMobileEntity) {
        Boolean flag = this.updateById(communityMobileEntity);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.MobileKeys.MOBILE_INFO, communityMobileEntity.getMobileId().toString(), "修改社区便民电话MobileKeys.MOBILE_INFO异常:");
            return ResultMessage.ok();
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 删除
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:48
     * @Return:
     */
    @Override
    public ResultMessage delCommunityMobile(Integer mobileId) {
        Boolean flag = this.removeById(mobileId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.MobileKeys.MOBILE_INFO, mobileId.toString(), "删除社区便民电话MobileKeys.MOBILE_INFO异常:");
            return ResultMessage.ok("删除社区便民电话成功");
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除社区便民失败");
    }


    /**
     * 功能描述: 获取详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 15:57
     * @Return:
     */
    @Override
    public CommunityMobileEntity getCommunityMobile(Integer mobileId) {
        CommunityMobileEntity communityMobileEntity = crudRedisManager.hget(RedisKeyUtils.MobileKeys.MOBILE_INFO, mobileId.toString(), CommunityMobileEntity.class, "获取社区便民电话详情MobileKeys.MOBILE_INFO异常:");
        if (communityMobileEntity == null) {
            communityMobileEntity = this.getById(mobileId);
            if (communityMobileEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.MobileKeys.MOBILE_INFO, mobileId.toString(), JSON.toJSONString(communityMobileEntity), "存储社区便民电话详情MobileKeys.MOBILE_INFO异常:");
            }
            return communityMobileEntity;
        }
        return communityMobileEntity;
    }

}
