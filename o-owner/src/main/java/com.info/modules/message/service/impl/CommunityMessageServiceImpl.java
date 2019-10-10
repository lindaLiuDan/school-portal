package com.info.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.message.entity.CommunityMessageEntity;
import com.info.manager.CrudRedisManager;
import com.info.modules.message.dao.ICommunityMessageDao;
import com.info.modules.message.service.ICommunityMessageService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
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
 * 社区通告通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@Service("communityMessageService")
public class CommunityMessageServiceImpl extends ServiceImpl<ICommunityMessageDao, CommunityMessageEntity> implements ICommunityMessageService {


    /**
     * 功能描述: 日志方法调用
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 18:46
     * @Return:
     */
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private CrudRedisManager<CommunityMessageEntity> crudRedisManager;


    /**
     * @Author: Gaosx
     * @Description: 社区通告通知表
     * @Date: 2019-06-17 17:52:42
     */
    /**
     * 功能描述: 社区通告通知表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityMessageEntity> page = this.page(
                new Query<CommunityMessageEntity>().getPage(params),
                new QueryWrapper<CommunityMessageEntity>()
                        .select("me_id,type_id,title,content")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq("info_id", params.get("infoId"))
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
//        page.getRecords().stream().forEach(info->{
//            crudRedisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_MESSAGE_INFO, info.getMeId().toString(), JSON.toJSONString(info), "存储社区消息通知详情,Redis异常,Exception{},异常信息为:");
//        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: 详情接口
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 18:42
     * @Return:
     */
    @Override
    public CommunityMessageEntity getMessageById(Integer meId) {
        CommunityMessageEntity messageEntity = crudRedisManager.hget(RedisKeyUtils.OwnerKeys.COMMUNITY_MESSAGE_INFO, meId.toString(), CommunityMessageEntity.class, "获取社区消息通知详情,Redis异常,Exception{},异常信息为:");
        if (messageEntity == null) {
            messageEntity = this.getById(meId);
            if (messageEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.OwnerKeys.COMMUNITY_MESSAGE_INFO, meId.toString(), JSON.toJSONString(messageEntity), "存储社区消息通知详情,Redis异常,Exception{},异常信息为:");
            }
        }
        return messageEntity;
    }


}
