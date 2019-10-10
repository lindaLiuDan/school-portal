package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductCommentTagEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商品评论标签表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
public interface IProductCommentTagService extends IService<ProductCommentTagEntity> {

    /**
     * @Author: Gaosx  
     * @Description: 商品评论标签表
     * @Date: 2019-06-25 19:50:03
     */
    PageUtils queryPage(Map<String, Object> params);

}

