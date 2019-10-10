package com.info.modules.product.service.impl;

import com.info.modules.product.entity.ProductCommentEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.product.dao.IProductCommentDao;
import com.info.modules.product.service.IProductCommentService;
import com.info.redis.RedisUtils;
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
import java.util.Map;

/**
 * 商品评论信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@Service("productCommentService")
public class ProductCommentServiceImpl extends ServiceImpl<IProductCommentDao, ProductCommentEntity> implements IProductCommentService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(ProductCommentServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ICrudRedisManager<ProductCommentEntity> crudRedisManager;


    /**
     * 功能描述: 商品评论信息表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<ProductCommentEntity> page = this.page(
                new Query<ProductCommentEntity>().getPage(params),
                new QueryWrapper<ProductCommentEntity>()
                        .select("")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
