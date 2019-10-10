package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityUserInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-07-19 11:09:55
 */
public interface ICommunityUserInfoService extends IService<CommunityUserInfoEntity> {

    /**
     * 功能描述: 社区物业管理员信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);



}

