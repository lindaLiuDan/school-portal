package com.info.modules.message.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.modules.message.entity.MessageInfoEntity;
import com.info.modules.message.entity.MessageInfoTypeEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.message.dao.IMessageInfoDao;
import com.info.modules.message.service.IMessageInfoService;
import com.info.modules.message.service.IMessageInfoTypeService;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 消息通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-18 11:40:10
 */
@Service("messageInfoService")
public class MessageInfoServiceImpl extends ServiceImpl<IMessageInfoDao, MessageInfoEntity> implements IMessageInfoService {


    @Autowired
    private ICrudRedisManager<MessageInfoEntity> crudRedisManager;

    @Autowired
    private IMessageInfoTypeService messageInfoTypeService;


    /**
     * @Author: Gaosx
     * @Description: 消息通知表
     * @Date: 2019-06-18 11:40:10
     */
    /**
     * 功能描述: 消息通知表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        String infoId = (String) params.get("infoId");
        IPage<MessageInfoEntity> page = this.page(
                new Query<MessageInfoEntity>().getPage(params),
                new QueryWrapper<MessageInfoEntity>()
                        .select("content_id,info_id,type_id,title,creator_time,is_read")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq(StringUtils.isNotBlank(infoId), "info_id", infoId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        page.getRecords().stream().forEach(info -> {
            MessageInfoTypeEntity typeEntity = crudRedisManager.hget(RedisKeyUtils.MessageKeys.MESSAGE_TYPE, info.getTypeId().toString(), MessageInfoTypeEntity.class, "获取系统消息类型详情,Redis异常,Exception{},异常信息为:");
            if (typeEntity == null) {
                typeEntity = messageInfoTypeService.getById(info.getTypeId());
                crudRedisManager.hset(RedisKeyUtils.MessageKeys.MESSAGE_TYPE, typeEntity.getTypeId().toString(), JSON.toJSONString(typeEntity), "存储系统消息类型,Redis异常,Exception{},异常信息为:");
            }
            info.setTname(typeEntity.getTname());
        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: 详情查看
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/18 13:22
     * @Return:
     */
    @Override
    public MessageInfoEntity getMessageByInfo(Integer contentId) {
        MessageInfoEntity messageInfo = crudRedisManager.hget(RedisKeyUtils.MessageKeys.MESSAGE_INFO, contentId.toString(), MessageInfoEntity.class, "获取系统消息详情,Redis异常,Exception{},异常信息为:");
        if (messageInfo == null) {
            messageInfo = this.getById(contentId);
            MessageInfoTypeEntity typeEntity = crudRedisManager.hget(RedisKeyUtils.MessageKeys.MESSAGE_TYPE, messageInfo.getTypeId().toString(), MessageInfoTypeEntity.class, "获取系统消息类型详情,Redis异常,Exception{},异常信息为:");
            if (typeEntity == null) {
                typeEntity = messageInfoTypeService.getById(messageInfo.getTypeId());
                crudRedisManager.hset(RedisKeyUtils.MessageKeys.MESSAGE_TYPE, typeEntity.getTypeId().toString(), JSON.toJSONString(typeEntity), "存储系统消息类型,Redis异常,Exception{},异常信息为:");
            }
            messageInfo.setTname(typeEntity.getTname());
            crudRedisManager.hset(RedisKeyUtils.MessageKeys.MESSAGE_INFO, contentId.toString(), JSON.toJSONString(messageInfo), "获取系统消息详情,Redis异常,Exception{},异常信息为:");
        }

        return messageInfo;
    }

}


