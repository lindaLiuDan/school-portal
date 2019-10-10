package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductCollcetionEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商品收藏信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 16:39:47
 */
public interface IProductCollcetionService extends IService<ProductCollcetionEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商品收藏信息表
     * @Date: 2019-06-25 16:39:47
     */
    PageUtils queryPage(Map<String, Object> params);

}

