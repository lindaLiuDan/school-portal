package com.info.modules.product.controller;

import com.info.common.annotation.Login;
import com.info.common.base.AbstractController;
import com.info.date.DateUtils;
import com.info.modules.product.entity.ProductImgEntity;
import com.info.modules.product.service.IProductImgService;
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
 * 商品图片表
 *
 * @author Gaosx
 * @email 
 * @date 2019-06-25 19:50:03
 */
@RestController
@RequestMapping("api/img")
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
    @Login
    @GetMapping("/list")
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
    @Login
    @GetMapping("/info/{imgId}")
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
    @Login
    @PostMapping("/save")
    public ResultMessage save(@RequestBody ProductImgEntity productImg) {
        ValidatorUtils.validateEntity(productImg, AddGroup.class);
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
    @Login
    @PostMapping("/update")
    public ResultMessage update(@RequestBody ProductImgEntity productImg) {
        ValidatorUtils.validateEntity(productImg, UpdateGroup.class);
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
    @Login
    @PostMapping("/delete")
    public ResultMessage delete(@RequestBody Integer[] imgIds) {
        productImgService.removeByIds(Arrays.asList(imgIds));
        return ResultMessage.ok();
    }

}
