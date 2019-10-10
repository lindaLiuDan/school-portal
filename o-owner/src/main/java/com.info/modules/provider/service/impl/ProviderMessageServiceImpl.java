package com.info.modules.provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.modules.provider.dao.IProviderMessageDao;
import com.info.modules.provider.entity.ProviderMessageEntity;
import com.info.modules.provider.service.IProviderMessageService;
import com.info.utils.ConfigConstant;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 商户通知表
 *
 * @author Gaosx
 * @email
 * @date 2019-07-14 19:42:01
 */
@Service("providerMessageService")
public class ProviderMessageServiceImpl extends ServiceImpl<IProviderMessageDao, ProviderMessageEntity> implements IProviderMessageService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(ProviderMessageServiceImpl.class);


    /**
     * 功能描述: 商户通知表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-14 19:42:01
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String typeId = (String) params.get("typeId");
        String providerId = (String) params.get("userId");
        IPage<ProviderMessageEntity> page = this.page(
                new Query<ProviderMessageEntity>().getPage(params),
                new QueryWrapper<ProviderMessageEntity>()
                        .select("me_id,provider_id,title,content,creator_time")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq(StringUtils.isNotBlank(typeId), "type_id", typeId)
                        .eq(StringUtils.isNotBlank(typeId), "provider_id", providerId)
        );
        return new PageUtils(page);
    }

}
