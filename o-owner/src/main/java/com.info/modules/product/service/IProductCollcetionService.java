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
     * 功能描述: 商品收藏信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:54
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述:
     *
     * @Params:  * @param null 
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/26 10:11
     * @Return:
     */

}

