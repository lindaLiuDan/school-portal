package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderTypeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商家分类信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
public interface IProviderTypeService extends IService<ProviderTypeEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商家分类信息表
     * @Date: 2019-06-24 16:06:30
     */
    PageUtils queryPage(Map<String, Object> params);

}

