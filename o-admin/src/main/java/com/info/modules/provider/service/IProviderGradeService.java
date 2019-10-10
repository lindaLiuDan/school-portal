package com.info.modules.provider.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.provider.entity.ProviderGradeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商家(店铺)等级表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
public interface IProviderGradeService extends IService<ProviderGradeEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商家(店铺)等级表
     * @Date: 2019-06-24 16:06:30
     */
    PageUtils queryPage(Map<String, Object> params);

}

