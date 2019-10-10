package com.info.modules.order.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.order.entity.OrderInfoLogEntity;
import com.info.modules.order.service.IOrderInfoLogService;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * 订单日志信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@RestController
@RequestMapping("api/orderLog")
public class OrderInfoLogController extends AbstractController {

    @Autowired
    private IOrderInfoLogService orderInfoLogService;


    /**
     * 功能描述: 分页列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:41
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderInfoLogService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页查询所有该订单的日志
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:41
     * @Return:
     */
    @Login
    @GetMapping("/all/{orderId}")
    public ResultMessage list(@PathVariable("orderId") Integer orderId) {
        Assert.isNull(orderId, "订单ID不能为空", ConfigConstant.ERROR);
        return ResultMessage.ok(orderInfoLogService.allLog(orderId));
    }

    /**
     * 功能描述: 添加日志的
     *
     * @Params: * @param null
     * @Author: Gaosx By User
     * @Date: 2019/7/9 17:12
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage list(@Valid @RequestBody OrderInfoLogEntity logEntity) {
        return ResultMessage.ok(orderInfoLogService.addLog(logEntity));
    }

}
