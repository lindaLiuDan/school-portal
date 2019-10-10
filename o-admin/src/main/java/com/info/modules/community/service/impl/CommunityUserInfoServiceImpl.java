package com.info.modules.community.service.impl;

import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.community.dao.ICommunityUserInfoDao;
import com.info.modules.community.entity.CommunityUserInfoEntity;
import com.info.modules.community.service.ICommunityUserInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
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
 * 社区物业管理员信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-07-19 11:09:55
 */
@Service("communityUserInfoService")
public class CommunityUserInfoServiceImpl extends ServiceImpl<ICommunityUserInfoDao, CommunityUserInfoEntity> implements ICommunityUserInfoService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(CommunityUserInfoServiceImpl.class);

    @Autowired
    private ICrudRedisManager<CommunityUserInfoEntity> crudRedisManager;


    /**
     * 功能描述: 社区物业管理员信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-07-19 11:09:55
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String mobile = (String) params.get("mobile");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityUserInfoEntity> page = this.page(
                new Query<CommunityUserInfoEntity>().getPage(params),
                new QueryWrapper<CommunityUserInfoEntity>()
                        .select("user_id,info_id,mobile,creator,creator_time")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(mobile), "mobile", mobile)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
