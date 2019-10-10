package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.vo.ProductUnitVO;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品单位信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
public interface IProductUnitService extends IService<ProductUnitVO> {


    /**
     * 功能描述: 商品单位信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:51
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 获取单个商品单位信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:59
     * @Return:
     */
    ProductUnitVO getProductUnitById(Integer unitId);

    /**
     * 功能描述: 获取获取商品单位的名字-返回的String名字
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:59
     * @Return:
     */
    String getProductUnitName(Integer unitId);

    /**
     * 功能描述: 无分页获取所有单位信息
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/8 10:42
     * @Return:
     */
    List<ProductUnitVO> getAllList(Map<String, Object> params);

}

