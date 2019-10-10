package com.info.modules.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.info.date.DateUtils;
import com.info.modules.area.entity.UserAddressEntity;
import com.info.modules.area.service.IUserAddressService;
import com.info.modules.order.entity.OrderInfoDetailEntity;
import com.info.modules.order.entity.OrderInfoEntity;
import com.info.manager.ICrudRedisManager;
import com.info.modules.order.dao.IOrderInfoDao;
import com.info.modules.order.entity.OrderInfoLogEntity;
import com.info.modules.order.form.SaveOrderInfoForm;
import com.info.modules.order.form.UpdateOrderInfoForm;
import com.info.modules.order.service.IOrderFlowService;
import com.info.modules.order.service.IOrderInfoDetailService;
import com.info.modules.order.service.IOrderInfoLogService;
import com.info.modules.order.service.IOrderInfoService;
import com.info.modules.pay.service.IPayService;
import com.info.modules.product.service.IProductInfoService;
import com.info.modules.product.vo.ProductInfoVO;
import com.info.modules.provider.service.IProviderInfoService;
import com.info.modules.provider.vo.ProviderInfoEntityVO;
import com.info.number.GenerateOrderNo;
import com.info.redis.RedisKeyUtils;
import com.info.utils.*;
import com.info.validator.Assert;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<IOrderInfoDao, OrderInfoEntity> implements IOrderInfoService {


    @Autowired
    private ICrudRedisManager<OrderInfoEntity> crudRedisManager;

    @Autowired
    private IOrderFlowService orderFlowService;

    @Autowired
    private IProductInfoService productInfoService;

    @Autowired
    private IProviderInfoService providerInfoService;

    @Autowired
    private IOrderInfoDetailService orderInfoDetailService;

    @Autowired
    private IUserAddressService userAddressService;

    @Autowired
    private IOrderInfoDao orderInfoDao;

    @Autowired
    private IOrderInfoLogService orderInfoLogService;

    @Autowired
    private IPayService payService;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 订单信息表
     * @Date: 2019-06-17 18:22:04
     */
    @Override
    @Transactional(readOnly = true)
    public PageUtils queryPage(Map<String, Object> params) {
        //用户ID主键
        String userId = (String) params.get("userId");
        //订单状态ID主键可有可无的参数，根据需要传递即可
        String flowId = (String) params.get("flowId");
        Assert.isNull(userId, "用户ID不能为空", ConfigConstant.ERROR);
        String endTime = (String) params.get("endTime");
        String begTime = (String) params.get("begTime");
        IPage<OrderInfoEntity> page = this.page(
                new Query<OrderInfoEntity>().getPage(params),
                new QueryWrapper<OrderInfoEntity>()
                        .select("order_id,flow_id,address_id,user_id,order_no,provider_id,total_money,pay_time,order_time,complete_time,service_time,use_coin,order_coin")
                        .eq("is_del", ConfigConstant.NOTDEL)
                        .eq("user_id", userId)
                        .eq(StringUtils.isNotBlank(flowId), "flow_id", flowId)
                        .ge(StringUtils.isNotBlank(begTime), "creator_time", begTime)
                        .le(StringUtils.isNotBlank(endTime), "creator_time", endTime)
        );
        page.getRecords().stream().forEach(info -> {
            //订单状态名称查询，这个根据需要来调整信息
//            OrderFlowEntity orderFlowEntity = orderFlowService.getFlowById(info.getFlowId());
//            info.setFlowName(orderFlowEntity.getFlowName());
            //查询商家名称
            info.setProviderName(this.getProviderInfo(info.getProviderId()).getProviderName());
        });
        return new PageUtils(page);
    }

    /**
     * 功能描述: 根据商家ID查询商家信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 18:38
     * @Return:
     */
    private ProviderInfoEntityVO getProviderInfo(Integer providerId) {
        return providerInfoService.getProviderInfoById(providerId);
    }

    /**
     * 功能描述: 根据订单ID查询我的订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/23 12:08
     * @Return:
     */
    @Override
    @Transactional(readOnly = true)
    public OrderInfoEntity getOrderInfoDetail(Integer orderId) {
        //根据ID订单，查询订单基本信息
        OrderInfoEntity orderInfoEntity = this.getOrderInfoById(orderId);
        if (orderInfoEntity == null) {
            return orderInfoEntity;
        }
        //查询订单状态
        orderInfoEntity.setFlowName(orderFlowService.getFlowById(orderInfoEntity.getFlowId()).getFlowName());
        //查询订单的收货地址
        UserAddressEntity addressEntity = userAddressService.selectByIdEntity(orderInfoEntity.getAddressId());
        orderInfoEntity.setUserAddressEntity(addressEntity);
        //根据订单ID主键，查询订单详情信息集合
        List<OrderInfoDetailEntity> list = orderInfoDetailService.getOrderIdItemList(orderId);
        list.stream().forEach(info -> {
            //再根据订单详情中商品ID主键查询对应的商品信息
            ProductInfoVO productInfoVO = productInfoService.getProductInfoById(info.getProId());
            orderInfoEntity.getProductInfoVOList().add(productInfoVO);
        });
        return orderInfoEntity;
    }

    /**
     * 功能描述: 提交订单方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/6/23 12:10
     * @Return:
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public ResultMessage saveOrderInfo(SaveOrderInfoForm orderInfoForm, HttpServletRequest request) throws Exception {
        //订单类型 orderType 1.美食订单 2.便民服务订单
        if (orderInfoForm.getOrderType() == 1) {
            return this.saveOrderInfoFood(orderInfoForm, request);
        } else {
            //处理便民订单的业务流程
            return this.saveOrderInfoService(orderInfoForm, request);
        }
    }

    /**
     * 功能描述: 根据不同的订单类型处理不同的订单
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/13 10:03
     * @Return:
     */
    public ResultMessage saveOrderInfoFood(SaveOrderInfoForm orderInfoForm, HttpServletRequest request) throws Exception {
//        BeanUtils.copyProperties(orderInfoForm,);
        //获取到的商品信息解析出来
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        String productId = orderInfoForm.getProductId();
        //商品productIds分割后的数组 ["productId,数量,cartId";"productIds,数量,cartId";"productIds,数量,cartId"]
        String[] productIds = productId.split(";");
        //所有商品订单的总金额
        BigDecimal productTotalMoney = new BigDecimal(0.00);
        List<OrderInfoDetailEntity> detailEntityList = new ArrayList<>();
        for (int a = 0; a < productIds.length; a++) {
            String[] number = productIds[a].split(",");
            /**根据productId，数量,numbers信息查询商品信息*/
            ProductInfoVO productInfoVO = productInfoService.getProductInfoById(Integer.valueOf(number[0]));
            //+ "," + productInfoVO.getProductImg() + "," + productInfoVO.getProductImgSmall();
//            productIds[a] = productIds[a] + "," + productInfoVO.getProviderId() + "," + productInfoVO.getSalesPrice() + "," + productInfoVO.getProductName() ;
            //将商品信息 订单信息组装到订单详情实体中 然后在add到list中 然后在入库
            OrderInfoDetailEntity detailEntity = new OrderInfoDetailEntity();
            detailEntity.setNumbers(Integer.valueOf(number[1]));
            detailEntity.setProId(productInfoVO.getProductId());
            detailEntity.setProPrice(productInfoVO.getSalesPrice());
            detailEntityList.add(detailEntity);
            //计算商品的总价格
            productTotalMoney = productTotalMoney.add(productInfoVO.getSalesPrice().multiply(new BigDecimal(number[1])));
        }
        //计算完毕 商品*数量的价格之后开始组装数据入库
        orderInfoEntity.setAddressId(orderInfoForm.getAddressId());
        orderInfoEntity.setFlowId(Constant.OrderInfoFlow.ORDER_FLOW_1.getValue());
        orderInfoEntity.setUserId(orderInfoForm.getUserId());
        orderInfoEntity.setOrderNo(GenerateOrderNo.getOrderIdByUUId());
        orderInfoEntity.setTitle(orderInfoForm.getTitle());
        orderInfoEntity.setMemo(orderInfoForm.getMemo());
        orderInfoEntity.setOrderSource(orderInfoForm.getOrderSource());
        orderInfoEntity.setTotalMoney(productTotalMoney);
        orderInfoEntity.setOrderTime(DateUtils.now());
        orderInfoEntity.setIsInvoice(orderInfoForm.getIsInvoice());
        orderInfoEntity.setInvoiceTitle(orderInfoForm.getInvoiceTitle());
        orderInfoEntity.setInvoiceInfo(orderInfoForm.getInvoiceInfo());
        orderInfoEntity.setPayType(orderInfoForm.getPayType());
        orderInfoEntity.setProviderId(orderInfoForm.getProviderId());
        orderInfoEntity.setProviderId(orderInfoForm.getProviderId());
        Integer orderId = orderInfoDao.save(orderInfoEntity);
        if (orderId != 0) {
            //表示订单入库成功了-》接着入库订单详情——》生成支付包微信密钥-》支付订单-》生成日志和修改订单状态
            this.addProductList(detailEntityList, orderInfoEntity.getOrderId());
            //这里是根据订单信息进行支付和验签,这里是返回验签信息
            orderInfoEntity.setOrderId(orderId);
            return ResultMessage.ok(this.signAndPay(request, orderInfoEntity));
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 根据不同的订单类型处理不同的订单
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/13 10:03
     * @Return:
     */
    public ResultMessage saveOrderInfoService(SaveOrderInfoForm orderInfoForm, HttpServletRequest request) throws Exception {
        //获取到的商品信息解析出来
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        String productId = orderInfoForm.getProductId();
        ProductInfoVO productInfoVO = productInfoService.getProductInfoById(Integer.valueOf(productId));
        if (productInfoVO == null) {
            return ResultMessage.error(ConfigConstant.ERROR, "商品已被下架或是暂停服务");
        }
        //计算完毕 商品*数量的价格之后开始组装数据入库
        orderInfoEntity.setAddressId(orderInfoForm.getAddressId());
        orderInfoEntity.setFlowId(Constant.OrderInfoFlow.ORDER_FLOW_1.getValue());
        orderInfoEntity.setUserId(orderInfoForm.getUserId());
        orderInfoEntity.setOrderNo(GenerateOrderNo.getOrderIdByUUId());
        orderInfoEntity.setTitle(orderInfoForm.getTitle());
        orderInfoEntity.setMemo(orderInfoForm.getMemo());
        orderInfoEntity.setOrderSource(orderInfoForm.getOrderSource());
        orderInfoEntity.setTotalMoney(productInfoVO.getSalesPrice());
        orderInfoEntity.setOrderTime(DateUtils.now());
        orderInfoEntity.setIsInvoice(orderInfoForm.getIsInvoice());
        orderInfoEntity.setInvoiceTitle(orderInfoForm.getInvoiceTitle());
        orderInfoEntity.setInvoiceInfo(orderInfoForm.getInvoiceInfo());
        orderInfoEntity.setPayType(orderInfoForm.getPayType());
        orderInfoEntity.setProviderId(orderInfoForm.getProviderId());
        orderInfoEntity.setProviderId(orderInfoForm.getProviderId());
        Integer orderId = orderInfoDao.save(orderInfoEntity);
        if (orderId == 1) {
            List<OrderInfoDetailEntity> detailEntityList = new ArrayList<>();
            //表示订单入库成功了-》接着入库订单详情——》生成支付包微信密钥-》支付订单-》生成日志和修改订单状态
            OrderInfoDetailEntity detailEntity = new OrderInfoDetailEntity();
            //服务商品次数
            detailEntity.setNumbers(ConfigConstant.AUTH);
            detailEntity.setProId(productInfoVO.getProductId());
            detailEntity.setProPrice(productInfoVO.getSalesPrice());
            detailEntityList.add(detailEntity);
            //批量插入订单详情的方法
            this.addProductList(detailEntityList, orderInfoEntity.getOrderId());
            //这里是根据订单信息进行支付和验签,这里是返回验签信息
            orderInfoEntity.setOrderId(orderId);
            return ResultMessage.ok(this.signAndPay(request, orderInfoEntity));
        }
        return ResultMessage.err();
    }

    /**
     * 功能描述: 批量插入订单详情的方法--配合上面使用
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 23:55
     * @Return:
     */
    private void addProductList(List<OrderInfoDetailEntity> list, Integer orderId) {
//        orderInfoDetailService.saveBatch(list);
        list.forEach(info -> {
            OrderInfoDetailEntity detailEntity = new OrderInfoDetailEntity();
            detailEntity.setOrderId(orderId);
            detailEntity.setCreatorTime(DateUtils.now());
            detailEntity.setNumbers(info.getNumbers());
            detailEntity.setProPrice(info.getProPrice());
            detailEntity.setProId(info.getProId());
            detailEntity.setBuyType(Constant.BuyProductType.BUY_TYPE_1.getValue());
            orderInfoDetailService.save(detailEntity);
        });
    }

    /**
     * 功能描述: 生成订单--》入库--》订单详情入库--》订单日志入库--》支付生成验签返回--配合上面的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 11:25
     * @Return: 支付类型 getPayType: 1 微信 2 支付宝 3 银联 4 苹果内购 5 谷歌支付 6 翼支付(PAY_BEST)
     */
    private Object signAndPay(HttpServletRequest request, OrderInfoEntity orderInfoEntity) throws Exception {
        Object sign = null;
        if (orderInfoEntity.getPayType().equals(Constant.PayType.PAY_WEIXIN)) {
            sign = payService.wxPay(request, orderInfoEntity);
        } else if (orderInfoEntity.getPayType().equals(Constant.PayType.PAY_ALI)) {
            sign = payService.aliPay(orderInfoEntity);
        } else if (orderInfoEntity.getPayType().equals(Constant.PayType.PAY_UNION)) {

        } else if (orderInfoEntity.getPayType().equals(Constant.PayType.PAY_IOS)) {

        } else if (orderInfoEntity.getPayType().equals(Constant.PayType.PAY_GOOGLE)) {

        } else if (orderInfoEntity.getPayType().equals(Constant.PayType.PAY_BEST)) {

        }
        return sign;
    }

    /**
     * 功能描述: 取消删除订单的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:57
     * @Return:
     */
    @Override
    public OrderInfoEntity delOrderInfo(Integer orderId) {

        return null;
    }

    /**
     * 功能描述: 根据订单No查询订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:57
     * @Return:
     */
    @Override
    @Transactional(readOnly = true)
    public OrderInfoEntity getOrderInfoByNO(String orderNo) {
        OrderInfoEntity orderInfoEntity = crudRedisManager.hget(RedisKeyUtils.OrderKeys.ORDER_INFO, orderNo, OrderInfoEntity.class, "获取订单信息ID:" + orderNo + ",详情异常");
        if (orderInfoEntity == null) {
            orderInfoEntity = this.getOne(new QueryWrapper<OrderInfoEntity>()
                    .select("order_id,flow_id,address_id,user_id,order_no,provider_id,total_money,pay_time,order_time,complete_time,use_coin,order_coin")
                    .eq("order_no", orderNo)
            );
            if (orderInfoEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.OrderKeys.ORDER_INFO, orderNo, JSON.toJSONString(orderInfoEntity), "存储订单信息ID:" + orderNo + ",详情异常");
            }
            return orderInfoEntity;
        }
        return orderInfoEntity;
    }

    /**
     * 功能描述: 根据订单ID主键查询订单信息
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 17:42
     * @Return:
     */
    @Override
    @Transactional(readOnly = true)
    public OrderInfoEntity getOrderInfoById(Integer orderId) {
        OrderInfoEntity orderInfoEntity = crudRedisManager.hget(RedisKeyUtils.OrderKeys.ORDER_INFO, orderId.toString(), OrderInfoEntity.class, "获取订单信息ID:" + orderId + ",详情异常");
        if (orderInfoEntity == null) {
            orderInfoEntity = this.getOne(new QueryWrapper<OrderInfoEntity>()
                    .select("order_id,flow_id,address_id,user_id,order_no,provider_id,total_money,pay_time,order_time,complete_time,use_coin,order_coin")
                    .eq("order_id", orderId)
            );
            if (orderInfoEntity != null) {
                crudRedisManager.hset(RedisKeyUtils.OrderKeys.ORDER_INFO, orderId.toString(), JSON.toJSONString(orderInfoEntity), "存储订单信息ID:" + orderId + ",详情异常");
            }
            return orderInfoEntity;
        }
        return orderInfoEntity;
    }

    /**
     * 功能描述: 根据订单编号修改订单状态
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 12:13
     * @Return:
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    public Boolean updateByOrderNO(UpdateOrderInfoForm updateOrderInfoForm) {
        Integer orderId = updateOrderInfoForm.getOrderId();
        String orderNo = updateOrderInfoForm.getOrderNo();
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setOrderId(updateOrderInfoForm.getOrderId());
        orderInfoEntity.setFlowId(updateOrderInfoForm.getFlowId());
        orderInfoEntity.setPayTime(updateOrderInfoForm.getPayTime());
        Boolean flag = this.update(new UpdateWrapper<OrderInfoEntity>()
                .eq(StringUtils.isNotBlank(orderId.toString()), "order_id", orderId)
                .eq(StringUtils.isNotBlank(orderNo), "order_no", orderNo)
        );
        if (flag) {
            //修改之后给订单添加订单操作日
            OrderInfoLogEntity orderInfoLogEntity = new OrderInfoLogEntity();
            orderInfoLogEntity.setOrderId(orderId);
            orderInfoLogEntity.setOrderNo(orderNo);
            orderInfoLogEntity.setFlowId(updateOrderInfoForm.getFlowId());
            orderInfoLogEntity.setMemo(updateOrderInfoForm.getMemo());
            orderInfoLogService.addLog(orderInfoLogEntity);
            return flag;
        } else {
            return flag;
        }
    }

    /**
     * 功能描述: 根据订单编号修改订单状态
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 12:13
     * @Return:
     */
    @Override
    public Boolean updateByOrderNO(OrderInfoEntity orderInfoEntity) {
        Integer orderId = orderInfoEntity.getOrderId();
        String orderNo = orderInfoEntity.getOrderNo();
        Boolean flag = this.update(new UpdateWrapper<OrderInfoEntity>()
                .eq(StringUtils.isNotBlank(orderId.toString()), "order_id", orderId)
                .eq(StringUtils.isNotBlank(orderNo), "order_no", orderNo)
        );
        if (flag) {
            //修改之后给订单添加订单操作日
            OrderInfoLogEntity orderInfoLogEntity = new OrderInfoLogEntity();
            orderInfoLogEntity.setOrderId(orderId);
            orderInfoLogEntity.setOrderNo(orderNo);
            orderInfoLogEntity.setFlowId(orderInfoEntity.getFlowId());
            orderInfoLogEntity.setMemo(orderInfoEntity.getMemo());
            orderInfoLogService.addLog(orderInfoLogEntity);
            return flag;
        } else {
            return flag;
        }
    }


}
