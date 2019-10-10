package com.info.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.CrudRedisManager;
import com.info.modules.message.dao.ICommunityMessageDao;
import com.info.modules.message.entity.CommunityMessageEntity;
import com.info.modules.message.service.ICommunityMessageService;
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
 * 社区通告通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@Service("communityMessageService")
public class CommunityMessageServiceImpl extends ServiceImpl<ICommunityMessageDao, CommunityMessageEntity> implements ICommunityMessageService {

    @Autowired
    private CrudRedisManager<CommunityMessageEntity> crudRedisManager;

    /**
     * 功能描述: 社区通告通知表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:05
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        Assert.isNull(infoId, "社区ID不能为空", ConfigConstant.ERROR);
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityMessageEntity> page = this.page(
                new Query<CommunityMessageEntity>().getPage(params),
                new QueryWrapper<CommunityMessageEntity>()
                        .select("me_id,type_id,title,content")
                        .eq("info_id", infoId)
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
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
        CommunityMessageEntity messageEntity = crudRedisManager.hget(RedisKeyUtils.MessageKeys.COMMUNITY_MESSAGE_INFO, meId.toString(), CommunityMessageEntity.class, "获取社区消息通知详情,Redis异常,Exception{},异常信息为:");
        if (messageEntity == null) {
            messageEntity = this.getById(meId);
            if (messageEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.MessageKeys.COMMUNITY_MESSAGE_INFO, meId.toString(), JSON.toJSONString(messageEntity), "存储社区消息通知详情,Redis异常,Exception{},异常信息为:");
            }
        }
        return messageEntity;
    }

    /**
     * 功能描述: 修改社区通告通知
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:10
     * @Return:
     */
    @Override
    public ResultMessage updateMessageInfo(CommunityMessageEntity communityMessageEntity) {
        Boolean flag = this.updateById(communityMessageEntity);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.MessageKeys.COMMUNITY_MESSAGE_INFO, communityMessageEntity.getMeId().toString(), "删除OwnerKeys.COMMUNITY_MESSAGE_INFO,异常信息为:");
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "修改失败");
    }

    /**
     * 功能描述: 删除社区通告通知
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:10
     * @Return:
     */
    @Override
    public ResultMessage delMessageInfo(Integer meId) {
        Boolean flag = this.removeById(meId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.MessageKeys.COMMUNITY_MESSAGE_INFO, meId.toString(), "删除OwnerKeys.COMMUNITY_MESSAGE_INFO,异常信息为:");
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除失败");
    }


}
