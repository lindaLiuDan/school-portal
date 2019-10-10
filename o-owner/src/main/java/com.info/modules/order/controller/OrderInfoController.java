package com.info.modules.order.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.order.form.SaveOrderInfoForm;
import com.info.modules.order.form.UpdateOrderInfoForm;
import com.info.modules.order.service.IOrderInfoService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 订单信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@RestController
@RequestMapping("api/order")
public class OrderInfoController extends AbstractController {


    @Autowired
    private IOrderInfoService orderInfoService;


    /**
     * 功能描述: 分页查询我的个人订单信息
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:07
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 根据订单ID获取订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:54
     * @Return:
     */
    @Login
    @GetMapping("/info/{orderId}")
    public ResultMessage info(@PathVariable("orderId") Integer orderId) {
        Assert.isNull(orderId, "订单ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(orderInfoService.getOrderInfoDetail(orderId));
    }

    /**
     * 功能描述: 根据订单No查询订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:54
     * @Return:
     */
    @Login
    @GetMapping("/getOrderInfoByNO/{orderNo}")
    public ResultMessage infoOrderNO(@PathVariable("orderNo") String orderNo) {
        Assert.isNull(orderNo, "订单no不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(orderInfoService.getOrderInfoByNO(orderNo));
    }

    /**
     * 功能描述: 根据订单No查询订单详情
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:54
     * @Return:
     */
    @Login
    @GetMapping("/getOrderInfoById/{orderId}")
    public ResultMessage infoOrderNO(@PathVariable("orderId") Integer orderId) {
        Assert.isNull(orderId, "订单ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(orderInfoService.getOrderInfoById(orderId));
    }


    /**
     * 功能描述: 用户提交订单--》支付订单过程——》支付日志
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:54
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody SaveOrderInfoForm orderInfoForm, HttpServletRequest request) throws Exception {
        ValidatorUtils.validateEntity(orderInfoForm, ConfigConstant.ERROR, AddGroup.class);
        return ResultMessage.ok(orderInfoService.saveOrderInfo(orderInfoForm, request));
    }

    /**
     * 功能描述: 取消删除订单的方法
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:54
     * @Return:
     */
    @Login
    @GetMapping("/del/{orderId}")
    public ResultMessage save(@PathVariable("orderId") Integer orderId) {
        Assert.isNull(orderId, "订单ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(orderInfoService.delOrderInfo(orderId));
    }

    /**
     * 功能描述: 根据订单ID修改订单状态
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/8 22:54
     * @Return:
     */
    @Login
    @GetMapping("/updateById/{orderId}")
    public ResultMessage updateById(@RequestBody UpdateOrderInfoForm updateOrderInfoForm) {
        ValidatorUtils.validateEntity(updateOrderInfoForm, ConfigConstant.ERROR, AddGroup.class);
        return ResultMessage.ok(orderInfoService.updateByOrderNO(updateOrderInfoForm));
    }


}
