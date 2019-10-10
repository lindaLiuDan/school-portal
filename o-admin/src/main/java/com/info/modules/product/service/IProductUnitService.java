package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductUnitEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商品单位信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 16:39:47
 */
public interface IProductUnitService extends IService<ProductUnitEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商品单位信息表
     * @Date: 2019-06-25 16:39:47
     */
    PageUtils queryPage(Map<String, Object> params);

}

