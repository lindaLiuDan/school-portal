package com.info.modules.mobile.service.impl;

import com.info.modules.mobile.entity.CommunityMobileEntity;
import com.info.modules.mobile.dao.ICommunityMobileDao;
import com.info.modules.mobile.service.ICommunityMobileService;
import com.info.validator.Assert;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 社区便民生活电话
 *
 * @author Gaosx
 * @email
 * @date 2019-06-08 11:59:37
 */
@Service("communityMobileService")
public class CommunityMobileServiceImpl extends ServiceImpl<ICommunityMobileDao, CommunityMobileEntity> implements ICommunityMobileService {

    /**
     * @Author: Gaosx
     * @Description: 社区便民生活电话
     * @Date: 2019-06-08 11:59:37
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        String infoId = (String) params.get("infoId");
        Assert.isNull(infoId, "社区信息ID不能为空", 0);
        IPage<CommunityMobileEntity> page = this.page(
                new Query<CommunityMobileEntity>().getPage(params),
                new QueryWrapper<CommunityMobileEntity>()
                        .select("mobile_id,info_id,project,mobile")
                        .eq("info_id", infoId)
                        .orderByDesc("sort")
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

}
