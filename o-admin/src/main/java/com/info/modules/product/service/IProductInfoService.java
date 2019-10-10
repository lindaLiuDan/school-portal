package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductInfoEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商品信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
public interface IProductInfoService extends IService<ProductInfoEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商品信息表
     * @Date: 2019-06-25 19:50:03
     */
    PageUtils queryPage(Map<String, Object> params);

}

