package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderMessageEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商户通知表
 *
 * @author Gaosx
 * @email 
 * @date 2019-07-14 19:42:01
 */
public interface IProviderMessageService extends IService<ProviderMessageEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商户通知表
     * @Date: 2019-07-14 19:42:01
     */
    /**
     * 功能描述: 商户通知表
     *
     * @Params:  * @param null
     * @Author:  Gaosx
     * @Date: 2019-07-14 19:42:01
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

