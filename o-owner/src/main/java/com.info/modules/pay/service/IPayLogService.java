package com.info.modules.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.pay.entity.PayLogEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 功能描述: 支付日志信息表
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/3/11 11:23
 */
public interface IPayLogService extends IService<PayLogEntity> {

    /**
     * 功能描述: 后台使用---支付日志信息表
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2019/3/11 11:23
     */
    PageUtils queryPage(Map<String, Object> params);

}

