package com.info.modules.advertis.service.impl;

import com.info.modules.advertis.entity.AdvertisTypeEntity;
import com.info.modules.advertis.dao.IAdvertisTypeDao;
import com.info.modules.advertis.service.IAdvertisTypeService;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 社区广告类型信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-11 15:08:22
 */
@Service("advertisTypeService")
public class AdvertisTypeServiceImpl extends ServiceImpl<IAdvertisTypeDao, AdvertisTypeEntity> implements IAdvertisTypeService {

    /**
     * @Author: Gaosx  
     * @Description: 社区广告类型信息表
     * @Date: 2019-06-11 15:08:22
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<AdvertisTypeEntity> page = this.page(
                new Query<AdvertisTypeEntity>().getPage(params),
                new QueryWrapper<AdvertisTypeEntity>()
                        .select("")
                        .eq("is_del", 1)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
        );
        return new PageUtils(page);
    }

}
