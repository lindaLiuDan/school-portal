package com.info.modules.pay.service.impl;

import com.alibaba.druid.support.json.JSONUtils;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.order.entity.OrderInfoEntity;
import com.info.modules.order.entity.OrderInfoLogEntity;
import com.info.modules.order.form.UpdateOrderInfoForm;
import com.info.modules.order.service.IOrderInfoLogService;
import com.info.modules.order.service.IOrderInfoService;
import com.info.modules.pay.dao.IPayDao;
import com.info.modules.pay.entity.PayTypeEntity;
import com.info.modules.pay.service.IPayService;
import com.info.modules.user.service.IUserInfoService;
import com.info.pay.alipay.AlipayConfig;
import com.info.pay.weixinpay.PayCommonUtil;
import com.info.pay.weixinpay.PropertyUtil;
import com.info.pay.weixinpay.XmlUtils;
import com.info.utils.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述: 支付
 *
 * @Params: * @param null
 * @Author: Gaosx By User
 * @Date: 2019/7/9 12:56
 * @Return:
 */
@Service("payServiceImpl")
@Transactional(readOnly = true)
public class PayServiceImpl extends ServiceImpl<IPayDao, PayTypeEntity> implements IPayService {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IOrderInfoService orderInfoService;

    @Autowired
    private IUserInfoService userInfoService;

    @Autowired
    private IOrderInfoLogService orderInfoLogService;


