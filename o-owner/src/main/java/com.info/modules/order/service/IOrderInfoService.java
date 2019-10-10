package com.info.modules.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.order.entity.OrderInfoEntity;
import com.info.modules.order.form.SaveOrderInfoForm;
import com.info.modules.order.form.UpdateOrderInfoForm;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
public interface IOrderInfoService extends IService<OrderInfoEntity> {

    /**
     * 功能描述: 分页查询我的订单信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:08
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述: 根据订单ID查询我的订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:08
     * @Return:
     */
    OrderInfoEntity getOrderInfoDetail(Integer orderId);

    /**
     * 功能描述: 提交订单方法
     *
     * @Params: @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:10
     * @Return:
     */
    ResultMessage saveOrderInfo(SaveOrderInfoForm orderInfoForm, HttpServletRequest request) throws Exception;

    /**
     * 功能描述: 取消删除订单的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:57
     * @Return:
     */
    OrderInfoEntity delOrderInfo(Integer orderId);

    /**
     * 功能描述: 根据订单No查询订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:57
     * @Return:
     */
    OrderInfoEntity getOrderInfoByNO(String orderNo);

    /**
     * 功能描述: 根据订单ID主键查询订单信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 17:42
     * @Return:
     */
    OrderInfoEntity getOrderInfoById(Integer orderId);

    /**
     * 功能描述: 根据订单编号修改订单状态
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 12:13
     * @Return:
     */
    Boolean updateByOrderNO(UpdateOrderInfoForm updateOrderInfoForm);

    /**
     * 功能描述: 根据订单编号修改订单状态
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 12:13
     * @Return:
     */
    Boolean updateByOrderNO(OrderInfoEntity orderInfoEntity);


}

