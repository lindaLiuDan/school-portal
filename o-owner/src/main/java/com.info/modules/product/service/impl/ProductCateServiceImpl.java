package com.info.modules.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.info.manager.ICrudRedisManager;
import com.info.modules.product.dao.IProductCateDao;
import com.info.modules.product.service.IProductCateService;
import com.info.modules.product.vo.ProductCateVO;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisKeyUtils;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.List;
import java.util.Map;

/**
 * 商品品类信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Service("productCateService")
public class ProductCateServiceImpl extends ServiceImpl<IProductCateDao, ProductCateVO> implements IProductCateService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private ICrudRedisManager<ProductCateVO> crudRedisManager;


    /**
     * 功能描述: 商品品类信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String cateName = (String) params.get("cateName");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<ProductCateVO> page = this.page(
                new Query<ProductCateVO>().getPage(params),
                new QueryWrapper<ProductCateVO>()
                        .select("cate_id,cate_name,parent_id,img,small_img,is_hot")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq(StringUtils.isNotBlank(cateName), "cate_name", cateName)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述:  单个商品类别详情--返回实体
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 17:20
     * @Return:
     */
    @Override
    public ProductCateVO getProductCateById(Integer cateId) {
        ProductCateVO cateEntity = crudRedisManager.hget(RedisKeyUtils.ProductKeys.CATE_INFO, cateId.toString(), ProductCateVO.class, "获取商品类别详情,Redis异常,Exception{},异常信息为");
        if (cateEntity == null) {
            cateEntity = this.getById(cateId);
            if (cateEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.ProductKeys.CATE_INFO, cateId.toString(), JSON.toJSONString(cateEntity), "存储单个商品类别详情,Redis异常,Exception{},异常信息为");
            }
            return cateEntity;
        }
        return cateEntity;
    }

    /**
     * 功能描述: 单个商品类别详情--返回String类型
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 17:20
     * @Return:
     */
    @Override
    public String getProductCate(Integer cateId) {
        String cateName = crudRedisManager.hget(RedisKeyUtils.ProductKeys.CATE_INFO_NAME, cateId.toString(), "获取商品类别详情,Redis异常,Exception{},异常信息为");
        if (cateName == null) {
            ProductCateVO cateEntity = this.getProductCateById(cateId);
            if (cateEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.ProductKeys.CATE_INFO_NAME, cateId.toString(), JSON.toJSONString(cateEntity), "存储单个商品类别详情,Redis异常,Exception{},异常信息为");
            }
            return cateEntity.getCateName();
        }
        return cateName;
    }

    /**
     * 功能描述: 根据父类ID查询下面的所有的子类别
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 19:08
     * @Return:
     */
    @Override
    public List<ProductCateVO> getParentIdList(Integer cateId,Integer typeId) {
        String cateListStr = crudRedisManager.hget(RedisKeyUtils.ProductKeys.CATE_PARENT, cateId.toString(), "获取parentId商品类别集合,Redis异常,Exception{},异常信息为");
        if (cateListStr == null) {
            List<ProductCateVO> list = this.listParent( cateId);
            if (list != null) {
                list.stream().forEach(info -> {
                    info.setProductCateVOList(this.listParent(info.getCateId()));
                });
            }
            crudRedisManager.hset(RedisKeyUtils.ProductKeys.CATE_PARENT, cateId.toString(), JSON.toJSONString(list), "存储parentId商品类别集合,Redis异常,Exception{},异常信息为");
            return list;
        }
        return JSON.parseArray(cateListStr, ProductCateVO.class);
    }

    /**
     * 功能描述: 根据ID主键查询自己的所有子类别---配合上面方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 10:16
     * @Return:
     */
    public List<ProductCateVO> listParent(Integer cateId) {
        return this.list(new QueryWrapper<ProductCateVO>()
                .select("cate_id,cate_name,parent_id,img,small_img")
                .eq("parent_id", cateId)
        );
    }

}
