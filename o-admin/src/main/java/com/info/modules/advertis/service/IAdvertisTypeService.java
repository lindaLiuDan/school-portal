package com.info.modules.advertis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.advertis.entity.AdvertisTypeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 社区广告类型信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-11 15:08:22
 */
public interface IAdvertisTypeService extends IService<AdvertisTypeEntity> {

    /**
     * 功能描述: 社区广告类型信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:48
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

