package com.info.modules.product.service.impl;

import com.info.modules.product.dao.IProductCollcetionDao;
import com.info.modules.product.entity.ProductCollcetionEntity;
import com.info.modules.product.service.IProductCollcetionService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisUtils;
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

import java.util.Map;

/**
 * 商品收藏信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@Service("productCollcetionService")
public class ProductCollcetionServiceImpl extends ServiceImpl<IProductCollcetionDao, ProductCollcetionEntity> implements IProductCollcetionService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 功能描述: 商品收藏信息表
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
        IPage<ProductCollcetionEntity> page = this.page(
                new Query<ProductCollcetionEntity>().getPage(params),
                new QueryWrapper<ProductCollcetionEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
