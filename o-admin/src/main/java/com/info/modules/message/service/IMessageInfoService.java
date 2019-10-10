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
     * 功能描述: 消息通知表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:38
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

