package com.info.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.order.entity.OrderCartEntity;
import com.info.utils.PageUtils;

import java.util.Map;

/**
 * 购物车信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:05
 */
public interface IOrderCartService extends IService<OrderCartEntity> {


    /**
     * 功能描述: 购物车信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 10:30
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

}

