//package com.info.modules.pay.controller;
//
//import com.info.common.ConfigConstant;
//import com.info.common.annotation.Login;
//import com.info.modules.sys.AbstractController;
//import com.info.modules.user.entity.UserPayEntity;
//import com.info.modules.user.service.IUserPayService;
//import com.info.pay.weixinpay.PayCommonUtil;
//import com.info.pay.weixinpay.PropertyUtil;
//import com.info.pay.weixinpay.XmlUtils;
//import com.info.utils.ResultMessage;
//import org.apache.commons.lang.StringUtils;
//import org.jdom.JDOMException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.BufferedOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
///*微信app支付接口*/
//
//
//@RestController
//public class WeixinAppController extends AbstractController {
//    @Autowired
//    private IUserPayService userPayService;
//    /*拉取微信预付单*/
//
//
//    /* @SysLog("微信支付")**
//
//     */
//    @Login
//    @RequestMapping("/api/pay/wxPay/getWXPay")
//    public Object getWXPay(HttpServletRequest httpRequest, Integer userId, Integer info, Integer payUse) {
//      /*  RechargeNorm rechargeNorm = rechargeNormService.getRechargeNormBySign(rechargeNormId);
//        if (rechargeNorm == null) {
//            logger.error("微信-----------支付异常，请稍后再试");
//            return R.error("Payment is abnormal. Please try again later");
//        }
//        BigDecimal price = rechargeNorm.getPriceChina();
//     */
//        if (payUse == null || userId == null) {
//            logger.error("重要参数为空, payUse{},userId{}", payUse, userId);
//            return ResultMessage.error("重要参数为空,无法支付");
//        }
//        try {
//            UserPayEntity userPayEntity = null;
//            if (payUse.equals(ConfigConstant.SysConfig.PAY_TYPE_REG)) {
//                userPayEntity = userPayService.saveInfo(ConfigConstant.SysConfig.PAY_WEIXIN, ConfigConstant.SysConfig.PAY_TYPE_REG, null, userId);
//            } else if (payUse.equals(ConfigConstant.SysConfig.PAY_TYPE_CAED)) {
//                userPayEntity = userPayService.saveInfo(ConfigConstant.SysConfig.PAY_WEIXIN, ConfigConstant.SysConfig.PAY_TYPE_CAED, info, userId);
//            } else if (payUse.equals(ConfigConstant.SysConfig.PAY_TYPE_MOBILE)) {
//                userPayEntity = userPayService.saveInfo(ConfigConstant.SysConfig.PAY_WEIXIN, ConfigConstant.SysConfig.PAY_TYPE_MOBILE, info, userId);
//            } else {
//                return ResultMessage.error("支付用途类型未知,请重试");
//            }
//            if (userPayEntity == null) {
//                return ResultMessage.error("Payment is abnormal. Please try again later");
//            }
//            // 获取预付单，此处已做封装，需要工具类
//            SortedMap<Object, Object> parameters = PayCommonUtil.getWXPrePayID();
//
//            parameters.put("body", "duoke");
//
//            parameters.put("spbill_create_ip", httpRequest.getRemoteAddr());
//            parameters.put("out_trade_no", userPayEntity.getPayNo()); // 订单号
//            parameters.put("total_fee", String.valueOf(userPayEntity.getTotalMoney().multiply(new BigDecimal(100)).intValue())); // 金额
//            parameters.put("attach", userPayEntity.getInfoId() + "_" + userId); // 充值规格id
//
//            // 设置签名
//            String sign = PayCommonUtil.createSign("UTF-8", parameters);
//            parameters.put("sign", sign);
//            // 封装请求参数结束
//            String requestXML = PayCommonUtil.getRequestXml(parameters); // 获取xml结果
//            logger.debug("微信----------封装请求参数是：" + requestXML);
//            // 调用统一下单接口
//            String result = PayCommonUtil.httpsRequest(PropertyUtil.getInstance().getProperty("WxPay.payURL"), "POST",
//                    requestXML);
//            logger.debug("微信---------调用统一下单接口：" + result);
//            Map<String, String> rem = XmlUtils.doXMLParse(result);
//            String return_code = rem.get("return_code");
//            if (!"SUCCESS".equals(return_code)) {
//                logger.error("微信-----------支付异常，请稍后再试");
//                return ResultMessage.error("ERROR");
//            }
//            SortedMap<Object, Object> parMap = PayCommonUtil.startWXPay(result);
//            logger.debug("微信-------最终的map是：" + parMap.toString());
//            if (parMap != null) {
//                return ResultMessage.ok(1, "success", parMap);
//            } else {
//                logger.error("微信-----------支付异常，请稍后再试");
//                return ResultMessage.error("Payment is abnormal. Please try again later");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            logger.error("微信-----------支付异常，请稍后再试");
//            return ResultMessage.error("Payment is abnormal. Please try again later");
//        }
//    }
//
//    /*支付成功回调*/
//   /* @SysLog("微信支付回调")*/
//    @RequestMapping("/api/wx/notify")
//    public void wxNotify(HttpServletRequest request, HttpServletResponse response) throws IOException, JDOMException {
//
//        logger.info("------------进入微信支付回调-----------------");
//
//        String result = PayCommonUtil.reciverWx(request); // 接收到异步的参数
//
//        logger.info("进入微信支付回调, 返回参数为: " + result);
//
//        if (StringUtils.isBlank(result)) {
//            return;
//        }
//        // 解析xml成map
//        Map<String, String> m = XmlUtils.doXMLParse(result);
//
//        // 过滤空 设置 TreeMap
//        SortedMap<Object, Object> packageParams = new TreeMap<>();
//        Iterator it = m.keySet().iterator();
//        while (it.hasNext()) {
//            String parameter = (String) it.next();
//            String parameterValue = m.get(parameter);
//            String v = "";
//            if (null != parameterValue) {
//                v = parameterValue.trim();
//            }
//            packageParams.put(parameter, v);
//        }
//
//        logger.info("微信------------解析参数为: GAOSHNXI" + packageParams);
//
//        // 判断签名是否正确
//        String resXml = "";
//        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
//            logger.info("微信-----------------验证返回签名结果值: 签名正确----------------");
//
//            if ("SUCCESS".equals((String) packageParams.get("return_code"))) {
//
//                logger.info("微信-----------返回参数正确----------");
//                // 如果返回成功
//                String mch_id = (String) packageParams.get("mch_id"); // 商户号
//                String out_trade_no = (String) packageParams.get("out_trade_no"); // 商户订单号
//                String total_fee = (String) packageParams.get("total_fee");
//                String[] attach = packageParams.get("attach").toString().split("_");
//
//                /*Integer rechargeNormId = Integer.parseInt(attach[0]);
//                Integer userId = Integer.parseInt(attach[1]);*/
//                String payNo = (String) packageParams.get("out_trade_no");
//                userPayService.userAliPaySuccess(payNo, total_fee);
//                /* RechargeNorm rechargeNorm = rechargeNormService.getRechargeNormById(rechargeNormId);*/
//
//                logger.info("微信---------内购订单信息: ");
//
//                // 验证商户ID 和 价格 以防止篡改金额
//                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id)) {
//                    logger.info("微信-------验证成功, 进行数据库操作-------------");
//                    //支付成功业务
//                    /*  rechargeOrderService.saveRechargeOrder(2, rechargeNormId, userId, rechargeNorm.getPriceChina(), rechargeNorm.getNumber(), out_trade_no, rechargeNorm.getIsActivity());
//                     */
//                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
//                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
//                } else {
//                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                            + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
//                }
//            } else // 如果微信返回支付失败，将错误信息返回给微信
//            {
//                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                        + "<return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
//            }
//        } else {
//            logger.info("-----------------微信验证返回签名结果值: 签名错误----------------");
//
//            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                    + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
//        }
//
//        logger.info("给微信返回信息: " + resXml);
//
//        // 处理业务完毕，将业务结果通知给微信
//        // ------------------------------
//        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
//        out.write(resXml.getBytes());
//        out.flush();
//        out.close();
//    }
//
//}
