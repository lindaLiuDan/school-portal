package com.info.modules.order.controller;

import com.info.common.base.AbstractController;
import com.info.modules.order.service.IOrderInfoDetailService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 订单详情信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:04
 */
@RestController
@RequestMapping("api/orderDetail")
public class OrderInfoDetailController extends AbstractController {


    @Autowired
    private IOrderInfoDetailService orderInfoDetailService;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 列表查询
     * @Date: 2019-06-17 18:22:04
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderInfoDetailService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }


}
