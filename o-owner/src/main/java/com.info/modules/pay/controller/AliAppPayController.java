//package com.info.modules.pay.controller;
//
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.info.common.annotation.Login;
//import com.info.common.base.AbstractController;
//import com.info.modules.order.entity.OrderInfoEntity;
//import com.info.modules.pay.service.IPayService;
//import com.info.pay.alipay.AlipayConfig;
//import com.info.utils.ConfigConstant;
//import com.info.utils.ResultMessage;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * 支付宝app支付接口
// */
//@RestController
//@RequestMapping(value = "api/aliPay")
//public class AliAppPayController extends AbstractController {
//
//    @Autowired
//    private IPayService payService;
//
//    /**
//     * 功能描述: ali支付开始调用
//     *
//     * @Params: * @param null
//     * @Author: Gaosx By User
//     * @Date: 2019/7/9 12:23
//     * @Return:
//     */
//    @Login
//    @GetMapping(value = "/sign", produces = "text/html;charset=UTF-8")
//    public ResultMessage signprams(OrderInfoEntity orderInfoEntity) {
//        payService.
//        return ResultMessage.error("支付失败,请重试");
//    }
//
//    /**
//     * 支付回调地址
//     *
//     * @param
//     * @return
//     */
//    @PostMapping(value = "/notify", produces = "application/json;charset=utf-8")
//    public String notify(HttpServletRequest request, HttpServletResponse response) {
//        Map requestParams = request.getParameterMap();
//        logger.info("支付宝----------支付结果通知" + requestParams.toString());
//        //获取支付宝POST过来反馈信息
//        Map<String, String> params = new HashMap<>(0);
//
//        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
//            String name = (String) iter.next();
//            String[] values = (String[]) requestParams.get(name);
//            String valueStr = "";
//            for (int i = 0; i < values.length; i++) {
//                valueStr = (i == values.length - 1) ? valueStr + values[i]
//                        : valueStr + values[i] + ",";
//            }
//            //乱码解决，这段代码在出现乱码时使用。
//            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//            params.put(name, valueStr);
//        }
//        //切记alipaypublickey是支付宝的公钥，请去open.alipay.com
//
//        logger.info("支付宝------------返回值参数: " + params);
//
//        try {
//            //验证签名
//            boolean flag = AlipaySignature.rsaCheckV1(params, AlipayConfig.ALIPAY_PUBLIC_KEY, AlipayConfig.INPUT_CHARSET, "RSA2");
//
//            logger.info("支付宝------------验证返回签名结果是否正确: " + flag);
//            if (flag) {
//                if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
//                    //付款金额
//                    String amount = params.get("buyer_pay_amount");
//                    //附加数据
//                    String[] business_params = params.get("passback_params").split("_");
//                    /*Integer payInfoId = Integer.parseInt(business_params[0]);
//                    Integer userId = Integer.parseInt(business_params[1]);*/
//                    //商户订单号
//                    String payNo = params.get("out_trade_no");
//                    userPayService.userAliPaySuccess(payNo, amount);
//                }
//            }
//        } catch (AlipayApiException e) {
//            logger.error("支付宝------------验证签名发生异常", e);
//            e.printStackTrace();
//        }
//        return "success";
//    }
//}
