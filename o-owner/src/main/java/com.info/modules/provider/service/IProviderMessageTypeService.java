package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderMessageTypeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商家通知类型表
 *
 * @author Gaosx
 * @email 
 * @date 2019-07-14 19:42:01
 */
public interface IProviderMessageTypeService extends IService<ProviderMessageTypeEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商家通知类型表
     * @Date: 2019-07-14 19:42:01
     */
    /**
     * 功能描述: 商家通知类型表
     *
     * @Params:  * @param null
     * @Author:  Gaosx
     * @Date: 2019-07-14 19:42:01
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

