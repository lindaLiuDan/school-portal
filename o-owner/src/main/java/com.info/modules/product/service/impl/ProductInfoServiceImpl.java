package com.info.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.manager.ICrudRedisManager;
import com.info.modules.product.dao.IProductInfoDao;
import com.info.modules.product.service.IProductInfoService;
import com.info.modules.product.vo.ProductInfoVO;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
@Service("productInfoService")
public class ProductInfoServiceImpl extends ServiceImpl<IProductInfoDao, ProductInfoVO> implements IProductInfoService {

    @Autowired
    private ICrudRedisManager<ProductInfoVO> crudRedisManager;


    /**
     * 功能描述: 商品信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String providerId = (String) params.get("providerId");
        String productName = (String) params.get("productName");
        String endTime = (String) params.get("endTime");
        IPage<ProductInfoVO> page = this.page(
                new Query<ProductInfoVO>().getPage(params),
                new QueryWrapper<ProductInfoVO>()
                        .select("product_id,unit_id,provider_id,cate_id,product_no,product_name,stock,img,small_img,sales_price,click_num,score_integral,weight,saled_num")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq("status", Constant.ProductInfoStatus.STATUS_THREE)
                        .eq(StringUtils.isNotBlank(providerId), "provider_id", providerId)
                        .like(StringUtils.isNotBlank(productName), "product_name", productName)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 根据ID主键商品详情查询
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 17:00
     * @Return:
     */
    @Override
    public ProductInfoVO getProductInfoById(Integer productInfoId) {
        ProductInfoVO productInfoVO = crudRedisManager.hget(RedisKeyUtils.ProductKeys.PRODUCT_INFO, productInfoId.toString(), ProductInfoVO.class, "获取商品详情信息,Redis异常,Exception{},异常信息为:");
        if (productInfoVO == null) {
            productInfoVO = this.detail(productInfoId);
            if (productInfoVO != null) {
                crudRedisManager.hset(RedisKeyUtils.ProductKeys.PRODUCT_INFO, productInfoId.toString(), JSON.toJSONString(productInfoVO), "获取商品详情信息,Redis异常,Exception{},异常信息为:");
            }
            return productInfoVO;
        }
        return productInfoVO;
    }

    /**
     * 功能描述: 根据商品ID查询单个商品信息-----配合上面的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 9:37
     * @Return:
     */
    public ProductInfoVO detail(Integer productInfoId) {
        return this.getOne(new QueryWrapper<ProductInfoVO>()
                .select("product_id,unit_id,provider_id,cate_id,product_no,product_name,stock,img,small_img,sales_price,click_num,score_integral,weight,saled_num")
                .eq("product_id", productInfoId)

        );
    }

    /**
     * 功能描述: 保存添加商品信息 TODO 图片未处理
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:53
     * @Return:
     */
    @Override
    public ResultMessage saveProductInfo(ProductInfoVO productInfoForm) {
        Boolean flag=this.save(productInfoForm);
        if(flag){
            return ResultMessage.ok();
        }else {
            return ResultMessage.err();
        }

    }

    /**
     * 功能描述: 商家修改商品信息 TODO 图片未处理
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:53
     * @Return:
     */
    @Override
    public ResultMessage updateProductInfo(ProductInfoVO productInfoForm) {
        Boolean flag=this.updateById(productInfoForm);
        if(flag){
            crudRedisManager.hdel(RedisKeyUtils.ProductKeys.PRODUCT_INFO,"删除商品详情信息,Redis异常:",productInfoForm.getProductId().toString());
            return ResultMessage.ok();
        }else {
            return ResultMessage.err();
        }
    }

    /**
     * 功能描述: 下架，上架商品信息等常规操作，商品状态  status:1 下架的 2 未销售  3 在售
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:57
     * @Return:
     */
    @Override
    public ResultMessage onProductInfo(Integer productInfoId,Integer status) {
        ProductInfoVO productInfoVO=new ProductInfoVO();
        productInfoVO.setProductId(productInfoId);
        productInfoVO.setStatus(status);
        Boolean flag=this.updateById(productInfoVO);
        if(flag){
            crudRedisManager.hdel(RedisKeyUtils.ProductKeys.PRODUCT_INFO,"删除商品详情信息,Redis异常:",productInfoId.toString());
            return ResultMessage.ok();
        }else {
            return ResultMessage.err();
        }
    }

    /**
     * 功能描述: 商家删除商品信息 TODO 图片未处理
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:57
     * @Return:
     */
    @Override
    public ResultMessage delProductInfoList(Integer[] productIds) {
        Boolean flag=this.removeByIds(Arrays.asList(productIds));
        if(flag){
//            crudRedisManager.hdel(RedisKeyUtils.ProductKeys.PRODUCT_INFO,"删除商品详情信息,Redis异常:",productIds);
            return ResultMessage.ok();
        }else {
            return ResultMessage.err();
        }
    }

    /**
     * 功能描述: 商家删除商品信息--单个删除  TODO 图片未处理
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/7 23:57
     * @Return:
     */
    @Override
    public ResultMessage delProductInfoById(Integer productId) {
        Boolean flag=this.removeById(productId);
        if(flag){
            crudRedisManager.hdel(RedisKeyUtils.ProductKeys.PRODUCT_INFO,"删除商品详情信息,Redis异常:",productId.toString());
            return ResultMessage.ok();
        }else {
            return ResultMessage.err();
        }
    }

}
