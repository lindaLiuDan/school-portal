package com.info.modules.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.product.entity.ProductCateEntity;
import com.info.modules.product.vo.ProductCateVO;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
public interface IProductCateService extends IService<ProductCateVO> {


    /**
     * 功能描述: 商品品类信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:56
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 单个商品类别详情--返回实体
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 17:20
     * @Return:
     */
    ProductCateVO getProductCateById(Integer cateId);

    /**
     * 功能描述: 单个商品类别详情--返回String类型----暂时废弃方法-会返回的是名字不是实体
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 17:20
     * @Return:
     */
    String getProductCate(Integer cateId);

    /**
     * 功能描述: 根据父类ID查询下面的所有的子类别
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 19:08
     * @Return:
     */
    List<ProductCateVO> getParentIdList(Integer cateId,Integer typeId);

}

