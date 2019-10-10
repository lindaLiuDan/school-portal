//package com.info.modules.pay.controller;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.info.common.base.AbstractController;
//import com.info.modules.pay.service.IPayService;
//import com.info.modules.user.service.IUserInfoService;
//import com.info.modules.token.service.ITokenService;
//import com.info.utils.ConfigConstant;
//import com.info.utils.ResultMessage;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * appStore苹果内支付验证接口
// */
//@RestController
//@RequestMapping
//public class AppStoreController extends AbstractController {
//    /**
//     * 购买凭证验证地址
//     */
//    private final String certificateUrl = "https://buy.itunes.apple.com/verifyReceipt";
//
//    /**
//     * 测试的购买凭证验证地址
//     */
//    private final String certificateUrlTest = "https://sandbox.itunes.apple.com/verifyReceipt";
//
//    @Autowired
//    private ITokenService tokenService;
//
//    @Autowired
//    private IPayService payService;
//
//    @Autowired
//    private IUserInfoService userInfoService;
//
//    /**
//     * 接收iOS端发过来的购买凭证
//     *
//     * @param userId
//     * @param receipt
//     */
//    @RequestMapping("api/pay/iosPay")
//    public ResultMessage appPayCertificate(Integer userId, String receipt, Integer info, String transactionId) {
//        String url = certificateUrl;
//        logger.info("AppStore appPayCertificate：userId:{},receipt:{},info{}", userId, receipt, info);
//        // 苹果服务器没有返回验证结果  先线上测试    发送平台验证
////        String verifyResult = IosVerifyUtil.buyAppVerify(receipt, 1);
////        if (verifyResult == null) {
////            return ResultMessage.error(0, "苹果服务器没有返回验证结果");
////        }
//        //判断此订单好是否存在
//        UserPayEntity userPayEntity = userPayService.selectOne(new EntityWrapper<UserPayEntity>().eq("user_id", userId).eq("pay_no", transactionId));
//        if (userPayEntity != null) {
//            logger.error("支付失败,user{},userPayEntity{},此订单已存在", userId);
//            return ResultMessage.error(0, "此订单已存在");
//        }
//
//        final String certificateCode = receipt;
//        if (StringUtils.isNotEmpty(certificateCode)) {
//            JSONObject obj = new JSONObject();
//            obj.put("receipt-data", certificateCode);
//            String reStr = new HttpsUtils().sendPOSTHttpsCoon(url, obj);
//
//            logger.info("appStore苹果内支付---------验证证书返回值: " + reStr);
//
//            JSONObject reJson = JSONObject.parseObject(reStr);
//            //服务器拿到苹果的校验结果后，首先判断订单状态是不是成功。
//
//            // 如果状态码=21007, 则重新进行沙盒验证
//            if (reJson != null && reJson.getInteger("status") == 21007) {
//                url = certificateUrlTest;
//                reStr = new HttpsUtils().sendPOSTHttpsCoon(url, obj);
//                logger.info("appStore苹果内支付---------验证证书返回值: " + reStr);
//                reJson = JSONObject.parseObject(reStr);
//            }
//
//            // 验证最终支付结果
//            if (reJson == null || reJson.getInteger("status") != 0) {
//                //      logger.info("appStore苹果内支付---------支付验证失败: " + reJson.getInteger("status"));
//                return ResultMessage.error(0, "appStore苹果内支付验证失败");
//            } else {
//                JSONObject receiptJson = reJson.getJSONObject("receipt");
//                JSONArray inApp = receiptJson.getJSONArray("in_app");
//                logger.info("in_app=========" + receiptJson.getJSONArray("in_app"));
//                //遍历数组
//                boolean flag = false;
//                Map<String, Object> payUser = null;
//                //判断in_app这个字段有没有，没有直接就返回失败了。如果存在的话，遍历整个数组
//                if (inApp == null) {
//                    return ResultMessage.error(0, "支付失败!");
//                }
//                for (int i = 0; i < inApp.size(); i++) {
//                    if (transactionId.equals(inApp.getJSONObject(i).get("transaction_id"))) {
//                        String productId = inApp.getJSONObject(i).getString("product_id");
//                        Map<String, Object> map = new HashMap<>();
//                        // com.duoke.DuoKeFinanc001   002  投递名片   获取联系方式      003  004  登陆注册
//                        payUser = getPayUser(productId, map, userId, info);
//
//                        //对比一下bundle_id 是不是正确的
//                        if (receiptJson.getString("bundle_id").equals(ConfigConstant.IosConfig.BUNDLEID)) {
//                            //ios支付成功
//                            flag = userPayService.saveIosInfo(transactionId, productId, info, userId);
//                        } else {
//                            return ResultMessage.error(0, "支付失败,bundle_id不正确");
//                        }
//                    }
//                }
//                if (flag) {
//                    return ResultMessage.ok(1, "success", payUser);
//                } else {
//                    return ResultMessage.error(0, "error");
//                }
//            }
//        } else {
//            logger.warn("appStore苹果内支付---------证书验证码为空, 支付验证失败");
//            return ResultMessage.error("Payment verification failed");
//        }
//    }
//
//
//    private Map<String, Object> getPayUser(String productId, Map<String, Object> map, Integer userId, Integer info) {
//        /*if (productId.equalsIgnoreCase(ConfigConstant.IosConfig.REGISTER_MONEY_CODE) || productId.equalsIgnoreCase(ConfigConstant.IosConfig.REGISTER_MONEY_NULL_CODE)) {
//           // UserInfoEntity userInfo = userInfoService.selectOne(new EntityWrapper<UserInfoEntity>().eq("user_id", userId));
//            //获取登录token
//            *//*TokenEntity token = tokenService.createToken(userId);
//            map.put("token", token);
//            map.put("typeId", userInfo.getIsType());
//            map.put("isAuth", userInfo.getIsAuth());*//*
//            return map;
//        } else*/
//        if (productId.equalsIgnoreCase(ConfigConstant.IosConfig.GET_MOBILE_MONEY_CODE)) {
//            UserGetMobileEntity mobileEntity = userGetMobileService.selectById(info);
//            String mobile = userGetMobileService.getMobile(userId, mobileEntity.getProType(), mobileEntity.getInfoId());
//            map.put("mobile", mobile);
//            return map;
//        } /*else if (productId.equalsIgnoreCase(ConfigConstant.IosConfig.DELIVER_CARD_MONEY_CODE)) {
//            return null;
//        }*/ else {
//            return null;
//        }
//    }
//
//
//}
