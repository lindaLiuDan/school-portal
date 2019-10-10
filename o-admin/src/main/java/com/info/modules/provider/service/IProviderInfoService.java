package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
public interface IProviderInfoService extends IService<ProviderInfoEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商家信息表
     * @Date: 2019-06-24 16:06:30
     */
    PageUtils queryPage(Map<String, Object> params);

}

