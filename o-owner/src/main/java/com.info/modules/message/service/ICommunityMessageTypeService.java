package com.info.modules.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.message.entity.CommunityMessageTypeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区通告通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 17:52:42
 */
public interface ICommunityMessageTypeService extends IService<CommunityMessageTypeEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区通告通知类型表
     * @Date: 2019-06-17 17:52:42
     */
    PageUtils queryPage(Map<String, Object> params);

}

