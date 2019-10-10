package com.info.modules.product.controller;

import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductImgEntity;
import com.info.modules.product.service.IProductImgService;
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
 * 商品图片表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("product/img")
public class ProductImgController extends AbstractController {

    @Autowired
    private IProductImgService productImgService;


    /**
     * 功能描述: 列表查询
     *
     * @Params: * @param null
     * @Author:  Gaosx  
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @GetMapping("/list")
    @RequiresPermissions("productImg:list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productImgService.queryPage(params);
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
    @GetMapping("/info/{imgId}")
    @RequiresPermissions("productImg:info")
    public ResultMessage info(@PathVariable("imgId") Integer imgId) {
        ProductImgEntity productImg = productImgService.getById(imgId);
        return ResultMessage.ok(productImg);
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
    @RequiresPermissions("productImg:save")
    public ResultMessage save(@RequestBody ProductImgEntity productImg) {
        ValidatorUtils.validateEntity(productImg, AddGroup.class);
        productImg.setCreator(getUserId().intValue());
        productImg.setCreatorTime(DateUtils.now());
        productImgService.save(productImg);
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
    @RequiresPermissions("productImg:update")
    public ResultMessage update(@RequestBody ProductImgEntity productImg) {
        ValidatorUtils.validateEntity(productImg, UpdateGroup.class);
        productImg.setEditor(getUserId().intValue());
        productImg.setEditorTime(DateUtils.now());
        productImgService.updateById(productImg);//全部更新
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
    @RequiresPermissions("productImg:del")
    public ResultMessage del(@RequestBody Integer[] imgIds) {
        productImgService.removeByIds(Arrays.asList(imgIds));
        return ResultMessage.ok();
    }

}
