package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductInfoEntity;
import com.info.modules.product.service.IProductInfoService;
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
 * 商品信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("product/info")
public class ProductInfoController extends AbstractController {

    @Autowired
    private IProductInfoService productInfoService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("productInfo:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @GetMapping("/info/{productId}")
    @RequiresPermissions("productInfo:info")
    public ResultMessage info(@PathVariable("productId") Integer productId) {
        ProductInfoEntity productInfo = productInfoService.getById(productId);
        return ResultMessage.ok(productInfo);
    }

    /**
     * 功能描述: 保存信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @PostMapping("/save")
    @RequiresPermissions("productInfo:save")
    public ResultMessage save(@RequestBody ProductInfoEntity productInfo) {
        ValidatorUtils.validateEntity(productInfo, AddGroup.class);
        productInfo.setCreator(getUserId().intValue());
        productInfo.setCreatorTime(DateUtils.now());
        productInfoService.save(productInfo);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @PostMapping("/update")
    @RequiresPermissions("productInfo:update")
    public ResultMessage update(@RequestBody ProductInfoEntity productInfo) {
        ValidatorUtils.validateEntity(productInfo, UpdateGroup.class);
        productInfo.setEditor(getUserId().intValue());
        productInfo.setEditorTime(DateUtils.now());
        productInfoService.updateById(productInfo);//全部更新
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @PostMapping("/del")
    @RequiresPermissions("productInfo:del")
    public ResultMessage del(@RequestBody Integer[] productIds) {
        productInfoService.removeByIds(Arrays.asList(productIds));
        return ResultMessage.ok();
    }

}
