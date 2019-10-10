package com.info.modules.advertis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.advertis.entity.AdvertisInfoEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

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
     * 功能描述: 社区小区广告信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 16:51
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页获取本社区所有的广告信息列表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 16:56
     * @Return:
     */
    List<AdvertisInfoEntity> all(Integer infoId);

    /**
     * 功能描述: 获取广告详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:12
     * @Return:
     */
    AdvertisInfoEntity getAdvertisInfo(Integer advertisId);

    /**
     * 功能描述: 添加广告信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:20
     * @Return:
     */
    ResultMessage addAdvertisInfo(AdvertisInfoEntity advertisInfoEntity);

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/17 17:23
     * @Return:
     */
    ResultMessage delAdvertisInfo(Integer advertisId,String InfoId);

    /**
     * 功能描述: 更改广告信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/17 17:29
     * @Return:
     */
    ResultMessage updateAdvertisInfo(AdvertisInfoEntity advertisInfoEntity);


}

