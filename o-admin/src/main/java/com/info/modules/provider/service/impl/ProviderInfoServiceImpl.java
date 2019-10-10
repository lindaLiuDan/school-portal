package com.info.modules.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.provider.dao.IProviderInfoDao;
import com.info.modules.provider.entity.ProviderInfoEntity;
import com.info.modules.provider.service.IProviderInfoService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisUtils;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 商家信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-24 16:06:30
 */
@Service("providerInfoService")
public class ProviderInfoServiceImpl extends ServiceImpl<IProviderInfoDao, ProviderInfoEntity> implements IProviderInfoService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @Author: Gaosx  
     * @Description: 商家信息表
     * @Date: 2019-06-24 16:06:30
     */
    /**
     * 功能描述: 商家信息表
     *
     * @Params:  * @param null
     * @Author:  Gaosx 
     * @Date: 2019-06-24 16:06:30
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<ProviderInfoEntity> page = this.page(
                new Query<ProviderInfoEntity>().getPage(params),
                new QueryWrapper<ProviderInfoEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
