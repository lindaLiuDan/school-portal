package com.info.modules.pay.controller;

import com.info.common.base.AbstractController;
import com.info.modules.order.entity.OrderInfoEntity;
import com.info.modules.pay.service.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述: 阿里支付的视图层
 *
 * @Params:  * @param null
 * @Author:  Gaosx By User
 * @Date: 2019/7/9 12:35
 * @Return:
 */
@Controller
@RequestMapping(value = "api/aliPay")
public class AlipayController extends AbstractController {

    /**
     * 微信支付业务实现层
     */
    @Autowired
    private IPayService payService;



    /*
    * body:对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。	Iphone6 16G
    * subject:商品的标题/交易标题/订单标题/订单关键字等。	大乐透
    * out_trade_no:商户网站唯一订单号	70501111111S001111119
    * total_amount:订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	9.00
    * seller_id:收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID	2088102147948060
    *
    *
    * */

    /**
     * 功能描述: 支付宝请求签名
     *
     * @param:
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 17:40
     */
    @ResponseBody
    @RequestMapping(value = "/pay", produces = "text/html;charset=UTF-8", method = {RequestMethod.GET})
    public String alipay(OrderInfoEntity orderInfoEntity) throws Exception {
       return payService.aliPay(orderInfoEntity);
    }


    /**
     * 功能描述:
     *
     * @param: 支付宝验签
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 17:40
     */
    @ResponseBody
    @RequestMapping(value = "/callback", produces = "text/html;charset=UTF-8", method = {RequestMethod.POST})
    public String payCallback(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return payService.callback(request,response);
    }


    
    public static void main(String[] args) throws Exception {
//        IAppOrderService appOrderService = new IAppOrderService();
//        OrderBean orderBean = new OrderBean();
//        orderBean.setOrderNo("YH20170220230247242115623");
//        orderBean.setTradeNo("22222222222222222");
//        BigDecimal bd=new BigDecimal(2.01000);
//        orderBean.setTotalMoney(bd);
//        orderBean.setPaymentTypeId(1);
//        orderBean.setOrderFlowId(2);
//        orderBean.setPaymentTypeName("支付宝");
//        orderBean.setRemark("订单支付成功，订单状态修改为：已支付");
//        appOrderService.updateOrderState(orderBean);
//        orderBean.setDescn("订单已支付成功，实际支付金额是：" + bd);
//        appOrderService.submitOrderLog(orderBean);

//        orderBean.setOrderNo("YH201704151202241295801108");
//        orderBean = appOrderService.selectUserInfo(orderBean);
//        if(orderBean.getIntroMan() != 0){
//            String str = "115.2";
//            Double integerInt = Double.parseDouble(str);
//            Double aDoubles = integerInt/100;
//            Integer ii = 0;
//            if(aDoubles < 1){
//                orderBean.setIntegral(1);
//            } else {
//                ii = (new BigDecimal(aDoubles).setScale(0, BigDecimal.ROUND_HALF_UP)).intValue();
//                orderBean.setIntegral(ii);
//            }
//            orderBean.setIntro(orderBean.getOrderNo() + "订单已支付成功,服务站返积分：" + ii);
//            appOrderService.addToIntroMan(orderBean);
//        }
    }


}


