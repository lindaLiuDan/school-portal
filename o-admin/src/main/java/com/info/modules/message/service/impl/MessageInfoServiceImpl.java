package com.info.modules.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.manager.ICrudRedisManager;
import com.info.modules.message.dao.IMessageInfoDao;
import com.info.modules.message.entity.MessageInfoEntity;
import com.info.modules.message.service.IMessageInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import com.info.validator.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 功能描述: 消息通知表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:38
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String infoId = (String) params.get("infoId");
        Assert.isNull(infoId, "消息ID不能为空", ConfigConstant.ERROR);
        String title = (String) params.get("title");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<MessageInfoEntity> page = this.page(
                new Query<MessageInfoEntity>().getPage(params),
                new QueryWrapper<MessageInfoEntity>()
                        .select("content_id,info_id,type_id,title,creator_time,is_read")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq(StringUtils.isNotBlank(infoId), "info_id", infoId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(title), "title", title)
        );
        return new PageUtils(page);
    }

}
