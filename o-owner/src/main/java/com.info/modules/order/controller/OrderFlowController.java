package com.info.modules.order.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.modules.order.entity.OrderFlowEntity;
import com.info.modules.order.service.IOrderFlowService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单流程跟踪表:已支付，未支付，配送中等
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@RestController
@RequestMapping("api/flow")
public class OrderFlowController extends AbstractController {

    @Autowired
    private IOrderFlowService orderFlowService;


    /**
     * 功能描述: 列表查询--分页
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:03
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderFlowService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 无分页获取所有的订单节点
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:04
     * @Return:
     */
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        List<OrderFlowEntity> list = orderFlowService.AllFlow(params);
        return ResultMessage.ok(list);
    }

    /**
     * 功能描述: 获取单个订单节点信息详情
     *
     * @Params: * @param null
     * @Author: Gaosx 960889426@qq.com By User
     * @Date: 2019/6/23 12:04
     * @Return:
     */
    @Login
    @GetMapping("/info/{flowId}")
    public ResultMessage all(@PathVariable("flowId") Integer flowId) {
        OrderFlowEntity orderFlow = orderFlowService.getFlowById(flowId);
        return ResultMessage.ok(orderFlow);
    }


}
