package com.info.modules.advertis.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.common.annotation.DataFilter;
import com.info.modules.advertis.dao.IAdvertisTypeDao;
import com.info.modules.advertis.entity.AdvertisTypeEntity;
import com.info.modules.advertis.service.IAdvertisTypeService;
import com.info.utils.Constant;
import com.info.utils.PageUtils;
import com.info.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

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
     * 功能描述: 社区广告类型信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/22 15:48
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
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
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
