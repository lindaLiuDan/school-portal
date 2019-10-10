package com.info.modules.user.service.impl;

import com.info.common.annotation.DataFilter;
import com.info.modules.user.dao.IUserSignDao;
import com.info.modules.user.entity.UserSignEntity;
import com.info.modules.user.service.IUserSignService;
import com.info.utils.Constant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 会员业主签到信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@Service("userSignService")
public class UserSignServiceImpl extends ServiceImpl<IUserSignDao, UserSignEntity> implements IUserSignService {


    /**
     * 功能描述: 会员业主签到信息表
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/18 17:56
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String userId = (String) params.get("userId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserSignEntity> page = this.page(
                new Query<UserSignEntity>().getPage(params),
                new QueryWrapper<UserSignEntity>()
                        .eq(StringUtils.isNotBlank(userId), "user_id", userId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
