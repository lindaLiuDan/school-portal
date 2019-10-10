package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductCollcetionEntity;
import com.info.modules.product.service.IProductCollcetionService;
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
 * 商品收藏信息表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 16:39:47
 */
@RestController
@RequestMapping("product/collcetion")
public class ProductCollcetionController extends AbstractController {

    @Autowired
    private IProductCollcetionService productCollcetionService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 16:39:47
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("productCollcetion:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productCollcetionService.queryPage(params);
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
    @GetMapping("/info/{collId}")
    @RequiresPermissions("productCollcetion:info")
    public ResultMessage info(@PathVariable("collId") Integer collId) {
        ProductCollcetionEntity productCollcetion = productCollcetionService.getById(collId);
        return ResultMessage.ok(productCollcetion);
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
    @RequiresPermissions("productCollcetion:save")
    public ResultMessage save(@RequestBody ProductCollcetionEntity productCollcetion) {
        ValidatorUtils.validateEntity(productCollcetion, AddGroup.class);
        productCollcetion.setCreator(getUserId().intValue());
        productCollcetion.setCreatorTime(DateUtils.now());
        productCollcetionService.save(productCollcetion);
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
    @RequiresPermissions("productCollcetion:update")
    public ResultMessage update(@RequestBody ProductCollcetionEntity productCollcetion) {
        ValidatorUtils.validateEntity(productCollcetion, UpdateGroup.class);
        productCollcetion.setEditor(getUserId().intValue());
        productCollcetion.setEditorTime(DateUtils.now());
        productCollcetionService.updateById(productCollcetion);//全部更新
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
    @RequiresPermissions("productCollcetion:del")
    public ResultMessage del(@RequestBody Integer[] collIds) {
        productCollcetionService.removeByIds(Arrays.asList(collIds));
        return ResultMessage.ok();
    }

}
