package com.info.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.order.entity.OrderFlowEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 订单流程跟踪表:已支付，未支付，配送中等
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
public interface IOrderFlowService extends IService<OrderFlowEntity> {

    /**
     * 功能描述: 订单流程跟踪表:已支付，未支付，配送中等
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/25 10:29
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 查询所有的订单流程
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 11:41
     * @Return:
     */
    List<OrderFlowEntity> AllFlow(Map<String, Object> params);

    /**
     * 功能描述: 获取单个流程信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 11:56
     * @Return:
     */
    OrderFlowEntity getFlowById(Integer flowId);

}

