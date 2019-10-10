package com.info.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.order.entity.OrderFlowEntity;

/**
 * 订单流程跟踪表:已支付，未支付，配送中等
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
public interface IOrderFlowDao extends BaseMapper<OrderFlowEntity> {

}
