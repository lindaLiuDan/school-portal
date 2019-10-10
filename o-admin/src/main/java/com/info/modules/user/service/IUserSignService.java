package com.info.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.user.entity.UserSignEntity;
import com.info.utils.PageUtils;

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
     * 功能描述: 会员业主签到信息表
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/18 17:56
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

