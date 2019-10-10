package com.info.modules.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.pay.entity.PayTypeEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 功能描述: 支付类型表
 *
 * @auther: Gaosx 960889426@qq.com By User
 * @param:
 * @date: 2019/3/11 11:24
 */
public interface IPayTypeService extends IService<PayTypeEntity> {

    /**
     * 功能描述: 后台使用----支付类型表
     *
     * @auther: Gaosx 960889426@qq.com By User
     * @param:
     * @date: 2019/3/11 11:24
     */
    PageUtils queryPage(Map<String, Object> params);

}

