package com.info.modules.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.message.entity.MessageInfoTypeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 消息通知类型表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-18 11:40:10
 */
public interface IMessageInfoTypeService extends IService<MessageInfoTypeEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 消息通知类型表
     * @Date: 2019-06-18 11:40:10
     */
    PageUtils queryPage(Map<String, Object> params);

}

