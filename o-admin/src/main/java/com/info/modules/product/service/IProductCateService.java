package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductCateEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 16:39:47
 */
public interface IProductCateService extends IService<ProductCateEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商品品类信息表
     * @Date: 2019-06-25 16:39:47
     */
    PageUtils queryPage(Map<String, Object> params);

}

