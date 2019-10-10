package com.info.modules.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.info.modules.order.entity.OrderInfoEntity;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
public interface IOrderInfoDao extends BaseMapper<OrderInfoEntity> {


    /**
     * 功能描述: 订单入库操作
     *
     * @Params:  * @param null
     * @Author:  Gaosx By User
     * @Date: 2019/7/9 9:03
     * @Return:
     */
    Integer save(OrderInfoEntity orderInfoEntity);

}