    /**
     * 功能描述: 分页查询订单日志信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/12 18:31
     * @Return:
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String cateId = (String) params.get("cateId");
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<PayTypeEntity> page = this.page(
                new Query<PayTypeEntity>().getPage(params),
                new QueryWrapper<PayTypeEntity>()
                        .select("product_id,unit_id,provider_id,cate_id,product_no,product_name,product_img,product_img_small,sales_price,like_num,saled_num,detail")
                        //statues=3 表示在售中
                        .eq("statues", 3)
                        .eq("is_del", 1)
                        .eq(StringUtils.isNotBlank(cateId), "cate_id", cateId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        return new PageUtils(page);
    }

    /**
     * 功能描述:
     *
     * @param: 微信支付
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/9 15:36
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Object wxPay(HttpServletRequest httpRequest, OrderInfoEntity orderInfoEntity) {
        logger.info("---------------微信开始生产签名---------------");
        if (orderInfoEntity.getOrderNo() == null || orderInfoEntity.getTotalMoney() == null) {
            logger.error("微信-----------orderInfoEntity信息支付异常");
            return ResultMessage.error(ConfigConstant.ERROR, "订单异常");
        }
        if (orderInfoEntity.getTotalMoney().equals(0)) {
            logger.error("支付金额异常:" + orderInfoEntity.getTotalMoney());
            return ResultMessage.error(ConfigConstant.ERROR, "金额异常");
        }
        try {
            // 获取预付单，此处已做封装，需要工具类
            SortedMap<Object, Object> parameters = PayCommonUtil.getWXPrePayID();
            parameters.put("attach", orderInfoEntity.getOrderNo());
            parameters.put("body", "gs-info");
            String format = String.format("%06d", orderInfoEntity.getUserId());
            parameters.put("out_trade_no", "wx" + format + PayCommonUtil.getDateStr());
            parameters.put("spbill_create_ip", httpRequest.getRemoteAddr());
            //测试的钱数
            Integer inter = orderInfoEntity.getTotalMoney().multiply(new BigDecimal(100)).intValue();
            String totalMoney = inter.toString();
            parameters.put("total_fee", totalMoney);
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);
            // 封装请求参数结束
            // 获取xml结果
            String requestXML = PayCommonUtil.getRequestXml(parameters);
            logger.debug("微信----------封装请求参数是：" + requestXML);
            // 调用统一下单接口
            String result = PayCommonUtil.httpsRequest(PropertyUtil.getInstance().getProperty("WxPay.payURL"), "POST", requestXML);
            logger.debug("微信---------调用统一下单接口：" + result);
            Map<String, String> rem = XmlUtils.doXMLParse(result);
            String return_code = rem.get("return_code");
            if (!"SUCCESS".equals(return_code)) {
                logger.error("微信-----------支付异常，请稍后再试");
                return ResultMessage.error(ConfigConstant.ERROR, "ERROR");
            }
            SortedMap<Object, Object> parMap = PayCommonUtil.startWXPay(result);
            logger.debug("微信-------最终的map是：" + parMap.toString());
            if (parMap != null) {
                return ResultMessage.ok(parMap);
            } else {
                logger.error("微信-----------支付异常，请稍后再试");
                return ResultMessage.error(ConfigConstant.ERROR, "支付异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            logger.error("微信-----------支付异常，请稍后再试");
            return ResultMessage.error(ConfigConstant.ERROR, "支付失败");
        }
    }


    /**
     * 功能描述:
     *
     * @param: 微信支付回调接口
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/9 15:35
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void notify(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("*******************进入WX支付回调************");
        String result = PayCommonUtil.reciverWx(request);
        // 接收到异步的参数
        logger.info("进入微信支付回调, 返回参数为: " + result);
        if (StringUtils.isBlank(result)) {
            return;
        }
        Map<String, String> m = new HashMap<>();
        if (m != null && !"".equals(m)) {
            m = XmlUtils.doXMLParse(result);
        }
        // 过滤空 设置 TreeMap
        SortedMap<Object, Object> packageParams = new TreeMap<>();
        Iterator it = m.keySet().iterator();
        while (it.hasNext()) {
            String parameter = (String) it.next();
            String parameterValue = m.get(parameter);
            String v = "";
            if (null != parameterValue) {
                v = parameterValue.trim();
            }
            packageParams.put(parameter, v);
        }
        logger.info("微信------------解析参数为: " + packageParams);
        // 判断签名是否正确
        String resXml = "";
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
            logger.info("微信-----------------验证返回签名结果值: 签名正确----------------");
            if ("SUCCESS".equals(packageParams.get("return_code").toString())) {
                logger.info("微信-----------返回参数正确----------");
                // 如果返回成功
                String mch_id = (String) packageParams.get("mch_id");
                String out_trade_no = (String) packageParams.get("out_trade_no");
                logger.info("订单号：" + out_trade_no);
                String total_fee = (String) packageParams.get("total_fee");
                String attach = packageParams.get("attach").toString();
                logger.info("-------------------oderNo={},total_fee={},attach={}" + out_trade_no + "---" + total_fee + "----" + attach);
                if (PropertyUtil.getInstance().getProperty("WxPay.mchid").equals(mch_id)) {
                    logger.info("-------------------WX验证SUCC, 进行数据库操作-------------------");
                    //修改订单状态
                    OrderInfoEntity orderInfoEntity = orderInfoService.getOrderInfoByNO(attach);
                    //订单支付成功之后的回调接口，修改订单状态和支付时间 //添加日志信息 //订单修改为已经支付，
                    orderInfoEntity.setFlowId(Constant.OrderInfoFlow.ORDER_FLOW_2.getValue());
                    orderInfoEntity.setPayTime(DateUtils.now());
                    orderInfoService.updateByOrderNO(orderInfoEntity);

                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
                } else {
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
                }
            } else { // 如果微信返回支付失败，将错误信息返回给微  信
                resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[交易失败]]></return_msg>" + "</xml> ";
            }
        } else {
            logger.info("-----------------微信验证返回签名结果值: 签名错误----------------");
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
        }
//        logger.info("给微信返回信息: " + resXml);
        // 处理业务完毕，将业务结果通知给微信
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @Override
    public void test(String orderNo) {
        //支付成功业务 后处理的业务层
        //  Map<String, Object> objectMap = new HashMap<String, Object>();
        /**修改订单状态*/
        UpdateOrderInfoForm updateOrderInfoForm = new UpdateOrderInfoForm();
//        updateOrderInfoForm.setPayTime(DateUtils.now());
        updateOrderInfoForm.setFlowId(Constant.OrderInfoFlow.ORDER_FLOW_1.getValue());
        orderInfoService.updateByOrderNO(updateOrderInfoForm);
    }

    /**
     * 功能描述:
     *
     * @param: 阿里支付
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/5 17:33
     */
    @Override
    @Transactional(readOnly = false)
    public String aliPay(OrderInfoEntity orderInfoEntity) throws Exception {
        logger.info("-----------------------进入支付宝延签------------------------");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Map<String, String> map4 = new HashMap<String, String>();
        map4.put("app_id", AlipayConfig.app_id);
        map4.put("method", "alipay.trade.app.pay");
        map4.put("format", "json");
        map4.put("charset", "utf-8");
        map4.put("sign_type", "RSA");
        map4.put("timestamp", sdf.format(new Date()));
        map4.put("version", "1.0");
        map4.put("notify_url", AlipayConfig.notify_url);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("seller_id", AlipayConfig.partner);
        map.put("product_code", "QUICK_MSECURITY_PAY");
//        map.put("total_amount", total_amount);
//        map.put("subject", subject);
//        map.put("body", body);
//        map.put("out_trade_no", out_trade_no);
        String content = "app_id=" + map4.get("app_id") + "&biz_content=" + JSONUtils.toJSONString(map) + "&charset=" + map4.get("charset") + "&format=" + map4.get("format") + "&method=" + map4.get("method")
                + "&notify_url=" + map4.get("notify_url") + "&sign_type=" + map4.get("sign_type") + "&timestamp=" + map4.get("timestamp") + "&version=" + map4.get("version");
        String rsaSign = AlipaySignature.rsaSign(content, AlipayConfig.private_key, "utf-8");
        //最后对请求字符串的所有一级value（biz_content作为一个value）进行encode，编码格式按请求串中的charset为准，没传charset按UTF-8处理
        String json4 = "";
        json4 = "app_id=" + encode(map4.get("app_id")) + "&biz_content=" + encode(JSONUtils.toJSONString(map)) + "&charset="
                + encode(map4.get("charset")) + "&format=" + encode(map4.get("format")) + "&method=" + encode(map4.get("method")) + "&notify_url=" + encode(map4.get("notify_url")) + "&sign_type="
                + encode(map4.get("sign_type")) + "&timestamp=" + encode(map4.get("timestamp")) + "&version=" + encode(map4.get("version")) + "&sign="
                + encode(rsaSign);
        logger.info("支付宝请求签名:" + json4);
        return json4;
    }

    /**
     * 功能描述:
     *
     * @param: ali支付回调接口
     * @return:
     * @auther: 高山溪 960889426@qq.com By User
     * @date: 2018/11/9 15:35
     */
    @Override
    @Transactional(readOnly = false)
    public String callback(HttpServletRequest request, HttpServletResponse response) throws IOException, AlipayApiException {
        System.out.println("-------------支付宝回调方法-----------！");
        Map map = request.getParameterMap();
//        System.out.println("----------------接口回调成功-------------！");
        // Map<String, String> paramsMap = map; //将异步通知中收到的待验证所有参数都存放到map中
        Map<String, String> map2 = new HashMap<String, String>();
        for (Iterator iter = map.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) map.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                // valueStr = valueStr + values[i] + ",";
                valueStr = valueStr + values[i];
            }
            System.out.println("回调参数返回:**name:" + name + "-------" + "values:" + valueStr);
            if (name.equals("sign_type")) {
                map2.put(name, valueStr);
            } else if (name.equals("sign")) {
                //String signs = Base64.byteArrayToBase64(valueStr.getBytes());
//                System.out.println("sign:"+valueStr);
                map2.put(name, valueStr);
            } else {
                map2.put(name, URLDecoder.decode(valueStr, "UTF-8"));
            }
        }
        boolean signVerified = AlipaySignature.rsaCheckV1(map2, AlipayConfig.alipay_public_key, "UTF-8");//调用SDK验证签名
        // 支付成功状态
        String trade_status = request.getParameter("trade_status");
        // 交易流水号
        String trade_no = request.getParameter("trade_no");
        /*订单号*/
        String out_trade_no = request.getParameter("out_trade_no");
        /*订单金额*/
        String total_amount = request.getParameter("total_amount");
        if (signVerified) {
            if ((trade_status.equals("WAIT_SELLER_SEND_GOODS")) || (trade_status.equals("TRADE_FINISHED")) || (trade_status.equals("TRADE_SUCCESS"))) {
                logger.info("------------------------ali支付成功------------------------");
                //修改订单状态
                OrderInfoEntity orderInfoEntity = orderInfoService.getOrderInfoByNO(out_trade_no);
                //订单支付成功之后的回调接口，修改订单状态和支付时间 //添加日志信息 //订单修改为已经支付，
                orderInfoEntity.setFlowId(Constant.OrderInfoFlow.ORDER_FLOW_2.getValue());
                orderInfoEntity.setPayTime(DateUtils.now());
                orderInfoService.updateByOrderNO(orderInfoEntity);
                return "success";
            } else {
                logger.error("------------------------ali支付失败-----------------------");
                return "fail";
            }
        } else {
            return "fail";
        }
    }

    private static String encode(String sign) throws UnsupportedEncodingException {
        return URLEncoder.encode(sign, "utf-8").replace("+", "%20");
    }


}
