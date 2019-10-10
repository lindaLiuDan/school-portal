package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductInfoEntity;
import com.info.modules.product.form.ProductInfoEntityForm;
import com.info.modules.product.vo.ProductInfoVO;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 商品信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
public interface IProductInfoService extends IService<ProductInfoVO> {

    /**
     * @Author: Gaosx
     * @Description: 商品信息表
     * @Date: 2019-06-25 19:50:03
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据ID主键商品详情查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 17:00
     * @Return:
     */
    ProductInfoVO getProductInfoById(Integer productInfoId);

    /**
     * 功能描述: 保存添加商品信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:53
     * @Return:
     */
    ResultMessage saveProductInfo(ProductInfoVO productInfoForm);

    /**
     * 功能描述: 商家修改商品信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:53
     * @Return:
     */
    ResultMessage updateProductInfo(ProductInfoVO productInfoForm);

    /**
     * 功能描述: 商家删除商品信息--批量删除
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:57
     * @Return:
     */
    ResultMessage delProductInfoList(Integer[] productIds);

    /**
     * 功能描述: 商家删除商品信息--单个删除
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:57
     * @Return:
     */
    ResultMessage delProductInfoById(Integer productId);

    /**
     * 功能描述: 下架，上架商品信息等常规操作，
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/7 23:57
     * @Return:
     */
    ResultMessage onProductInfo(Integer productInfoId,Integer status);


}

