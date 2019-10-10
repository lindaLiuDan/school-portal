package com.info.modules.pay.controller;

import com.info.common.base.AbstractController;
import com.info.modules.order.entity.OrderInfoEntity;
import com.info.modules.pay.service.IPayService;
import org.jdom.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 功能描述: 微信支付业务层
 *
 * @Params:  * @param null
 * @Author:  Gaosx By User
 * @Date: 2019/7/9 12:34
 * @Return:
 */
@RestController
@RequestMapping("/api/wxPay")
public class WeiXinPayController extends AbstractController {

    /**
     * 支付业务层
     */
    @Autowired
    private IPayService payService;


    /**
     * 功能描述:
     *
     * @param:  微信支付
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 16:35
     */
    @PostMapping("/pay")
    public Object wXPay(HttpServletRequest httpRequest, OrderInfoEntity orderInfoEntity) {
        return payService.wxPay(httpRequest,orderInfoEntity);
    }

    /**
     * 功能描述:
     *
     * @param:  微信支付回调
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 16:35
     */
    @PostMapping("notify")
    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
        payService.notify(request,response);
    }

    /**
     * 功能描述:  测试回调接口中SQL语句
     *
     * @param:
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/10/27 11:10
     */
    @PostMapping("/test")
    public void test(String orderNo) throws Exception {
        payService.test(orderNo);
    }

}
