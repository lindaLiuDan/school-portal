package com.info.modules.message.service.impl;

import com.info.modules.message.entity.CommunityMessageTypeEntity;
import com.info.modules.message.dao.ICommunityMessageTypeDao;
import com.info.modules.message.service.ICommunityMessageTypeService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisUtils;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import java.util.Map;
/**
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-17 17:52:42
 */
@Service("communityMessageTypeService")
public class CommunityMessageTypeServiceImpl extends ServiceImpl<ICommunityMessageTypeDao, CommunityMessageTypeEntity> implements ICommunityMessageTypeService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @Author: Gaosx  
     * @Description: 社区通告通知类型表
     * @Date: 2019-06-17 17:52:42
     */
    /**
     * 功能描述: 社区通告通知类型表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityMessageTypeEntity> page = this.page(
                new Query<CommunityMessageTypeEntity>().getPage(params),
                new QueryWrapper<CommunityMessageTypeEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
