package com.info.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.order.entity.OrderInfoDetailEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 订单详情信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
public interface IOrderInfoDetailService extends IService<OrderInfoDetailEntity> {


    /**
     * 功能描述: 订单详情信息表
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 10:27
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据订单号查询订单详情集合
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/9 17:19
     * @Return:
     */
    List<OrderInfoDetailEntity> getOrderIdItemList(Integer orderId);

}

