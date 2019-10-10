package com.info.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.order.entity.OrderInfoLogEntity;
import com.info.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 订单日志信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
public interface IOrderInfoLogService extends IService<OrderInfoLogEntity> {

    /**
     * 功能描述: 分页列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:42
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 无分页查询所有该订单的日志
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:42
     * @Return:
     */
    List<OrderInfoLogEntity> allLog(Integer orderId);

    /**
     * 功能描述: 添加日志信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:56
     * @Return:
     */
    Boolean addLog(OrderInfoLogEntity orderInfoLogEntity);



}

