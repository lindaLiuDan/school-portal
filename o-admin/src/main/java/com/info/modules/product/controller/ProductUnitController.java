package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductUnitEntity;
import com.info.modules.product.service.IProductUnitService;
import com.info.modules.sys.controller.AbstractController;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("product/unit")
public class ProductUnitController extends AbstractController {

    @Autowired
    private IProductUnitService productUnitService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("productUnit:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productUnitService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @GetMapping("/info/{unitId}")
    @RequiresPermissions("productUnit:info")
    public ResultMessage info(@PathVariable("unitId") Integer unitId) {
        ProductUnitEntity productUnit = productUnitService.getById(unitId);
        return ResultMessage.ok(productUnit);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("productUnit:save")
    public ResultMessage save(@RequestBody ProductUnitEntity productUnit) {
        ValidatorUtils.validateEntity(productUnit, AddGroup.class);
        productUnit.setCreator(getUserId().intValue());
        productUnit.setCreatorTime(DateUtils.now());
        productUnitService.save(productUnit);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("productUnit:update")
    public ResultMessage update(@RequestBody ProductUnitEntity productUnit) {
        ValidatorUtils.validateEntity(productUnit, UpdateGroup.class);
        productUnit.setEditor(getUserId().intValue());
        productUnit.setEditorTime(DateUtils.now());
        productUnitService.updateById(productUnit);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("productUnit:del")
    public ResultMessage del(@RequestBody Integer[] unitIds) {
        productUnitService.removeByIds(Arrays.asList(unitIds));
        return ResultMessage.ok();
    }

}
