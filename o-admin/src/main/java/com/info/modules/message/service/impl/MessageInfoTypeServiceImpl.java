package com.info.modules.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.message.dao.IMessageInfoTypeDao;
import com.info.modules.message.entity.MessageInfoTypeEntity;
import com.info.modules.message.service.IMessageInfoTypeService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisUtils;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 消息通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-18 11:40:10
 */
@Service("messageInfoTypeService")
public class MessageInfoTypeServiceImpl extends ServiceImpl<IMessageInfoTypeDao, MessageInfoTypeEntity> implements IMessageInfoTypeService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @Author: Gaosx
     * @Description: 消息通知类型表
     * @Date: 2019-06-18 11:40:10
     */
    /**
     * 功能描述: 消息通知类型表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-18 11:40:10
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<MessageInfoTypeEntity> page = this.page(
                new Query<MessageInfoTypeEntity>().getPage(params),
                new QueryWrapper<MessageInfoTypeEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
