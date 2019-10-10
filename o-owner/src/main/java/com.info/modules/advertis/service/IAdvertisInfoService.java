package com.info.modules.advertis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 社区小区广告信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-11 15:08:22
 */
public interface IAdvertisInfoService extends IService<AdvertisInfoEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 社区小区广告信息表
     * @Date: 2019-06-11 15:08:22
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页获取本社区所有的广告信息列表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/17 16:56
     * @Return:
     */
    List<AdvertisInfoEntity> all(Integer infoId);

    /**
     * 功能描述: 获取广告详细信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/17 17:12
     * @Return:
     */
    AdvertisInfoEntity getAdvertisInfo(Integer advertisId);

}

