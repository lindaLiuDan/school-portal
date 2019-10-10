package com.info.modules.product.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.product.service.IProductInfoService;
import com.info.modules.product.vo.ProductInfoVO;
import com.info.number.GenerateOrderNo;
import com.info.utils.ConfigConstant;
import com.info.utils.PageUtils;
import com.info.utils.ResultMessage;
import com.info.validator.Assert;
import com.info.validator.ValidatorUtils;
import com.info.validator.group.AddGroup;
import com.info.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Map;

/**
 * 商品信息业业务层
 *
 * @author Gaosx
 * @email
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("api/product")
public class ProductInfoController extends AbstractController {


    @Autowired
    private IProductInfoService productInfoService;


    /**
     * 功能描述: 分页查询商品信息-复合查询-》支持按照商品名称模糊查询，按照上架ID主键精确查询
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @GetMapping("/list")
    public ResultMessage list(@RequestParam Map<String, Object> params) {
        PageUtils page = productInfoService.queryPage(params);
        return ResultMessage.ok(page);
    }

    /**
     * 功能描述: 获取详细信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @GetMapping("/info/{productId}")
    public ResultMessage info(@PathVariable("productId") Integer productId) {
        Assert.isNull(productId,"商品ID主键不能为空",ConfigConstant.ERROR);
        ProductInfoVO infoVO = productInfoService.getProductInfoById(productId);
        return ResultMessage.ok(infoVO);
    }

    /**
     * 功能描述: 保存添加商品信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/save")
    public ResultMessage save(@Valid @RequestBody ProductInfoVO infoEntityForm) {
        ValidatorUtils.validateEntity(infoEntityForm, ConfigConstant.ERROR, AddGroup.class);
        infoEntityForm.setCreatorTime(DateUtils.now());
        infoEntityForm.setOnlineTime(DateUtils.now());
        infoEntityForm.setProductNo(GenerateOrderNo.getNumberNo());
        return productInfoService.saveProductInfo(infoEntityForm);
    }

    /**
     * 功能描述: 修改信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/update")
    public ResultMessage update(@Valid @RequestBody ProductInfoVO infoEntityForm) {
        ValidatorUtils.validateEntity(infoEntityForm, ConfigConstant.ERROR, UpdateGroup.class);
        infoEntityForm.setEditorTime(DateUtils.now());
        return productInfoService.updateProductInfo(infoEntityForm);//全部更新
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/del/{productId}")
    public ResultMessage delete(@PathVariable("productId") Integer productId) {
        Assert.isNull(productId, "商品ID不能为空", ConfigConstant.ERROR);
        productInfoService.delProductInfoById(productId);
        return ResultMessage.ok();
    }

    /**
     * 功能描述: 下架，上架商品信息等常规操作，
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/on/{productId}/{status}")
    public ResultMessage on(@PathVariable("productId") Integer productId,@PathVariable("status") Integer status) {
        Assert.isNull(productId, "商品ID不能为空", ConfigConstant.ERROR);
        return productInfoService.onProductInfo(productId,status);
    }

    /**
     * 功能描述: 删除信息
     *
     * @Params: * @param null
     * @Author: Gaosx
     * @Date: 2019-06-25 19:50:03
     * @Return:
     */
    @Login
    @PostMapping("/delList")
    public ResultMessage delete(@RequestBody Integer[] productIds) {
        Assert.isNull(productIds, "商品ID不能为空", ConfigConstant.ERROR);
        productInfoService.removeByIds(Arrays.asList(productIds));
        return ResultMessage.ok();
    }

}
