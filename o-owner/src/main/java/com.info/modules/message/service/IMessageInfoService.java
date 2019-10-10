package com.info.modules.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.message.entity.MessageInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 消息通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-18 11:40:10
 */
public interface IMessageInfoService extends IService<MessageInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 消息通知表
     * @Date: 2019-06-18 11:40:10
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 详情查看
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/18 13:22
     * @Return:
     */
    MessageInfoEntity getMessageByInfo(Integer contentId);

}

