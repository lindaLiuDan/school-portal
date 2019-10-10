package com.info.modules.user.service.impl;

import com.info.common.annotation.DataFilter;
import com.info.manager.ICrudRedisManager;
import com.info.modules.user.dao.IUserInfoDao;
import com.info.modules.user.entity.UserInfoEntity;
import com.info.modules.user.service.IUserInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.Constant;
import org.apache.commons.lang.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.info.utils.PageUtils;
import com.info.utils.Query;

import java.util.Map;

/**
 * 会员业主信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends ServiceImpl<IUserInfoDao, UserInfoEntity> implements IUserInfoService {

    @Autowired
    private ICrudRedisManager<UserInfoEntity> crudRedisManager;

    /**
     * 功能描述: 会员业主信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 17:51
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String userName = (String) params.get("userName");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        String userId = (String) params.get("userId");
        String mobile = (String) params.get("mobile");
        String sex = (String) params.get("sex");
        IPage<UserInfoEntity> page = this.page(
                new Query<UserInfoEntity>().getPage(params),
                new QueryWrapper<UserInfoEntity>()
                        .select("user_id,user_name,nick_name,mobile,integral,sex,img,small_img,birthday,weixin")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq(StringUtils.isNotBlank(sex), "sex", sex)
                        .eq(StringUtils.isNotBlank(userId), "user_id", userId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(userName), "user_name", userName)
                        .like(StringUtils.isNotBlank(mobile), "mobile", mobile)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
