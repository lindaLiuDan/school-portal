package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCateEntity;
import com.info.modules.product.service.IProductCateService;
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
 * 商品品类信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 16:39:47
 */
@RestController
@RequestMapping("product/cate")
public class ProductCateController extends AbstractController {

    @Autowired
    private IProductCateService productCateService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("productCate:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productCateService.queryPage(params);
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
    @GetMapping("/info/{cateId}")
    @RequiresPermissions("productCate:info")
    public ResultMessage info(@PathVariable("cateId") Integer cateId) {
        ProductCateEntity productCate = productCateService.getById(cateId);
        return ResultMessage.ok(productCate);
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
    @RequiresPermissions("productCate:save")
    public ResultMessage save(@RequestBody ProductCateEntity productCate) {
        ValidatorUtils.validateEntity(productCate, AddGroup.class);
        productCate.setCreatorTime(DateUtils.now());
        productCateService.save(productCate);
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
    @RequiresPermissions("productCate:update")
    public ResultMessage update(@RequestBody ProductCateEntity productCate) {
        ValidatorUtils.validateEntity(productCate, UpdateGroup.class);
        productCate.setEditorTime(DateUtils.now());
        productCateService.updateById(productCate);//全部更新
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
    @RequiresPermissions("productCate:del")
    public ResultMessage del(@RequestBody Integer[] cateIds) {
        productCateService.removeByIds(Arrays.asList(cateIds));
        return ResultMessage.ok();
    }

}
