package com.info.modules.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.community.entity.CommunityInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区小区信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
public interface ICommunityInfoService extends IService<CommunityInfoEntity> {

    /**
     * @Author: Gaosx
     * @Description: 社区小区信息表
     * @Date: 2019-06-11 15:08:22
     */
    PageUtils queryPage(Map<String, Object> params);

}

