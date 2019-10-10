package com.info.modules.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.message.dao.ICommunityMessageTypeDao;
import com.info.modules.message.entity.CommunityMessageTypeEntity;
import com.info.modules.message.service.ICommunityMessageTypeService;
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
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
@Service("communityMessageTypeService")
public class CommunityMessageTypeServiceImpl extends ServiceImpl<ICommunityMessageTypeDao, CommunityMessageTypeEntity> implements ICommunityMessageTypeService {

    @Autowired
    private ICrudRedisManager<CommunityMessageTypeEntity> crudRedisManager;


    /**
     * 功能描述: 社区通告通知类型表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 17:52:42
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String typeId = (String) params.get("typeId");
        Assert.isNull(typeId, "类型ID不能为空", ConfigConstant.ERROR);
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityMessageTypeEntity> page = this.page(
                new Query<CommunityMessageTypeEntity>().getPage(params),
                new QueryWrapper<CommunityMessageTypeEntity>()
                        .select("type_id,tname,creator,creator_time")
                        .eq("is_del", ConfigConstant.ERROR)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(typeId), "type_id", typeId)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 修改社区通告通知类型
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:20
     * @Return:
     */
    @Override
    public ResultMessage updateCommunityMessageType(CommunityMessageTypeEntity communityMessageTypeEntity) {
        Boolean flag = this.updateById(communityMessageTypeEntity);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.MessageKeys.COMMUNITY_MESSAGE_TYPE_INFO, communityMessageTypeEntity.getTypeId().toString(), "删除异常：");
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除失败");
    }

    /**
     * 功能描述: del社区通告通知类型
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:20
     * @Return:
     */
    @Override
    public ResultMessage delCommunityMessageType(Integer typeId) {
        Boolean flag = this.removeById(typeId);
        if (flag) {
            crudRedisManager.hdel(RedisKeyUtils.MessageKeys.COMMUNITY_MESSAGE_TYPE_INFO, typeId.toString(), "删除异常：");
            return ResultMessage.ok();
        }
        return ResultMessage.error(ConfigConstant.ERROR, "删除失败");
    }


}
