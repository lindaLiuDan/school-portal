package com.info.modules.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.message.entity.CommunityMessageEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区通告通知表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-17 17:52:42
 */
public interface ICommunityMessageService extends IService<CommunityMessageEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 社区通告通知表
     * @Date: 2019-06-17 17:52:42
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 详情接口
     *
     * @Params:  * @param null
     * @Author:  Gaosx 960889426@qq.com By User
     * @Date: 2019/6/17 18:42
     * @Return:
     */
    CommunityMessageEntity getMessageById(Integer meId);

}

