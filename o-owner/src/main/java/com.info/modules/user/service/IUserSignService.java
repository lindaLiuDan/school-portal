package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.UserSignEntity;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import java.util.Map;

/**
 * 会员业主签到信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-13 14:47:56
 */
public interface IUserSignService extends IService<UserSignEntity> {

    /**
     * @Author: Gaosx
     * @Description: 会员业主签到信息表
     * @Date: 2019-06-13 14:47:56
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 用户签到接口
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/15 17:55
     * @Return:
     */
    ResultMessage saveSignInfo(UserSignEntity userSignEntity);

}

