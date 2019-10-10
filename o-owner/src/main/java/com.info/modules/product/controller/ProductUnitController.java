package com.info.modules.product.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductUnitEntity;
import com.info.modules.product.service.IProductUnitService;
import com.info.modules.product.vo.ProductUnitVO;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 商品单位信息表
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 16:39:47
 */
@RestController
@RequestMapping("api/unit")
public class ProductUnitController extends AbstractController {

    @Autowired
    private IProductUnitService productUnitService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productUnitService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取所有商品单位详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Login
    @GetMapping("/all")
    public ResultMessage all(@RequestParam Map<String, Object> params) {
        return ResultMessage.ok(productUnitService.getAllList(params));
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Login
    @GetMapping("/info/{unitId}")
    public ResultMessage info(@PathVariable("unitId") Integer unitId) {
        ProductUnitVO productUnit = productUnitService.getProductUnitById(unitId);
        return ResultMessage.ok(productUnit);
    }

    /**
     * 功能描述: 获取获取商品单位的名字-返回的String名字
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @Login
    @GetMapping("/infoName/{unitId}")
    public ResultMessage infoName(@PathVariable("unitId") Integer unitId) {
        return ResultMessage.ok( productUnitService.getProductUnitName(unitId));
    }

}
