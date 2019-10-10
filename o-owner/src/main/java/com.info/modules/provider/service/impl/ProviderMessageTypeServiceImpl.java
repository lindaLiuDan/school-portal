package com.info.modules.provider.service.impl;

import com.info.modules.provider.dao.IProviderMessageTypeDao;
import com.info.modules.provider.entity.ProviderMessageTypeEntity;
import com.info.modules.provider.service.IProviderMessageTypeService;
import com.info.utils.ConfigConstant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 商家通知类型表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-14 19:42:01
 */
@Service("providerMessageTypeService")
public class ProviderMessageTypeServiceImpl extends ServiceImpl<IProviderMessageTypeDao, ProviderMessageTypeEntity> implements IProviderMessageTypeService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(ProviderMessageTypeServiceImpl.class);


    /**
     * 功能描述: 商家通知类型表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-14 19:42:01
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProviderMessageTypeEntity> page = this.page(
                new Query<ProviderMessageTypeEntity>().getPage(params),
                new QueryWrapper<ProviderMessageTypeEntity>()
                        .select("type_id,tname")
                        .eq("is_del", ConfigConstant.NOTDEL)
        );
        return new PageUtils(page);
    }

}
