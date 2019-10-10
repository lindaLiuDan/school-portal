package com.info.modules.complaint.service.impl;

import com.info.common.annotation.DataFilter;
import com.info.modules.complaint.dao.ICommunityComplaintDao;
import com.info.modules.complaint.entity.CommunityComplaintEntity;
import com.info.modules.complaint.service.ICommunityComplaintService;
import com.info.modules.user.service.impl.UserAuthServiceImpl;
import com.info.redis.RedisUtils;
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
 * 社区投诉建议信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-17 14:31:58
 */
@Service("communityComplaintService")
public class CommunityComplaintServiceImpl extends ServiceImpl<ICommunityComplaintDao, CommunityComplaintEntity> implements ICommunityComplaintService {


    //日志方法调用
    private static final Logger logger = LoggerFactory.getLogger(UserAuthServiceImpl.class);

    @Autowired
    private RedisUtils redisUtils;


    /**
     * @Author: Gaosx
     * @Description: 社区投诉建议信息表
     * @Date: 2019-06-17 14:31:58
     */
    /**
     * 功能描述: 社区投诉建议信息表
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-17 14:31:58
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<CommunityComplaintEntity> page = this.page(
                new Query<CommunityComplaintEntity>().getPage(params),
                new QueryWrapper<CommunityComplaintEntity>()
                        .select("")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
