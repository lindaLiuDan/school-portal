package com.info.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.manager.ICrudRedisManager;
import com.info.modules.product.dao.IProductUnitDao;
import com.info.modules.product.service.IProductUnitService;
import com.info.modules.product.vo.ProductUnitVO;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 商品单位信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Service("productUnitService")
public class ProductUnitServiceImpl extends ServiceImpl<IProductUnitDao, ProductUnitVO> implements IProductUnitService {

    @Autowired
    private ICrudRedisManager<ProductUnitVO> crudRedisManager;


    /**
     * 功能描述: 商品单位信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<ProductUnitVO> page = this.page(
                new Query<ProductUnitVO>().getPage(params),
                new QueryWrapper<ProductUnitVO>()
                        .select("unit_id,unit_name,img,small_img")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述: 获取单个商品单位信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:59
     * @Return:
     */
    @Override
    public ProductUnitVO getProductUnitById(Integer unitId) {
        ProductUnitVO productUnitVO = crudRedisManager.hget(RedisKeyUtils.ProductKeys.UNIT_INFO, unitId.toString(), ProductUnitVO.class, "获取商品单位信息详情,Redis异常,Exception{},异常信息为");
        if (productUnitVO == null) {
            productUnitVO = this.getById(unitId);
            if (productUnitVO != null) {
                crudRedisManager.hset(RedisKeyUtils.ProductKeys.UNIT_INFO, unitId.toString(), JSON.toJSONString(productUnitVO), "获取商品单位信息详情,Redis异常,Exception{},异常信息为");
            }
            return productUnitVO;
        }
        return productUnitVO;
    }

    /**
     * 功能描述: 获取获取商品单位的名字-返回的String名字
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 16:59
     * @Return:
     */
    @Override
    public String getProductUnitName(Integer unitId) {
        String unitString = crudRedisManager.hget(RedisKeyUtils.ProductKeys.UNIT_INFO_NAME, unitId.toString(), "获取商品单位信息详情,Redis异常,Exception{},异常信息为");
        if (unitString == null) {
            ProductUnitVO productUnitVO = this.getProductUnitById(unitId);
            if (productUnitVO != null) {
                crudRedisManager.hset(RedisKeyUtils.ProductKeys.UNIT_INFO_NAME, unitId.toString(), productUnitVO.getUnitName(), "获取商品单位信息详情,Redis异常,Exception{},异常信息为");
            }
            return unitString;
        }
        return unitString;
    }

    /**
     * 功能描述: 无分页获取所有单位信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 10:42
     * @Return:
     */
    @Override
    public List<ProductUnitVO> getAllList(Map<String, Object> params) {
        String list = crudRedisManager.get(RedisKeyUtils.ProductKeys.UNIT_ALL, "获取所有商品单位信息详情,Redis异常,Exception{},异常信息为");
        if (list == null) {
            List<ProductUnitVO> unitVOList = this.list(new QueryWrapper<ProductUnitVO>()
                    .select("unit_id,unit_name,img,small_img")
            );
            if (unitVOList != null) {
                crudRedisManager.set(RedisKeyUtils.ProductKeys.UNIT_ALL, JSON.toJSONString(unitVOList), "存储所有商品单位信息详情,Redis异常,Exception{},异常信息为");
            }
            return unitVOList;
        }
        return JSON.parseArray(list, ProductUnitVO.class);
    }

}
