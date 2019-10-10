package com.info.modules.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.user.entity.UserSignEntity;
import com.info.utils.ResultMessage;

/**
 * 会员业主签到信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-13 14:47:56
 */
public interface IUserSignDao extends BaseMapper<UserSignEntity> {

    /**
     * 功能描述: 用户签到接口
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/15 17:55
     * @Return:
     */
    Boolean saveSignInfo(UserSignEntity userSignEntity);

}
