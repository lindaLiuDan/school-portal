package com.info.modules.user.service.impl;

import com.info.common.annotation.DataFilter;
import com.info.modules.user.dao.IUserAuthDao;
import com.info.modules.user.entity.UserAuthEntity;
import com.info.modules.user.service.IUserAuthService;
import com.info.utils.ConfigConstant;
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
 * 会员业主身份认证表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
@Service("userAuthService")
public class UserAuthServiceImpl extends ServiceImpl<IUserAuthDao, UserAuthEntity> implements IUserAuthService {


    /**
     * 功能描述: 会员业主身份认证表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 17:59
     * @Return:
     */
    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String parames = (String) params.get("params");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<UserAuthEntity> page = this.page(
                new Query<UserAuthEntity>().getPage(params),
                new QueryWrapper<UserAuthEntity>()
                        .select("user_id,user_name,nick_name,mobile,integral,sex,img,small_img,birthday,weixin")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
                        .like(StringUtils.isNotBlank(parames), "parames", parames)
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }

}
