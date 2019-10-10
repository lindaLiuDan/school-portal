package com.info.modules.mobile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.mobile.entity.CommunityMobileEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区便民生活电话
 *
 * @author Gaosx
 * @email
 * @date 2019-06-08 11:59:37
 */
public interface ICommunityMobileService extends IService<CommunityMobileEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区便民生活电话
     * @Date: 2019-06-08 11:59:37
     */
    PageUtils queryPage(Map<String, Object> params);

}

