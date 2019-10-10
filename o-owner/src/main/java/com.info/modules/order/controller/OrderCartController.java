package com.info.modules.order.controller;

import com.info.common.base.AbstractController;
import com.info.modules.order.service.IOrderCartService;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 购物车信息表
 *
 * @author Gaosx
 * @email 960889426@qq.com
 * @date 2019-06-17 18:22:05
 */
@RestController
@RequestMapping("api/cart")
public class OrderCartController extends AbstractController {

    @Autowired
    private IOrderCartService orderCartService;


    /**
     * @Author: Gaosx  960889426@qq.com
     * @Description: 列表查询
     * @Date: 2019-06-17 18:22:05
     */
    @RequestMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = orderCartService.queryPage(params);
        return ResultMessage.ok().put("page", page);
    }


}
