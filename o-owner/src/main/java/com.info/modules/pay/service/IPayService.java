package com.info.modules.pay.service;

import com.alipay.api.AlipayApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import com.info.modules.order.entity.OrderInfoEntity;
import com.info.modules.pay.entity.PayTypeEntity;
import com.info.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


/**
 * 功能描述: 支付业务实现
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/9 12:32
 * @Return:
 */
public interface IPayService extends IService<PayTypeEntity> {


    /**
     * 功能描述: 分页查询订单日志信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/12 18:31
     * @Return:
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 功能描述:
     *
     * @param: 微信支付的业务层
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 16:27
     */
    Object wxPay(HttpServletRequest httpRequest, OrderInfoEntity orderInfoEntity);

    /**
     * 功能描述:
     *
     * @param: 微信支付回调接口
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 16:30
     */
    void notify(HttpServletRequest request, HttpServletResponse response) throws IOException;

    /**
     * 功能描述:
     *
     * @param: 支付测试
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 16:36
     */
    void test(String orderNo);

    /**
     * 功能描述:
     *
     * @param: 阿里支付
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 17:32
     */
    String aliPay(OrderInfoEntity orderInfoEntity) throws Exception;

    /**
     * 功能描述:
     *
     * @param: 支付宝会签
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 17:36
     */
    String callback(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException;


}
