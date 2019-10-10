package com.info.modules.lease.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.modules.lease.dao.ICommunityLeaseInfoDao;
import com.info.modules.lease.entity.CommunityLeaseInfoEntity;
import com.info.modules.lease.service.ICommunityLeaseInfoService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
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

import java.util.Map;

/**
 * 社区租赁信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 15:28:11
 */
@Service("communityLeaseInfoService")
public class CommunityLeaseInfoServiceImpl extends ServiceImpl<ICommunityLeaseInfoDao, CommunityLeaseInfoEntity> implements ICommunityLeaseInfoService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @Author: Gaosx
     * @Description: 社区租赁信息表
     * @Date: 2019-06-17 15:28:11
     */
    /**
     * 功能描述: 社区租赁信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 15:28:11
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityLeaseInfoEntity> page = this.page(
                new Query<CommunityLeaseInfoEntity>().getPage(params),
                new QueryWrapper<CommunityLeaseInfoEntity>()
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
